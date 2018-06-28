import { FETCH_WEATHER } from "../actions/index";

export default function(state = [], action){
  // 보여주려는 게 한 row 당 여러 column이기 때문에, 초기 스테이트는 null이 아닌 배열로 대신해야 한다.

  switch(action.type){
    case FETCH_WEATHER:
    console.log(action.payload.data);
      /* return [ action.payload.data ]; */
      // 여러 도시들을 가지고 오기 때문에 배열으로 리턴해야 한다.
      // 현재 state의 현재 도시 배열에 이 새로운 도시를 추가해줘야 한다.
      // 기존 state에 추가해야 하고, 이를 완전히 바닥부터 대체하는 건 아니다! -> 따라서 state.push와 같은 방법으로 추가해줘야 한다.

      /* return state.push(action.payload.data); */
      // 근데 리액트에서 state의 값을 바꿀 때, this.state = ??? 이런 식으로 대입해준게 아니고, 
      // 오직 setState 함수를 통해서 state를 업데이트 해줬었다. ##### 이는 리덕스에서도 마찬가지로 작용한다! #####
      // 따라서 concat를 쓴다! 기존 배열을 바꾸는 것이 아니라, 새 배열을 만들어서 기존 것과 새로운 것을 포함시킨다.
      
      /* return state.concat([action.payload.data]); */
      // 이를 ES6 구문으로 변환해주자. ...state는 이전의 배열의 이전 엔트리들을 가져와서 새 배열에 집어 넣는다.

      return [action.payload.data, ...state];      
  }
  return state;
}