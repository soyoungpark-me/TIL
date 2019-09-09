# Apollo 써보기

 **출처 : [Apollo 공식 레퍼런스](https://www.apollographql.com/docs/react/)**

#### 목차



------

## Querying local state

- 로컬 데이터 끌어오는 건 GraphQL 서버에 쿼리하는 거랑 비슷함!
- 차이는 `@client` directive만 붙여주면 됨 (**Apollo Client cache**나 `local resolver`에서 끌어와야 된다고 알려줘야 하니까)

___

### 쿼리 예제

```javascript
const GET_TODOS = gql`
  {
    todos @client { // @client를 붙여서 얘들이 로컬에서 끌고와야 하거나 실행되어야 한다는 걸 알랴줌!
      id
      completed
      text
    }
    visibilityFilter @client
  }
`;

function TodoList() {
  const { data: { todos, visibilityFilter } } = useQuery(GET_TODOS);
  return (
    <ul>
      {getVisibleTodos(todos, visibilityFilter).map(todo => (
        <Todo key={todo.id} {...todo} />
      ))}
    </ul>
  );
}
```

- 근데 만약 이런 상황에서 초기에 설정된 캐시 정보가 없다면... 에러난다!

___

### Initializing the cache
- 캐시 초기화가 안되어 발생하는 오류를 막자
- `cache.writeData` 함수를 쓰면 된다.
- 여기서 설정해둔 초기 상태는 앞으로 쿼리하고자하는 모양과 일치해야 함!

```javascript
const cache = new InMemoryCache();
const client = new ApolloClient({
  cache,
  resolvers: { /* ... */ },
});

cache.writeData({
  data: {
    todos: [], // 이렇게 빈 칸이어도 되니까 먼저 모양만 잡아두자.
    visibilityFilter: 'SHOW_ALL',
    networkStatus: {
      __typename: 'NetworkStatus',
      isConnected: false,
    },
  },
});
```

___

### Reset the store
- 앱 내에서 스토어를 비워주고 싶을 때... (ex. 유저가 로그아웃함)
- `client.resetStore`를 호출하면 (앱 내 아무데서나) 로컬 캐시를 다시 초기화한다.
- `client.onResetStore`로 콜백 함수를 호출해서, 여기서 `cache.writeData`를 다시 써줄 수도 있음!

```javascript
const data = {
  todos: [],
  visibilityFilter: 'SHOW_ALL',
  networkStatus: {
    __typename: 'NetworkStatus',
    isConnected: false,
  },
};

cache.writeData({ data });
client.onResetStore(() => cache.writeData({ data }));
```

___

### Local data query flow
- `@client` directive가 포함된 쿼리가 실행되면, **Apollo Client**는 다음 스텝을 거친다...
```javascript
const GET_LAUNCH_DETAILS = gql`
  query LaunchDetails($launchId: ID!) {
    launch(id: $launchId) {
      isInCart @client
      site
      rocket {
        type
      }
    }
  }
`;
```

- 이 쿼리는 로컬과 서버 필드들이 짬뽕되어 있다.
- 이 상황에서 `@client`가 붙은 `isInCart`가 어떻게 땡겨지는지 보자!

1. `isInCart`와 관련된 리졸버 함수가 있는지 찾는다

    - `ApolloClient` 생성자의 `resolvers` 인자라든가...
    - **Apollo Client**의 `setResolvers`, `addResolvers` 메소드라든가...

2. 만약 맞는 리졸버 함수가 없으면, **Apollo Client cache**를 확인해서 `isInCart` 값이 있는지 찾는다.

3. `@client`가 안 붙은 다른 값들은 쿼리하기 위해 서버로 요청하게 된다.

4. 결과가 나온 뒤에는 두 값이 merge되어서 나간다.

> 이건 GraphQL의 Query, Mutation, Subscription에 모두 적용된다!

___

### Handling `@client` fields with resolvers

- 이건 계속 얘기하는데... 로컬 리졸버는 서버 리졸버랑 비슷하다~~
- `@client` directive가 붙은 애들에 마킹된 리졸버 함수를 미리 정의해놓고 쓴다!

```javascript
const GET_CART_ITEMS = gql`
  query GetCartItems {
    cartItems @client // 이게 붙었으니까 로컬에서 뒤질 것
  }
`;

const cache = new InMemoryCache();
cache.writeData({
  data: {
    cartItems: [], // 깨지면 안되니까 미리 초기화 해두기
  },
});

const client = new ApolloClient({
  cache,
  link: new HttpLink({
    uri: 'http://localhost:4000/graphql',
  }),
  resolvers: { // 리졸버를 생성자에서 등록해줬음 > Apollo Client의 internal resolver map에 등록됨
    Launch: { // Launch 라는 이름의 GraphQL 오브젝트 타입으로 등록된다
      isInCart: (launch, _args, { cache }) => { // 로컬 리졸버 정의
        // 여기서 launch는 서버에서 반환된 결과값... 여기서 id를 캐올 수 있다
        // 두 번째 인자인 argument는 딱히 줄 게 없어서 저렇게 skip
        // context에는 지금 캐시 볼 거니까 캐시를 넣어준다 > 데이터 끌어올 때 캐시를 바로 보게 됨!

        const { cartItems } = cache.readQuery({ query: GET_CART_ITEMS }); // 캐시에서 가져온다음에
        return cartItems.includes(launch.id); // true, false로 반환하게 될 것 (id를 가진 값이 있는지)
      },
    },
  },
});

const GET_LAUNCH_DETAILS = gql`
  query LaunchDetails($launchId: ID!) {
    launch(id: $launchId) {
      isInCart @client
      site
      rocket {
        type
      }
    }
  }
`;
```

**Async local resolvers**

- 해당 로컬 리졸버가 `Promise`를 반환하도록 만들 수도 있다!

```javascript
const client = new ApolloClient({
  cache: new InMemoryCache(),
  resolvers: {
    Query: {
      async cameraRoll(_, { assetType }) { // return Promise
        try {
          const media = await CameraRoll.getPhotos({
            first: 20,
            assetType,
          });

          return {
            ...media,
            id: assetType,
            __typename: 'CameraRoll',
          };
        } catch (e) {
          console.error(e);
          return null;
        }
      },
    },
  },
});
```
___

### Handling `@client` field with cache

- `@client` directive에 항상 `local resolver`가 따라다니는 건 아니다.
- 매칭되는 값을 캐시에서 바로 빼올 수도 있다.

```javascript
const cache = new InMemoryCache();
const client = new ApolloClient({
  cache,
  link: new HttpLink({ uri: "http://localhost:4000/graphql" }),
  resolvers: {}, // 미리 정의한 로컬 리졸버는 없다
});

cache.writeData({
  data: {
    isLoggedIn: !!localStorage.getItem("token"), // 초기값을 설정해준다
  },
});

const IS_LOGGED_IN = gql`
  query IsUserLoggedIn {
    isLoggedIn @client // @client가 붙었으니 로컬 상태에서 뒤진다 > 근데 리졸버는 없다...
  }
`;

function App() {
  const { data } = useQuery(IS_LOGGED_IN); // 여기서 data에는 캐시에서 바로 뽑은 값이 들어감
  return data.isLoggedIn ? <Pages /> : <Login />;
}

ReactDOM.render(
  <ApolloProvider client={client}>
    <App />
  </ApolloProvider>,
  document.getElementById("root"),
);
```

**[주의]** `local resolver` 없이 캐시에서 바로 뽑고싶으면, `ApolloClient` 생성자에 빈 `resolvers` 옵션을 줘야한다 (위 예제처럼)

- 캐시에서 직접 뽑는건 리졸버 정의만큼 flexiable 하지는 않다.
- 리졸버를 정의하면 결과를 반환하기 전에 추가적으로 extra computation을 해주는 게 가능함
- 그래도 단순하게 구현이 가능하니까 편하게 쓸 수 있다는 장점은 있는 듯
- 두 방법을 적절하게 섞어서 쓰는 게 좋다.

___

### Working with fetch policies

- 로컬 상태를 뒤질 때, 리졸버부터 찾을지 캐시를 볼지 서버에서 떙길지 정해줄 수 있다.
- `fetchPolicy` 정책의 중요성 : 모든 요청에서 `local resolver`가 도는 건 아님!
- `local resolver`의 결과는 나머지 쿼리 결과와 함께 캐시되고, 다음 요청 시 캐시에서 당겨진다.
- 정의해주지 않으면 `cache-first`가 기본값

```javascript
export const GET_LAUNCH_DETAILS = gql`
  query LaunchDetails($launchId: ID!) {
    launch(id: $launchId) {
      isInCart @client // 로컬에서 뒤진다!
      site
      rocket {
        type
      }
    }
  }
`;

export default function Launch({ launchId }) {
  const { loading, error, data } = useQuery(
    GET_LAUNCH_DETAILS,
    { variables: { launchId } }
    // fetchPolicy 정의 X > cache-first로 ㄱㄱ
  );
 ...
}

...

import { GET_CART_ITEMS } from './pages/cart';

export const resolvers = {
  Launch: {
    isInCart: (launch, _, { cache }) => {
      const { cartItems } = cache.readQuery({ query: GET_CART_ITEMS });
      return cartItems.includes(launch.id);
    },
  },
};

```

- 캐시를 체크할 때, 전체 쿼리는 캐시에 대해 실행되지만, `@client`가 관련된 로컬 리졸버들은 스킵된다.
  - 이럴 경우, 결과는 캐시에서 꺼내진 게 아니다.
  - 그래서 아폴로는 이런 쿼리를 2개로 쪼개서 하나는 리졸버로, 하나는 서버로 보낸다. > 그리고 합쳐져 캐시에 쓴다.
  - 결국 캐시에 써졌으니까, 이 요청을 다시 해도 로컬 리졸버는 실행되지 않는다.
- **근데 로컬 리졸버가 매 요청마다 실행되어야 한다면 ... ?** : `no-cache`, `network-only` 등으로 설정해주자

___

### Forcing resolvers with `@client(always: true)`

- 위와 같은 상황에서, 캐시를 타야하는 전체 쿼리는 서버로 보내고, 모든 요청에 `@client`가 실행은 되었으면 할 때 사용한다 
  - 네트워크 오버헤드 비용을 줄이고 싶을 때 사용하는 게 캐싱인데...
  - 서버에서 가져온 다음에 같은 쿼리가 또 날아가면, 캐싱이 되어있을 테니까 `local resolver`는 실행되지 않을 것.
- 매 요청마다 `local resolver`가 실행되어야 함 (연산작업이 붙는다든가 ...)
  - **근데 캐시 탈거는 태웠으면 좋겠다. (`no-cache`로 하면 캐시를 아예 안본다)**
  - `@client` directive에게 `always` argument를 넘기자!
  - 이거 쓸 때는 조심하자... 사이드 이펙트가 발생할 수도 있음.

```javascript
const client = new ApolloClient({
  cache: new InMemoryCache(),
  resolvers: {
    Query: {
      isLoggedIn() {
        return !!localStorage.getItem('token'); // 로컬스토리지를 체크한다.
        // 쿼리가 실행될 떄마다 이 부분이 실행되었으면 좋겠다
      },
    },
  },
});

const IS_LOGGED_IN = gql`
  query IsUserLoggedIn {
    isLoggedIn @client(always: true) // 이렇게 해주면 된다!
  }
`;
```

**[주의]** `always:true`를 줘도, `fetchPolicy` 자체가 캐시 먼저로 되어있다면 (`cache-first`, `cache-and-network`, `cache-only`) 여전히... 쿼리는 캐시부터 볼 것!

___

### Using @client fields as variables

- **Apollo Client**는 `@client` 필드의 결과를 변수로 쓸 수 있게 해준다.
- 로컬 데이터 요청하고 > 받아와서 > 그 결과로 다시 쿼리하지 않고... 이게 한 요청으로 끝날 수 있다.
- `@client`와 `@export(as: "변수명")` directive를 같이 붙여주면 됨!
- 얘는 리모트 쿼리에만 붙는 건 아니다. 로컬에서도 활용 가능함!

```javascript
const query = gql`
  query currentAuthorPostCount($authorId: Int!) {
    currentAuthor @client {
      name
      authorId @export(as: "authorId") // 캐시에서 authorId라는 이름으로 끌고와서
    }
    postCount(authorId: $authorId) // 여기서 활용한다!
  }
`;

const cache = new InMemoryCache();
const client = new ApolloClient({
  cache,
  resolvers: {
    Query: {
      postCount(_, { authorId }) { // 로컬 리졸버로 정의된 부분에서도 활용 가능하다.
        return authorId === 12345 ? 100 : 0;
      },
    },
  },
});
```

**[주의]** `@export`는 `@client`가 꼭 있어야 사용 가능함!