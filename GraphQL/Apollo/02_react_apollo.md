# Apollo 써보기

 **출처 : [Apollo 공식 레퍼런스](https://www.apollographql.com/docs/react/)**

#### 목차



___

## React Apollo

- GraphQL 서버로부터 데이터를 가져오고, 그걸 복잡한 UI를 구현하는 데 쓴다.
- **Hooks API** : HOC (higher-order components)나 보일러플레이트 없이 `prop` 컴포넌트 렌더링

- **Apollo Boost** : 스타터 키트, 추천 세팅으로 클라이언트 바로 사용 가능
  > memory cache, local state management, and error handling.

  - `gql`로 쿼리 보내기

```javascript
client
  .query({
    query: gql`
      {
        rates(currency: "USD") {
          currency
        }
      }
    `
  })
  .then(result => console.log(result));
```

___

### Connect your client to React

- 쿼리해보고 결과를 확인해보면 `loading`, `networkStatus`같은 프로퍼티도 같이 온다.
![image](https://media.oss.navercorp.com/user/13702/files/b17bcc80-ce8a-11e9-90f5-18c6df6ee231)

- 데이터를 끌어올 때 다른 프레임워크가 필요하지 않다.
  - `view layer integration`이 쿼리를 UI에 바인딩하고, 컴포넌트를 업데이트하는 걸 쉽게 해준다.

- `ApolloProvider` : **Apollo Client**를 React에 연결하기 위해 필요함!
  - React의 `Context.Provider`와 유사함.
  - React 앱을 래핑해서 클라이언트를 **context**에 위치하도록 한다.
  - 결국.. 컴포넌트 트리 아무데서나 access 가능하게 해준다는 뜻 같음

```javascript
const App = () => (
  <ApolloProvider client={client}>
    <div>
      <h2>My first Apollo app 🚀</h2>
    </div>
  </ApolloProvider>
);
```
- GraphQL의 데이터에 접근해야하는 컴포넌트 상위 아무데나 두면 된다

___

### Request Data

- 이거 붙이고 나면 `useQuery Hook`으로 데이터 끌어올 수 있다~!
  - `@apollo/react-hooks`에 포함되어있는... **Hooks API** 라고 한다...

- 데이터 끌어오기~
1. `gql`으로 감싼 GraphQL 쿼리를 `useQuery` 훅에게 넘긴다
2. 컴포넌트가 렌더링되고 `useQuery`가 돌면, 결과값은 `loading`, `error`, `data`를 리턴한다.

    - 데이터 페칭 중의 상태도 관리해주는 듯 ... 한 곳에서 처리할 수 있어 편함!

```javascript
function ExchangeRates() {
  const { loading, error, data } = useQuery(gql`
    {
      rates(currency: "USD") {
        currency
        rate
      }
    }
  `);

  if (loading) return <p>Loading...</p>; // loading 중이라던가...
  if (error) return <p>Error :(</p>; // error가 났을 때 이렇게 쳐리해주면 된다.

  // 결과적으로 data가 잘 들어오면 이 부분이 실행된다!
  return data.rates.map(({ currency, rate }) => (
    <div key={currency}>
      <p>
        {currency}: {rate}
      </p>
    </div>
  ));
}
```

+) [`ApolloClient` 옵션들](https://www.apollographql.com/docs/react/essentials/get-started/#configuration-options)