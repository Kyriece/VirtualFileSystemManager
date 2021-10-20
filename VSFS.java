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
    final char FILE_SYMBOL = '@';
    final char FOLDER_SYMBOL = '=';
    final char DELETED_SYMBOL = '#';
    final int NOTES_NAME = 2;
    public void VFSM(){
    }

    public void run(String[] args) throws IOException{

        //Load notes file
        FS = new File(args[NOTES_NAME]);
        // Scanner myReader = new Scanner(notesFile);

        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        ArrayList<String> FSContent = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            FSContent.add(line);
        }
        bufferedReader.close();
        System.out.println(FSContent);

        if(FS.exists()){
            if(!FSContent.get(0).equals("NOTES V1.0")){
                System.out.println("Invalid notes file");
            } 
            else{
                commandTerminal(args);
            }
        }

        
        // myReader.close();


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
                    mkDir(input.get(input.size()-1));
                    break;
                case ("rm"):
                    rm(input.get(input.size()-1));
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

    public void mkDir(String directoryName) throws IOException {

        ArrayList<String> directories = new ArrayList<String>(Arrays.asList(directoryName.split("/")));
        String path = "";
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));

        //Checks existance of all subdirectories
        for(String directory : directories){

            //Creates possible permuations of subdirectories
            path += directory + "/";

            // Creates directory
            if(!pathExist(FOLDER_SYMBOL+ path)){
                writer.println();
                writer.print(FOLDER_SYMBOL+ path);
            }
        }
        writer.flush();
        writer.close();
    }

    public boolean pathExist(String path) throws IOException{
        boolean existance = false;
        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        //Reads until it finds matching path
        while ((line = bufferedReader.readLine()) != null && existance == false) {
            if(line.equals(path)){
                existance= true;
            }
        }
        reader.close();
        System.out.println(existance);
        return existance;
    }

    public void rm(String filePath) throws IOException{
        ArrayList<String> tempLines = new ArrayList<>(); 
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));

        //Checks if file exist
        if(pathExist(FILE_SYMBOL + filePath)){

            //Copies all lines excluding matching filedirectory to tempLines AND gets linecount
            tempLines = createFSCopy();
            int targetLine = getTargetLineNumber(FILE_SYMBOL + filePath);

            //Empties file
            new FileWriter(FS, false).close();

            //Writes copy to new file, replacing targetLine with new symbol
            for(int i = 0; i < tempLines.size(); i++){
                if(i == targetLine){
                    writer.println(DELETED_SYMBOL + filePath);
                }
                else{
                    writer.println(tempLines.get(i));
                }
            }
        }
        writer.flush();
        writer.close();
    }

    public ArrayList<String> createFSCopy() throws IOException{
        ArrayList<String> tempLines = new ArrayList<>(); 
        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
            while ((line = bufferedReader.readLine()) != null){
                tempLines.add(line);
            }
        bufferedReader.close();
        return tempLines;
    }

    public int getTargetLineNumber(String filePath) throws IOException{
        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        int lineCount = 0;
        int targetLine = 0;

        while ((line = bufferedReader.readLine()) != null){
            if(line.equals(filePath)){
                targetLine = lineCount;
            }
            lineCount++;
        }
        bufferedReader.close();
        return targetLine;
    }
}
