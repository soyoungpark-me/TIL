module.exports = {
  server_port : 3000,
  db_url : 'mongodb://localhost:27017/local',

  db_schemas : [
    {
      file: './user_schema',
      collection: 'users6',
      schemaName: 'UserSchema',
      modelName: 'UserModel'
    },
    {
      file: './coffeeshop_schema',
      collection: 'coffeeshop',
      schemaName: 'CoffeeShopSchema',
      modelName: 'CoffeeShopModel'
    }
  ],

  route_info : [
    {file : './coffeeshop', path : '/coffeeshop/create', method : 'add', type : 'post'},
    {file : './coffeeshop', path : '/coffeeshop/list', method : 'list', type : 'post'},
    {file : './coffeeshop', path : '/coffeeshop/list/near', method : 'findNear', type : 'post'},
    {file : './coffeeshop', path : '/coffeeshop/list/within', method : 'findWithin', type : 'post'},
    {file : './coffeeshop', path : '/coffeeshop/list/circle', method : 'findCircle', type : 'post'}
  ],

  facebook : {
    clientID : '157587564967045',
    clientSecret : '9d2da6615ba5ff180c1d16d98ef55b77',
    callbackURL : '/users/facebook/callback'
  }
}
