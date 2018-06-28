var net = require('net');

var hostname = 'localhost';
var port = 3000;

//connect server
var client = new net.Socket();
client.connect(port, hostname, function(){
    console.log("Client Socket is connected -> " + hostname + ":" + port);
    client.write("Hello!");
});

client.on('data', function(data){
    console.log("Data from server -> " + data);
    
    //disconnect
    client.destroy();
});

client.on('close', function() {
    console.log("Client Socket is disconnected");
})