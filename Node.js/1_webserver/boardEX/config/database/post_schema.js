var SchemaObj = {};

SchemaObj.createSchema = function(mongoose) {
  // 글 스키마 정의
  var PostSchema = mongoose.Schema({
    title : {type : String, trim : true, 'default' : ''} // 글 제목
    ,contents : {type : String, trim : true, 'default' : ''} // 글 내용
    ,writer : {type : mongoose.Schema.ObjectId, ref : 'users6'} // 글 쓴 사람
    ,tags : {type : [], 'default' : ''} // 태그
    ,created_at : {type : Date, index : {unique : false}, 'default' : Date.now}
    ,updated_at : {type : Date, index : {unique : false}, 'default' : Date.now}
    ,comments : [{
      contents : {type : String, trim : true, 'default' : ''}
      ,writer : {type : mongoose.Schema.ObjectId, ref : 'users6'}
      ,created_at : {type : Date, 'default' : ''}
    }]
  });

  // 필수 속성에 대한 'required' validation
	PostSchema.path('title').required(true, '글 제목을 입력하셔야 합니다.');
	PostSchema.path('contents').required(true, '글 내용을 입력하셔야 합니다.');

  PostSchema.methods = {
    // 글을 저장하는 메소드
    savePost : function(callback){
      var self = this;

      this.validate(function(err){
        if(err) return callback(err);
        self.save(callback);
      });
    },
    // 댓글을 추가하는 메소드
    addComment : function(user, comment, callback){
      this.comment.push({
        contents : comment.contents,
        writer : user._id
      });
      this.save(callback);
    },
    // 댓글을 삭제하는 메소드
    removeComment : function(id, callback){
      var index = utils.indexOf(this.comments, {id : id});

      if(~index){
        this.comments.splice(index, 1);
      }else{
        return callback("ID [" + id + "]를 가진 댓글 객체를 찾을 수 없습니다.");
      }
      this.save(callback);
    }
  }

  PostSchema.statics = {
    // ID로 글 찾기
    load : function(id, callback){
      this.findOne({_id : id}).populate('writer', 'name provider email')
          .populate('comments.writer').exec(callback);
      //populate : ObjectId로 참조한 다른 컬렉션의 객체를 가져와 데이터를 채워 줌.
    },
    // 모든 데이터 조회하기.
    // pagination : 페이지 단위로 조회하도록 만드는 것.
    list : function(options, callback){
      var criteria = options.criteria || {};

      this.find(criteria).populate('writer', 'name provider email')
          .sort({'created_at' : -1}).limit(Number(options.perPage))
          .skip(options.perPage * options.page).exec(callback);
    }
  }

	console.log("PostSchema 정의함!");

	return PostSchema;
};

// module.exports에 PostSchema 객체 직접 할당
module.exports = SchemaObj;
