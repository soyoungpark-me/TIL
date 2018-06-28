module.exports = {
  server_port : 3000,
  db_url : '',
  db_schemas : [
    {
      file: './user_schema',
      collection: 'users6',
      schemaName: 'UserSchema',
      modelName: 'UserModel'
    },
    {
      file: './post_schema',
      collection: 'post',
      schemaName: 'PostSchema',
      modelName : 'PostModel'
    }
  ],

  route_info : [
    {file : './post', path : '/post/create', method : 'addpost', type : 'post'},
    {file : './post', path : '/post/list', method : 'listpost', type : 'get'},
    {file : './post', path : '/post/show/:id', method : 'showpost', type : 'get'}
  ],

  facebook : {
    clientID : '157587564967045',
    clientSecret : '9d2da6615ba5ff180c1d16d98ef55b77',
    callbackURL : '/users/facebook/callback'
  }
}
