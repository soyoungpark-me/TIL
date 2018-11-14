package _02_Thread;

// Runnable 인터페이스 구현
class RunnableThread implements Runnable {
    // run을 오버라이딩해 재정의
    @Override
    public void run() {
        System.out.println("Runnable 인터페이스를 구현");
    }
}

public class RunnableThreadTest {
    public static void main (String[] args) {
        // Thread 생성자에 RunnableThread 인스턴스를 파라미터로 전달!
        Thread t = new Thread(new RunnableThread());
        t.start();
    }
}
