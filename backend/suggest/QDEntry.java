import java.util.*;
import java.io.*;

public class QDEntry {
    public static void main(String[] args) {
        String wholeFilename = args[0];
        int offset = Integer.parseInt(args[1]);
        String partFilename = args[2];

        String whole;
        String part;

        try {
            whole = new Scanner(new File(wholeFilename)).useDelimiter("\\Z").next();
            part = new Scanner(new File(partFilename)).useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.err.println("failed with I/O error");
            return;
        }

        MainEntrance ent = new MainEntrance(whole, part, offset);
        
        try {
            ent.Synthesize();
        } catch (InterruptedException e) {
            System.err.println("failed with interrupted error");
            return;
        } catch (Exception e) {
            System.err.println("failed with unknown error");
            e.printStackTrace();
            return;
        }
    }
}
