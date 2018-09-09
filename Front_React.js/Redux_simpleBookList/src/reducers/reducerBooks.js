// 리듀서는 어플리케이션 레벨의 state 값을 설정해주는 역할을 한다.
// 두 개의 스텝 프로세스
// 1. 리듀서 생성
// 2. 만든 리듀서를 앱과 연결시키기

export default function() {
  return [
    {title: 'Javascript: The Good Parts', pages: 100},
    {title: 'Hello World', pages: 50},
    {title: 'Beautiful Cretures', pages: 150},
    {title: 'Eloquent Ruby', pages: 1}
  ]
}
