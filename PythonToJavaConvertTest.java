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
Steps:
1) Get a file from the user
2) Confirm that the file exists
3) Open up the file and begin reading it
4) 


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
*/

/* Imports the Utility Package */
import utilityPackage.FileUtility;
import utilityPackage.HelpUtility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.HashMap;

/*
 * javac PythonToJavaConvertTest.java && java PythonToJavaConvertTest TestFiles/Test1.py
 */

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
         * fileName will store the full name of the file. This will be used again
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
            System.out.println("\nEnter the name of the folder along with the name"
                + " of the file you would like converted. Ex: \"Folder1/Folder2/Pythonfile.py\":");
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



        /*
         * Instantiates 'PythonToJavaConvertReader' which will read the python file
         * and feed the 
         */
        PythonToJavaConvertReader fileToRead = new PythonToJavaConvertReader(fileName);
        fileToRead.readPythonFileContents();
    }
}
