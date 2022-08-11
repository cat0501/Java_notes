var http = require("http");

http.createServer(function(request, response) {
    response.writeHead(200, {
        "Content-Type" : "text/plain"
    });
    response.write("Welcome to Nodejs");
    response.end();
}).listen(8000, "127.0.0.1");

console.log("Creat server on http://127.0.0.1:8000/");