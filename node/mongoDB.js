
//database url
var url = 'mongodb://127.0.0.1:50432/db'; 
var collections = ['rides']; 

var mongojs = require('mongojs');
var assert = require('assert');

module.exports = function() {
	var mongodb = mongojs(url, collections); //creation of the mongo connection
	
	mongodb.on('error', function (err) {
	console.log('database error', err)
	})

	mongodb.on('connect', function () {
	console.log('database connected DulBik')
	})
	
	/** ********************************************************************
     	* printDatabase - Prints the whole collection, for debugging purposes.
    	* @param collectionName - the name of the collection
     	* @param callback - need to provide a function to return the data
     	*/
	printDatabase = function(collectionName, callback) {
	
		// 
		// Collection look ups with find return a 
		// MongoDB 'cursor'. More info can be found here
		// https://docs.mongodb.com/v3.2/reference/glossary/#term-cursor
		// 
		
        	var cursor = mongodb.collection(collectionName).find(function(err, docs){
	    
            	if(err || !docs) {
			console.log("Cannot print database or database is empty\n");
	    	}
            	else {
			//console.log(collectionName, docs);
			
			callback(docs);
	    	}
        	});
	
	};

	printRides = function(colName,callback){

		var cursor = mongodb.collection(colName).find(function(err, docs){

		if(err||!docs){
			console.log("database empty\n");
		}
		else{
			callback(docs);
		}
		});
	};	

	insertRoute = function(routeData){
		mongodb.collection('RideHistory').save(
			{point:routeData},function(err,result){
			if(err||!result)console.log("point not saved");
			else console.log("point entered into RideHistory");
		});
	};

	insertFullRide = function(fullRide){
		mongodb.collection('FullRidesRecorded').save(
			{ride:fullRide},function(err,result){
			if(err||!result)console.log("ride not saved");
			else console.log("ride loged in FullRidesRecord");
		});
	};


	insertLatLng = function(LatLng){
		mongodb.collection('FullLatLngsRecorded').save(
			{latlng:LatLng},function(err,result){
			if(err||!result)console.log("latlng not saved");
			else console.log("latlng loged in DB");
		});
	};

	insertUsername = function(user){
		//if (mongodb.collection('UsersSaved').find(user)) console.log("USER IS FOUND IN DB"); Does not work yet. 
		//else {
 
			mongodb.collection('UsersSaved').save(
				{users:user},function(err,result){
				if(err||!result)console.log("user not saved");
				else console.log("user loged in User DB");
			});
		//}
	};

	printUsers = function(collectionName, callback) {

       	var cursor = mongodb.collection(collectionName).find(function(err, docs){
           	if(err || !docs) {
				console.log("Cannot print database or database is empty\n");	
    		} 
    		else {
				callback(docs);
	    	}
    	});
	
	};

	insertPicture = function(pic){
		mongodb.collection('PicturesSaved').save(
			{pictures:pic},function(err,result){
				if(err||!result)console.log("Picture not saved");
				else console.log("picture saves in picture DB")
			});
	};

	printPictures = function(collectionName, callback) {

       	var cursor = mongodb.collection(collectionName).find(function(err, docs){
	    
           	if(err || !docs) {
				console.log("Cannot print database or database is empty\n");	
    		} 
    		else {
				callback(docs);
	    	}
    	});
	
	};
	
	deleteAll = function(colName,callback){
		mongodb.collection(colName).remove({});
		callback(true);
	};

 	return mongodb;

};
