import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataConverter {

    public static String[] readTxtFile (String file){
        int howManyWords = 0;
        try {
            Scanner s1 = new Scanner(new File(file));
            while (s1.hasNextLine()){
                howManyWords++;
                s1.next();
            }
            String[] words = new String[howManyWords];

            Scanner s2 = new Scanner(new File(file));
            for (int i = 0; i < howManyWords; i++) {
                words[i] = s2.next();
            }
            return words;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
