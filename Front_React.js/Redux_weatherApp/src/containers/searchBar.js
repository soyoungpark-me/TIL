import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { fetchWeather } from '../actions/index'

class SearchBar extends Component{
  constructor(props){
    super(props);

    this.state = { term: '' };

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
    // 검색바의 인스턴스가 onInputChange 함수를 가지게 만들어준다.
    // 기존 함수를 가지고 바인딩해서, 그 함수를 자식 메소드로 (오버라이딩이라고 할 수도 있다고 함) 대체해버리는 것.
  }

  onInputChange(event){
    console.log(event.target.value);
    this.setState({ term: event.target.value });
    // 여기서 this에는 아무것도 없다. onInputChange 내부에서는 SearchBar 실제 컴포넌트에 접근할 수 없다.
    // onInputChange는 실제로 SearchBar의 인스턴스에 속한 메소드가 아니기 때문.
    // 실제로 state를 바꿔주는 경우에는 이 함수를 생성자에서 바인딩 해줘야 한다.
  }

  onFormSubmit(event){
    event.preventDefault();
    // 엔터를 눌렀을 때 폼이 제출되어서 자동으로 리프레쉬되는 현상을 막는다.
    // 이제 엔터를 눌렀을 때 해당 날씨의 API를 호출해서 정보를 가져와야 한다!
    // ##### 엔터나 검색 버튼을 누를 때 액션 생성자를 호출해 실제로 API 리퀘스트를 보낸다. #####

    this.props.fetchWeather(this.state.term);
    this.setState({ term: '' });
  }

  render(){
    return(
      <form onSubmit={ this.onFormSubmit }
        className="input-group">
        <input 
          placeholder="Get a five-day forecast in your favorite cities"
          className="form-control"
          value={ this.state.term }
          onChange={ this.onInputChange } />

        <span className="input-group-btn">
          <button type="submit" className="btn btn-secondary">SUBMIT</button>
        </span>
      </form>
    )
  }
}

function mapDispatchToProps(dispatch){
  return bindActionCreators({ fetchWeather }, dispatch);
}

export default connect(null, mapDispatchToProps)(SearchBar);
// 왜 첫 번째 인자가 null일까?
// > 컨테이너가 스테이트를 유지하고 있으니 리덕스가 신경 쓸 필요가 없다.
//   리덕스에서 컨테이너로 전달할 어플리케이션 스테이트가 없다.