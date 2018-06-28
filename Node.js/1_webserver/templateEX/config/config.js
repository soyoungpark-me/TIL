module.exports = {
  server_port : 3000,
  db_url : 'mongodb://localhost:27017/local',

  db_schemas : [
    {
      file: './user_schema',
      collection: 'users3',
      schemaName: 'UserSchema',
      modelName: 'UserModel'
    }
  ],

  route_info : [
    //1. file : 라우팅 파일 지정
    //2. path : 클라이언트로부터 받은 요청 경로 지정
    //3. method : 라우팅 파일 안에 만들어 놓은 객체의 함수 이름 지정
    //4. type : 요청 방식 지정
    {
      file:'./users', path:'/users/login', method:'login', type:'post'
    }
	  ,{
      file:'./users', path:'/users/signup', method:'signup', type:'post'
    }
	  ,{
      file:'./users', path:'/users/list', method:'list', type:'get'
    }
  ]
}
