var Entities = require('html-entities').AllHtmlEntities;

var addpost = function(req, res){
  console.log("post 모듈 안에 있는 addport 호출됨.");

  var paramTitle = req.body.title || req.query.title;
  var paramContents = req.body.contents || req.query.contents;
  var paramWriter = req.body.writer || req.query.writer;

  console.log("요청 파라미터 : " + paramTitle + ', ' + paramContents + ', ' + paramWriter);

  var database = req.app.get('database');

  // 데이터베이스 객체가 초기화된 경우
  if(database.db){
    // 1. 아이디를 사용해 사용자 검색
    database.UserModel.findByEmail(paramWriter, function(err, result){
      if(err){
        console.log("게시판 글 추가 중 오류 발생 : " + err.stack);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>게시판 글 추가 중 오류 발생</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();
        return;
      }

      if(result == undefined || result.length < 1){
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 [' + paramWriter + ']를 찾을 수 없습니다.</h1>');
        res.end();
        return;
      }

      var userObjectId = result[0]._doc._id;
      console.log("사용자 ObjectId : " + paramWriter + ' -> ' + userObjectId);

      // save()로 저장
      var post = new database.PostModel({
        title : paramTitle,
        contents : paramContents,
        writer : userObjectId
      });

      post.savePost(function(err, result){
        if(err) throw err;

        console.log("글 데이터 추가함.");
        console.log("글 작성", "포스팅 글을 생성했습니다. : " + post._id);

        return res.redirect('/post/show/' + post._id);
      });
    });
  }else{
    res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
		res.write('<h2>데이터베이스 연결 실패</h2>');
		res.end();
  }
}

var listpost = function(req, res){
  console.log("post 모듈 안에 있는 listport 호출됨.");

  var paramPage = req.body.page || req.query.page;
  var paramPerPage = req.body.perPage || req.query.perPage;

  console.log("요청 파라미터 : " + paramPage + ', ' + paramPerPage);

  var database = req.app.get('database');

  // 데이터베이스 객체가 초기화된 경우
  if(database.db){
    // 1. 글 목록
    var options = {
      page : paramPage,
      perPage : paramPerPage
    }

    database.PostModel.list(options, function(err, result){
      if(err){
        console.error("게시판 글 목록 조회 중 오류 발생 : " + err.stack);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>게시판 글 목록 조희 중 오류 발생</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();
        return;
      }

      if(result){
        console.dir(result);

        // 전체 문서 객체 수 확인하기
        database.PostModel.count().exec(function(err, count){
          res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});

          // 뷰 템플릿을 사용해 렌더링한 후 전송하기
          var context = {
            title : '글 목록',
            posts : result,
            page : parseInt(paramPage),
            pageCount : Math.ceil(count/paramPerPage),
            perPage : paramPerPage,
            totalRecords : count,
            size : paramPerPage
          };

          req.app.render('listpost', context, function(err, html){
            if(err){
              console.error("응답 웹 문서 생성 중 오류 발생 : " + err.stack);

              res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
              res.write('<h1>응답 웹 문서 생성 중 오류 발생</h1>');
              res.write('<p>' + err.stack + '</p>');
              res.end();
              return;
            }
            res.end(html);
          });
        });
      }
    });
  }
}

var showpost = function(req, res){
  console.log("post 모듈 안에 있는 showpost 호출됨.");

  // URL 파라미터로 전달됨.
  var paramId = req.body.id || req.query.id || req.params.id;

  console.log("요청 파라미터 : " + paramId);

  var database = req.app.get('database');

  // 데이터베이스 객체가 초기화된 경우
  if(database.db){
    // 1. 글 리스트
    database.PostModel.load(paramId, function(err, result){
      if(err){
        console.error("게시판 글 조회 중 오류 발생 : " + err.stack);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>게시판 글 조희 중 오류 발생</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();
        return;
      }

      if(result){
        console.dir(result);
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});

        // 뷰 템플릿을 사용해 렌더링한 후 전송
        var context = {
          title : '글 조회',
          posts : result,
          Entities : Entities
        };

        req.app.render('showpost', context, function(err, html){
          if(err) throw err;

          console.log("응답 웹 문서 : " + html);
          res.end(html);
        });
      }
    })
  }else{
    res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
		res.write('<h2>데이터베이스 연결 실패</h2>');
		res.end();
  }
}

// 이거 추가 안해주면 에러난다!!!
module.exports.addpost = addpost;
module.exports.listpost = listpost;
module.exports.showpost = showpost;
