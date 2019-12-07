/* Adds FileUtility to the utility package. */
package utilityPackage;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
// import java.io.FilenameFilter;
import java.util.Scanner;

/*
 * Class: 'FileUtility'
 * Arguments: NONE
 * 
 * Description: Utility class decides if a given file path is valid and has a
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
     * Description: checks to make sure the given string points to a file with
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
     * Method: isDirectory
     * Arguments:
     * String dir - directory that is being checked.
     *
     * Description: takes a directory and returns to the user if the directory
     * exist or not as well as a status statement.
     */
    public static Object[] isDirectory(String dir){
        File directory = new File(dir);

        Object[] returnStatus = new Object[2];
        returnStatus[0] = true; returnStatus[1] = "SUCCESS: No Errors Found.";

        /* Checks if the folder given in 'dir' exists */
        if(!directory.exists() && !directory.isDirectory()){
            System.out.println("Folder does not exist");
            returnStatus[0] = false;
            returnStatus[1] = "ERROR: Given Folder Cannot Be Found: " + dir;
            return returnStatus;
        } else {
            System.out.println("Folder Does exist");
            returnStatus[0] = true;
            returnStatus[1] = "SUCCESS: Given Folder Has Been Found.";
        }

        return returnStatus;
    }

    /*
     * Method: createDirectory
     * Arguments:
     * String dir - directory that is being created.
     *
     * Description: takes a given directory and creates it. 
     */
    public static Object[] createDirectory(String dir){
        Object[] returnStatus = new Object[2];
        returnStatus[0] = true;
        returnStatus[1] = "SUCCESS Default: Directory Been Created.";
        try {
            /* Creates new 'File' object with 'dir' as the parameter */
            File file = new File(dir);
            /* Makes a directory where the given directory is. */
            file.mkdirs();
            returnStatus[1] = "SUCCESS create: Directory Been Created.";
        /*
         * If there is an error, set 'returnStatus[0]' to 'false' and provide
         * an error message.
         */
        } catch(Exception e) {
            returnStatus[0] = false;
            returnStatus[1] = "ERROR: Directory Could Not Be Created.";
        }
        return returnStatus;
    }

    /*
     * Method: 'removeExtention'
     * Arguments: 
     * String file - the string which needs to have it's extention removed
     * 
     * Description: removes the extention from a string.
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
     * Description: removes the directories from a string.
     */
    public static String removeDirectories(String file){
        String output = "";
        String[] parts = file.split("/");

        return parts[parts.length - 1];
    }

    /*
     * Method: 'calcSingleQuote'
     * Arguments: 
     * int idx: the current index of 'text' that is being searched for single quotes
     * String text: the current line of code being scanned for single quotes
     * 
     * Description: help with calculating the number of single quotes present in
     * 'text' so that it can be decided if code is inside of a string or not.
     */
    public static int calcSingleQuote(String text, int idx){
        try{
            if(text.charAt(idx) == '\''){
                return 1;
            } else if(text.charAt(idx) == '\\' && text.charAt(idx+1) == '\''){
                return -1;
            } else {
                return 0;
            }
        } catch(Exception e){
            return 0;
        }
    }

    /*
     * Method: 'calcSingleQuote'
     * Arguments: 
     * int idx: the current index of 'text' that is being searched for double quotes
     * String text: the current line of code being scanned for double quotes
     * 
     * Description: help with calculating the number of double quotes present in
     * 'text' so that it can be decided if code is inside of a string or not.
     */
    public static int calcDoubleQuote(String text, int idx){
        try{
            if(text.charAt(idx) == '\"'){
                return 1;
            } else if(text.charAt(idx) == '\\' && text.charAt(idx+1) == '\"'){
                return -1;
            } else {
                return 0;
            }
        } catch(Exception e){
            return 0;
        }
    }
}
