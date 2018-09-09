package _01_Thread;

public class ThreadJoinTest {
    public static void main(String[] args) {
        // 스레드 생성
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("MyThread 종료");
                    Thread.sleep(3000);
                } catch (Exception e) {}
            }
        };

        t.start();

        try {
            // join 메소드를 실행한다.
            // t 스레드가 종료될 때까지 main 스레드가 기다리게 된다.
            // join 메소드가 InterruptedException을 발생시키는 것에 즈의하자!
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // main 메소드 종료 메시지
        System.out.println("main() 종료");
    }
}
