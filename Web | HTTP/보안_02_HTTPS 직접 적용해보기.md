# HTTPS 적용해보기

작성일 : ```2018.08.14 FRI```

**공부한 내용을 직접 홈페이지에 적용해보자**

***

### SSL 인증서 발급받기

- Letsecrypt 에서 SSL 인증서를 무료로 발급받는다.
- **[```Letsecrpyt```](https://letsencrypt.org/)** : 전 세계 모든 사이트를 HTTPS로 만들자!
  - 오픈 소스 프로젝트로, 무료로 SSL 인증서를 발급받을 수 있다.
  - 공인된 중개 기관 및 CA와 체인이 되어 안전하다.
  - 와일드카드는 적용되지 않아서 서브도메인의 경우 하나하나 인증받아야 한다. ~~공짜니까 괜찮다~~
  - **발급 과정** : [미니브러리 - Letsencrypt에서 SSL 인증서를 무료로 발급 받아 웹 서버에 적용하기](https://kr.minibrary.com/353/)
  - **인증서 위치** : ```/etc/letsencrypt/archive/도메인명/```



***

### 발급받은 인증서 적용하기

- 해당 인증서의 위치를 **```nginx```**에  적용한다.

- ```sudo nano /etc/nginx/sites-enabled/default``` 설정 파일을 열어서

  ```
  server {
  	listen 80;
      listen [::]:80;

      server_name ${도메인};
      
      listen 443 ssl;
      listen [::]:443 ssl;
      
      ssl on;
      ssl_certificate     /etc/letsencrypt/live/${도메인}/fullchain.pem;
      ssl_certificate_key /etc/letsencrypt/live/${도메인}/privkey.pem;
      ssl_protocols       TLSv1 TLSv1.1 TLSv1.2;
      ssl_ciphers         HIGH:!aNULL:!MD5;

      location / {
              proxy_pass_header Server;
              proxy_set_header Host $http_host;
              proxy_set_header X-Real-IP $remote_addr;
              proxy_set_header X-Scheme $scheme;
              proxy_pass http://127.0.0.1:${포트};
      }
  }
  ```

  - 433번 포트로 접속을 받고, ssl_sertificate 및 ssl_certificate_key를 방금 발급 받은 fullchain.pem 및 private.pem으로 설정한다.

    ​

  #### 이렇게 하니까 안된다

  ​

- 홈페이지 구축을 할 때, 한 서버 내에 여러 포트로 여러 프로젝트를 띄워 뒀고, 이거를 하나의 도메인에 연결 하느라 ```리버스 프록시```를 사용했는데, 그래서 더 복잡해진 것 같다. ```nginx```도 처음 써봐서 설정하는 게 어려웠다ㅠㅠ

- 삽질하던 끝에 다음의 방법으로 해결했다.

  ```
  server {
          listen 80;
          server_name ${도메인};

          return 301 ${도메인}$request_uri;
  }

  server {
          server_name ${도메인};
          client_max_body_size 5m;
          add_header X-UA-Compatible    "IE=Edge,chrome=1";
          
          # Log configuration

          access_log /var/log/nginx/${도메인}_access.log;
          error_log /var/log/nginx/${도메인}_error.log;

          # SSL configuration

          listen 443 ssl;
          listen [::]:443 ssl;

          ssl on;
          ssl_certificate     /etc/letsencrypt/live/${도메인}/fullchain.$
          ssl_certificate_key /etc/letsencrypt/live/${도메인}/privkey.pe$
          ssl_protocols       TLSv1 TLSv1.1 TLSv1.2;
          ssl_ciphers         HIGH:!aNULL:!MD5;

          location / {
                  proxy_pass_header Server;
                  proxy_set_header Host $http_host;
                  proxy_set_header X-Real-IP $remote_addr;
                  proxy_set_header X-Scheme $scheme;
                  proxy_pass http://127.0.0.1:${포트};
          }
  }
  ```

  일단 이렇게 이해를 해보고 있다!

  1. 먼저 해당 도메인의 요청을 80번 포트로 받아 온 다음에, 그대로 443 포트로 넘겨버린다.
  2. 443 포트로 요청이 들어오면 발급 받은 인증서를 가지고 SSL 인증을 해주고, 
  3. ${포트}에 해당 서비스가 띄워져 있으니 거기로 넘긴다.

  ​



------

#### 참고 문헌