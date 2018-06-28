/* 파일 업로드 기능 만들기
 *
 * 웹 서버에 파일 자체를 업로드하거나 다운로드하는 경우
 * 이미지 파일을 다루는 경우도 많음!
 * 외장 모듈을 사용해 익스프레스에서 파일 업로드!

 * multer 미들웨어의 주요 속성과 메소드는?
 * 1. destination : 업로드한 파일이 저장될 폴더 지정.
 * 2. filename : 업로드한 파일의 이름 변경.
 * 3. limits : 파일 크기나 파일 개수 등의 제한 속성 설정.
 *
 */

var express = require('express');
var http = require('http');
var path = require('path');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

// 파일 업로드용 미들웨어 불러오기
var multer = require('multer');
var fs = require('fs');

// 클라이언트에서 ajax로 요청했을 때 CORS (다중 서버 접속) 지원
var cors = require('cors');

// 익스프레스 객체 생성
var app = express();

// 기본 속성 설정
app.set('port', process.env.PORT || 3000);

// body-parser를 이용해 application/x-www-form-urlencoded 파싱
app.use(bodyParser.urlencoded({ extended: false }))

// body-parser를 이용해 application/json 파싱
app.use(bodyParser.json());

//Try to install cookieParser separately. It because express no longer contains inside cookieParser
//app.use(express.cookieParser());
app.use(cookieParser());
app.use(expressSession({
  secret:'my key',
  resave:true,
  saveUninitialized:true
}));

app.use('/public', static(path.join(__dirname, 'public')));

/* 클라이언트에서 ajax로 요청했을 때 CORS(다중 서버 접속) 지원 */
app.use(cors());

// 라우터를 사용해 라우팅 함수 등록
var router = express.Router();

/* multer 미들웨어 사용 : 미들웨어의 순서가 중요하다. body-parser -> multer -> router 순서대로 진행되어야 함. */
// 저장 폴더, 파일 이름 지정.
var storage = multer.diskStorage({
  destination: function(req, file, callback){
    callback(null, 'uploads')
  },
  filename: function(req, file, callback){
    callback(null, file.originalname + Date.now())
  }
});

// 파일 크기나 개수 등 제한. 10개, 1G
var upload = multer({
  storage: storage,
  limits: {
    files: 10,
    fileSize: 1024 * 1024 * 2014
  }
});

router.route('/process/photo').post(upload.array('photo', 1), function(req, res){
  console.log("/process/photo 시작");

  try{
    // 업로드한 파일의 정보를 확인할 때는 req.files 배열의 원소 참조.
    var files = req.files;

    console.dir("#===== 업로드된 첫번째 파일 정보 =====#");
    console.dir(req.files[0]);
    console.dir("#=====");

    // 현재의 파일 정보를 저장할 변수 선언
    var originalname = '';
    var filename = '';
    var mimetype = '';
    var size = 0;

    if(Array.isArray(files)){
      // files가 배열에 들어가 있는 경우...
      console.log("배열에 들어있는 파일 갯수 : %d", files.length);

      for(var index = 0; index < files.length; index++){
        originalname = files[index].originalname;
        filename = files[index].filename;
        mimetype = files[index].mimetype;
        size = files[index].size;
      }
    }else{
      // 배열에 들어가 있지 않은 경우
      console.log("파일 갯수 : 1");

      originalname = files[index].originalname;
      filename = files[index].filename;
      mimetype = files[index].mimetype;
      size = files[index].size;
    }

    console.log("현재 파일 정보 : " + originalname + ', ' + filename + ', ' + mimetype + ', ' + size);

    // 클라이언트에 응답 전송하기
    res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
    res.write('<h3>파일 업로드 성공</h3>');
    res.write('</hr>');
    res.write('<p>원본 파일 이름 : ' + originalname + ' -> 저장 파일명 : ' + filename + '</p>');
    res.write('<p>MIME TYPE : ' + mimetype + '</p>');
    res.write('<p>파일 크기 : ' + size + '</p>');
    res.end();
  }catch(err){
    console.dir(err.stack);
  }
});

app.use('/', router);

// Express 서버 시작
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
