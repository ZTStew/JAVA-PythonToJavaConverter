/* Adds HelpUtility to the utility package. */
package utilityPackage;

public final class HelpUtility{

    public HelpUtility(){
        /*
         * Forces an error to be returned in the case that a user tries to instantiate
         * the class.
         */
        throw new NullPointerException("Uninstantiateable Object.");
    }

    public static void about(){
        String str = "";
        str += "This program takes in a given Python file and attempts to convert it"
            + "into working .java code.\n\n"
            + "In order to start the program, first execute it by calling: \n"
            + "javac PythonToJavaConvertTest.java && java PythonToJavaConvertTest\n"
            + "OR you can enter in the name of the file you would like converted upon"
            + "execution by saying: \n"
            + "javac PythonToJavaConvertTest.java && java PythonToJavaConvertTest TestFiles/Test1.py\n"
            + "IF you did not enter in the desired file to be converted when executing"
            + "the file at first, the program will ask you for a file to convert"
            + "until one is provided. The file will then be converted for you.\n"
            + "IN PROGRESS: Once the given file has been converted, it will then"
            + "prompt you for a desired directory to save the file to.\n"
            + "The program then will ask you if you would like to convert another file.";
            
        System.out.println(str);
    }
}
