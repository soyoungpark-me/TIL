import React from 'react';
import { Route, IndexRoute } from 'react-router';
// url을 맵핑해, 해당 컴포넌트를 보여주는 것을 결정하게 한다.
// route 오브젝트는 url과 컴포넌트 사이의 매칭을 정의하는 용도로 쓰인다.

/* IndexRoute
   - route 처럼 행동하지만, 해당 url을 자식이 아닌 부모인 path만 매치시킨다.
   - 매칭한 컴포넌트는 부모의 url에만 등장한다. 따라서 path를 입력해주지 않는다.
*/

import App from './components/app';
import PostIndex from './components/postIndex';
import PostNew from './components/postNew';
import PostShow from './components/postShow';

const Greeting = () => {
  return <div>Hey there!</div>
};

export default (                                  /* 해당 패스를 들어오면, 이 컴포넌트가 보여지게 된다.*/
  <Route path="/" component={App} >               {/* /           App           */}
    <IndexRoute component={PostIndex} />
    <Route path="post/new" component={PostNew} /> {/* /post/new   App, PostNew */}
    <Route path="post/:id" component={PostShow}/> {/* /post/:id   App, PostShow */}
    
    <Route path="greet" component={Greeting} />   {/* /greet      App, Greeting */}
    <Route path="greet2" component={Greeting} />  {/* /greet2     App, Greeting */}
    <Route path="greet3" component={Greeting} />  {/* /greet2     App, Greeting */}
  </Route >
);

// Greeting과 App은 형제가 아닌 부모, 자식 관계이다.
// 부모 라우트가 자식 라우트를 렌더링 할 때, this.props.children이 렌더링하는 데 필요하다.