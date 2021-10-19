import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VSFM {

    private char attribute; // - = file, d = folder
    private String hardLink; // directory of the file?
    private int subDirectories;
    private int subFiles;
    private int size;
    //date time
    //make size

    public void VFSM(){
    }

    public void run(){
        boolean exit = false;
        while(!exit){
            try {
                exit = commandTerminal();
            } catch (IOException e) {
                exit = true;
                System.out.println("Invalid Arguement... Exiting...");
            }
        }
        // File testFile = new File("filename.txt");
        // try {
        //     System.out.println(Files.getAttribute(testFile.toPath(), "unix:nlink"));
        // } catch (IOException e1) {
        //     e1.printStackTrace();
        // }
        // try {
        //     Scanner myReader = new Scanner(testFile);
        // } catch (FileNotFoundException e) {
        //     e.printStackTrace();
        // }
        // if (testFile.exists()) {
        //     System.out.println("File name: " + testFile.getName());
        //     System.out.println("Absolute path: " + testFile.getAbsolutePath());
        //     System.out.println("Writeable: " + testFile.canWrite());
        //     System.out.println("Readable " + testFile.canRead());
        //     System.out.println("File size in bytes " + testFile.length());
        //   } else {
        //     System.out.println("The file does not exist.");
        //   }
    }


    public boolean commandTerminal() throws IOException{
        ArrayList<File> fileDirectory = new ArrayList<>();
        // Get input
        System.out.println("Please input a command");
        Scanner scanner = new Scanner(System.in);
        String scan = scanner.nextLine();

        boolean exitStatus = false;
        //Read command
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(scan.split(" ")));
        String command = input.get(1).toLowerCase();
        if(VFSMcommandCheck(input)){
            switch(command){
                case ("copyin"):

                    break;
                case ("copyout"):

                    break;
                case ("mkdir"):
                    //Creates file with ID inputted
                    createFile(input.get(input.size()-1));
                    break;
                case ("rm"):

                    break;
                case ("rmdir"):

                    break;
                case ("defrag"):

                    break;
                case ("index"):
                    break;
            }
        }
        return exitStatus;
    }

    public boolean VFSMcommandCheck(ArrayList<String> input){
        boolean VFSMcommand = false;
        if(input.get(0).toLowerCase().equals("vfsm")){
            VFSMcommand = true;
        }
        return VFSMcommand;
    }

    public void createFile(String fileName) throws IOException {

        //Checks to see if file or directory is to be created
        boolean isDirectory = false;
        if(fileName.substring(fileName.length()-1).equals("/")){
            isDirectory = true;
        }
        if(isDirectory){
            File newDirectory = new File(fileName);
            newDirectory.mkdirs();
            System.out.println("Made Directory");
        }
        else{
            File newFile = new File(fileName);
            newFile.createNewFile();
            System.out.println("Made File");
        }
    }
}
