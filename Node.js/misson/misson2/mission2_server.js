var net = require('net');

//create Socket server
var server = net.createServer(function (socket){
    socket.name = socket.remoteAddress + ":" + socket.remotePort;
    console.log("Client is connected -> " + socket.name);
    
    socket.on('data', function(data){
        console.log("Data from client : " + data);
        
        socket.write(data + 'from server');
    });
    
    socket.on('end', function(){
        console.log("Client is disconnected -> " + socket.name);
    });
});

var port = 3000;
server.listen(port);

console.log('Socket server is running : ' + port);