// 패스포트 회원가입 설정

var LocalStrategy = require('passport-local').Strategy;

module.exports = new LocalStrategy({
  usernameField : 'email',
  passwordField : 'password',
  passReqToCallback : true
}, function(req, email, password, done){
  var paramName = req.param('name');
  console.log("passport의 local-signup 호출됨 : " + email + ", " + password + ", " + paramName);

  //User.findOne이 blocking되므로 async 방식으로 변경할 수도 있음.
  process.nextTick(function(){
    var database = req.app.get('database');
    database.UserModel.findOne({'email': email}, function(err, user){
      // 오류가 발생하면
      if(err) return done(err);

      // 기존에 이메일이 있다면
      if(user){
        console.log("기존에 계정이 있음.");
        return done(null, false, req.flash('signupMessage', '계정이 이미 있습니다.'));
      }else{
        // 모델 인스턴스 객체를 만들어서 저장함
        var user = new database.UserModel({'email': email, 'password': password, 'name': paramName});
        user.save(function(err){
          if(err) throw err;
          console.log("사용자 데이터 추가함.");
          return done(null, user);
        });
      }
    });
  });
});
