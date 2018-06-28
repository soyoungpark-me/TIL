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
    id: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    name: {type: String, index: 'hashed'},
    age: {type: Number, 'default' : -1},
    created_at: {type: Date, index: {unique: false}, 'default': Date.now},
    updated_at: {type: Date, index: {unique: false}, 'default': Date.now}
  });
  // 스키마에 static 메소드 추가
  UserSchema.static('findById', function(id, callback){
    return this.find({id: id}, callback);
  });

  UserSchema.static('findAll', function(callback){
    return this.find({}, callback);
  });

  console.log("UserSchema 정의함!");

  return UserSchema;
};

// module.exports에 UserSchema 객체를 직접 할당한다.
module.exports = Schema;
