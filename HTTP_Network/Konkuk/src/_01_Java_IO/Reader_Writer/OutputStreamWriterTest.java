package _01_Java_IO.Reader_Writer;

import java.io.*;

public class OutputStreamWriterTest {
    public static void main(String[] args) throws IOException {
        File file = new File("abcc.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        BufferedWriter bw1 = new BufferedWriter(osw);
        PrintWriter pw1 = new PrintWriter(bw1);

        pw.println("파일에 출력되었음.");
        pw1.println("화면에 출력되었음.");

        pw.close();
        pw1.close();
    }
}
