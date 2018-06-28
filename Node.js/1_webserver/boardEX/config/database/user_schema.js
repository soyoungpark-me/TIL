/*
 * 1. Schema 객체 정의
 * 2. 그 안에 createSchema 속성 추가
 * 3. 이렇게 하면 이 모듈을 불러들일 때 createSchema() 함수를 호출해 mongoose 객체를 전달받을 수 있음!
 * 4. 마지막 부분에 module.exports에 Schema 객체를 할당!
 */

var crypto = require('crypto');
var Schema = {};

Schema.createSchema = function(mongoose){

  // 스키마 정의하기 (인덱스 추가한 버전)
  var UserSchema = mongoose.Schema({
    email: {type: String, 'default':''}
    , hashed_password: {type: String, 'default':''}
    , name: {type: String, index: 'hashed', 'default':''}
    , salt: {type:String}
    , created_at: {type: Date, index: {unique: false}, 'default': Date.now}
    , updated_at: {type: Date, index: {unique: false}, 'default': Date.now}

    // 하단에는 페이스북 인증과 관련된 정보가 들어감.
    // provider : facebook, twitter, google 처럼 어떤 서비스인지 들어감
    // authToken : 인증 서비스를 제공하는 서버에서 응답 받은 accessToken 값
    // facebook : 응답받은 사용자 객체 자체가 들어감.
    , provider: {type: String, 'default':''}
    , authToken: {type: String, 'default':''}
    , facebook: {}
  });

  // 비밀번호 암호화 작업
  // password를 virtual 메소드로 정의 : 몽고디비에 저장되지 않는 편리한 속성임.
  // 특정 속성을 지정하고 set, get 메소드를 정의함
	UserSchema.virtual('password').set(function(password) {
    this._password = password;
    this.salt = this.makeSalt();
    this.hashed_password = this.encryptPassword(password);
    console.log('virtual password 호출됨 : ' + this.hashed_password);
  }).get(function() { return this._password });

  // 스키마에 모델 인스턴스에서 사용할 수 있는 메소드 추가
	// 비밀번호 암호화 메소드
	UserSchema.method('encryptPassword', function(plainText, inSalt) {
		if (inSalt) {
			return crypto.createHmac('sha1', inSalt).update(plainText).digest('hex');
		} else {
			return crypto.createHmac('sha1', this.salt).update(plainText).digest('hex');
		}
	});

	// salt 값 만들기 메소드
	UserSchema.method('makeSalt', function() {
		return Math.round((new Date().valueOf() * Math.random())) + '';
	});

  // 인증 메소드 : 입력된 비밀번호와 비교 (true/false 리턴)
	UserSchema.method('authenticate', function(plainText, inSalt, hashed_password) {
		if (inSalt) {
			console.log('authenticate 호출됨 : %s -> %s : %s', plainText, this.encryptPassword(plainText, inSalt), hashed_password);
			return this.encryptPassword(plainText, inSalt) === hashed_password;
		} else {
			console.log('authenticate 호출됨 : %s -> %s : %s', plainText, this.encryptPassword(plainText), this.hashed_password);
			return this.encryptPassword(plainText) === this.hashed_password;
		}
	});

  var checkValidation = function() {
	    return (this.provider == '');
	};

  // 값이 유효한지 확인하는 함수 정의
	var validatePresenceOf = function(value) {
		return value && value.length;
	};

	// 저장 시의 트리거 함수 정의 (password 필드가 유효하지 않으면 에러 발생)
	UserSchema.pre('save', function(next) {
		if (!this.isNew) return next();

		if (checkValidation() == null && !validatePresenceOf(this.password)) {
			next(new Error('유효하지 않은 password 필드입니다.'));
		} else {
			next();
		}
	});

  // 입력된 칼럼 값이 있는지 확인. path 메소드는 칼럼 값을 확인하는 데 사용.
  // 이 메소드를 호출할 때 칼럼의 이름을 전달하면 됨.
  // 칼럼 값을 확인한 후 validate() 메소드를 호출하면 그 값이 유효한지를 체크함.
  UserSchema.path('email').validate(function(email){
    return email.length;
  }, 'email 칼럼의 값이 없습니다.');

  // UserSchema.path('hashed_password').validate(function(hashed_password){
  //   return hashed_password.length;
  // }, 'hashed_password 칼럼의 값이 없습니다.');

  // 스키마에 static 메소드 추가
  UserSchema.static('findByEmail', function(email, callback){
    return this.find({email: email}, callback);
  });

  UserSchema.static('findAll', function(callback){
    return this.find({}, callback);
  });

  UserSchema.static('load', function(options, callback) {
		options.select = options.select || 'name';
	    this.findOne(options.criteria)
	      .select(options.select)
	      .exec(callback);
	});

  // 모델을 위한 스키마 등록
	mongoose.model('User', UserSchema);

  console.log("UserSchema 정의함!");
  return UserSchema;
};

// module.exports에 UserSchema 객체를 직접 할당한다.
module.exports = Schema;
