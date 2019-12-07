import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

import java.util.StringTokenizer;

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
    /* 'tabCounter' tracks the spacing of each line in order to define scope */
    private int tabCounter = 2;

    public PythonToJavaConvertReader(String fileName, String folder){
        this.fileName = fileName;
        /*
         * Creates by default a file with the same name as the file that was provided
         * and creates an instance of 'PythonToJavaConvert' for the name.
         */
        fileNames.put(this.fileName, new PythonToJavaConvert(fileName, folder, true));
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
        String currentLine = null;
        try {
            File fileName = new File(this.fileName);
            Scanner scan = new Scanner(fileName);
            while(scan.hasNextLine()){
                currentLine = scan.nextLine();
                readLine(currentLine);
                // System.out.println(currentLine);
            }
            fileNames.get(this.fileName).closeFile();
            scan.close();
        } catch(Exception e){
            System.out.println("ERROR: readPythonFileContents - " + e.getMessage());
            System.out.println(currentLine);
        }
    } // readPythonFileContents


    /*
     * Method: 'readLine'
     * Arguments: 
     * String text - line being read and converted
     * 
     * Description: reads each line and converts it to Java and saves it to the
     * appropriate file
     */

    public boolean readLine(String text){
        /* 'line' tracks all code found on each line */
        String line = "";
        /* 'comment' tracks any comments that might be made on a line */
        String comment = "";

        /*
         * Tracking variables to help figure out if key characters are within specific
         * sets of characters or if they are actual code. 
         *
         * If when checked, one of the variables is greater than '0' it is inside
         * of the respective symbol, if it is '0' then all opening and closings 
         * are accounted for and the key character is outside of the respective symbol
         *
         * Characters Filtered:
         * - ()
         * - ""
         * - ''
         */
        int parenthese = 0;
        int singleQuote = 0;
        int doubleQuote = 0;
        
        /* Checks if the line has a comment in it or not. */
        if(text.contains("#")){
            /* Calls 'lineComment' and sets the return to 'returnString' */
            String[] returnString = fileNames.get(this.fileName).lineComment(text);
            /* Sets returned values to appropriate matching values */
            text = returnString[0];
            comment = returnString[1];
        }

        /*
         * Runs through the file and looks for key words in Python that do not exist
         * in Java and converts them preemptively.
         */
        text = fileNames.get(this.fileName).cleanFile(text);

        /*
         * checks with the current scope of the file to find out if lines need
         * to have their scope reduced.
         */
        if(fileNames.get(this.fileName).checkScope(text)){
            return false;
        }

        /* 
         * Adds proper indentation to each line.
         * Generated before any code so that spacing is not altered by methods
         * that increase the number of tabs.
         */
        String tabs = fileNames.get(this.fileName).addTabs();


        /* Containing Key Words */

        /* Checks for 'elif' statements and for ':' which all elif statements have. */
        // if(text.contains("elif") && text.contains(":")){
        //     line = fileNames.get(this.fileName).elifStatement(text);
        // /* Checks for 'if' statements and for ':' which all if statements have. */
        // } else 
        if(text.contains("if") && text.contains(":")){
            line = fileNames.get(this.fileName).ifStatement(text);
        } 

        /* Non-Containing Key Words */

        /* Checks if the line has a print statement in it or not. */
        if(text.contains("print(")){
            line = fileNames.get(this.fileName).linePrint(text);
        }


        /* Adds a ';' to all lines that need one. */
        line = fileNames.get(this.fileName).addLineEnding(line);

        /*
         * Calls 'addSpacing' which creates a new line whenever a line gets longer
         * than 80 characters long and gives it 'tabs', 'line' and 'comment'. 
         * Assigns return to 'text'.
         */
        text = addSpacing(tabs, line, comment);
        /* Saves 'line' and 'comment' to the file */
        fileNames.get(this.fileName).saveTextToFile(tabs + line + comment);
        return true;
    } // readLine


    /*
     * Method: 'addSpacing'
     * Arguments: 
     * String tabs - amount of space before code
     * String line - the code that will be saved to the Java file
     * String comment - the comment on each line
     * 
     * Description: 'addSpacing' creates a new line whenever a line gets longer
     * than 80 characters long. 
     */

    public String addSpacing(String tabs, String line, String comment){
        return tabs + line + comment;
    } // addSpacing
} // PythonToJavaConvertReader
