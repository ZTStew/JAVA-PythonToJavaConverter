/**
 * Title: Python To Java Converter
 * Author: Zachary Stewart
 * Course Section: CMIS201-HYB1 (Seidel) Fall 2019
 * File: PythonToJavaConvertTest.java
 * Date: 9/29/19
 * Description: Program takes a program written in python (.py) and then converts
 * the program over to Java (.java) in a way that allows it to compile and run
 * without any errors.
 */

/*

Ideas:

take the file that is to be converted from the 'args' parameter 
then can take that file, read it, and convert it.

Make it so the user can outline an entire folder and then do the opperation. Potentally
recursive.

Potental Issues:

Python variables are unlisted, they are not specificly an int, string, boolean, etc.
Deciding if a variable is a number or if it is a character

Each class in Python will have to be given a different file in Java. In general,
the Python file should mirror a similar file structure as Java. However, this is
not enforced by Python.

Need way of telling the spacing rules that the user specified files
*/

/* Imports the Utility Package */
import utilityPackage.FileUtility;
import utilityPackage.HelpUtility;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.HashMap;

// javac PythonToJavaConvertTest.java && javac PythonToJavaConvert.java && PythonToJavaConvertReader.java && java PythonToJavaConvertTest TestFiles/Test1.py

public class PythonToJavaConvertTest {
    
    /* In args, the user should enter the name of the file they would like to convert */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        /* Deleter methods !!! Remove from final product !!! */

        // File deleteFile = new File("TestFiles/Test1.py");
        // if(deleteFile.delete()) { 
        //     System.out.println("File deleted successfully"); 
        // } else { 
        //     System.out.println("Failed to delete the file"); 
        // } 

        // File file1 = new File("PythonToJavaConvertTest.class");
        // File file2 = new File("PythonToJavaConvert.class");
        // File file3 = new File("PythonToJavaConvertReader.class");
        // file1.delete();
        // file2.delete();
        // file3.delete();

        /*
         * fileLocate will return 'false' until a file in python has been correctly
         * identified and located.
         */
        Boolean fileLocate = false;
        /*
         * 'fileName' will store the full name of the file. This will be used again
         * when it comes time to save the file.
         */
        String fileName = "";

        /* If the user input a value into 'args' */
        if(args.length > 0){
            fileName = args[0];
            /*
             * Calles 'FileUtility.isFile' which will return if the file exists
             * or not.
             *
             * Creates an array of objects named 'returnStatus' which will return
             * [boolean, String] where the boolean is a statement as to if the
             * given file exists and the String is the statement of what happened
             * to cause the return boolean.
             */
            Object[] returnStatus = FileUtility.isFile(args[0]);
            /* If the returned boolean (returnStatus[0]) is 'false' */
            if(!(Boolean)returnStatus[0]){
                /* Prints out the error for the user. */
                System.out.println(returnStatus[1]);
            }
            fileLocate = (Boolean)returnStatus[0];
        }

        /* 
         * If a file has still not been found or args is empty, program will prompt
         * the user for a valid file until a valid file has been provided.
         */
        while(!fileLocate){
            HelpUtility.about();
            /* Prompts the user for the name of the file */
            System.out.println((char)27 + "[1;36m" + "\nEnter the name of the folder along with the name"
                + " of the file you would like converted. Ex: \"Folder1/Folder2/Pythonfile.py\":" + (char)27 + "[00m");
            fileName = scan.nextLine();

            /*
             * Creates 'returnStatus' which will hold the outcome of the search for
             * the given file.
             */
            Object[] returnStatus = FileUtility.isFile(fileName);
            /* If the returned boolean (returnStatus[0]) is 'false' */
            if(!(Boolean)returnStatus[0]){
                /* Prints out the error for the user. */
                System.out.println(returnStatus[1]);
            }
            /*
             * sets the value of 'fileLocate' to the value of the boolean in the
             * return statement.
             */
            fileLocate = (Boolean)returnStatus[0];
        }

        /* Section: Prompts user for folder that the file will be saved to. */

        String selectedFolder = "";

        boolean folderConfirmed = false; 
        while(!folderConfirmed){
            /* Prompts user for folder to save files to */
            System.out.println((char)27 + "[1;36m" + "\nEnter the folder you would like to save to." 
                + " Ex: \"C/:Users/<UserName>/Desktop/Folder1/Folder2\":" + (char)27 + "[00m");
            selectedFolder = scan.nextLine();

            /* Checks if 'selectedFolder' is empty */
            if(selectedFolder.length() < 1) {
                selectedFolder = "ConvertedFiles/";
                folderConfirmed = true;
            } else {
                /* checks if the given folder is a directory or not */
                Object[] returnStatus = FileUtility.isDirectory(selectedFolder);

                /* If the return is false... */
                if((Boolean)returnStatus[0] == false){
                    /* Print error */
                    System.out.println((String)returnStatus[1] + "\n");

                    String createFolder = "";
                    try {
                        /*
                         * Asks user if they would like to create a folder where the
                         * specified directory should be.
                         */
                        System.out.println("Would You Like To Create A Folder At This"
                            + " Location To Save To?");
                        createFolder = scan.nextLine();
                        /* If the user answers 'Yes'... */
                        if(createFolder.charAt(0) == 'y' || createFolder.charAt(0) == 'Y'){
                            /* Run creation method */
                            returnStatus = FileUtility.createDirectory(selectedFolder);
                            /* Prints outcome of the creation method */
                            System.out.println((String)returnStatus[1]);
                            /* If a 'true' is returned, the folder was successully created */
                            if((Boolean)returnStatus[0] == true){
                                folderConfirmed = true;
                            /*
                             * If the folder was not successfully created, 'folderConfirmed'
                             * is set to false and the user is reprompted for a folder to
                             * save to.
                             */
                            } else {
                                folderConfirmed = false;
                            }
                        /*
                         * If the user answers anything but, 'Yes' 'folderConfirmed' is
                         * set to false and the user if reprompted for a folder to save to.
                         */
                        } else {
                            folderConfirmed = false;
                        }
                    /* In the case that the user does not enter anything. */
                    } catch(Exception e){
                        folderConfirmed = false;
                    }

                /*
                 * If the given folder produces no errors, a success message is given
                 * and 'folderConfirmed' is set to true.
                 */
                } else {
                    System.out.println((String)returnStatus[1]);
                    folderConfirmed = true;
                }
            }
        }

        System.out.println("\n# - - - - - # # - - - - - # # - - - - - #");

        /*
         * Instantiates 'PythonToJavaConvertReader' which will read the python file
         * and feed the 
         */
        PythonToJavaConvertReader fileToRead = new PythonToJavaConvertReader(fileName, selectedFolder);
        fileToRead.readPythonFileContents();

        /*
         * Auto-Run method
         * automaticlly runs the now converted file in the command line for the
         * user to see if the file came out right.
         Structure:
         1) ask user if they would like to auto-run the file.
         2) finds the now created file
         3) compiles the file with the main method
         4) executes the now compiled file.
         */

        scan.close();
    }
}
