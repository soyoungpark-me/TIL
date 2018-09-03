* Gradle : Maven과 같은 프로젝트 빌드 도구.
  자주 사용할 빌드 명령어를 미리 작성해 놓은 일종의 라이브러리.

* Gradle 프로젝트를 웹 프로젝트로 만들기 > Gradle 설정 파일 변경
  1) 웹 프로젝트 관련 Gradle 플러그인 추가하기
     apply plugin: 'eclipse-wtp'
     apply plugin: 'war'

  2) 소스 파일의 인코딩 형식 지정하기. Gradle은 빌드할 때 소스 코드의 인코딩이 OS의 기본 인코딩과 같다고 간주함
     compileJava.options.encoding = 'UTF-8'

  3) 소스 코드의 자바 버전 지정하기. 컴파일 할 때도 JVM 해당 버전 이상에서만 실행할 수 있게 됨.
     sourceCompatibility = 1.7

  4) 웹 프로젝트의 서블릿 버전 및 자바 버전 지정하기
     eclipse{
        wtp{
            facet{
                facet name: 'jst.web' version: '3.0'
                facet name: 'jst.java', version: '1.7'
            }
        }
     }

  5) 스프링 프레임워크 관련 라이브러리 가져오기. dependencies에 추가
     compile 'org.springframework:spring-context:5.0.2.RELEASE'

* Gradle 파일의 속성들
  - jar{} : jar 작업과 제어 속성. jar 파일의 이름이나 jar 파일 안에 넣어야 할 것과 넣지 말아야 할 것을 조정한다.
  - repositories{} : 의존 라이브러리를 가져올 저장소 설정.
  - dependencies{} : 프로젝트에서 사용할 의존 라이브러리 지정.
    [설정 이름] [의존 라이브러리 정보]
    > 의존 설정 이름 : 의존 라이브러리가 사용되는 시점. (Ex. compile, runtile ... )
      providedCompile : 웹 애플리케이션이 서블릿 컨테이너(Ex. 톰캣)에 디플로이되면 그 서버에서 제공하는 서블릿 API를 씀
      compile : 컴파일할 때 사용되고, 배포 파일(war)에도 포함된다.
    > 의존 라이브러리 정보 : 'group:name:version:classifier' name은 필수!

  - test{} : JUnit이나 TestNG를 실행할 때 필요한 정보. 테스트 정보를 설정한다.
  - uploadArchives{} : 프로젝트를 빌드한 후 생성된 산출물은 uploadArchives 작업을 통해 저장소에 배포함. 여기서 설정.


# Gradle 관련 메뉴가 안보일 땐!?
  ./project 파일의 <natures> 첫 번째 자식으로 아래 내용 추가
  <nature>org.springsource.ide.eclipse.gradle.core.nature</nature>
