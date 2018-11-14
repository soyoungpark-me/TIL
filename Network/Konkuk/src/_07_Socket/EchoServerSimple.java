package _07_Socket;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class EchoServerSimple {
	
	public final static int PORT = 2000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(500);
		
		try (ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket connection = server.accept();
					Callable<Void> task = new EchoTask(connection); // task를 생성한다.
					pool.submit(task);
				} catch (IOException ex) {
					
				}
			}
		} catch (IOException ex) {
			System.err.println("Couldn't start server");
		}

	}
	
	private static class EchoTask implements Callable<Void> {
		private Socket connection;
		
		EchoTask(Socket connection) {
			this.connection = connection;
		}
		
		@Override
		public Void call() throws IOException { // 실행해야 하는 핵심 부분
			try {
				// Input과 Output 스트림을 모두 가져온다.
				InputStream in = new BufferedInputStream(connection.getInputStream());
				OutputStream out = connection.getOutputStream();

				int c;
				while ((c = in.read()) != -1) {
					// Input에서 읽어들인 내용을 한 바이트 씩 가져와 그대로 Output으로 써낸다.
					// 즉, 클라이언트가 보낸 것을 그대로 출력한다.
					out.write(c);
					out.flush();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			} finally {
				connection.close();
			}
			return null;
		}
	}

}