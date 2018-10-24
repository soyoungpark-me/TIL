package _02_Thread;

public class DaemonThreadTest {
    public static void main(String[] args) {
        // 스레드 생성
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000); //5초간 멈춤!
                    System.out.println("MyThread 종료");
                } catch(Exception e) {}
            }
        };

        t.setDaemon(true);  // 데몬 스레드로 설정
        t.start();  // 스레드 시작

        // main 메소드 종료 메시지
        System.out.println("main() 종료");
    }
}
