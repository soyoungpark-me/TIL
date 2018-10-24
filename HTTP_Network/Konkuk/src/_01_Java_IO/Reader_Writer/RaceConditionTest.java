/******************************************************************************
' 파일명    : RaceConditionTest.java
' 작성자    : 201411203 박소영
******************************************************************************/

package _01_Java_IO.Reader_Writer;

import java.io.*;
import java.security.*;
import javax.xml.bind.*;

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
			
			while (din.read() != -1);
			
			din.close();
			
			//calculate hash
			digest = sha.digest();
			//Build a string with format "filename: key"
			StringBuilder result = new StringBuilder(filename);
			result.append(": ");
			result.append(DatatypeConverter.printHexBinary(digest));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException ex) {}
			System.out.println(result + " in run()");
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
	}
	public byte[] getDigest() {
		return digest;
	}
}

public class RaceConditionTest {
	///*
	public static void main(String[] args) throws InterruptedException {
		for (String filename : args) {
			//Calculate the digest
			ReturnDigest dr = new ReturnDigest(filename);
			dr.start();
			dr.join(); // join을 통해 ReturnDigest이 작업을 모두 끝낼때까지 기다리도록 합니다.
			
			// Now print the result
			StringBuilder result = new StringBuilder(filename);
			result.append(": ");
			byte[] digest = dr.getDigest();
			System.out.println(digest);
			result.append(DatatypeConverter.printHexBinary(digest));
			System.out.println(result+"\n");
		}
	}//*/
}
