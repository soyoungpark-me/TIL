var FacebookStrategy = require('passport-facebook').Strategy;
var config = require('../config');

// new FacebookStrategy를 사용해 인스턴스 객체를 만들어서 module.exports에 할당!
// 인스턴스 객체를 만들 때 전달하는 객체엔 clientID, clientSecret, callbackURL이 필요함.
// config에 설정해논 것을 갖다가 쓰면 됨.
// 두 번째 파라미터에는 페이스북 인증이 완료됐을 때 호출되는 콜백 함수가 들어감.
module.exports = function(app, passport){
  return new FacebookStrategy({
    clientID : config.facebook.clientID,
    clientSecret : config.facebook.clientSecret,
    callbackURL : config.facebook.callbackURL,
    profileFields: ['id', 'emails', 'displayName'] //This
  }, function(accessToken, refreshToken, profile, done){
    console.log("passport의 facebook 호출됨.");
    console.dir(profile);

    var options = {
      criteria : {'facebook.id' : profile.id}
    };

    var database = app.get('database');
    database.UserModel.load(options, function(err, user){
      if(err) return done(err);

      // user가 존재하지 않는다면
      if(!user){
        var user = new database.UserModel({
          name : profile.displayName,
          email : profile.emails[0].value,
          provider : 'facebook',
          facebook : profile._json
        });

        user.save(function(err){
          if(err) console.log(err);
          return done(err, user);
        });
      }else{
        return done(err, user);
      }
    });
  });
};
