package _01_Thread;

class Producer extends Thread {
    private Buffer blank;

    public Producer(Buffer blank) {
        this.blank = blank;
    }

    public void run() {
        for (int i=0; i<10; i++) {
            blank.put(i); // 생산자 스레드는 계속해서 put 메소드 호출
            System.out.println("생산자: 생산 " + i);

            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {}
        }
    }
}

class Consumer extends Thread {
    private Buffer blank;

    public Consumer(Buffer c) {
        blank = c;
    }

    public void run() {
        int value = 0;

        for (int i=0; i<10; i++) {
            value = blank.get();
            System.out.println("소비자: 소비 " + value);
        }
    }
}

class Buffer {
    private int contents;
    private boolean available = false;

    // flag 역할을 하는 available 변수의 초기값을 false로 설정
    // 처음에는 생산자가 먼저 데이터를 가져다 놓아야 한다.

    public synchronized int get() {
        // 임계영역을 지정하는 메소드. 버퍼로부터 데이터를 get
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        System.out.println("소비자######### : 소비 " + contents);
        notify();
        available = false;

        // 데이터를 반환하기 전에 put()에 들어가기 위해 기다리는 메소드를 깨운다.
        return contents;
    }

    public synchronized void put (int value) {
        // 임계영역을 지정하는 메소드. 버퍼에 데이터를 get
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        contents = value;
        System.out.println("생산자######### : 생산 " + contents);
        notify();
        available = true;
        //값을 저장한 후, get 메소드에 들어가기 위해
        // 대기하고 있는 스레드 중 하나를 깨운다.
    }
}

public class ProducerConsumer {
    public static void main(String args[]) {
        Buffer c= new Buffer();
        Producer p1 = new Producer(c);
        Consumer c1 = new Consumer(c);

        p1.start();
        c1.start();
    }
}
