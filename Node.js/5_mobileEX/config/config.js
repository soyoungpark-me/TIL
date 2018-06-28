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
      file: './device_schema',
      collection: 'device',
      schemaName: 'DeviceSchema',
      modelName: 'DeviceModel'
    }
  ],

  route_info : [
    {file : './user', path : '/users/list', method : 'list', type : 'get'},
    {file : './device', path : '/device/create', method : 'adddevice', type : 'post'},
    {file : './device', path : '/device/list', method : 'listdevice', type : 'get'},
    {file : './device', path : '/device/register', method : 'register', type : 'post'},
    {file : './device', path : '/device/sendpush', method : 'sendpush', type : 'post'}
  ],

  facebook : {
    clientID : '157587564967045',
    clientSecret : '9d2da6615ba5ff180c1d16d98ef55b77',
    callbackURL : '/users/facebook/callback'
  }
}
