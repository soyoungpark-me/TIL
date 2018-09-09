package _02_Java_IO;

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
			
			// Now print the result
			StringBuilder result = new StringBuilder(filename);
			result.append(": ");
			byte[] digest = dr.getDigest();
			System.out.println(digest);
			result.append(DatatypeConverter.printHexBinary(digest));
			System.out.println(result);
		}

	}//*/
	
}
