import React, {Component} from 'react';
import {connect} from 'react-redux';
// 1. Component 대신 Container를 사용한다.
//    컨테이너는 리액트 컴포넌트로, 리덕스에 의해 관리되는 스테이트에 직접적으로 연결할 수 있다.
//    리덕스의 데이터를 리액트의 컴포넌트에 주입시키는 역할을 한다.

import {selectBook} from '../actions/index';
import {bindActionCreators} from 'redux';
// 생성된 액션이 모든 리듀서에 흘러가는지 확인한다. 실제로 앱의 모든 리듀서로 들어가는지 확인!

class BookList extends Component{
  renderList(){
    return this.props.books.map((book) => {
      return(
        <li 
          onClick={() => this.props.selectBook(book)} //클릭할때마다 액션 생성자의 selectBook이 호출된다.
          key={book.title} 
          className="list-group-item">
          {book.title}
        </li>
      )
    })
  }

  render(){
    return(
      <ul className="list-group col-sm-4">
        {this.renderList()}
      </ul>
    )
  }
}

function mapStateToProps(state){
  // 2. 중!요!한!함수!
  //    어플리케이션 스테이트를 요소로 가져온다. 어플리케이션 스테이트를 가져와서 bookList 안의 props 형태로 만들어줌.
  //    어플리케이션 스테이를를 가져와 무엇이든지 함수로 반환하고, 컨테이너 안에 어떤 것을 props로 보여줄지 나타낸다.
  //    실질적으로 리액트와 리덕스를 연결해주는 역할을 한다.

  return {
    books: state.books
  }
}

function mapDispatchToProps(dispatch){
  // selectBook이 호출될때마다, 결과는 리듀서에 전달되어야 한다.
  // 액션 생성자가 호출된 결과가 dispatch 함수로 흘러가고 이 함수는 이러한 액션들을 받아온다.
  // 모든 액션들을 가져와서 어플리케이션 안에 있는 모든 리듀서에게 전달한다.
  // 결국 mapDispatchToProps 이 함수가 반환하는 것은 컨테이너의 props로 연결하는 역할을 한다.
  // 반환받는 것은 this.props로 이용할 수 있게 된다!

  return bindActionCreators({selectBook: selectBook}, dispatch);
}

// 3. 컴포넌트를 가져오고, mapStateToProps를 가져와서 컨테이너를 반환한다. 그리고 파일로부터 내보내준다.
//    export를 여기서 해준다.
export default connect(mapStateToProps, mapDispatchToProps)(BookList);

/*
   1) 어플리케이션 스테이트가 변할 때마다 (스테이트가 변하면) 컨테이너는 즉시 리렌더링해 새 리스트를 갖는다. 
   2) connect를 사용함으로써 컨테이너를 생성하는데, 스테이트가 변할 때마다 스테이트 함수 안의 객체가 컴포넌트의 props로 할당된다.
*/