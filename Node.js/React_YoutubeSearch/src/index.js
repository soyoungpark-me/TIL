import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import _ from 'lodash';
// 1. node_modules에 설치된 애들 중 react를 찾고 React라는 변수에 할당한다.
//    - React : 컴포넌트를 생성하고 관리한다.
//    - ReactDOM : 실제로 컴포넌트를 가져와 DOM에 삽입하는 건 ReactDOM이다. 실제로 DOM과 상호작용을 함.

import SearchBar from './components/searchBar';
import VideoList from './components/videoList';
import VideoDetail from './components/videoDetail';
// 4. npm으로 설치된 애들이 아니라 내가 직접 만든 컴포넌트의 경우 상대 주소를 입력해서 바벨이 찾을 수 있도록 한다.

import YTSearch from 'youtube-api-search';
const API_KEY = 'AIzaSyAYGMYcNntnKSPKKiHqlSqebpvVWXl6P-8';


// 2. 새로운 컴포넌트 만들기. 이 컴포넌트는 HTML을 생성하게 될 것!
// const App = () => { // 화살표 함수는 리액트 내부 어디서나 작성할 수 있다. function 키워드를 쓰는 것과 종일!
//   return (
//     <div>
//       <SearchBar />
//     </div>
//   );
//   // JSX를 반환한다.
//   // 웹팩과 바벨은 JSX를 바닐라 자바스크립트로 변환해 브라우저가 해석할 수 있도록 한다.
//   // 이 JSX가 HTML으로 변환되어 DOM에 삽입되게 된다.
// }

class App extends Component{
  constructor(props){
    super(props);

    this.state = {
      videos: [],
      selectedVideo: null
    };

    // YTSearch({key: API_KEY, term: 'faker'}, (videos) => {
    //   // this.setState({videos: videos});
    //   // ES6 에서는 key와 value의 이름이 같을 경우 하나만 써도 인식됨!
    //   this.setState({
    //     videos,
    //     selectedVideo: videos[0]
    //   });
    //   // video state는 초기에 빈 배열이었다가 렌더링을 마친 후 데이터를 가져온다.
    //   // 데이터를 다 가져오기 전까지 0이 화면에 보이게 된다.
    //   // setState가 호출된 후에는 다시 한 번 렌더링이 된다.
    // });

    this.videoSearch('faker');
  }

  // 유투브 검색 자체를 리팩토링해 하나의 메소드로 만든다.
  // 이 메소드는 searchBar 컴포넌트로 전달된다.
  // 검색 바의 내용이 바뀌면 이 검색 함수가 호출되게 되고, 새 비디오를 가져온다.
  videoSearch(term){
    YTSearch({key: API_KEY, term: term}, (videos) => {
      this.setState({
        videos,
        selectedVideo: videos[0]
      });
    });
  }

  render(){
    const videoSearch = _.debounce((term) => {this.videoSearch(term)}, 300)
    // 너무 빨리 호출되니까 주기를 조절하기 위해 추가됨.
    // debounce는 같은 함수를 반환하는데, 반환되는 새로운 함수는 매 300 밀리초마다 호출된다.
    // 300 밀리초가 지날 때까지 기존 함수가 호출되지 않는다는 뜻.

    return(
      <div>
        <SearchBar onSearchTermChange={videoSearch} />
        <VideoDetail video={this.state.selectedVideo} />
        {/*
          <VideoDetail video={this.state.videos[0]}/>             
          위에 것과 같은 방법으로 해주게 되면, 처음으로 렌더할 때 this.state.video에는 값이 없다.
          따라서 undefined로 넘어가는데, 거기서 프로퍼티를 접근하려고 하니까 에러가 발생하게 되는 것. 
          해결 방법은 videoDetails에 명시!
        */}
        <VideoList 
          // VideoList가 onVideoSelect를 부르면 selectedVideo가 업데이트되는 로직!
          // 비디오를 가져와서 selectedVideo를 업데이트한다. (비디오를 받아와야 한다.)
          onVideoSelect={selectedVideo => this.setState({selectedVideo})}
          videos={this.state.videos} /* 자식에게 현재 props를 전달함 *//> 
      </div>
    )
  }
}

// 3. 컴포넌트는 자동으로 HTML 문서에 삽입되는 게 아님! 리액트에게 DOM에 넣어달라고 요청해야 함.
//    - 인수로 '컴포넌트 클래스'가 아니라 '컴포넌트 인스턴스'를 넘겨줘야 한다!
//    - 넘기기 전에 컴포넌트를 인스턴스화 해야 한다는 뜻.
//    - 두 번째 인수로, 페이지의 어떤 DOM에 집어넣을 건지를 정해줘야 한다. (타겟 컨테이너, 타겟 DOM 노드)
ReactDOM.render(<App />, document.querySelector('.container'));
