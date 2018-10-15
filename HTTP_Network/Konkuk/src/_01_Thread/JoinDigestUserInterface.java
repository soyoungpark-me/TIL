package _01_Thread;

import javax.xml.bind.DatatypeConverter;

// 스레드를 조인해 경쟁 조건을 회피한다.
public class JoinDigestUserInterface {
    public static void main(String[] args) {
        ReturnDigest[] digestThreads = new ReturnDigest[args.length];

        for (int i=0; i<args.length; i++) {
            digestThreads[i] = new ReturnDigest(args[i]);
            digestThreads[i].start();
        }

        for (int i=0; i<args.length; i++) {
            try {
                digestThreads[i].join();    // 스레드가 시작한 순서와 같은 순서로 조인시킨다.
                // 따라서 작업을 끝마친 순서가 아닌, 실행 시 전달된 인자와 같은 순서로 결과가 출력된다.
                // 모든 스레드들의 작업이 모두 종료되어야 다음 라인을 실행할 수 있다.

                StringBuffer result = new StringBuffer(args[i]); //Builder가 아니라 Buffer를 써도 된다.
                result.append(": ");
                byte[] digest = digestThreads[i].getDigest();
                result.append(DatatypeConverter.printHexBinary(digest));
                System.out.println(result);
            } catch (InterruptedException e) {
                System.err.println("Thread Interrupted before completion");
            }
        }
    }
}
