// module.exports에 함수 그대로 할당.
// 이 모듈을 불러와 사용하는 곳에서 함수를 호출하면 이 함수 안에 있는 코드 그대로 실행!

module.exports = function(router, passport){
  console.log("passport 라우터 호출됨.");

  // 홈 화면 : index.ejs 템플릿으로 홈 화면이 보이도록 함.
  router.route('/').get(function(req, res){
    console.log("/ 패스가 요청됨.");
    console.log("req.user의 정보");
    console.dir(req.user);

    // 인증 안된 경우
    if (!req.user) {
        console.log("사용자 인증 안된 상태임.");
        res.render('index.ejs', {login_success:false});
    } else {
        console.log("사용자 인증된 상태임.");
        res.render('index.ejs', {login_success:true});
    }
  });

  // 로그인 화면
  router.route('/users/login').get(function(req, res){
    console.log("/login 패스 요청됨.");
    res.render('login.ejs', {message: req.flash('loginMessage')});
  });

  // 로그인 인증
  router.route('/users/login').post(passport.authenticate('local-login', { //authenticate() 메소드를 호출해 사용자 인증 처리.
    successRedirect: '/users/profile',
    failureRedirect: '/users/login',
    failureFlash: true
  }));

  // 회원가입 화면
  router.route('/users/signup').get(function(req, res){
    console.log("/signup 패스 요청됨");
    res.render('signup.ejs', {message: req.flash('signupMessage')});
  });

  // 회원가입 인증
  router.route('/users/signup').post(passport.authenticate('local-signup', {
    successRedirect: '/users/profile',
    failureRedirect: '/users/signup',
    failureFlash: true
  }));

  // 프로필 화면 : 로그인 여부를 확인할 수 있도록 먼저 isLoggedIn 미들웨어를 실행함.
  router.route('/users/profile').get(function(req, res){
    console.log("/profile 패스 요청됨");

    // 인증된 경우 req.user 객체에 사용자 정보가 있는 것이고, 인증이 되지 않은 경우 req.user엔 false가 들어간다.
    // 인증이 되지 않은 경우
    if(!req.user){
      console.log("사용자 인증이 되지 않은 상태입니다.");
      res.redirect('/');
      return;
    }else{
      // 인증된 경우
      console.log("사용자 인증 성공!");
      console.dir(req.user);

      // user 객체가 배열 객체인지 아닌지를 먼저 체크함.
      if(Array.isArray(req.user)){
        res.render('profile.ejs', {user: req.user[0]._doc});
      }else{
        res.render('profile.ejs', {user: req.user});
      }
    }
  });

  // 로그아웃
  router.route('/users/logout').get(function(req, res){
    console.log("/logout 패스 요청됨");
    req.logout();
    res.redirect('/');
  });

  // 패스포트 : 페이스북 인증
  router.route('/users/facebook').get(passport.authenticate('facebook', {
    scope: 'email'
  }));

  // 패스포트 : 페이스북 인증 콜백
  router.route('/users/facebook/callback').get(passport.authenticate('facebook', {
    successRedirect: '/users/profile',
    failureRedirect: '/'
  }));
}
