import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.SwingPropertyChangeSupport;

public class VSFS {

    private File FS;
    ArrayList<String> FSContent = new ArrayList<>();
    final char FILE_SYMBOL = '@';
    final char FOLDER_SYMBOL = '=';
    final char DELETED_SYMBOL = '#';
    final char FILE_CONTENT_SYMBOL = ' ';
    final int NOTES_NAME = 2;
    public void VFSM(){
    }

    public void run(String[] args) throws IOException{

        //Load notes file
        FS = new File(args[NOTES_NAME]);

        FileReader reader = new FileReader(FS);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            FSContent.add(line);
        }
        if(FSContent.size() == 1){
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));
            writer.println();
            writer.flush();
            writer.close();
        }
        bufferedReader.close();

        if(FS.exists()){
            if(!FSContent.get(0).equals("NOTES V1.0") || !contentRequirement()){
                System.err.println("Invalid notes file");
            } 
            else{
                commandTerminal(args);
            }
        }
       
    }

    public boolean contentRequirement(){
        for(int i = 1; i < FSContent.size() - 1; i++){
            int j = i+1;
            //Checks to see if its file content
            if(FSContent.get(j).substring(0,1).equals(String.valueOf(FILE_CONTENT_SYMBOL))){
                //Checks to see if previous line was either file content OR a file. 
                if(!FSContent.get(i).substring(0,1).equals(String.valueOf(FILE_SYMBOL)) && !FSContent.get(i).substring(0, 1).equals(String.valueOf(FILE_CONTENT_SYMBOL))){
                    return false;
                }
            }
        }
        return true;
    }
    public void commandTerminal(String[] args) throws IOException, FileNotFoundException{
        //Read command
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(args));
        String command = input.get(1).toLowerCase();
        if(VFSMcommandCheck(input)){
            switch(command){
                case ("list"):
                    printList();
                    break;
                case ("copyin"):
                    copyIn(input.get(input.size()-2), input.get(input.size()-1));
                    break;
                case ("copyout"):
                    copyOut(input.get(input.size()-2), input.get(input.size()-1));
                    break;
                case ("mkdir"):
                    //Creates new directory
                    mkDir(input.get(input.size()-1));
                    break;
                case ("rm"):
                    //Deletes file
                    rm(input.get(input.size()-1));
                    break;
                case ("rmdir"):
                    //Delets directory
                    rmDir(input.get(input.size()-1));
                    break;
                case ("defrag"):
                    defrag();
                    break;
            }
        }
    }

    public boolean VFSMcommandCheck(ArrayList<String> input){
        boolean VFSMcommand = false;
        if(input.get(0).toLowerCase().equals("vsfs")){
            VFSMcommand = true;
        }
        return VFSMcommand;
    }

    public void defrag() throws IOException{
        //Gets all deleted lines index
        ArrayList<Integer> deletedLines = getDeletedLines();
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));
        //Clears file
        new FileWriter(FS, false).close();
        //Rewrites new file content without the deleted lines
        for(int i = 0; i < FSContent.size(); i++){
            if(!deletedLines.contains(i)){
                writer.println(FSContent.get(i));
            }
        }
        System.out.println("Defragment completed!");
        writer.flush();
        writer.close();
    }

    public ArrayList<Integer> getDeletedLines(){
        ArrayList<Integer> targetLines = new ArrayList<>();
        int lineCount = 0;
        for(String line : FSContent){
            //Makes sure not to count deleted files
            if(line.contains(String.valueOf(DELETED_SYMBOL))){
                targetLines.add(lineCount);
            }
            lineCount++;
        }
        targetLines.addAll(getFilesLinesIndex(targetLines));
        return targetLines;
    }

    public void printList() throws IOException{
        for(String line : FSContent){
            // drwxr-xr-x+ 3 W8431514+ronvs W8431514+None 0 Oct 22 2020 usr/libexec/mc/extfs.d
            String fileType = line.substring(0, 1);
            if(!fileType.equals(String.valueOf(DELETED_SYMBOL))){
                if(!fileType.equals(String.valueOf(FILE_CONTENT_SYMBOL)) && !fileType.substring(0, 1).equals("N")){
                    // AAAAAAAAAAAAAAAAA 
                    if(fileType.equals(String.valueOf(FILE_SYMBOL))){
                        fileType = "-";
                    }
                    else{
                        fileType = "d";
                    }
                    System.out.print(fileType + PosixFilePermissions.toString(Files.getPosixFilePermissions(FS.toPath())) + " ");
                    // NNN
                    System.out.print(1 + " ");
                    // OOOOOOOOOO
                    System.out.print(Files.getOwner(FS.toPath()) + " ");
                    // GGGGGGGGGG
                    System.out.print(Files.getAttribute(FS.toPath(), "unix:gid") + " ");
                    // DATETIME
                    BasicFileAttributes file_att = Files.readAttributes(FS.toPath(), BasicFileAttributes.class);
                    SimpleDateFormat sd = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                    System.out.print(sd.format(file_att.creationTime().toMillis()) + " ");
                    // FILE
                    System.out.print(line.substring(1));
                    System.out.println();
                }
            }
            
        }
        
    }

    public void copyIn(String EF, String IF) throws IOException{
        //Deletes file if it already exist
        rm(IF);
        // Creates all necessary subdirectories
        ArrayList<String> IFSplit = new ArrayList<String>(Arrays.asList(IF.split("/")));
        String path = "";
        for (int i = 0; i < (IFSplit.size()-1); i++) {
            path += IFSplit.get(i) + "/";
        }
        mkDir(path);

        // Read external file
        File externalFile = new File(EF);
        FileReader reader = new FileReader(externalFile);
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        // Create file in FS
        writer.println(FILE_SYMBOL + IF);
        // Appends content to file (truncates)
        while ((line = bufferedReader.readLine()) != null) {
            if(line.length() > 254){
                writer.println(" " + line.substring(0, 254));
            }
            else{
                writer.println(" " + line);
            }
        }
        bufferedReader.close();
        writer.flush();
        writer.close();
    }

    public void copyOut(String IF, String EF) throws IOException{
        // Create file
        new FileWriter(EF, false).close();
        File newFile = new File(EF);
        // newFile.createNewFile();
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(newFile.getPath(), true)));


        //Read contents of IF
        ArrayList<Integer> indexOfContent = new ArrayList<>();
        if(pathExist(FILE_SYMBOL + IF)){
            indexOfContent = getTargetLineIndex(IF);
            for(int i = 1; i < indexOfContent.size(); i++){
                writer.println(FSContent.get(indexOfContent.get(i)).substring(1));
            }
        }
        writer.flush();
        writer.close();
        System.out.println("File created in external system");
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
            if(!pathExist(FOLDER_SYMBOL + path)){
                writer.println(FOLDER_SYMBOL+ path);
                System.out.println("File path created: " + path);
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
        return existance;
    }

    public void rm(String filePath) throws IOException{
        ArrayList<String> tempLines = new ArrayList<>(); 
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));

        //Checks if file exist
        if(pathExist(FILE_SYMBOL + filePath)){

            //Copies all lines excluding matching filedirectory to tempLines AND gets linecount
            tempLines = FSContent;
            ArrayList<Integer> targetLine = getTargetLineIndex(FILE_SYMBOL + filePath);

            //Empties file
            new FileWriter(FS, false).close();

            //Writes copy to new file, replacing targetLine with new symbol
            for(int i = 0; i < tempLines.size(); i++){
                if(targetLine.contains(i)){
                    System.out.println("Deleted file: " + tempLines.get(i));
                    writer.println(DELETED_SYMBOL + tempLines.get(i).substring(1));
                }
                else{
                    writer.println(tempLines.get(i));
                }
            }
        }
        writer.flush();
        writer.close();
    }

    public void rmDir(String filePath) throws IOException{
        
        //Gets the index of files that are within target filepath
        ArrayList<Integer> targetLines = new ArrayList<>();

        //Checks if folder exist
        if(pathExist(FOLDER_SYMBOL + filePath)){
            targetLines = getTargetLineIndex(filePath);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FS.getPath(), true)));
            ArrayList<String> tempLines = new ArrayList<>(); 

            tempLines = FSContent;
            new FileWriter(FS, false).close();

            // Replaces entries of matching index -> Deleting all subfiles / subfolders
            for (int i = 0; i < tempLines.size(); i++) {
                if (targetLines.contains(i)) {
                    System.out.println("Deleted Directory: " + tempLines.get(i));
                    writer.println(DELETED_SYMBOL + tempLines.get(i).substring(1));
                } else {
                    writer.println(tempLines.get(i));
                }
            }
            writer.flush();
            writer.close();
        }
        else{
            System.err.println("Directory does not exist");
        }
    }

    public ArrayList<Integer> getTargetLineIndex(String filePath) throws IOException{
        ArrayList<Integer> targetLines = new ArrayList<>();
        int lineCount = 0;
        for(String line : FSContent){
            //Makes sure not to count deleted files
            if(line.contains(filePath) && !line.contains(String.valueOf(DELETED_SYMBOL))){
                targetLines.add(lineCount);
            }
            lineCount++;
        }
        targetLines.addAll(getFilesLinesIndex(targetLines));
        return targetLines;
    }

    public ArrayList<Integer> getFilesLinesIndex(ArrayList<Integer> indexOfFiles){
        int fileContentIndex = 0;
        ArrayList<Integer> fileContent = new ArrayList<>();
        //Checks all deleted index's, and if they're followed by 'filecontent' then it adds them to the list 
        for(int fileIndex : indexOfFiles){
            fileContentIndex = fileIndex;
            fileContentIndex++;
            while(fileContentIndex < FSContent.size() && FSContent.get(fileContentIndex).substring(0, 1).equals(" ")){
                fileContent.add(fileContentIndex);
                fileContentIndex++;
            }
        }
        return fileContent;
    }
}
