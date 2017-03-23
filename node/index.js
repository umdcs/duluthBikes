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


/*
 * varaible area for any required varaibles we might use
 */

var routeHistory = [];

// setting the port for the app system to use
app.set("port",8080);

// this section tells the body parser what type of data to expect
// for now it is mainly json
app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(bodyParser.json());

// this next section is for our GET, POST, PUT, DELETE routes
// the first one is the default dashboard route
//
app.get('/', function(request, response) {

	response.writeHead(200, {'Content-Type': 'text/html'});


	response.write('<!DOCTYPE html><head><title>Duluth Bike DashBoard</title></head><body>');
	response.write('<H1>Duluth Bike Route Data</H1>');

	response.write('JSON Data:');

	response.write(JSON.stringify(routeHistory));

	response.write('</body></html>');

	respnse.end();

	console.log('DashBoard request received!');
});


app.post('/postroute', function(request, response) {
	
	if (!request.body)return response.sendStatus(400);

	var routeData = {'lat':request.body.lat,
			 'lang':request.body.lang};

	var date = {'Date':"time stamp from phone"};

	routeHistory.unshift(date);
	routeHistory.unshift(routeData);

	console.log('Post Request: postroute');

	response.sendStatus(200);
});

app.get('/getRouteData', function(request,response) {
	console.log('Get Request: getRouteData');

	response.json(routeData);
});



// this last section is to start the app and start listening on 
// the given port for requests
//


app.listen(app.get("port"), function () {
	console.log('duluth bikes node listening on port: ', app.get("port"));
})


.get('/users', function(req, res) {
	
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
