import React, {Component} from 'react';
// 중괄호의 의미 : react 라이브러리를 불러온 후 Component라고 부르는 변수를 프로퍼티 형태로 끌어와라!

// 클래스 컴포넌트는 상태나 정보 등을 저장할 때 사용한다. (smart)
// Ex) 지금 사용자가 키보드로 입력 하고 있고, 그 값은 이러하다. (상태와 정보)
// React의 Component 클래스로부터 상속받는다. 
class SearchBar extends Component {
  // state는 자바스크립트 객체로, 유저 이벤트를 저장하고 반응하는 데 쓰인다.
  // 컴포넌트들은 그 자체의 state를 가지고, state가 변하면 자동으로 렌더하고 자식들도 렌더하게 한다.
  // state를 쓰기 위해서는 생성자에서 초기화를 해줘야 한다.
  constructor(props) {
    super(props);

    this.state = {term: ''};
    // constructor 안에서만 this.state = 구문을 이용할 수 있다.
    // 일단 state를 초기화한 후에는 저렇게 쓸 수 없고, setState 함수를 써야 한다.
  }
  // 1. 이벤트 핸들러 선언 : 이벤트가 발생할 때마다 실행되는 함수. 브라우저에서 발생하는 이벤트르 감지한다.
  onInputChange(event) {
    // 모든 브라우저는 네이티브 요소인 인풋이나 버튼과 같은 요소에서 트리거를 받는다.
    // 언제나 이 e나 eventObject같은 친구들을 전달한다. 이벤트에 대한 정보가 들어 있다.
    // 화살표 함수로 바꿔서 필요 없음!
  }

  // JSX를 렌더링하기 위해 render 함수는 꼭 필요하다.
  render(){
    // 모든 클래스의 함수는 JSX를 렌더해줘야 화면에 보이게 된다.
    return (
      <div className="search-bar">
        <input 
          value={this.state.term} // term이 변했을 경우 변화를 감지해 새로 렌더링한다. state를 통해 업데이트 해야 반영된다고 볼 수 있다.
          // onChange={event => this.setState({term: event.target.value})} // 새로운 변화 값이 있어!!!
          onChange={event => this.onInputChange(event.target.value)}
        />
        {/* input 값 = state */}
      </div>
    );
    // 2. 이 이벤트 핸들러를 살펴보려는 요소에 전달. (검색 바 안에 input 요소가 바뀐다!)
    //    onChange는 리액트에서 정의된 프로퍼티! onClick, onChange 등등이 있다.
  }

  onInputChange(term){
    // 1. term을 state에 세팅하는 것
    this.setState({term})
    // 2. 콜백으로 인덱스에 새로운 검색어를 전달하는 것.
    this.props.onSearchTermChange(term);
  }
}

/* 이런게 필요 없고 그냥 출력만 해줄 거면 함수형 컴포넌트를 쓰면 된다. (dumb)
    const SearchBar = () => {
      return <input /> // React.createElement로 변환된다.
    }
*/

export default SearchBar;
// 모든 파일들은 서로 연결되어 있다고 선언해주지 않으면 상호작용 할 수 없다. 각자 따로 논다!
// default로 내보내면 내부 코드에서 명시한 SearchBar만 내보내진다.