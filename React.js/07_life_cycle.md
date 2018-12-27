# React.js 기초 다지기

#### 목차

1. 리액트란?
2. JSX란?
3. 컴포넌트란?
4. props와 state
5. state와 stateless
6. style 적용하기
7. **life cycle**

___

## 7. life cycle

### 라이프사이클 메소드란?

* 라이프사이클 메소드는 컴포넌트 life의 특정 시점에 호출되는 함수임.
* 이걸 이용하면 컴포넌트가 렌더링하기 전, 후 등등 다양한 시점을 컨트롤 할 수 있음.
* 이 함수들은 리액트에 의해 자동으로 호출된다.
* **마운팅 라이프사이클 이벤트**는 컴포넌트가 렌더되는 딱 첫 번째에만 활성화된다!



***

### 라이프사이클 메소드의 종류와 역할

- **렌더링**과 관련된 라이프사이클 메소드
  - `componentWillMount` :  컴포넌트가 렌더링 되기 전에 호출되는 함수

  - `render` : 렌더링하는 함수

  - `componentDidMount` : 컴포넌트가 렌더링 된 후

    > 얘는 쓸모가 많다는데... 예를 들면

    1. 리액트 앱이 AJAX로 fetch해서 API로부터 데이터를 끌어올 때, 여기서 AJAX 콜을 해준다.
    2. 리액트 앱을 외부 어플리케이션 (웹 API나 자바스크립트 프레임워크와 연결할 때) 좋다.
    3. setTimeout이나 setInterval같은 타이머를 놓기에도 좋다.

    


- **업데이트**와 관련된 라이프사이클 메소드!

  - `componentWillReceiveProps` : 컴포넌트 인스턴스가 업데이트 되었을 때, 새로 렌더링해주기 전에 호출된다.

    - 컴포넌트가 props를 받았을 때만 호출된다.
    - 자동으로 nextProps 라는 오브젝트를 인자로 받는데, 컴포넌트가 곧 받을 props의 preview 라고는 하는데 ~~잘 이해되진 않는다...~~
    - 새로 받은 props와 현재 props나 state를 비교해야 할 때 사용하면 좋다.

  - `shouldComponentUpdate` : `componentWillReceiveProps`가 호출된 후 자동으로 호출된다. 

    - 렌더링 되기 전에!

    - 얘는 true나 false를 리턴해야 한다.

      - **true** : 아무 일도 일어나지 않음.

      - **false** : 컴포넌트는 업데이트되지 않는다. 

        render를 포함한 이후 라이프사이클 메소드가 호출되지 않는다.