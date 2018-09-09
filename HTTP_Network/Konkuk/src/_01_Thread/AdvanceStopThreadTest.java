package _01_Thread;

class AdvanceStopThread implements Runnable {

    @Override
    public void run() {
        try {
            // isInterrupted 메소드를 while문 조건으로 사용.
            // 만약 이 스레드에서 interrupt() 메소드를 호출하면
            // isInterrupted 메소드는 ture를 리턴해서 while문을 빠져나온다!

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is alive..");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        } finally {
            // 마무리 할 일이 있다면 이곳에서 정리!
            System.out.println("Thread id daed..");
        }
    }
}

public class AdvanceStopThreadTest {
    public static void main(String[] args) {
        System.out.println("# Start AdcancedStopThreadTest.java");
        AdvanceStopThreadTest astt = new AdvanceStopThreadTest();
        astt.process();
    }

    public void process() {
        // AdvancedStopThread 인스턴스를 생성한 후 이 인자를
        // 파라미터로 받는 스레드 인스턴스르 생성한 후에 출발!
        AdvanceStopThread ast = new AdvanceStopThread();
        Thread thread = new Thread(ast);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // AdvancedStopThread를 정지시킴!
        thread.interrupt();
    }
}
