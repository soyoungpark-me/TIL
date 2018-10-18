import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MidTerm2018 {
    public static void main(String args[]) throws IOException {
        long startTime = System.currentTimeMillis(); // 시간 측정 시작

        double maxF = 0, minF = 5; // F의 최댓값과 최솟값
        String maxT = "", minT = "";
        int maxB = 0, maxD = 0; // f가 최대가 되는 값의 row의 b, d 숫자
        int minB = 0, minD = 0; // f가 최소가 되는 값의 row의 b, d 숫자

        for (int i=2; i<=29; i++) { // 해당 파일이 없을 경우에는 그냥 무시
            for (int j=100; j<=1000; j+=10) {
                System.out.println("b: " + i + ", d: " + j);
                String base = "http://home.konkuk.ac.kr/~leehw/Site/nptest/";
                String file = String.format("file b%d_d%d.txt", i, j);
                String encoded = file.replace(" ", "%20"); // 파일 이름 인코딩
                URL url = new URL(new URL(base), encoded);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        String[] list = inputLine.split(",");
                        for (int n = 1; n<list.length; n++){
                            double forCompare = Double.parseDouble(list[n]);
                            if (forCompare > maxF) { // 최댓값보다 크다면 대체하고 필드를 채움
                                maxF = forCompare;
                                maxT = list[0];
                                maxB = i;
                                maxD = j;
                            }
                            if (forCompare < minF) { // 최솟값보다 작다면 대체
                                minF = forCompare;
                                minT = list[0];
                                minB = i;
                                minD = j;
                            }
                        }
                    }
                    if (in != null) in.close();
                } catch (FileNotFoundException e) {}
            }
        }

        System.out.println(String.format("(a) b: %d, d: %d, t: %s, f: %f", maxB, maxD, maxT, maxF));
        System.out.println(String.format("(b) b: %d, d: %d, t: %s, f: %f", minB, minD, minT, minF));

        long endTime = System.currentTimeMillis(); // 실행이 끝난 시간
        long elapsedTime = endTime - startTime;
        // 첫줄부터 마지막줄까지 걸리는 시간을 초단위로 출력
        System.out.println(String.format("%d sec", TimeUnit.MILLISECONDS.toSeconds(elapsedTime)));
    }
}
