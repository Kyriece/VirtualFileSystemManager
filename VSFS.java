import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VSFS {

    private File FS;
    final int notesName = 2;
    public void VFSM(){
    }

    public void run(String[] args) throws IOException{

        //Load notes file
        File notesFile = new File(args[notesName]);
        FS = new File(args[notesName]);
        // Scanner myReader = new Scanner(notesFile);
        // System.out.println();
        // if (notesFile.exists()) {
        //     while(myReader.hasNextLine()){
        //         System.out.println(myReader.nextLine());
        //     }
        // } else {
        //     myReader.close();
        //     throw new IOException();
        // }

        // myReader.close();
        //Input commands
        try {
            commandTerminal(args);
        } catch (IOException e) {
            System.out.println("Invalid Arguement... Exiting...");
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


    public void commandTerminal(String[] args) throws IOException{
        // ArrayList<File> fileDirectory = new ArrayList<>();
        // Get input
        //Read command
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(args));
        String command = input.get(1).toLowerCase();
        System.out.println(command);
        if(VFSMcommandCheck(input)){
            switch(command){
                case ("copyin"):

                    break;
                case ("copyout"):

                    break;
                case ("mkdir"):
                    //Creates file with ID inputted
                    mkDir(input.get(notesName), input.get(input.size()-1));
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
    }

    public boolean VFSMcommandCheck(ArrayList<String> input){
        boolean VFSMcommand = false;
        System.out.println(input.get(0));
        if(input.get(0).toLowerCase().equals("vsfs")){
            VFSMcommand = true;
        }
        return VFSMcommand;
    }

    public void mkDir(String notesName, String fileName) throws IOException {

        ArrayList<String> directories = new ArrayList<String>(Arrays.asList(fileName.split("/")));
        String path = "";
        PrintWriter blah = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));

        //Checks existance of all subdirectories
        Scanner myReader = new Scanner(FS);
        for(String directory : directories){
            //Creates possible permuations of subdirectories
            boolean directoryExist = false;
            directory = "@" + directory + "/";
            path += directory.replace("@", "");
            System.out.println("DIRECTORY = " + directory);
            System.out.println("PATH = " + path);
            //Checks if possible subdirectory already exist
            while(myReader.hasNext()){
                if(myReader.nextLine().equals(fileName)){
                    directoryExist = true;
                }
            }
            //Creates directory
            if(!directoryExist){
                blah.println("@" + path);
            }
        }
        myReader.close();
        
        blah.flush();
        blah.close();
    }
}
