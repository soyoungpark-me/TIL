import axios from 'axios';
// axios : ajax 리퀘스트를 브라우저에서 만들 수 있도록 하는 라이브러리
// 작동은 jQuery와 비슷하다.
// get으로 요청하면 프로미스를 반환한다. 프로미스는 데이터를 가지진 않는다. 프로미스 자체를 페이로드로 반환한다.

const API_KEY = 'e85b92a1e8565c2e562c62d2d1aec99f';
const ROOT_URL = `http://api.openweathermap.org/data/2.5/forecast?appid=${API_KEY}`;

export const FETCH_WEATHER = 'FETCH_WEATHER';
// string을 상수로 선언해서 쓰는데, 왜 굳이 이렇게 쓸까?
// >> 액션 생성자와 리듀서 사이의 액션 타입을 일정하게 유지하기 위해서.
//    이렇게 해놓으면, 스펠링이 틀렸을 때 오류를 잡기가 편하다. 문자열로 하면 잡기 쉽지 않음.
//    상수 내용을 바꾸기도 편하다. 이렇게 안 해놓으면 하나하나 바꿔줘야 하는데, 이렇게 해놓으면 선언문만 바꿔주면 됨.

export function fetchWeather(city){
  const url = `${ROOT_URL}&q=${city},kr`;
  const request = axios.get(url);
  
  return{
    type: FETCH_WEATHER,
    payload: request
  };

  // 리퀘스트가 끝나면 새로운 액션으로 같은 타입으로 디스패치 된다.
  // 이 때 페이로드는 풀어진 리퀘스트(프로미스가 해체된 것)이다. 리듀서는 프로미스가 아니라 데이터를 요구한다. 
  // 때문에 프로미스가 풀어질 때까지 기다렸다가, 이게 다 풀어지면 서버로부터 가져온 데이터를 리듀서에게 페이로드로 보낼 거라고 통지함!

  // >> 미들웨어를 사용해 프로미스와 액션을 프로미스가 리졸브 될 때 까지 멈춰 놓고, 그 다음 것을 실행한다.

  // >> 프로미스가 페이로드로 주어졌다면
  //    해당 액션을 멈추고, 프로미스가 리졸브 된 후에 새 액션을 만들어서 리듀서에게 보내준다. 
  // >> 프로미스가 페이로드로 주어지지 않았다면
  //    그냥 무시하고 리듀서로 보낸다.
}

