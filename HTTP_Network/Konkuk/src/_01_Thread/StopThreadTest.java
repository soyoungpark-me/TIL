package _01_Thread;

class StopThread implements Runnable {
    private boolean stopped = false;

    @Override
    public void run() {
        while (!stopped) {
            // stopped 플래그를 while문 조건으로 사용한다.
            System.out.println("Thread is alive..");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("THread is dead..");
    }

    public void stop() { // 이 메소드를 호출해 StopThread를 멈춘다.
        stopped = true;
    }
}

public class StopThreadTest {
    public static void main(String[] args) {
        System.out.println("# Start StopThreadTest.java");
        StopThreadTest stt = new StopThreadTest();
        stt.process();
    }

    public void process() {
        // StopThread 인스턴스를 생성한 후,
        // 이 인자르르 파라미터로 받는 스레드 인스턴스를 생성한 후 출발!
        StopThread st = new StopThread();
        Thread thread = new Thread(st);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        st.stop();
    }
}
