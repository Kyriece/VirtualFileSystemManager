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
        if(args[0].equals("VSFS")){
            try {
                vsfsFile.run(args);
            } catch (IOException e) {
                System.err.println("File or Directory does not exist");
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Invalid commands! Refer to man manual");
            }
        }
    }
}
