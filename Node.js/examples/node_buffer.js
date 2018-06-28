// Make buffer object by setting size
var output = 'Hi 1';
var buffer1 = new Buffer(10);
var len = buffer1.write(output, 'utf8');
console.log("First buffer's string : %s", buffer1.toSring());

// Make buffer object by string
var buffer2 = new Buffer('Hi 2', 'utf8');
console.log("Second buffer's string : %s", buffer2.toString());

// Check type
console.log("Buffer Object's type : %s", Buffer.isBuffer(buffer1));

// Make String data in Buffer object into variable of string type
var byteLen = Buffer.byteLength(output);
var str1 = buffer1.toString('utf8', 0, byteLen);
var str2 = buffer2.toString('utf8');

// Copy from first buffer's string to second buffer object
buffer1.copy(buffer2, 0, 0, len);
console.log("Second buffer's string after copied from first buffer : ", buffer2.toString('utf8'));

// Attach both buffer.
var buffer3 = Buffer.concat([buffer1, buffer2]);
console.log("String after attach both buffer : %s", buffer3.toString('utf8'));