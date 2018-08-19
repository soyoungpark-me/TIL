# SSH란?

작성일 : ```2018.08.20```

**SSH : 시큐어 쉘(Secure Shell)의 약자**

***

### SSH의 기본 설명

- **시큐어 셸**(Secure Shell, **SSH**)은 [네트워크](https://ko.wikipedia.org/wiki/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC) 상의 다른 컴퓨터에 로그인하거나 원격 시스템에서 명령을 실행하고 다른 시스템으로 파일을 복사할 수 있도록 해 주는 [응용 프로그램](https://ko.wikipedia.org/wiki/%EC%9D%91%EC%9A%A9_%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8) 또는 그 [프로토콜](https://ko.wikipedia.org/wiki/%ED%86%B5%EC%8B%A0_%ED%94%84%EB%A1%9C%ED%86%A0%EC%BD%9C)을 가리킨다.  (위키백과)

- 원격지에 있는 컴퓨터를 안전하게 제어하기 위한 프로토콜 또는 이 프로토콜을 사용하는 프로그램들.

- 클라이언트와 서버는 **강력한 암호화 방법**을 통하 연결되어 있기 때문에 데이터를 중간에서 가로채도 해석할 수 없는 **암호화 된 문자**만이 노출된다.

  

  ![SSH](https://blogfiles.pstatic.net/MjAxODA4MjBfMTQx/MDAxNTM0NzA1ODA2NDQ3.FpiSuxohvUGAL5jrAR6R0eAqLSAYFEdpl-oE9uU1P9Ag.zgRK_xcXZffQ5ktLizxzdRUjhiAhVTfJrz12cguf-bcg.PNG.3457soso/2130B5505!7E3767105.png)

  - SSH 프로토콜을 통해 데이터를 주고 받는 양 측을 SSH 서버와 SSH 클라이언트라 부른다.

  - 리눅스와 MAC과 같은 유닉스 계열에서는 SSH 서버와 SSH 클라이언트를 모두 내장하고 있지만,

    **윈도우의 경우에는 유닉스 계열의 컴퓨터를 제어하기 위해 프로그램 설치가 필요하다**

    - 이 때 쓰이는 프로그램이 **```Putty```**

      

- SSH는 크게 2가지 역할을 한다고 정리할 수 있는데, 예시를 들자면 다음과 같다.

  1. 데이터 전송 : 원격 저장소는 **```깃허브```**에 푸시하는 경우에도 사용된다.

  2. 원격 제어 : **```AWS EC2```**에 접속해 제어할 때 사용된다.

     

___

### SSH의 등장 배경

- 비슷한 역할을 했던 프로토콜으로는 **```FTP```**와 **```Telnet```**이 있다. 이 프로토콜들은 통신할 때, **데이터를 암호화하는 과정이 없다!**

  중간에 통신을 누군가 가로챈다면, 주고받는 데이터가 그대로 노출된다는 **보안상의 문제**가 있었다.

- SSH는 다음과 같은 세 가지 기본 요소로 구성되어, 안전하게 데이터를 주고 받을 수 있도록 했다.

  ![계층](https://blogfiles.pstatic.net/MjAxODA4MjBfOTEg/MDAxNTM0NzA2NTAyOTE1.S1RCo5IcuC9FQg7ARGIDgXnRZTVjgevbsVLVwlrVKpYg.DvWchEcDQrDpY7ROdm3lLywcSF1JVxYLvUEIMqaz0tYg.PNG.3457soso/20090603_797808_image001_641548_57_0.png)

  1. **전송 계층 프로토콜** : 완전한 개인정보 보호 정책을 사용한 무결성, 서버 인증 및 개인정보 보호 기능이 제공되며, 보통 TCP/IP 연결을 거쳐 실행된다.
  2. **사용자 인증 프로토콜** : 서버에서 클라이언트를 이증하며, 전송 계층에서 실행된다.
  3. **연결 프로토콜** : 암호화된 터널을 다수의 논리적 채널로 다중화하며, 사용자 인증 프로토콜에서 실행된다.



___

### SSH KEY란?

- SSH를 통해 원격 서버에 접속할 때, 비밀번호 대신 key를 제출하는 방식.
- 비밀번호보다 **높은 수준의 보안**이 필요할 때나, 로그인 없이 자동으로 서버에 접속할 때 쓴다.
- **동작 방식**
  1. 키를 생성하는 공개 키(public key)와 비공개 키 (private key)가 생성된다.
     - 공개 키는 공개되어도 안전한 키이고, 공개 키로 메시지를 전송하기 전에 암호화한다.
     - 공개키로는 암호화는 가능하지만 **복호화는 불가능하다**
     - 비공개 키로 **암호화된 메시지를 복호화 할 수 있다**
  2. **비공개 키**는 **로컬 머신(SSH 클라이언트, 내 컴퓨터)**에, **공개 키는** **리모트 머신(SSH 서버, EC2)**에 위치한다.
  3. SSH로 접속하면, 서버(EC2)에 위치한 공개 키와, 그 키와 쌍을 이루는 로컬(나)의 비공개 키를 비교해 쌍을 키루는지 검사한다.
  4. 쌍을 이루는 키라는 것이 증명되면 채널이 형성되어 데이터를 주고 받을 수 있게 된다.

___

### 사용법

- 터미널로 접속하기

  **```$ ssh Username@Hostname```**

- 포트 설정해 접속하기

  **```$ ssh -p 22 Username@Hostname```**

- 파일 전송하기

  **```$ ssh Filename Username@Hostname```**

  

***

### 참고 문헌

- [위키백과 - 시큐어 셀](https://ko.wikipedia.org/wiki/%EC%8B%9C%ED%81%90%EC%96%B4_%EC%85%B8)
- [이동건의 이유있는 코드 : SSH란?](http://baked-corn.tistory.com/52)
- [밍그루의 소소한 이야기 : SSH란? SSH Protocol](http://myyo88.tistory.com/53)
- [생활코딩 : 원격제어 - SSH란 무엇인가?](https://opentutorials.org/module/432/3738)