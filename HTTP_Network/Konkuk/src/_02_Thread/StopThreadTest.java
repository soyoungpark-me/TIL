package _02_Thread;

class StopThread implements Runnable {
    private boolean stopped = false;    // 조건문을 빠져나가기 위한 플래그 변수

    @Override
    public void run() {
        while (!stopped) {
            // stopped 플래그를 while문 조건으로 사용한다.
            System.out.println("Thread is alive..");

            try {
                Thread.sleep(500);  // 0.5초 간격으로 출력한다.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread is dead.."); // stopped가 true가 되면 dead라고 출력!
    }

    public void stop() { // 이 메소드를 호출해 StopThread를 멈춘다.
        stopped = true;
    }   // stop 함수를 호출해 stopped를 true로 바꾼다.
}

public class StopThreadTest {
    public static void main(String[] args) {
        System.out.println("# Start StopThreadTest.java");
        StopThreadTest stt = new StopThreadTest();
        stt.process();
    }

    public void process() {
        // StopThread 인스턴스를 생성한 후,
        // 이 인자를 파라미터로 받는 스레드 인스턴스를 생성한 후 출발!
        StopThread st = new StopThread();
        Thread thread = new Thread(st);
        thread.start();

        try {
            Thread.sleep(1000); // 1초 간 대기한 다음
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        st.stop();  // 멈추게 한다.
    }
}
