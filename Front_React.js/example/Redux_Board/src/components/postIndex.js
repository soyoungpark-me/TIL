// 인덱스 라우터에 포스트 리스트를 보여줌

import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link} from 'react-router';
// 링크 컴포넌트 네비게비션! 얘를 컴포넌트에 위치시키면, anchor나 a 태그로 만들어진다.
// anchor 태그를 정의하고, 실제로 a 태그처럼 작동한다. 아주 편하다!

// import { bindActionCreators } from 'redux'; 

import { fetchPosts } from '../actions/index';

class PostIndex extends Component {
  componentWillMount(){
    this.props.fetchPosts();
  }

  renderPosts(){
    return this.props.posts.map((post) => {
      return (
        <Link to={"post/" + post.id}>
          <li className="list-group-item" key={post.id}>
              <span className="pull-xs-right">{post.categories}</span>
              <strong>{post.title}</strong>
          </li>
        </Link>
      );
    });
  }

  render(){
    return(
      <div>
        <div classNale="text-xs-right">
          <Link to ="/post/new" className="btn btn-primary"> 글쓰기 </Link>
        </div>
        <h3>Posts</h3>
        <ul className="list-group">
          {this.renderPosts()}
        </ul>
      </div>
    );
  };
};

// function mapDispatchToProps(dispatch){
//   return bindActionCreators({ fetchPosts }, dispatch);
// }
// export default connect(null, mapDispatchToProps)(PostIndex);

function mapStateToProps(state){
  return { posts: state.posts.all }
}

export default connect(mapStateToProps, { fetchPosts })(PostIndex);
// 액션 생성자를 찾는 프로세스를 거치는 것 대신에, 
// fetchPosts 오브젝트를 직접 전달한다.