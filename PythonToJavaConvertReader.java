import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

import java.util.HashMap;


/*
 * Class: 'PythonToJavaConvertReader'
 * Arguments: 
 * String fileName - the name of the file that has been selected to be converted.
 * 
 * Description - class will read through a given file and then instruct the seperation
 * of the file into subsections based on what is read. Additionally, class will track
 * all files created by this.
 */
public class PythonToJavaConvertReader {
    private String fileName = "";
    /* 
     * 'fileNames' tracks all the files that have been created in responce to the
     * converting of the specified file.
     */
    private HashMap<String, PythonToJavaConvert> fileNames = new HashMap<String, PythonToJavaConvert>();

    public PythonToJavaConvertReader(String fileName){
        this.fileName = fileName;
        /*
         * Creates by default a file with the same name as the file that was provided
         * and creates an instance of 'PythonToJavaConvert' for the name.
         */
        fileNames.put(this.fileName, new PythonToJavaConvert(fileName));
        // System.out.println(fileNames.get("fileName"));
    }

    /*
     * Method: 'readPythonFileContents'
     * Arguments: NONE
     *
     * Description: Loops through the given file and sends off each line to
     * be converted.
     *
     * TODO: Each line needs to be counted for tabs and tracked so that brackets
     * can be placed
     */
    public void readPythonFileContents(){
        try {
            File fileName = new File(this.fileName);
            Scanner scan = new Scanner(fileName);
            String currentLine = null;
            while(scan.hasNextLine()){
                currentLine = scan.nextLine();
                readLine(currentLine);
                // System.out.println(currentLine);
            }
            scan.close();
        } catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /*
     * 'readLine' will convert each line to java and then instruct the saving
     * of the file.
     */
    public boolean readLine(String text){
        /* 'line' tracks all code found on each line */
        String line = "";
        /* 'comment' tracks any comments that might be made on a line */
        String comment = "";

        /* 
         * COMMENTS: '#'
         * Cases:
         * # alone
         * # with satement after
         * # multiple
         * # after statement
         */
        /* Checks if the line has a comment in it or not. */
        if(text.contains("#")){
            String[] parts = text.split("#", 2);
            for(int i = 0; i < parts.length; i++){
                /* Resets 'comment' to be empty */
                comment = "";

                /* If there is something after a comment tag... */
                if(parts[i].length() > 1){
                    System.out.println(parts[i]);
                    comment += "/* " + parts[i] + " */";
                }



                /* Adds everything after a comment in the file to 'comment' */
                // try {
                //     for(int j = i + 1; j < parts.length; j++){
                //         comment += parts[j];
                //     }
                //     // System.out.println(comment);
                //     /* Adds comment tags to each 'comment' */
                //     comment = "/* " + comment + " */";
                //     // System.out.println(comment);
                // } catch(Exception e){
                //     System.out.println("readLine: ERROR - Comments - " + e);
                // }

                // /* Adds everything before a comment in a file to 'line' */
                // try {
                //     for(int k = i - 1; k >= 0; k--){
                //         line += parts[k];
                //     }
                // /*
                //  * If there is nothing before the comment then it is alone on the
                //  * line and there is no code present.
                //  */
                // } catch(IndexOutOfBoundsException e){
                //     /* Comment is only thing on line */
                //     /* Sends 'comment' to the file to be saved. */
                //     fileNames.get(this.fileName).saveTextToFile(comment);
                // } catch(Exception e){
                //     System.out.println("readLine: ERROR - Comment - " + e);
                //     return false;
                // }
            }
            fileNames.get(this.fileName).saveTextToFile(comment);
        }
        return true;
    }
}
