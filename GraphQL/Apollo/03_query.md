# Apollo 써보기

 **출처 : [Apollo 공식 레퍼런스](https://www.apollographql.com/docs/react/)**

#### 목차



___

## Queries

- React에서 `useQuery`로 데이터 fetch해오는 법!
- GraphQL에서의 쿼리와 같은 문법 씀

___

### Executing a query

- `useQuery`한테 QraphQL query string 넘기면 됨!
- 컴포넌트가 렌더되면 `useQuery`는 **Apollo Client** 로부터 오브젝트를 리턴해옴
  - 여기에는 `loading`, `error`, `data` props가 포함됨!

- Hooks로 넘어오면서 조금 달라짐

**As-Is**
```javascript
const Dogs = ({ onDogSelected }) => (
  <Query query={GET_DOGS}>
    {({ loading, error, data }) => {
      if (loading) return 'Loading...';
      if (error) return `Error! ${error.message}`;

      return (
        <select name="dog" onChange={onDogSelected}>
          {data.dogs.map(dog => (
            <option key={dog.id} value={dog.breed}>
              {dog.breed}
            </option>
          ))}
        </select>
      );
    }}
  </Query>
);
```

**To-be**
```javascript
function Dogs({ onDogSelected }) {
  // Query 클래스 선언 없이 그냥 함수로 할당해버린다
  const { loading, error, data } = useQuery(GET_DOGS);

  if (loading) return 'Loading...';
  if (error) return `Error! ${error.message}`;

  return (
    <select name="dog" onChange={onDogSelected}>
      {data.dogs.map(dog => (
        <option key={dog.id} value={dog.breed}>
          {dog.breed}
        </option>
      ))}
    </select>
  );
}
```

+) `variables` 넘기기
```javascript
function DogPhoto({ breed }) {
  const { loading, error, data } = useQuery(GET_DOG_PHOTO, {
    variables: { breed },
  });
  ...
```

___

### Caching query results

- **Apollo Client**는 서버로부터 데이터를 fetch 해오자마자 자동적으로 캐싱한다.
- **캐싱된 데이터가 최신인지 어떻게 보장하지?**
=> 이걸 확실하게 하고 싶을 경우 다음 두 가지 방법이 있심

1. **Polling**
- 특정 주기로 계속 쿼리해서 서버랑 동기화함
- `pollInterval` : 주기 설정, 1000일 경우 1초마다 폴링. 0이면 폴링 안함.
- `useQuery` Hook이 반환하는 `startPolling`, `stopPolling` 함수로 제어 가능.

```javascript
function DogPhoto({ breed }) {
  const { loading, error, data } = useQuery(GET_DOG_PHOTO, {
    variables: { breed },
    skip: !breed,
    pollInterval: 500,
  });
  ...
```

2. **Refetching**
- 특정 유저 액션이 발생하면 쿼리 결과를 갱신하도록 함. (특정 인터벌이 X)
- `refetch` 함수한테 새로 `variables` 오브젝트를 넘겨줄 수도 있음.
- 최신 데이터임을 보장해주는 데 좋은 방법임!

```javascript
function DogPhoto({ breed }) {
  const { loading, error, data, refetch } = useQuery(GET_DOG_PHOTO, {
    ...
  });
  ...
  return (
    <button onClick={() => refetch()}>Refetch!</button> // 버튼 누르면 refetch!
  );
}
```

___

### Inspecting loading states

- refetching 중이거나 polling 중일 때는 `loading` 상태를 어떻게 관리할 건지 ...
- refetching을 해도 새로운 데이터를 받는 게 아니면 컴포넌트는 새로 렌더링하지 않음.

- `networkStatus` : 이럴 때, 새로 데이터를 받아온 것이라는 걸 알려주기 위한 프로퍼티!
  - `notifyOnNetworkStatusChange` 옵션을 `true`로 바꿔줘야 함!
  - 이 친구는 enum이고, 1부터 8까지 값을 가진다.
  - 여기서 4는 `refetch`, `polllling`, `pagination`의 값을 의미한다고 함!

```javascript
function DogPhoto({ breed }) {
  const { loading, error, data, refetch, networkStatus } = useQuery(
    GET_DOG_PHOTO,
    {
      notifyOnNetworkStatusChange: true,
    },
  );

  if (networkStatus === 4) return 'Refetching!';
  ...
}
```

___

### Inspecting error states

- `useQuery`에 `errorPolicy` 설정 옵션을 줘서 에러 핸들링을 커스터마이징 할 수 있음!
- 기본값으로 두면, **Apollo Client**가 모든 GraphQL 에러를 런타임 에러로 처리해버린다.

___

### Executing queries manually

- `useQuery` Hook을 콜한 컴포넌트를 React가 마운팅하거나 렌더링할 때, **Apollo Client**는 자동으로 특정 쿼리를 실행한다.
- 이렇게 말고... 특정 이벤트에 대한 결과값에 물려서 실행하고 싶으면..?
- `useLazyQuery` Hook을 쓰면 됨! 컴포넌트 렌더링이 아니라 이벤트에 의해 실행된다.

  - 이게 실행되면, 이거랑 관련된 쿼리들은 즉시 실행되지 않는다...
  - 대신에 언제든지 호출 가능한 함수들을 반환한다!

```javascript
function DelayedQuery() {
  const [dog, setDog] = useState(null);
  const [getDog, { loading, data }] = useLazyQuery(GET_DOG_PHOTO);
  // 결과값으로 함수를 반환함... (getDog)

  if (loading) return <p>Loading ...</p>;

  if (data && data.dog) {
    setDog(data.dog);
  }

  return (
    <div>
      {dog && <img src={dog.displayImage} />}
      <button onClick={() => getDog({ variables: { breed: 'bulldog' } })}>
        // 이 버튼이 눌리고 나서야 getDog이 실행된다!
        Click me!
      </button>
    </div>
  );
}
```

+) [`useQuery API 문서`](https://www.apollographql.com/docs/react/api/react-hooks/#usequery)
+) [`useQuery 옵션들`](https://www.apollographql.com/docs/react/essentials/queries/#options)