import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { fetchPost, deletePost } from '../actions/index';
import { Link } from 'react-router';

class PostShow extends Component{
  static contextTypes = {
    router: PropTypes.object
  };

  componentWillMount(){
    this.props.fetchPost(this.props.params.id);
  }

  onDeleteClick(){
    this.props.deletePost(this.props.params.id)
      .then(() => {
        this.context.router.push('/');
      });
  }

  render(){
    const { post } = this.props;

    if(!post){
      return <div>Loading...</div>
    }

    return(
      <div>
        <Link to="/" className="btn btn-primary pull-xs-right">돌아가기</Link>
        <button 
          className="btn btn-danger pull-xs-right"
          onClick={this.onDeleteClick.bind(this)}>
          삭제하기
        </button>
        
        <h3>{post.title}</h3>
        <h6><b>Category : </b>{post.categories}</h6>
        <p>{post.content}</p>
      </div>
    )
  }
}

function mapStateToProps(state){
  return { post: state.posts.post };
}

export default connect(mapStateToProps, { fetchPost, deletePost })(PostShow);