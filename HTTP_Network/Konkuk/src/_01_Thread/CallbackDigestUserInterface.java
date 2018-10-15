package _01_Thread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CallbackDigest implements Runnable {
    private String filename;

    public CallbackDigest(String filename) {
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

            byte[] digest = sha.digest(); // 스레드 내부 변수에 저장하지 않는다.
            CallbackDigestUserInterface.receiveDigest(digest, filename);
            // 직접 메인 클래스의 메소드를 호출해 결과를 전달한다.

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class CallbackDigestUserInterface {
    // 이 메소드는 main() 메소드나 main() 메소드의 실행 과정 중에 있는 어떤 메소드에서도 호출되지 않는다.
    // 이미 독립적으로 실행 중인 각 스레드에 의해서 호출된다.
    // 메인 스레드가 아닌, 다이제스트를 계산하는 스레드 안에서 실행된다.
    public static void receiveDigest(byte[] digest, String name) {
        StringBuilder result = new StringBuilder(name);
        result.append(": ");
        result.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(result);
    }

    public static void main(String args[]) {
        for (String filename: args) {
            CallbackDigest cb = new CallbackDigest(filename);
            Thread t = new Thread(cb);
            t.start();
        }
    }
}
