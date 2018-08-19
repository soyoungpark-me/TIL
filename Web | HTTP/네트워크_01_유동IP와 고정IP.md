# 유동IP와 고정IP

작성일 : ```2018.08.20```

**SSH : 시큐어 쉘(Secure Shell)의 약자**

***

### IP란?

- **IP**란 **인터넷 프로토콜(Internet Protocol)**의 약자로, 흔히 이 프로토콜에서 각 장치를 나타내는 **IP 주소**를 가리키는 말로 쓰인다.

- **IP 주소**란 IP 통신에 필요한 고유 주소를 말하며, **```IPv4```**와 **```IPv6```** 두 가지 체계가 있다.

- **IPv4의 구성 단위**

  ![클래스 체계](https://blogfiles.pstatic.net/MjAxODA4MjBfMTQ3/MDAxNTM0NzA4MjUwMTY5.OUnL6-Ued3vCVH3aTEwrAzczwsY_gwX3XMieNSRxXHIg.F1uLLrQiHU9YOdi6NFggLIPMzfbw1MnrmvS3u5gW35Qg.PNG.3457soso/Table1.png)

  - **A 클래스** : 최고 단위 클래스 ```1.0.0.1 ~ 126.255.255.254```

  - **B 클래스** : 두 번째로 높은 단위 ```128.0.0.1 ~ 191.255.255.254```

  - **C 클래스** : 제일 낮은 클래스 ``` 192.0.0.1 ~ 223.255.255.254```

    

___

### 공인IP와 사설IP

![공인IP와 사설IP](https://blogfiles.pstatic.net/MjAxODA4MjBfMjUz/MDAxNTM0NzA5MTkxNjYx.vjBCu6vzssqju4GwTLaafF0OQ9rTlp-gcv0N5_gnhWwg.ngTd68mBF8pA21TF8Q5Gfdv_d8IvxiNp6dQJnanXWTMg.JPEG.3457soso/Network_Address_Translation_%28file1%29.jpg)

- **공인 IP** : 공인 기관에서 인증한 공개형(public) IP 주소
  - 실제 다른 컴퓨터와 통신할 때 쓰이는 IP 주소이다. 집 대문같은 개념으로 생각하면 된다.
  - 지역별로 쓸 수 있는 IP 주소의 범위는 정해져 있다.
  - 내 맘대로 받아다 쓸 수 있는 게 아니다. 주소를 관리하는 기관에서 할당해주는 것!
  - 우리 나라에서는 한국 인터넷 진흥원(KISA)에서 주소 체계를 관리한다고 한다.
  - **확인 방법**
    - **```$ curl icanhazip.com```**
    - [IP.FACT 접속](http://ip.fatc.club/page/)
- **사설 IP** : 가상 IP 라고도 불리며, **폐쇄형**이다.
  - 외부에 공개되지 않았으므로 **외부에서 검색, 접근하는 것이 불가능**하다.
  - IP의 개수가 고갈되는 것을 방지한다.
  - 주로 인터넷 유무선 공유기를 사용할 때 흔히 접하게 된다.
  - **사설 IP 대역**
    - ```10.0.0.0 ~ 10.255.255.255```
      ```172.16.0.0 ~ 172.31.255.255```
      ```192.168.0.0 ~ 192.168.255.255```



___

### 개인 서버를 운영하는 게 어려운 이유

- 가정에서 개인 서버를 운영하는 게 어려운 이유는 **통신사에서 제공받은 IP 주소가 바뀌기 때문**

- **유동 IP**

  - 부족한 IP의 개수를 해결하기 위해 등장한 방안
  - 가정에서는 **ISP (Internet Service Provider / 통신사)** 에게 IP를 할당받게 된다.
    - IP의 개수에는 한계가 있기 때문에, 인터넷을 쓰지 않는 컴퓨터에게도 계속 IP를 할당해주지는 못한다.
    - 그래서 IP가 바뀌어도 문제가 되지 않는 일반 가정에는 유동 IP 회선을 제공한다.

- **고정IP**

  - 유동IP의 반대니까, IP가 변하지 않고 고정적으로 같은 IP를 사용하는 것.

  - 비용이 더 들어간다고 한다.

    

___

### 특수 용도 주소

| 주소 대역       | 용도                                                       |
| --------------- | ---------------------------------------------------------- |
| **0.0.0.0/8**   | 자체 네트워크.  IP 주소를 할당받기 전 임시로 사용하는 주소 |
| **127.0.0.1/8** | 루프백(loopback) 즉 자기 자신을 의미한다.                  |



***

### 참고 문헌

- [위키백과 - IPv4](https://ko.wikipedia.org/wiki/IPv4)
- [GotoCloud : 공인 IP, 사설 IP, 고정 IP, 유동 IP](http://gotocloud.co.kr/?p=320)](https://opentutorials.org/module/432/3738)
- [생활코딩 : 유동 아이피와 고정 아이피](https://opentutorials.org/course/3265/20056)