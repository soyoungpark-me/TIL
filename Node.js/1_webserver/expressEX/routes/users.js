var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.post('/login').post(function(req, res){
  console.log('/users/login 시작');

  var paramName = req.params.name;
  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;

  res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
  res.write('<h1>Express 서버에서 응답한 결과입니다.</h1>');
  res.write('<p>Param name : ' + paramName + '</p></br>');
  res.write('<p>Param id : ' + paramId + '</p></br>');
  res.write('<p>Param password : ' + paramPwd + '</p></br>');
  res.end();
});

module.exports = router;
