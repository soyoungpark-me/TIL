package _01_Thread;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZipSingleThread {
    public static void zipFile(File input) {
        if (!input.getName().endsWith(".gz")) {
            File output = new File(input.getParent(), input.getName() + ".gz");
            if (!output.exists()) {
                try (
                        InputStream in = new BufferedInputStream(new FileInputStream(input));
                        OutputStream out = new BufferedOutputStream(
                                new GZIPOutputStream(new FileOutputStream(output)));
                ) {
                    int b;
                    while ((b = in.read()) != -1) out.write(b);
                    out.flush(); // 끝나면 비워준다
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
    public static void main(String args[]) {
        for (String filename : args) {
            File f = new File(filename);
            if (f.exists()) {
                if (f.isDirectory()) {
                    File[] files = f.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (!files[i].isDirectory()) {
                            zipFile(files[i]);
                        }
                    }
                } else {
                    zipFile(f);
                }
            }
        }
    }
}
