package _03_Byte_Stream;

import java.io.*;

public class LineWriter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("사용법 : java LineWriter 파일명");
            System.exit(0);
        }

        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(new BufferedWriter(new FileWriter(args[0])));

            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println("읽어들인 문자열 : " + line);
                pw.println(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                pw.close();
            } catch (Exception e){}
        }
    }
}
