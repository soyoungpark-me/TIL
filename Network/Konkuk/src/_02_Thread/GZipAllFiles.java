package _02_Thread;

import java.io.*;
import java.util.concurrent.*;

public class GZipAllFiles {

	public final static int THREAD_COUNT = 1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
		// 스레드 풀 생성
		
		for (String filename : args) {
			File f = new File(filename);
			if (f.exists()) { // 해당 파일 이름이 이미 존재한다면
				if (f.isDirectory()) { // 해당 파일 이름이 디렉토리라면
					File[] files = f.listFiles(); // 디렉토리 내부 파일들을 가져온다.
					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) { // 다시 해당 파일이 디렉토리인지 체크하고 아니라면
							Runnable task = new GZipRunnableTest(files[i]); // 압축하는 task를 생성하고
							pool.submit(task); // 스레드 풀에 집어넣는다.
						}
					}
				} else { // 디렉토리가 아닐 경우
					Runnable task = new GZipRunnableTest(f); // 압축하는 task를 생성하고
					pool.submit(task); // 스레드 풀에 집어넣는다.
				}
			}
		}
		pool.shutdown(); //notify the pool that no further tasks will be added and shutdown once finished

	}

}
