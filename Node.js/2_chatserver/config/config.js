module.exports = {
  server_port : 3000,
  db_url : 'mongodb://localhost:27017/local',

  db_schemas : [
    {
      file: './user_schema',
      collection: 'users6',
      schemaName: 'UserSchema',
      modelName: 'UserModel'
    }
  ],

  route_info : [
  ],

  facebook : {
    clientID : '157587564967045',
    clientSecret : '9d2da6615ba5ff180c1d16d98ef55b77',
    callbackURL : '/users/facebook/callback'
  }
}
