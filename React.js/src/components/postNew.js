import React, { Component, PropTypes } from 'react';
import { reduxForm } from 'redux-form';
import { Link } from 'react-router';
import { createPost } from '../actions/index';

class PostNew extends Component{
  // 기존 props는 부모가 자식에게 전달할 때 사용됐다면, context는 자식이 부모에게 정보를 요청하는 경우라고 함.
  // 이걸 찾을 때까지 부모 컴포넌트를들 뒤져본다! 어플리케이션의 모든 컴포넌트를 context 프로퍼티를 통해 이용할 수 있다는 것.
  // 근데 이건 자주 안 쓰는 게 좋고, 그냥 리액트 라우터 쓸 때만 쓴다고 가정하는 게 좋다고 함.
  // 리액트는 PostNew의 인스턴스가 생성될 때 다음과 같은 오브젝트를 해석힌다.
  static contextTypes = {
    router: PropTypes.object
  };

  onSubmit(props){
    this.props.createPost(props)
      .then(() => {
        this.context.router.push('/');
      });
    // createPost 액션이 발생하면 프로미스가 생성되고, 프로미스가 리졸브되면 글이 생성된다. << 이 타이밍이 리다이렉트하기 좋은 시점!  
    // this.context.router.push를 호출해 이동하고자 하는 path를 명시한다.
  }
  render(){
    const { fields: {title, categories, content}, handleSubmit } = this.props;
    // handleSubmit는 리덕스 폼으로부터 전달된 메소드이다.
    // 유저가 폼을 전송할 때 호출된다.
    // 폼을 validate 하기 좋은 위치임!
    // handleSubmit 함수는 최종 폼 프로퍼티를 호출하기 전에 액션 생성자를 필요로 한다.
    // 폼이 제출되면 handleSubmit이 호출되고, 액션 생성자를 선택할 수 있다.
    // >>> 해야할 것 : 액션 생성자를 만들어서 폼의 프로퍼티를 받아옴. 그리고 그 프로퍼티를 받아서 백엔드로 보내 새 블로그 포스트 생성!    
    
    return (
      <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
        {/* handleSubmit(this.props.createPost)
            폼 제출의 권한이 리덕스 폼의 handleSubmit 핸들러 함수에게 넘어간다. 
            폼이 제출되면 handleSubmit이 폼의 컨텐츠를 들고 호출된다.
            폼이 모두 타당하면, 액션 생성자를 폼의 컨텐츠와 함께 호출한다. 오브젝트로 각각의 인풋 값들을 들고 간다.
            오브젝트는 액션 생성자로, props 형태로 전달된다. 죽고만 싶다! 
            
            handleSubmit(this.onSubmit.bind(this)
            handleSubmit이 호출되면 onSubmit이 호출되고, 프로퍼티가 전달되는데...
            props 오브젝트는 content, title 등을 가진다. 이 프로퍼티를 받아 createPost 액션 생성자에게 전달한다. */}

        <h3>Create A New Post</h3>
        <div className={`form-group ${title.touched && title.invalid ? 'has-danger' : ''}`}>
                                                      {/* title이 터치 되었고 타당하지 않았을 경우 className에 has-denger를 추가하고,
                                                          그렇지 않을 경우에는 form-group만 보여준다. */}
          <label>Title</label>
          <input type="text" className="form-control" {...title} /> 
                                                      { /*오브젝트를 인풋으로 보낼 때, 다음과 같은 프로퍼티처럼 onChange=... 
                                                          구체적인 형태가 아니라 모든 프로퍼티를 오브젝트의 인풋 안에 보이게 한다. 
                                                          오브젝트를 키와 값으로 분리해서 전달한다.*/ }
          <div className="text-help">                 {/* validate를 만족하지 못했을 때만 나타난다. */}
            {title.touched ? title.error : ''}        {/* touched가 참일 경우 title.error, 거짓일 경우 '' */}                                               
          </div>
        </div>
        <div className={`form-group ${categories.touched && categories.invalid ? 'has-danger' : ''}`}>
          <label>Categories</label>
          <input type="text" className="form-control" {...categories} />
          <div className="text-help">                 
            {categories.touched ? categories.error : ''}        
          </div>
        </div>
        <div className={`form-group ${content.touched && content.invalid ? 'has-danger' : ''}`}>
          <label>Content</label>
          <textarea className="form-control" {...content} />
          <div className="text-help">                 
            {content.touched ? content.error : ''}        
          </div>
        </div>
        <button type="submit" className="btn btn-primary">전송</button>      
        <Link to="/" className="btn btn-danger">취소</Link>  
      </form>
    );
  };
}

function validate(values){
  const errors = {};

  if(!values.title){
    errors.title = "Enter a title";
  }

  if(!values.categories){
    errors.categories = "Enter a categories";
  }

  if(!values.content){
    errors.content = "Enter a content";
  }

  return errors;
}
// validate는 form config에 추가해야 한다.

export default reduxForm({
  // 여기에 폼의 구성을 전달한다.
  form: 'PostNewForm',
  // 그냥 폼의 이름. 컴포넌트 이름일 필요는 없다. 이름은 유니크해야 한다.
  // 인풋이 변할 때마다 인풋들의 새 값이 글로벌 어플리케이션 스테이트로 세팅된다.
  // 이름이 유니크하지 않으면 어떤 폼의 값이 변경된건지 알 수가 없다.
  fields: ['title', 'categories', 'content'], // 필드의 배열로, 인풋에 대한 구성을 생성해줘야 한다.
  validate
}, null, { createPost })(PostNew);

// 이렇게 묶어주고 나면, 리덕스 폼은 헬퍼로 주입되어 PostNew 컴포넌트의 this.props가 된다.
// 액션들을 컨테이너에 묶어줬던 것처럼 폼과 액션도 connect로 묶어줄 필요가 있다!
// 리덕스 폼은 connect와 같은 역할을 하는데, 액션 생성자를 컴포넌트로 주입시키고 컨테이너를 생성한다. 
// 리덕스 폼과 connect의 차이는, 리덕스 폼이 추가적인 요소를 가진다는 것이다. (폼의 구성 요소)

// connect   (mapStateToProps, mapDispatchToProps)
// reduxFOrm (form config, mapStateToProps, mapDispatchToProps)
//            mapStateToProps는 state를 필요로 하지 않을 시 null로 처리.