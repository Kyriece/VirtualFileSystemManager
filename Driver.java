import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args)
    {
        VSFS vsfsFile = new VSFS();
        try {
            vsfsFile.run(args);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Failed");
            e.printStackTrace();
        }
        
    }
}
