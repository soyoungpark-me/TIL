package _01_Thread;

// Thread 클래스를 상속
class ExtendThread extends Thread {
    // run()을 오버라이딩해 재정의
    public void run() {
        System.out.println("Thread 클래스를 상속");
    }
}

public class ExtendThreadTest {
    public static void main(String[] args) {
        ExtendThread et = new ExtendThread();
        // start를 이용해 스레드 시작!
        // 이후 ExtendThread의 run() 함수가 실행되고,
        // run() 함수가 종료되면 바로 ExtendThread가 소명된다.
        et.start();
    }
}