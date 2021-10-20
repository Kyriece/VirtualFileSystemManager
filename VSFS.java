import java.io.BufferedReader;
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
        Scanner myReader = new Scanner(notesFile);
        // System.out.println();
        // if (notesFile.exists()) {
        //     while(myReader.hasNextLine()){
        //         System.out.println(myReader.nextLine());
        //     }
        // } else {
        //     myReader.close();
        //     throw new IOException();
        // }

        if(notesFile.exists()){
            if(!myReader.hasNext() || !myReader.nextLine().equals("NOTES V1.0")){
                System.out.println("Invalid notes file");
            }
            else{
                commandTerminal(args);
            }
        }
        myReader.close();


        // myReader.close();
        //Input commands
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
        for(String directory : directories){

            //Creates possible permuations of subdirectories
            path += directory + "/";

            // Creates directory
            if(!pathExist("@" + path)){
                blah.println();
                blah.print("@" + path);
            }
        }
        blah.flush();
        blah.close();
    }

    public boolean pathExist(String path) throws IOException{
        boolean existance = false;
        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if(line.equals(path)){
                existance= true;
            }
        }
        reader.close();
        System.out.println(existance);
        return existance;
    }

    public void rm(){

    }
}
