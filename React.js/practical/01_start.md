# 실전 리액트 프로그래밍

출처 : [실전 리액트 프로그래밍](https://book.naver.com/bookdb/book_detail.nhn?bid=15008532)

목차
1. **리액트 프로젝트 시작하기**
2. ES6+를 품은 자바스크립트, 매력적인 언어가 되다
3. 중요하지만 헷갈리는 리액트 개념 이해하기
4. 리액트 코딩은 결국 컴포넌트 작성이다
5. 진화된 함수형 컴포넌트: 리액트 훅
6. 리덕스로 상태 관리하기
7. 바벨과 웹팩 자세히 들여다보기
8. 서버사이드 렌저링 그리고 Next.js
9. 정적 타입 그리고 타입스크립트
10. 다가올 리액트의 변화: 파이버

___

## 리액트 프로젝트 시작하기

### 1. 리액트란 무엇인가
- 리액트는 페이스북에서 개발하고 관리하는 **UI 라이브러리**
- 단순하게 UI 기능만 제공한다!
  - 전역 상태 관리, 라우팅, 빌드 시스템 등은 개발자가 직접 구축해야 함...
  - `create-react-app` 으로 쉽게 구축 가능하다
- **편리성 및 장점** 
  - UI를 자동으로 업데이트해준다 : 각 프로그램의 상태가 변하면 UI도 변경된다.
  - 가상 돔을 활용해 UI를 빠르게 업데이트한다.


### 2. 리액트 개발 환경 직접 구축하기
- 나중에 해보자 ^^

### 3. create-react-app으로 시작하기
- **명령어**
  - 패키지 설치 : `npm install -g create-react-app`
  - 프로젝트 생성 : `create-react-app hello-world`
  - 프로젝트 시작 : `hello-world > npm start`
  - 주요 명령어
    - `npm start` : 개발 모드로 프로그램 시작 (HMR 반영, `development`)
    - `npm run build` : 배포 환경에서 사용할 파일 생성 (`production`)
    - `npm test` : 테스트 코드 실행 (`test`)
    - `npm run eject` : 숨겨져 있던 `create-react-app`의 내부 설정파일이 밖으로 노출됨
      - 방법 1 : `react-scripts` 프로젝트 포크 (자유도 높음)
      - 방법 2 : `react-app-rewired` 패키지 사용 (편함)

- **폴더 구조**
  - `src` : `index.js`로부터 연결된 모든 자바스크립트 파일과 CSS 파일은 여기 위치해야 함!
  - `public` : `index.html`에서 참조하는 파일들은 여기 위치해야 함!
  - `build` : 빌드 후 생성되는 static 파일들
    - `/static/css/main.{해시값}.chunk/css` : CSS 파일 위치
    - `/static/media` : `import` 키워드를 사용해 가져온 폰트, 이미지 등의 리소스 파일

- **모듈 비동기 호출**
```javascript
const TodoList extends Component {
  onClick = () => {
    import('./Todo.js').then(({ Todo }) => {
      // 이런 식으로 동적으로 호출할 수 있다.
      // 필요한 경우에만 내려받는다. (초기 호출...)
    })
  }
}
```

- **환경 변수**
  - **빌드 시점**에 환경 변수를 코드로 전달할 수 있음!
  - `development`, `test`, `production`의 기본 환경 변수들이 제공된다.

### 4. CSS 작성 방법 결정하기
- **CSS** 파일을 생성해 포함시키는 방법
- `css-module` 활용
- **Sass** 활용하기

### 5. 단일 페이지 애플리케이션 만들기
- **SPA** (Single Page Application) : 단일 페이지 애플리케이션!
  - 최초 요청 시 서버에서 첫 페이지를 처리하고, **이후 라우팅은 클라이언트에서 처리**
  - 기존 웹 페이지는 페이지 전환 시 마다 렌더링 결과를 서버에서 받아 **깜박임 발생**
  - SPA 구현을 위해서는 **브라우저 히스토리 API** 필요
- **브라우저 히스토리 API**
  - 자바스크립트에서 브라우저로 **페이지 전환 요청**을 보낼 수 있음 (브라우저 > 서버로는 X)
  - 브라우저의 뒤로 가기와 같은 사용자 요청도 자바스크립트에서 처리해야 함!
  - `pushState`, `replaceState`, `popState` : 히스토리 관리를 위한 **스택**이 존재한다.
    - `pushState` : 스택에 state를 추가한다 (`window.history.pushState('v1', '', '/page1')`)
    - `popState` : 스택이 비워질때까지 이전 상태로 돌아감
    - `replaceState` : 스택에 state를 쌓지 않고 가장 최신의 state로 대체
- 이렇게 일일이 하는건 귀찮으니 `react-router-dom` 사용!
  - 리액트로 SPA 만들 때 많이 사용한다!
  - 이 친구도 내부적으로 **브라우저 히스토리 API**를 사용함