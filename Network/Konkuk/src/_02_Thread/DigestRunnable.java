package _02_Thread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestRunnable implements Runnable {
    private String filename;

    public DigestRunnable(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);

            while (din.read() != -1);   // 끝날 때까지 읽어들인다.
            din.close();    // 끝나면 스트림을 닫아준다.
            byte[] digest = sha.digest();   // 암호화한 결과를 저장한다.

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest)); // 16진수로 변환한 결과를 뒤에 붙여준다.
            System.out.println(result);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        for (String filename: args) {   // 각 인자별로 파일 이름을 하나씩
            DigestRunnable dr = new DigestRunnable(filename);   // Runnable 객체를 생성한 다음에
            Thread t = new Thread(dr);  // 생성한 스레드의 생성자 인자로 넘긴 뒤
            t.start();  // 시작한다.
        }
    }
}
