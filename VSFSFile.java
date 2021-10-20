import java.nio.file.Path;
import java.util.ArrayList;

public class VSFSFile {
    private char attribute; // - = file, d = folder
    private int hardLink; // number of directories in its folder
    private Path path;
    private int subDirectoriesNum;
    private int subFilesNum;
    private ArrayList<VSFSFile> subDirectories = new ArrayList<>();
    private int size;
    //date time
    //make size

    public void VFSMFile(boolean attribute, Path path){
        //If directory
        if(attribute){
            this.attribute = 'd';
        }
        else{
            this.attribute = '-';
        }
        this.hardLink = 0;
        this.path = path;
        this.subDirectoriesNum = 0;
        this.subFilesNum = 0;
        if(!attribute){
            this.subDirectories = null;
        }
        this.size = 0;
    }
}
