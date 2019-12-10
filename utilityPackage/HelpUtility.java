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
        str += "This program takes in a given Python file and attempts to convert it "
            + "into working .java code.\n\n"
            + "In order to start the program, first execute it by calling: \n"
            + "javac PythonToJavaConvertTest.java && java PythonToJavaConvertTest\n"
            + "OR you can enter in the name of the file you would like converted upon "
            + "execution by saying: \n"
            + "javac PythonToJavaConvertTest.java && java PythonToJavaConvertTest TestFiles/Test1.py\n"
            + "IF you did not enter in the desired file to be converted when executing "
            + "the file at first, the program will ask you for a file to convert "
            + "until one is provided. The file will then be converted for you.\n"
            + "Once you have provided a valid file to have converted, you will then "
            + "be prompted for a desired directory to save the file to.\n"
            + "If the given directory does not exist, the user will be asked "
            + "if they would like to create a directory in the location previously "
            + "given or if they would like to enter a new directory. Process will "
            + "repeat until a valid directory has been identified.\n\n"
            + "The .py file will now start to be converted automaticly and the "
            + ".java file will be saved to the directory specified.\n\n"
            + "Current Python Lines Being Translated: \n"
            + "1) Comments\n"
            + "2) Print Statements\n"
            + "3) if/elif/else Checks (Basic)";
            
        System.out.println(str);
    }
}
