import utilityPackage.FileUtility;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

/* Date Imports */
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Class: 'PythonToJavaConvert'
 * Arguments: 
 * String fileName - the name of the file that has been selected to be converted.
 * 
 * Description - takes each line and converts it based on predefined logic.
 */
public class PythonToJavaConvert {
    private String fileName = null;

    /*
     * Constructor: when 'PythonToJavaConvert' is instanciated, a file
     * with the same name as the given file will automaticly be created so that
     * it can be writen to as each line is compiled
     */
    public PythonToJavaConvert(String fileName){

        /* 
         * assigns 'this.fileName' to the return of 'removeExtention' which removes
         * the extention attached to 'fileName' so that saving the file is easier.
         */
        this.fileName = FileUtility.removeExtention(fileName);
        /* 
         * assigns 'this.fileName' to the return of 'removeDirectories' which removes
         * the directories from the file so that the user can be asked where they
         * want the file to be saved.
         */
        this.fileName = FileUtility.removeDirectories(this.fileName);

        /*
         * Upon the constructor being activated a file by the name of what is
         * given will be created and saved.
         */
        try {
            // PrintWriter printWrite = new PrintWriter(new FileWriter("ConvertedFiles/" + this.fileName + ".java", false));
            PrintWriter printWrite = new PrintWriter(new FileWriter("ConvertedFiles/" + this.fileName + ".java", false));
            /*
             * Loads a basic comment in the file to ensure that it is created
             * and provide evidence that it is working.
             */
            printWrite.println("/* File Automaticly Generated */");
            /* Generates time stamp to show when the file has been generated */
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();
            printWrite.println("/* Time: " + formatter.format(date) + " */");
            printWrite.println("");
            printWrite.close();
            System.out.println("\nFile Created.\n");
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public boolean saveTextToFile(String text){
        // System.out.println("SaveTextToFile Run");
        try {
            PrintWriter printWrite = new PrintWriter(new FileWriter("ConvertedFiles/" + this.fileName + ".java", true));
            
            printWrite.println(text);
            printWrite.close();
        } catch(Exception e) {
            System.out.println("ERROR: saveTextToFile failed");
            return false;
        }
        return true;
    }
}
