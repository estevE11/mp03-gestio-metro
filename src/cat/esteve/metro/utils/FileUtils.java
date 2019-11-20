package cat.esteve.metro.utils;

import javax.swing.*;
import java.io.*;

public class FileUtils {

    public static void writeFile(String str, String path) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(str);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);
            String str = reader.readLine();
            fr.close();
            reader.close();
            return str;
        } catch (FileNotFoundException e) {
            System.exit(0);
        } catch (IOException e) {
            System.exit(0);
        }

        return null;
    }
}
