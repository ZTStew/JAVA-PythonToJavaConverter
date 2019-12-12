import variableDirectory.*;

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
    private int spacing = 4;
    private int line = 1;
    /* 
     * 'fileNames' tracks all the files that have been created in responce to the
     * converting of the specified file.
     */
    private HashMap<String, PythonToJavaConvert> fileNames = new HashMap<String, PythonToJavaConvert>();
    /*
     * Will be toggled to 'true' whenever a command that spans multiple lines is
     * found in the Python file.
     */
    private boolean multiline = false;

    public PythonToJavaConvertReader(String fileName, String folder){
        this.fileName = fileName;
        this.spacing = findFileSpacing(fileName);

        /*
         * Creates by default a file with the same name as the file that was provided
         * and creates an instance of 'PythonToJavaConvert' for the name.
         */
        this.fileNames.put(this.fileName, new PythonToJavaConvert(this.fileName, folder, this.spacing, true));
        // System.out.println(fileNames.get("fileName"));
    } // PythonToJavaConvertReader(Constructor)

    // Recursion Segment
    /*
     * Method: 'findFileSpacing'
     * Arguments:
     * String fileName - The name of the file being searched.
     *
     * Description: recursivly searches 'fileName' for the first instance of spacing
     * with characters afterwards. When this happens, the number of spaces in the
     * line will be returned and the returned value will be used as the standard
     * spacing rule for all generated files.
     */
    public int findFileSpacing(String fileName){
        try{
            /* Creates a Scanner to look through the file */
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            /* Ensures that the file is not empty */
            if(scan.hasNextLine()){
                /* Calls 'findFileSpacing(Scanner, String)' */
                return findFileSpacing(scan, scan.nextLine());
            }
        } catch(Exception e){}
        /* If the file is empty, the spacing rule defaults to 4 */
        return 4;
    } // findFileSpacing
    public int findFileSpacing(Scanner scan, String line){
        /* Checks if there is another line */
        if(scan.hasNextLine()){
            /* If the line is empty */
            if(line.length() < 1){
                return findFileSpacing(scan, scan.nextLine());
            }
            try {
                /* If the first character in 'line' is a space */
                if(line.charAt(0) == ' '){
                    /* Loops through 'line' */
                    for(int i = 0; i < line.length(); i++){
                        /*
                         * If a value other than space is found, it is the end of
                         * the spacing block and the value of 'i' is the number of
                         * spaces in each tab.
                         */
                        if(line.charAt(i) != ' '){
                            return i;
                        }
                    }
                }
                /*
                 * The first character in 'line' was something other than space
                 * and cannot be read for spacing information.
                 */
                return findFileSpacing(scan, scan.nextLine());
            } catch(Exception e){
                return 4;
            }
        } else {
            return 4;
        }
    } // findFileSpacing


    /*
     * Method: 'readPythonFileContents'
     * Arguments: NONE
     *
     * Description: Loops through the given file and sends off each line to
     * be converted.
     */
    public void readPythonFileContents(){
        String currentLine = null;
        try {
            File fileName = new File(this.fileName);
            Scanner scan = new Scanner(fileName);
            while(scan.hasNextLine()){
                currentLine = scan.nextLine();
                readLine(currentLine);
                this.line++;
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
        /* Checks for muli-line commenting */
        } 
        // else if(text.contains("\"\"\"")){
        //     Object[] returnStatus = fileNames.get(this.fileName).multiLine(text, multiline);
        //     line += (String)returnStatus[0];
        //     multiline = (boolean)returnStatus[1];
        // }

        /* Variable Handling */
        // if(text.contains("=")){
        //     fileNames.get(this.fileName).findVariable(text);
        // }

        /*
         * Runs through the file and looks for key words in Python that do not exist
         * in Java and converts them preemptively.
         */
        text = fileNames.get(this.fileName).cleanFile(text);

        // if(text.contains("elif")){
        //     text = fileNames.get(this.fileName).elifStatement(text);
        // }

        /*
         * checks with the current scope of the file to find out if lines need
         * to have their scope reduced.
         */
        fileNames.get(this.fileName).checkScope(text);
        // if(fileNames.get(this.fileName).checkScope(text)){
        //     return false;
        // }

        /* 
         * Adds proper indentation to each line.
         * Generated before any code so that spacing is not altered by methods
         * that increase the number of tabs.
         */
        String tabs = fileNames.get(this.fileName).addTabs();


        /* Containing Key Words */

        /* Checks for 'if' statements and for ':' which all if statements have. */
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

    /*
     * Method: 'returnMainFile'
     * Arguments: NONE
     * 
     * Description: returns the name of the folder being saved to and the name of
     * the file that has been saved.
     */

    public String[] returnMainFile(){
        String[] returnStatement = new String[2];
        returnStatement[0] = fileNames.get(this.fileName).getFolderName();
        returnStatement[1] = fileNames.get(this.fileName).getFileName();
        return returnStatement;
    } // returnMainFile
} // PythonToJavaConvertReader
