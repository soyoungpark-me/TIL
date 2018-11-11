# WebSocket과 Socket.io

작성일 : ```2018.08.23```

**실시간 웹 서비스를 위한 대책(?)**

------

### 웹소켓 (Web Socket)의 등장 배경

- **웹소켓**은 ```HTTP```의 stateless한 단점을 극복하기 위해 등장한 ```HTML5``` 표준안의 일부이다.

  ![웹소켓](https://blogfiles.pstatic.net/MjAxODA4MjNfMTIx/MDAxNTM1MDI2NDQyMDg1.ZUtbwsgxJrGd025NZGJv6mK5MvFjl5eQ_zSQIbnCgJog.8ts-2fqyMVeFhXfSawvW_NFG39Zc54ESYTKyO0gUTDsg.JPEG.3457soso/helloworld-1336-1-1.png)

- 웹 브라우저는 HTTP 프로토콜로 요청, 응답을 주고 받기 때문에 ```TCP/IP 소켓```처럼 커넥션이 유지되지 못한다. 

  - 브라우저에서 서버로 요청을 보내 응답을 줄 수는 있지만, 브라우저의 요청 없이 서버가 일방적으로 정보를 보낼 수 없다. (푸시할 수 X)
  - 이 문제를 극복하기 위해 등장한 것이 **WebSocket**!!
  - 웹소켓을 이용하면 브라우저와 서버 사이에서도 소켓 통신처럼 실시간으로 데이터를 주고 받을 수 있다.



------

### 웹소켓 프로토콜

- 웹소켓은 다른 HTTP 요청과 마찬가지로 80번 포트를 통해 웹 서버에 연결

- 요청 시의 헤더는 다음과 같다.

  ```
  GET /... HTTP/1.1  
  Upgrade: WebSocket  
  Connection: Upgrade  
  ```

- 브라우저와 서버가 서로 ```웹소켓 핸드쉐이킹```을 주고받은 후 연결된다.

- 연결이 된 후로는 **```Protocol Overhead```** 방식으로 웹 서버와 브라우저가 데이터를 주고 받을 수 있다.

  - ```Protocol Overhead  ```
    - 여러 TCP 커넥션을 생성하지 않고, 하나의 80번 포토 TCP 커넥션을 이용
    - 별도의 헤더 등으로 논리적인 데이터 흐름 단위를 생성해 여러개의 커넥션을 맺는다.

- **웹소켓 핸드쉐이킹**

  ![핸드쉐이킹](https://blogfiles.pstatic.net/MjAxODA4MjNfMTUx/MDAxNTM1MDI2NDMzMjA5.TWWN-L3rcgVbY8JtGaeluO861glarmKSUyoWdnbpXosg.4saboYTgZc-Br9yMWSv0YJ9kcUggdxHN_hj0wWQtIvwg.PNG.3457soso/websocket-lifecycle.png)

------

### Socket.io

- 웹소켓은 다 좋지만.. 쓸 수 있는 신기술이기 때문에 브라우저에 한계가 있다는 단점이 있다.

- 이를 극복하기 위한 수단이 Socket.io

  - ```WebSocket```, ```FlashSocket```, ```AJAX Long Polling```, ```AJAX Multi part Streaming```, ```IFrame```,``` JSONP Polling```를 하나의 API로 통합한 것!

  - 브라우저와 웹 서버의 종류와 버전을 파악해, 실시간 서비스를 구현할 수 있도록 자동으로 기술을 선택해 사용한다.

    

------

### 참고 문헌

- [Naver D2 : WebSocket과 Socket.io](https://d2.naver.com/helloworld/1336)
- http://stevelacey.github.io/websocket-examples/