# Apollo 써보기

 **출처 : [Apollo 공식 레퍼런스](https://www.apollographql.com/docs/react/)**

#### 목차



------

## Local state management

- **Apollo Client**로 로컬 상태 관리하기!
- 상태는 관리하고 싶지만, **Redux**나 **MobX** 스토어를 만들고 싶지 않을 때!
- **Apollo cache** 자체를 클라이언트 앱의 하나의 데이터 소스로 써보자
- local의 데이터와 remote 데이터를 같이? (alongside)로 사용할 수 있는 듯...

___

### Updating local state

- local state를 갱신하는 데에는 두 가지 방법이 있다 : `cache.writeData`, `useMutation`

**Direct writes**
- 현재 캐시에 있는 데이터에 의존하지 않는 경우에 좋음! (ex. writing a single value)
- GraphQL mutation이나 resolver 함수를 필요로 하지 않는다.
- `useQuery` Hook의 결과 중 사용 가능한... `useApolloClient` 훅으로부터 리턴받은... `client` 프로퍼티에 직접 접근함!
- 또는 `ApolloConsumer` 컴포넌트의 렌더 함수를 쓴다고 함 (이해 잘 안됨)
- 어쨌든 object가 아니라 간단한 string one-off 쓰기에서만 쓰는 걸 권장함.
- 데이터 validation 없음.

```javascript
function FilterLink({ filter, children }) {
  const client = useApolloClient();
  return (
    <Link
      onClick={() => client.writeData({ data: { visibilityFilter: filter } })}
    >
      {children}
    </Link>
  );
}
```

- 방금 캐시에 쓴 데이터를 바로 subscribe하고 싶으면?
  - 링크의 필터에 `active` 프로퍼티를 준다.
  - 현재 캐시에 있는 `visibilityFilter`와 동일할 경우 링크를 `active` 상태로 만든다 (네..?)

```javascript
const GET_VISIBILITY_FILTER = gql`
  {
    visibilityFilter @client
  }
`;

function FilterLink({ filter, children }) {
  const { data, client } = useQuery(GET_VISIBILITY_FILTER);
  return (
    <Link
      onClick={() => client.writeData({ data: { visibilityFilter: filter } })}
      active={data.visibilityFilter === filter}
    >
      {children}
    </Link>
  )
}
```

- `@client` : **Apollo Client**가 이 데이터를 locally하게 가져올 것이라는 뜻! (캐시든 로컬 리졸버든)

**Local Resolvers**
- mutation이 현재 캐시에 있는 값에 의존한다면, 리졸버를 쓰는 게 좋다.
- 로컬 상태 업데이트를 GraphQL mutation 처럼 하고 싶다면, `local resolver map`를 정의해줘야 함!
  - `resolver map` : 각 GraphQL 오브젝트 타입에 대한 리졸버 함수!
  - 이해하기 위해, 각 쿼리나 뮤테이션이 각 필드들을 위한 트리 구조의 함수 호출이라고 생각하자...
  - 필드에 `@client` directive가 있으면, internal `resolver map`을 반환해서 실행되도록 한다.
  - **Apollo Server** 에서와 같이 `resolver function`을 유연하게 사용할 수 있다고 함!

- 형식 : `fieldName: (obj, args, context, info) => result`
  - `obj` : 부모 필드의 리졸버로부터 오는 결과나, 최상위 쿼리일 경우엔 `ROOT_QUERY` 오브젝트
  - `args` : 필드로 넘겨야 하는 arguments들 (varaibles?)
  - `context` : React 컴포넌트와 **Apollo Client** 간 공유되는 contextual information.
    - `context.client` : **Apollo Client** 인스턴스
    - `context.cache` : **Apollo Cache** 인스턴스
    - `context.getCacheKey` : `__typename`과 `id`를 통해 캐시를 가져와야 함. 그 키 값.
 - `info` : 쿼리의 실행 상태에 대한 정보

```javascript
const client = new ApolloClient({
  cache: new InMemoryCache(),
  resolvers: {
    Mutation: {
      toggleTodo: (_root, variables, { cache, getCacheKey }) => {
        const id = getCacheKey({ __typename: 'TodoItem', id: variables.id }) // 캐시를 찾을 키 값
        const fragment = gql`
          fragment completeTodo on TodoItem {
            completed
          }
        `;
        const todo = cache.readFragment({ fragment, id }); // 캐시의 fragment를 읽어옴
        const data = { ...todo, completed: !todo.completed };
        cache.writeData({ id, data }); // 캐시에 반영해준다
        return null; // UI에 결과를 반영해 줄 필요는 없는 듯!
      },
    },
  },
});
```

저렇게 캐시 업데이트를 설정해두고, 실제로 업데이트를 쳐보자!

```javascript
const TOGGLE_TODO = gql`
  mutation ToggleTodo($id: Int!) { // 업데이트 해야 할 id를 넘겨준다
    toggleTodo(id: $id) @client // 로컬 리졸버에 정의된 toggleTodo를 실행하겠다는 뜻!
  }
`;

function Todo({ id, completed, text }) {
  const [toggleTodo] = useMutation(TOGGLE_TODO, { variables: { id } });
  // 컴포넌트로 뮤테이션이 넘겨지고, 컴포넌트는 UI를 다시 렌더링하게 될 것!
  return (
    <li
      onClick={toggleTodo}
      style={{
        textDecoration: completed ? "line-through" : "none",
      }}
    >
      {text}
    </li>
  );
}
```

___

### Managing the cache

**writeData**
- 캐시를 업데이트하는 가장 쉬운 방법은 `cache.writeData`
- 쿼리같은거 안쓰고 캐시 바로 업데이트 칠 수 있음!

```javascript
const client = new ApolloClient({
  cache: new InMemoryCache(),
  resolvers: {
    Mutation: {
      updateVisibilityFilter: (_, { visibilityFilter }, { cache }) => {
        const data = { visibilityFilter, __typename: 'Filter' };
        cache.writeData({ data }); // 로컬 리졸버 안에서 캐시 바로 업데이트 치기
      },
    },
  },
};
```

- 추가적으로 `id` 값을 받아서, 캐시에 해당 fragment가 존재하면 그거 업데이트 할 수도 있다.
  - 이럴 경우, `id`는 해당 오브젝트의 **cache key**와 일치해야 한다.
  - `InMemoryCache`를 쓰고 있고, `dataObjectFromId` 설정을 오버라이딩 하지 않았다면, 기본 **cache key**는 `__typename:id`
  - 고로 `cache.writeData({ id: `User:${id}`, data });` (`__typename`은 `User`, `id`는 `${id}`)

**writeQuery and readQuery**
- 캐시에 쓸 데이터가 이미 캐시에 존재하는 값에 영향을 받을 수도 있음.
- 이럴 때는 `cache.readQuery`로 **캐시를 쓰기 전에** 쿼리를 넘기고, 캐시로부터 값을 읽어와야 한다.

```javascript
let nextTodoId = 0;

const cache = new InMemoryCache();
cache.writeData({ // 초기 값이 없으면 에러나니까 초기화 해주자
  data: {
    todos: [],
  },
});

const client = new ApolloClient({
  resolvers: {
    Mutation: {
      addTodo: (_, { text }, { cache }) => { // 캐시에 Todo를 써주고 싶다
        const query = gql` // 그러려면 일단 현재 캐시에 있는 Todo들을 가져와야 한다
          query GetTodos {
            todos @client { // 클라이언트에서 가져올거다
              id
              text
              completed
            }
          }
        `;

        const previous = cache.readQuery({ query });
        const newTodo = { id: nextTodoId++, text, completed: false, __typename: 'TodoItem' };
        const data = {
          todos: [...previous.todos, newTodo], // 기존에 있던 Todo들과 합쳐준다
        };

        // you can also do cache.writeData({ data }) here if you prefer
        cache.writeQuery({ query, data }); // 그리고 나서 캐시에 써준다!
        return newTodo;
      },
    },
  },
});
```

- **마지막 과정에서 `cache.writeData`와 `cache.writeQuery`의 차이는?!**
  - `cache.writeQuery` : 데이터의 모양새를 validate하기 위해 쿼리를 넘겨줘야 한다.
  - `cache.writeData` : 데이터로부터 자동으로 쿼리를 생성해서 쓴다 (validate X)

**writeFragment and readFragment**
- `cache.readFragment`는 `cache.readQuery`와 비슷한데.. fragment를 넘긴다는 게 다르다.
- flexible 해진다! (cache와 cache key를 가진 특정 엔트리로부터 읽어들일 수 있다)
  - `cache.readQuery`는 **cache의 root에서만 읽을 수 있다**

```javascript
const client = new ApolloClient({
  resolvers: {
    Mutation: {
      toggleTodo: (_, variables, { cache }) => {
        const id = `TodoItem:${variables.id}`;
        const fragment = gql`
          fragment completeTodo on TodoItem { // TodoItem 하위로 프래그먼트를 쪼개자
            completed // todos의 하위인 completed만 선택할 수 있다!
          }
        `;
        const todo = cache.readFragment({ fragment, id }); // id를 넘겨서 캐시에서 찾을 수 있다 (cache Key)
        const data = { ...todo, completed: !todo.completed };

        // you can also do cache.writeData({ data, id }) here if you prefer
        cache.writeFragment({ fragment, id, data });
        return null;
      },
    },
  },
});
```

+) [`ApolloCache API`](https://www.apollographql.com/docs/react/essentials/local-state/#apollocache)