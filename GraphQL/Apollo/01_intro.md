# Apollo 써보기

 **출처 : [Apollo 공식 레퍼런스](https://www.apollographql.com/docs/react/)**

#### 목차



___

## Apollo Client 소개

- 자바스크립트 앱을 위한 상태관리 라이브러리
  - GraphQL 쿼리 쓰기
  - 데이터 캐싱하기
  - UI 업데이트하기 가능!

> Data management shouldn't have to be so difficult!

___

### Declarative data fetching
  - `useQuery` Hook : 데이터 가져오고, 로딩과 에러 상태 트래킹하고 UI 업데이트까지 한 번에 가능

```javascript
function Feed() {
  const { loading, error, data } = useQuery(GET_DOGS);
  if (error) return <Error />;
  if (loading || !data) return <Fetching />;

  return <DogList dogs={data.dogs} />;
}
```

  - `useQuery`는 내부적으로 React의 **Hooks API**를 사용함

  - 결과적으로 작성해야 하는 코드가 많이 줄어든다

> Advanced features like optimistic UI, refetching, and pagination are all easily accessible through  useQuery options.

___

### Zero-config caching
  - **Apollo Client**를 쓰기만 해도 좋은 캐시 시스템을 사용할 수 있음!

```javascript
import ApolloClient from 'apollo-boost';

// the Apollo cache is set up automatically
const client = new ApolloClient();
```

  - 캐시의 일관성 유지

    - 캐시의 데이터로 접근할 수 있는 path가 여러대가 보니까,,,
    - **normalization**은 여러 컴포넌트들 사이에서 데이터의 일관성을 유지하는 데 중요함
    - 이미지를 보여주는 쿼리가 있을수도, 이미지를 수정하는 뮤테이션이 있을 수도 있음
    
  - 그래서 GraphQL 쿼리 결과를 각각의 오브젝트로 쪼갠 다음에... 

    - **Apollo cache**의 엔트리에 `__typename`, `id` 프로퍼티를 붙인다.
    - 이렇게 하면 `mutation`의 결과로 얻은 (`id`를 가진) 값이 자동적으로 **같은 `id`를 가진 오브젝트를 가져오는 쿼리들을 업데이트** 하게 된다. *(짱어렵)*
    - 또한, 같은 데이터를 가져오는 여러 쿼리들이 늘 같은 결과를 가져오는 것도 보장됨!

  - **cache redirect** : 전체 리스트를 가져오고 특정 한 오브젝트를 다시 가져와야 하는 경우...

    - 같은 정보를 다시 가져올 필요는 없다!
    - **Apollo cache**는 두 쿼리를 연결해주어서, 우리는 이미 가지고 있는 정보를 다시 fetch하지 않아도 됨!

```javascript
import ApolloClient from 'apollo-boost';

const client = new ApolloClient({
  cacheRedirects: {
    Query: {
      dog: (_, { id }, { getCacheKey }) => getCacheKey({ id, __typename: 'Dog' })
    }
  }
})
```

___

### Combine local & remote data

- GraphQL을 모든 데이터의 단일화된 인터페이스로 사용할 수 있게 해준다
- local, remote 스키마들을 **GraphiQL** 같은 툴로 확인할 수 있게 된다

```javascript
const GET_DOG = gql`
  query GetDogByBreed($breed: String!) {
    dog(breed: $breed) {
      images {
        url
        id
        isLiked @client
      }
    }
  }
`;
```
- Apollo Client의 로컬 상태 기능을 활용해서, `client-side only fields`를 추가할 수 있다.
  - remote 데이터에 원활하게 접근해서, 컴포넌트로부터 걔들을 끌어온다.
  - 위 예제에서는, `isLiked` (`client only`)를 서버의 데이터와 함께 합쳐서 뱉어줌!

> Your components are made up of local and remote data, now your queries can be too!

___

### Vibrant ecosystem

- `apollo-boost`로 커버되지 않는 기능이 필요하면...
  > such as app-specific middleware or cache persistence
  
- **Apollo cache** + **Apollo Link** (네트워크 스택) = 커스텀 클라이언트
  
- 커뮤니티 짱짱 많음