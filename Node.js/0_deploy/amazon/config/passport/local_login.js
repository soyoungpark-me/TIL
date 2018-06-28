// 패스포트 로그인 설정
// 첫번째 인자는 Strategy 이름, 두 번째는 인증 방식을 정의한 객체.
// 여기서 이름을 local-login으로 설정했기 때문에 추후 passport.authenticate() 메소드를 사용할 때도 똑같은 이름을 써야 함.

var LocalStrategy = require('passport-local').Strategy;

// 스트레티지 객체를 사용해 new 연산자로 만든 인스턴스 객체를 module.exports에 그대로 할당.
// passport.js 파일에서 이 모듈 파일을 불러와 그대로 use() 메소드의 파라미터로 전달할 수 있습니다.
module.exports = new LocalStrategy({
  usernameField : 'email',
  passwordField : 'password',
  passReqToCallback : true
}, function(req, email, password, done){
  console.log("passport의 local-login 호출됨 : " + email + ', ' + password);

  var database = req.app.get('database');
  database.UserModel.findOne({'email': email}, function(err, user){
    if(err) { return done(err); }

    // 등록된 사용자가 없는 경우
    if(!user){
      console.log("계정이 일치하지 않음.");
      return done(null, false, req.flash('loginMessage', '등록된 계정이 없습니다.'));
    }

    // 비밀번호를 비교했는데 맞지 않는 경우
    var authenticated = user.authenticate(password, user._doc.salt, user._doc.hashed_password);

    if(!authenticated){
      console.log("비밀번호가 일치하지 않음.");
      return done(null, false, req.flash('loginMessage', '비밀번호가 일치하지 않습니다.'));
    }

    // 로그인에 성공한 경우
    console.log("계정과 비밀번호가 일치함.");
    return done(null, user);
  });
})
