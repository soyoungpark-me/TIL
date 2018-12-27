import React from 'react';
import ReactDOM from 'react-dom';
import ReduxPromise from 'redux-promise';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';

import App from './components/app';
import reducers from './reducers';

const createStoreWithMiddleware = applyMiddleware(ReduxPromise)(createStore);

/* 미들웨어란?
   = 리듀서로 가기 전에 그 사이를 막거나 수정하거나 액션들을 통과할 수 있도록 '문지기' 역할을 한다.

   - 함수로 액션을 취하고, 액션 타입과 액션 페이로드 등 다른 요소에 의존해 액션을 전달하도록 선택한다.
   - 액션을 조작하거나 멈추거나... 등등의 작업을 리듀서로 가기 전에 처리한다.
   - 아예 리듀서로 못가게 하거나... 가더라도 액션을 조작하도록 하거나 등등...
   - 일종의 문지기라고 생각하자!
   - 누군가가 액션 생성자를 호출하고, 이 액션을 리듀서로 던지기 전에 문지기 역할을 한다.
   
   > 왜 쓰나요?
   - 액션을 가로챔을 통해 재미있는 일들을 할 수 있게 된다.
*/

ReactDOM.render(
  <Provider store={ createStoreWithMiddleware(reducers) }>
    <App />
  </Provider>
  , document.querySelector('.container'));
