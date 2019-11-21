/* Adds FileUtility to the utility package. */
package utilityPackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.Scanner;
import java.util.HashMap;

/*
 * Class: 'FileUtility'
 * Arguments: NONE
 * 
 * Description - Utility class decides if a given file path is valid and has a
 * has a valid file to convert.
 */
public final class FileUtility{

    public FileUtility(){
        /*
         * Forces an error to be returned in the case that a user tries to instantiate
         * the class.
         */
        throw new NullPointerException("Uninstantiateable Object.");
    }

    /*
     * Method: 'isFile'
     * Arguments: 
     * String input - the name of the file that has been selected to be converted.
     * 
     * Description - checks to make sure the given string points to a file with
     * a '.py' extention as well as a real directory.
     */
    public static Object[] isFile(String input){
        Object[] returnStatus = new Object[2];
        returnStatus[0] = true; returnStatus[1] = "SUCCESS: No Errors Found.";
        Boolean doesFile = true;
        String str = "";

        /* Ensures that there is a '.py' extention on the given file */
        if(!input.contains(".py")){
            returnStatus[0] = false;
            returnStatus[1] = "ERROR: Given file is missing an extention.";
            return returnStatus;
        }


        File directory = new File(input);

        /* Checks if the folder given in 'input' exists */
        if(!directory.exists() && !directory.isDirectory()){
            returnStatus[0] = false;
            returnStatus[1] = "ERROR: Given File Cannot Be Found.";
            return returnStatus;
        } else {
            returnStatus[0] = true;
            returnStatus[1] = "SUCCESS: Folder Does Exist.";
        }

        /* Unclear if this section is needed */
        /* Checks if the file exists */
        if(!directory.exists() && !directory.isFile()){
            returnStatus[0] = false;
            returnStatus[1] = "ERROR: Given File Cannot Be Found.";
            return returnStatus;
        } else {
            returnStatus[0] = true;
            returnStatus[1] = "SUCCESS: File Does Exist.";
        }

        return returnStatus;
    }

    /*
     * Method: 'removeExtention'
     * Arguments: 
     * String file - the string which needs to have it's extention removed
     * 
     * Description - removes the extention from a string.
     */
    public static String removeExtention(String file){
        String[] parts = file.split(".py");

        return parts[0];
    }

    /*
     * Method: 'removeDirectories'
     * Arguments: 
     * String file - a file structure which you wish to have the directories removed from
     * 
     * Description - removes the directories from a string.
     */
    public static String removeDirectories(String file){
        String output = "";
        String[] parts = file.split("/");

        return parts[parts.length - 1];
    }
}
