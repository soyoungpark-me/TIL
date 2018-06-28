import { combineReducers } from 'redux';
import PostReducer from './reducerPosts';
import { reducer as formReducer } from 'redux-form';
// redux-form에서 reducer를 불러오는데, 변수 이름을 formReducer로 정해준다.
// 다른 리듀서에서 불러오는 부분에 대해 이름이 충돌할 가능성을 없애기 위한 용도로 쓰인다.

/* 리덕스 폼 작동 과정
   1. 리덕스 폼을 셋업해서, '새로운 폼을 만들거야!'라고 요청한다.
   2. 폼을 생성하면 각 input이 생기는데, name을 붙여줄 수 있다.
   3. 리덕스 폼은 이 정보들을 가져와서 새 폼을 보고 이를 기록하고 응답한다.
   4. 각 폼을 다루고, 규칙을 각각의 필드에 설정해준다. 리덕스 폼에 의해 관리되기 때문에 우리가 건드릴 필요는 없다.
      인풋을 다루는 것을 리덕스 폼에게 이관한다. 전체 폼 오브젝트 자체를 전달한다.
   5. 리덕스 폼은 폼의 제출 데이터를 다루는 것을 담당한다.
   6. 폼의 프로퍼티 형태로 오브젝트를 돌려주는데, 폼이 제출되면 handleSubmit이 호출된다.
*/
const rootReducer = combineReducers({
  posts: PostReducer,
  form: formReducer
});

export default rootReducer;
