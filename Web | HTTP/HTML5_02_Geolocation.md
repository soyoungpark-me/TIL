# HTML5 Navigator.geolocation

- 브라우저가 직접 접속한 위치 정보를 수집한다.

- 앱의 GPS를 통하지 않아서도, 웹에서도 유저의 위치 정보를 알아낼 수 있다!

- 접속 주소의 위도와 경도를 수집해주는 인터페이스를 이용할 수 있지만, 실제 위치인진 확실하지 X

  ​

---

### 위치 정보 수집 함수

1. **```getCurrentPosition```**: 위치 정보를 수집하는 API

   ```javascript
   void getCurrentPosition(PositionCallback successCallback, optional PositionErrorCallback errorCallback, optional PositionOptions options);
   ```

   - **``` PositionCallback ``` **:  요청이 성공했을 경우의 콜백 함수

     - 성공시 ```Position``` 값을 리턴한다.

       ```
       Coordinates coords : 위치 정보
       	double latitude : 위도
       	double longitude : 경도
       	double accuracy : 정확도
       	double heading : (옵션) 현재 향하고 있는 방향
       	double speed (옵션) : 현재 속도
       DOMTimeStamp timestamp : 수집 시간 정보
       ```

   - **```PositionErrorCallback```** : 요청이 실패했을 때의 콜백 함수

     - 실패시 ```PositionError``` 값을 리턴한다.

        unsigned short code : 오류 코드
        	DOMString message : 오류 메시지
        	unsigned short PERMISSION_DENIED = 1: 사용자가 위치 정보 수집을 거부
        	unsigned short POSITION_UNAVAILABLE = 2: 위치 정보 수집 불가
        								(예: GPS 동작 불가 지역 등)
        	unsigned short TIMEOUT = 3: 위치 정보를 수집하기 전에 먼저 
        								옵션의 timeout 값 시간이 소모

   - **```PositionOptions```** : 실제로 요청할 때 쓰는 인자들

     - ```boolean enableHighAccuracy``` : (기본 falue) 앱의 위치 정보를 최대한 정확하게 할건지.

       ​	이거 활성화하면 응답 속도가 느려지거나 기기에 무리가 간다.

     - ```long timeout``` : (기본 Infinity) 콜백함수가 호출되기까지 기다리는 허용 시간

     - ```long maximumAge``` : (기본 0) 기존에 수집한 위치 정보를 얼마나 오래 캐쉬로 보관할건지


***

### 기타 참고사항 및 특징

1. 개인정보 강화로 인해서 특정 브라우저에서는 https:// 사이트에서만 사용 가능하게 되었다.

   - 없이 접근하면 ```Only secure origins are allowed``` 라고 뜬다 ㅠㅠ

   > 위치 정보 수집 API를 쓰고 싶다면 먼저 https 환경을 구성해 놓아야 한다.

2. 사용자에게 먼저 위치 수집 권한을 요청하는데, 이를 거절하면 어떻게 대처할지도 구현해놔야 한다.

3. 정보 수집 중 오류가 발생할 경우 사용자에게 다시 재시도를 하거나, 새로고침 해버리는 게 좋은듯...​



1. - ​

------

#### 참고 문헌

- [Unikys : [HTML5 튜토리얼] navigator.geolocation 위치 정보 수집 API](http://unikys.tistory.com/375)

------

#### 참고 문헌

- [생활코딩 : HTTPS와 SSL 인증서](https://opentutorials.org/course/228/4894)
- [Outsider's Dev Story : HTTPS로 보안 강화하기](https://blog.outsider.ne.kr/1149)