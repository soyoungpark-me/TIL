package _02_Thread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ReturnDigest extends Thread {
    private String filename;
    private byte[] digest;

    public ReturnDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);

            while(din.read() != -1); // 파일 전체 읽기
            din.close();

            digest = sha.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getDigest() { // 인코딩한 결과값을 반환하기 위해 선언한 메소드
        return digest;
    }
}

public class ReturnDigestUserInterface {
    public static void main(String args[]) {
        /*
        1) 답 없는 상황
        for (String filename: args) { // 각각 파일에 대해 새로운 ReturnDigest 스레드 생성
            // 1. 다이제스트 계산
            ReturnDigest dr = new ReturnDigest(filename);
            dr.start();

            // 2. 결과 출력
            // 이렇게 하면 해당 스레드의 digest 필드가 세팅되기 전에 getDigest() 함수가 호출되어
            // NullPointerException이 발생할 수 있다.
            // 이 코드의 동작 유무는 각 스레드의 getDigest() 메소드가 호출되기 전에 ReturnDigest 스레드의 실행이 먼저 끝나야 한다.
            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            byte[] digest = dr.getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
        */

        /*
        2) 굴러는 가지만 답 없는 상황
        메인 스레드가 작업의 종료 상태를 확인하느라 너무 바빠서 나머지 작업 스레드가 일하지 못한다.
        ReturnDigest[] digests = new ReturnDigest[args.length];

        for (int i=0; i<args.length; i++) {
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }

        for (int i=0; i<args.length; i++) {
            while(true) {
                byte[] digest = digests[i].getDigest();

                if (digest != null) {
                    StringBuilder result = new StringBuilder(args[i]);
                    result.append(": ");
                    result.append(DatatypeConverter.printHexBinary(digest));
                    System.out.println(result);
                    break;
                }
            }
        }
        */

        /*
        3. 폴링 : result가 설정되기 전에는 플래그 값을 대신 반환하도록 한다.
        즉, 동기화를 목적으로 상태를 주기적으로 검사해, 일정한 조건을 만족할 때까지 작업을 처리한다.
        작 동작하긴 하는데... 메인 스레드가 조건을 확인하느라 너무 바빠져 비효율적이다.

        ReturnDigest[] digests = new ReturnDigest[args.length];

        for (int i=0; i<args.length; i++) {
            digests[i] = new ReturnDigest(args[i]); // 각자 스레드를 할당한 다음에
            digests[i].start(); // 시작한다.
        }

        for (int i=0; i<args.length; i++) {
            while (true) {
                byte[] digest = digests[i].getDigest();

                if (digest != null) {   // 조건을 추가해 지속적으로 폴링한다.
                    StringBuilder result = new StringBuilder(args[i]);
                    result.append(": ");
                    result.append(DatatypeConverter.printHexBinary(digest));
                    System.out.println(result);
                    break;
                }
            }
        }
        */

        /*
        4. 클백 : 폴링보다 더 쉽고 효과적인 방법이다.
        메인 프로그램이 반복해 스레드의 상태를 검사하지 않고, 스레드가 작업이 끝났을 때 직접 메인 프로그램에게 알려준다.
        스레드가 작업이 끝났을 때 자신을 생성한 클래스를 다시 호출하는 방식을 콜백 (callback)이라고 한다.
        > CallbackDigestUserInterface.java 안에 있음!
         */
    }
}