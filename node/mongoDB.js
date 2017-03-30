

var mongojs = require('mongojs');
var url = 'mongodb://localhost:23405/ridehistory'; //URL needs to be changed
var collections = ['rides']; //Array of known collections ???

var assert = require('assert');

module.exports = function() {
	mongodb = mongojs(url, collections); //creation of the mongo connection
	console.log("Connected to Mongo DB - all functions are now active.");
	
	/** ********************************************************************
     * printDatabase - Prints the whole collection, for debugging purposes.
     * @param collectionName - the name of the collection
     * @param callback - need to provide a function to return the data
     */
	  mongodb.printDatabase = function(collectionName, callback) {
	
	// 
	// Collection look ups with find return a MongoDB 'cursor'. More info can be found here
	// https://docs.mongodb.com/v3.2/reference/glossary/#term-cursor
	// 
        var cursor = mongodb.collection(collectionName).find(function(err, docs) {
	    
            if(err || !docs) {
		console.log("Cannot print database or database is empty\n");
	    }
            else {
		console.log(collectionName, docs);
		callback(docs);
	    }
        });
	
};

//need to define all inser methods	


