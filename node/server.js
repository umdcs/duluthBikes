/*globals require, __dirname, console*/
'use strict';

var express = require('express'),
	app = express(),
	server = require('http').createServer(app),
	io = require('socket.io').listen(server);



//note port is 23406 for testing
server.listen(23406);

app.get('/', function (req, res) {
	console.log('Dashboard request')
	res.sendFile(__dirname + '/maps.html');
});

io.sockets.on('connection', function (socket) {

	console.log('socket io connect')

	socket.emit('createMap', {
		div: '#map',
		lat: -12.133333,
		lng: -77.028333,
		zoom: 4
	});


	//everything after here isnt doing much its an attempt to create markers live
	//there needs to be a correcsponding function in the maps html for this stuff to work
	//im still looking into it.
	setInterval(function () {

		var icon, title;

		if ([true,false][Math.round(Math.random())]) {
			icon = 'https://maps.google.com/mapfiles/ms/icons/red-dot.png';
			title = 'player1';

			console.log('if true')

		} else {
			icon = 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png';
			title = 'player2';
			console.log('or false')
		}

		socket.emit('updateMarker', {
			title: title,
			lat: -12.043333 + (10 * Math.random()),
			lng: -77.028333 + (10 * Math.random()),
			isHit: [true,false][Math.round(Math.random())],
			radius: 100000,
			icon: icon,
			hitTimeout: 2000

		});
		console.log('inner interval function');
	}, 1000);

});
