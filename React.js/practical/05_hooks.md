# 실전 리액트 프로그래밍

출처 : [실전 리액트 프로그래밍](https://book.naver.com/bookdb/book_detail.nhn?bid=15008532)

목차
1. 리액트 프로젝트 시작하기
2. ES6+를 품은 자바스크립트, 매력적인 언어가 되다
3. 중요하지만 헷갈리는 리액트 개념 이해하기
4. 리액트 코딩은 결국 컴포넌트 작성이다
5. **진화된 함수형 컴포넌트: 리액트 훅**
    - [리액트 훅 기초 익히기]()
    - [리액트 내장 훅 살펴보기]()
    - [클래스형 컴포넌트와 훅]()
    - [기존 클래스형 컴포넌트를 고려한 커스텀 훅 작성법]()
6. 리덕스로 상태 관리하기
7. 바벨과 웹팩 자세히 들여다보기
8. 서버사이드 렌저링 그리고 Next.js
9. 정적 타입 그리고 타입스크립트
10. 다가올 리액트의 변화: 파이버

___

## 진화된 함수형 컴포넌트: 리액트 훅

### 1. 리액트 훅 기초 익히기

- **훅의 등장 배경**
  - 훅 : 함수형 컴포넌트에서도 클래스형 컴포넌트의 기능을 사용할 수 있게 해줌!
  - 컴포넌트의 **상태값 관리**
  - 컴포넌트의 **생명 주기 함수 이용**
- 클래스형 컴포넌트의 한계
  - 서로 연관성이 없는 여러 로직을 **한 생명주기 메소드에 작성**
  - 등록만 하고 해제는 까먹기! (`componentDidMount`, `componentWillUnmount`)  
- **훅의 장점**
  - 재사용 가능한 로직을 쉽게 만들 수 있음!
  - 미리 만든 **커스텀 훅**을 조립해 새 훅 만들기~!
  - 같은 로직을 한 곳으로 모을 수 있다 > **가독성**
  - 훅은 단순한 함수라서 **정적 타입 언어로 정의하기 쉽다**

#### `useState` : 함수형 컴포넌트에 상탯값 추가

```javascript
function Profile() {
  const [name, setName] = useState('') 
  const [age, setAge] = useState(0)
  // 배열의 첫 번째 원소는 상탯값
  // 두 번째 원소는 상탯값을 변경할 수 있는 함수!
  // useState 안에는 초깃값이 들어간다!
  return (
    <div>
      <p>{`name is ${name}`}</p>
      <p>{`age is ${age}`}</p>
      <input type="text" value={name} onChange={e => setName(e.target.value)}/>
    </div>
  )
  // 위에서 setName이 아니라 setAge를 써도 사실상 name의 값이 바뀐다
}
```
- 여기서 여러 상태값을 하나의 객체로 담아서 쓰고 싶으면
```javascript
function Profile() {
  const [state, setState] = useState({ name: '', age: 0})

  return (
    <div>
      <p>{`name is ${state.name}`}</p>
      <p>{`age is ${state.age}`}</p>
      <input type="text" value={name} onChange={e => setState({ ...state, name: e.target.value})}/>      
    </div>
  )  
```
- **[주의]** `setState` 메소드는 이전 상탯값을 싹 지우기 때문에 `...state` 필요!

#### `useEffect` : 생명 주기 함수 이용하기
- 훅의 생명 주기 함수는 클래스형 컴포넌트의 생명 주기 함수에 1:1로 대응하진 않는다!
- 비슷한 기능을 한 곳으로 모으는 데 좋다
- 렌더링 결과가 **실제 돔에 반영된 후**에 호출됨! (`componentDidMount`, `componentDidUpdate`)

```javascript
function Profile({ userId }) {
  const [user, setUser] = useState(null)
  useEffect(() => {
    getUserApi(userId).then(data => setUser(data))
  },
  [userId]) // 이 배열 내의 값이 변경되는 경우에만 useEffect가 호출됨!


  /* 아래와 같이 별도 useEffect를 추가해서, 로직별로 코드를 모을 수 있다 */
  const [width, setWidth] = useState(window.innerWidth)
  useEffect(() => {
    // ...
  }, [])
}
```
- 두 번째 매개변수로 **빈 배열**을 넣으면, 
  - 컴포넌트가 **마운트 될 때**,
  - **언마운트 될 때**에만 반환된 함수가 호출된다 (`componentDidMount`, `componentWillUnmount`)

#### 훅 직접 만들기
- 리액트의 훅으로 **커스텀 훅**을 만들 수 있고, 커스텀 훅으로 또 커스텀 훅...
- 리액트 내장 훅처럼 `use`로 시작하는 게 좋다!

```javascript
function useWindowWidth() {
  const [width, setWidth] = useState(window.innerWidth)
  useEffect(() => {
    const onResize = () => setWidth(window.innerWidth)
    // ...
  }, [])

  return width
}
// 위와 같이 커스텀 훅을 선언 해두고, 쓸 때는...
function Profile() {
  const width = useWindowWidth() // 이렇게 가져다 쓸 수 있음!
  // width 값이 변경되면 다시 렌더링하게 된다!
  // ...
}
```
- 컴포넌트 마운트 여부 알려주기
```javascript
function useHasMounted() {
  const [hasMounted, setHasMounted] = useState(false)
  useEffect(() => setHasMounted(true), [])
  return hasMounted
}
```

#### [주의] 훅 사용 시 지켜야 할 규칙!

- **규칙 1 :** 하나의 컴포넌트에서 **훅을 호출하는 순서**는 항상 같아야 함!
  - 훅은 내부적으로 순서를 통해 관리되기 때문에, 순서를 어기면 이상하게 호출될 수 있음
  - 리액트가 상탯값을 구별할 수 있는 유일한 정보는 **훅이 사용된 순서**

- **규칙 2 :** 훅은 **함수형 컴포넌트** 혹은 **커스텀 훅** 안에서만 호출되어야 함!

___

### 2. 리액트 내장 훅 살펴보기

#### `useContext` : `Consumer` 컴포넌트 없이 콘텍스트 사용

- `Consumer` 컴포넌트를 사용하지 않고도 부모 컴포넌트로부터 전달된 **컨텍스트 데이터 활용 가능**
- 기존엔 `Provider`로 싸놓은 컴포넌트 내부에 `Consumer`로 싸놨어야 했음
```javascript
function ParentComponent() {
  return (
    <UserContext.Provider value={user}>
      <ChildComponent />
    </UserContext.Provider>
  )
}

function ChildComponent() {
  const user = useContext(UserContext)
  // ...
}

/* 기존 방법 */
function ChildComponent() {
  return (
    <div>
      <UserContext.Consumer>
        {user => (
          // ...
        )}
      </UserContext.Consumer>
    </div>
  )
}
```

#### `useRef` : 함수형 컴포넌트에서 돔 요소 접근
- 클래스형 컴포넌트의 `createRef` 함수 대체!

```javascript
function MyComponent() {
  const inputEl = useRef(null)
  const onClick = () => {
    // 호출되면 input 값이 focus를 가지게 됨!
    if (inputEl.current) {
      inputEl.current.focus()
    }
  }
  return (
    <div>
      <input ref={inputEl} type="text" />
      <button onClick={onClick} />
    </div>
  )
}
```

- 렌더링과 무관한 값을 저장해서 **멤버 변수로도 활용 가능**하다!
```javascript
function Profile() {
  const [age, setAge] = useState(20)
  const prevAgeRef = useRef(20) // 이전의 값을 저장해둔다!
  useEffect(() => {
    prevAgeRef.current = age // 렌더링이 끝나면 prevAgeRef는 최신 상태값을 가진다
  })

  const prevAge = prevAgeRef.current // 렌더링 시, prevAge는 이전 값을 가지게 된다
  // ...
}
```

#### `useMemo`, `useCallback` : 메모이제이션 훅

- 이전 값을 기억해서 성능을 최적화할 때 사용한다

- `useMemo` : 계산량이 많은 함수의 반환값을 재활용한다
  - 두 번째 매개변수로 주어진 배열의 값이 **변경되지 않으면 이전의 값 재사용**
  - 값이 변경되면 입력된 함수를 실행하고, **반환값 기억**

```javascript
function MyComponent({ v1, v2 }) {
  const value = useMemo(() => runExpensiveJob(v1, v2), [v1, v2])
  
  return <p>`{value is ${value}}`</p>
}
```
- `useCallback` : 렌더링 성능 개선!
  - 훅을 사용하면, 컴포넌트가 렌더링될 때마다 **함수를 새로 생성**할 수도 있다...
  - 속성값이 매번 변경되기 때문에, 함수의 재 생성이 불가피함!
  - 아래의 `onSave` 속성값은 `name`이나 `age` 값이 변경되지 않으면 **항상 같아야 함!**
    - `useCallback`으로 미리 만들어두면 그렇게 된다!
    - 두번째 인자로 주는 **배열의 값이 바뀌지 않으면** 이전에 생성한 함수가 **재사용됨**

```javascript
function Profile() {
  const [name, setName] = useState('')
  const [age, setAge] = useState('')
  const onSave = useCallback(() => saveToServer(name, age))

  return (
    <div>
      <UserEdit
        // onsave={() => saveToServer(name, age)} 이렇게 하지 말고!
        onsave={onSave} // 미리 선언한 것을 갖다씁시다!
        setName={setName}
        setAge={setAge} />
    </div>
  )
}
```

#### `useReducer` : 상태값을 리덕스처럼 관리하자

```javascript
const INITIAL_STATE = { name: 'empty', age: 0 }
function reducer(state, action) {
  switch (action.type) {
    case 'setName':
      return { ...state, name: action.name }
    case 'setAge':
      return { ...state, age: action.age }
    default:
      return state
  }
}
// 위와 같이 리듀서와 초기 상태값을 미리 만들어두고,

function Profile() {
  const [state, dispatch] = useReducer(reducer, INITIAL_STATE)
  // 만든 값들을 전달해 상탯값과 dispatch 함수를 반환받는다!
  // 여기서 dispatch 함수는 리덕스에서와 같이 작용한다

  return (
    <div>
      <input onChange={e => dispatch({ type: 'setName', name: 'e.currentTarget.value })} />
      // type과 action 쌍으로 넣어서 상탯값을 갱신한다!
    </div>
  )
}
```

- `useReducer`와 `Context API`를 같이 쓰면 상위 컴포넌트에서 하위 깊은 곳으로  **이벤트 처리 함수를 전달**할 수 있다!

```javascript
export const ProfileDispatch = React.createContext(null)
// dispatch 함수를 전달해 줄 컨텍스트 객체를 생성해두고,

function Profile() {
  const [state, dispatch] = useReducer(reducer, INITIAL_STATE)

  return (
    <div>
      <ProfileDispatch.Provider value={dispatch}>
        <SomeComponent /> 
      </ProfileDispatch.Provider>
    </div>
  )
  // 저렇게 하면, SomeComponent 하위의 모든 컴포넌트가 컨텍스트를 통해
  // dispatch 함수를 호출해 값을 변경할 수 있다~!
}
```

#### `useImperativeHandle` : 부모 컴포넌트에서 접근 가능한 함수 구현

- 이해를 못헀음

___

### 3. 클래스형 컴포넌트와 훅

- 훅을 쓰면, 몇개 빼고 클래스형 컴포넌트의 모든 기능을 쓸 수 있다
  - 제외 대상 : `getSnapshotBeforeUpdate`, `getDrivedStateFromError`, `componentDidCatch`

#### `constructor` : `useRef` 훅을 쓰자

- 클래스형 컴포넌트의 `constructor` 메소드는 

  - 주로 **초기 상탯값을 계산**하는 용도로 쓰거나
  - `componentDidMount` 보다 빨리 작업해야 할 때 쓴다

```javascript
function Profile({ firstName, lastName }) {
  const [name, setName] = useState(`${firstName} ${lastName}`)

  const isFirstRef = useRef(true) // 최초인지 체크할 멤버 변수!
  if (isFirstRef.current) { // 현재 true라면
    isFirstRef.current = false
    // ... todo
  }
}

// 커스텀 훅으로 빼버려도 됨!
function useOnFirstRender(func) {
  const isFirstRef = useRef(true)
  if (isFirstRef.current) {
    isFirstRef.current = false
    func()
  }
}

function Profile({ firstName, lastName }) {
  // ...
  useOnFirstRender(callApi) // 함수 자체를 넘겨버리자
}
```

#### `componentDidUpdate` : 얘도 `useRef` 훅 사용!

- 클래스형 컴포넌트에서 최초 렌더링 시 `componentDidMount`, 이후엔 `componentDidUpdate`가 호출됨!
- `useEffect`는 `componentDidMount`에도 호출된다. **후자에서만 쓰고 싶다면?**
- `componentDidMount`는 매개변수로 **이전 상탯값, 이전 속성값**을 받는다
  - 이건 리액트가 클래스형 컴포넌트의 상탯값, 속성값들을 저장해 제공해주기 때문
  - 이렇게 함수형 컴포넌트에서 쓰고 싶다면... 이전 값을 직접 `useRef`로 관리해야 함!

```javascript
function usePrevious(value) {
  const valueRef = useRef()

  useEffect(() => {
    valueRef.current = value // 렌더링 후 현재 값을 이전 값으로 만든다!
  }, [value]) // value 값이 바뀔때만 호출

  return valueRef.current // 이전 값 반환
}

// 이걸 활용하자면...

function Profile(props) {
  const [name, setName] = useState(props.name)
  const prevUserId = usePrevious(props.userId) // 이전값을 계속 들고있도록
  const isMountedRef = useRef(false) // 마운팅 된 이후인지 체크

  useEffect(() => {
    if (isMountedRef.current) { // 이미 마운팅 된 이후라면
      if (prevUserId !== props.userId) { // 이전, 현재값이 다르다면!!!
        // ... todo
      }
    } else {
      isMountedRef.current = true
    }
  })
}

// 아예 커스텀 훅으로 빼버리자면,,,
function useOnUpdate(func) {
  const isMountedRef = useRef(false)

  useEffect(() => {
    if (isMountedRef.current) {
      func()
    } else {
      isMountedRef.current = true
    }
  })
}
```

#### `getDerivedStateFromProps`

- 속성값 변경에 따라 **상탯값도 변경해줘야 할 때**
- **[주의]** 렌더 함수가 무한대로 호출될 수 있음에 주의!

```javascript
function SpeedIndicator({ speed }) {
  const [isFaster, setIsFaster] = useState(false)
  const [prevSpeed, setPrevSpeed] = useState(0)
  // 이전 속성값을 기억하고 있어야 한다

  if (speed !== prevSpeed) {
    setIsFaster(speed > prevSpeed) // 속성값에 의해 상탯값 변경
    // 상탯값이 변경되면서 리렌더링 발생!
  }
}
```

#### `forceUpdate` : 지양해야 함

```javascript
function MyComponent() {
  const [_, forceUpdate] = useReducer(s => s + 1, 0)

  function onClick() {
    forceUpdate() // 호출 시 상태값이 항상 변경되어 리렌더링 발생!
  }
}
```

___

### 4. 기존 클래스형 컴포넌트를 고려한 커스텀 훅 작성법

- 클래스형 컴포넌트에서는 훅이 동작하지 않는다!
- 커스텀 훅을 감싸는 **래퍼 (wrapper) 컴포넌트**를 만들어 클래스혀 컴포넌트에도 커스텀 훅의 로직을 **재사용**할 수 있음!


#### 커스텀 훅의 반환값이 없는 경우
- 반환값이 없는 훅은 **간단한 래퍼 컴포넌트**를 통해 사용 가능하다

```javascript
// 미리 만들어둔 훅을 쓰고싶은데, 반환값이 없다
function useDebounce({ callback, ms, args }) {
  useEffect(() => {
    const id = setTimeout(callback, ms)
    return () => clearTimeout(id)
  }, args)
}

// 아래와 같이 래퍼 컴포넌트를 만든다
function Debounce({ children, ...props }) {
  useDebounce(props) // 훅을 쓴다음에
  return children // 감싸야 하는 컴포넌트를 반환한다
}

// 감싸야 할 컴포넌트 예시임!
class Profile extends React.Component {
  state = { name: '', nameTemp: '' }
  render() {
    const { name, nameTemp } = this.state

    // children에는 div와 하위 친구들이,
    // props에는 callback, ms, args에 들어갈 것!
    return (
      <Debounce
        callback={() => this.setState(name: nameTemp)}
        ms={1000}
        args={[nameTmpe]}
      >
        <div>
          ...
        </div>
      </Debounce>
    )
  }
}
```

#### 커스텀 훅의 반환 값이 있는 경우

- 반환값이 있는 훅은 고차 컴포넌트나 렌더 **속성값으로 반환값을 전달**할 수 있다

```javascript
function HasMounted({ childred }) {
  const hasMounted = useHasMounted()
  return children(hasMounted) // 매개변수로 정보를 전달한다
}

function withHasMounted(Component) { // wrapping 해 줄 함수
  return function(props) {
    const hasMounted = useHasMounted()
    return <Component {...props} hasMounted={hasMounted} />
  }
}
ㅉ
class MyComponent extends React.Component {
  render() {
    const { hasMounted } = this.props // 속성값으로 정보를 받아온다
    return <p>{hasMounted ? 'yes' : 'no'}</p>
  }
}

export default withHasMounted(MyComponent) // 함수로 감싸버린 다음에 반환한다
```