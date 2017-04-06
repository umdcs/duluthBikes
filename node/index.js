/*
 * Our node server index.js file for use with our
 * duluthbikes app and the corresponding 
 * dashboard 
 *
 *
 */

// Body Parser for icoming http requests 

var bodyParser = require('body-parser');

var express = require('express');

// express framework for handling http requests
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);

/*
 * varaible area for any required varaibles we might use
 */

var routeHistory = [];

// setting the port for the app system to use
app.set("port",23405);

// this section tells the body parser what type of data to expect
// for now it is mainly json
app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(bodyParser.json());


//********MangoDB*******
// Connect to the mongo module
var mongodb = require('./mongoDB.js')();


app.get('/fullRide',function(req,res){
	var rides = printRides('FullRidesRecorded',function(result){
		res.write('<HTML><head><title>Duluth Bikes DashBoard</title></head><BODY>'
		+'<H1>Full Rides.</H1>');
		result.reverse();
		res.write(JSON.stringify(result,['ride']));
		res.send();
	});
	console.log('full ride request');
}); 

// this next section is for our GET, POST, PUT, DELETE routes
// the first one is the default dashboard route
//
app.get('/raw', function(request, response) {

	

	//when using Mongo
	var str = printDatabase('RideHistory', function(result) {
		response.write('<HTML><head><title>Duluth Bikes DashBoard</title></head><BODY>' 
		+ '<H1>Duluth Bikes Route Data.</H1>')
		result.reverse();
		response.write(JSON.stringify(result,['point','lat','lng'],'\n') + '</BODY></HTML>');
		response.send();
    	});
	
	console.log('DashBoard request received!');
});

app.get('/rides',function(request,response){
	response.sendFile(__dirname +'/ride.html');
});

app.get('/maps',function(req,res){
	res.sendFile(__dirname + 'maps.html');
});

app.get('/',function(req,res){
	res.sendFile(__dirname + 'duluthbikes.html');
});


app.post('/postroute', function(request, response) {
	
	if (!request.body)return response.sendStatus(400);

	var routeData = {'lat':request.body.lat,
			 'lng':request.body.lang};

	var date = {'Date':request.body.time};

	//Mongo
	insertRoute(routeData);
	//mongodb.insertDate(date);

	console.log('Post Request: postroute');

	response.sendStatus(200);
});

app.post('/postfinish',function(req,res){

	if(!req.body)return res.sendStatus(400);
	
	
	insertFullRide(req.body.ride);

	console.log('Post Full Ride');

	res.sendStatus(200);
});



app.get('/getRouteData', function(request,response) {
	console.log('Get Request: getRouteData');
	
		//Mongo
		//mongodb.getRoutes(some argument);

	response.json(routeData);
});



app.get('/deletealltherides',function(res,req){
	console.log('deleted all rides atempt');
	
	deleteAll('FullRidesRecorded',function(result){
		if(result==true)console.log("deleted all");
		else console.log("didnt work");
		});
});
	




io.on('connection',function(socket){
	console.log('a socket io connection');

	printFullRides('FullRidesRecorded',function(doc){

	socket.emit('FullRidesRecorded',doc)
	});
});



// this last section is to start the app and start listening on 
// the given port for requests
//



http.listen(app.get("port"),function(){
	console.log('duluth bikes node listening on port:',app.get("port"));
});


app.get('/users', function(req, res) {
	
		if (!req.body) return res.sendStatus(400)
	
		var jsonArray = [];
		
		for(var key in users) {
					jsonObject = {"username": key};
			        jsonArray.push(jsonObject);
				}
		console.log(JSON.stringify(jsonArray));
		res.send(JSON.stringify(jsonArray));
})


//Get protocol for all user objects
app.get('/users', function(req, res) {

	if (!req.body) return res.sendStatus(400)
	var jsonArray = [];
	
	for(var key in users) {
		jsonObject = {"username": key};
        jsonArray.push(jsonObject);
	}
	console.log(JSON.stringify(jsonArray));
	res.send(JSON.stringify(jsonArray));
})


// POST protocol for new user
	app.post('/users', function (req, res) {
		//If the JSON isn't parsed, return an error
		if (!req.body) return res.sendStatus(400)


	// Variables for user profile
		var quotesUser = JSON.stringify(req.body.Username);
		console.log("user to add = " + quotesUser);

		for(var key in users) {
			console.log("existing user: " + key);
			var quotesKey = "\"" + key + "\"";
			console.log("quotesKey = " + quotesKey);
			if (quotesKey == quotesUser) {
				console.log("Duplicate username");
				return res.sendStatus(500)
		}
	}
		

		var newUser = {
			"friends": [],
			"rides": []
		}
			console.log("userName = " + quotesUser);
			users[req.body.Username] = newUser;

	
			res.send(req.body);
	})


// DELETE protocol for user
	 app.delete('/users', function (req, res) {
	// If the JSON isn't parsed, return an error
 	    if (!req.body) return res.sendStatus(400)

	    	var userName = req.body.Username;

	    		delete users[userName];

	    			return res.send(req.body);
	    			})

//Get protocol for user profile objects
	app.get('/profile/:user', function(req, res) {
	var name = req.params.user;
	var profileObj;
	var found = false;

	for(var key in users){
		if(key == name){
			profileObj = JSON.stringify(users[key]);
			found = true;
		}
	}


	if(found)
		return res.send(profileObj)

		else

		return res.sendStatus(400)
	})
