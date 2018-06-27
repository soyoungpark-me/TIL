import React from 'react';
import ReactDOM from 'react-dom';
import promise from 'redux-promise';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import { Router, browserHistory } from 'react-router';

/*
  router는 url이 바뀔 때 어떤 컴포넌트를 렌더링할 건지 결정한다.
  History는 리액트 라우터에게 어떻게 url이 바뀌었는지를 전달한다.
  browserHistory : 여러 History 중 한 종류로, url이 업데이트 될 때 리액트 라우터는 프로토콜 이후 모든 것을 해석한다.
    - url 중 path 부분이 바뀌면 리액트 라우터에게 업데이트 하라고 전해준다.
    - http://blog.com/posts/5 => posts/5
  hashHistory : 해시 심볼 이후의 모든 것을 감지할 수 있다. 해시(#)
    - http://blog.com/#posts/5 => #posts/5
  memoryHistory : 실제로 자주 사용하지는 않는다.
*/

import reducers from './reducers';
import routes from './routes';
// 정의 한 라우터를 불러온다.

const createStoreWithMiddleware = applyMiddleware(promise)(createStore);

ReactDOM.render(
  <Provider store={createStoreWithMiddleware(reducers)}>
    <Router history={browserHistory} routes={routes} />
  </Provider>
  , document.querySelector('.container'));
