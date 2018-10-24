package _02_Thread;

import java.io.*;
import java.util.zip.*;

public class GZipRunnableTest implements Runnable {

	private final File input;
	
	public GZipRunnableTest (File input) {
		this.input = input;
	}
	
	@Override
	public void run() {
		if (!input.getName().endsWith(".gz")) { // .gz로 끝날 경우에는 이미 압축이 된 경우이다.
			File output = new File(input.getParent(), input.getName() + ".gz"); // 결과물의 끝에 .gz를 붙여준다.
			if (!output.exists()) { // output이 존재할 경우에는
				try (
						InputStream in = new BufferedInputStream(new FileInputStream(input));
						OutputStream out = new BufferedOutputStream(
								new GZIPOutputStream(new FileOutputStream(output)));
						// in, out 스트림을 먼저 열어주고
				) {
					int b;
					while ((b = in.read()) != -1) out.write(b); // 읽어들이는 값이 있을 경우 스트림에 출력한 뒤
					out.flush(); // 끝나면 비워준다
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
		}
	}
}
