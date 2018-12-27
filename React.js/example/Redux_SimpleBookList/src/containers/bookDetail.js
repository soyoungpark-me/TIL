import React, {Component} from 'react';
import {connect} from 'react-redux';

class BookDetail extends Component{
  render(){
    // 렌더링해주기 전에 초기화 체크를 해준다. 안 해주면 null값으로 왔을 때 오류가 남!
    if(!this.props.book){
      return <div>선택된 책이 없습니다.</div>
    }

    return(
      <div>
        <h3>Details for:</h3>
        <div>Title: {this.props.book.title}</div>
        <div>Pages: {this.props.book.pages}</div>
      </div>
    );
  };
}

function mapStateToProps(state){
  return{
    book: state.activeBook
  };
}

export default connect(mapStateToProps)(BookDetail);