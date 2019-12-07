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
    private String location = null;
    private int tabCounter = 2;
    private int spacing = 4;

    /*
     * Constructor: when 'PythonToJavaConvert' is instanciated, a file
     * with the same name as the given file will automaticly be created so that
     * it can be writen to as each line is compiled
     */
    public PythonToJavaConvert(String fileName, String folder, boolean generate){

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

        if(folder.charAt(folder.length()-1) != '\\' || folder.charAt(folder.length()-1) != '/'){
            folder += "/";
        }

        this.location = folder + this.fileName + ".java";

        /*
         * Upon the constructor being activated a file by the name of what is
         * given will be created and saved.
         */
        try {
            PrintWriter printWrite = new PrintWriter(new FileWriter(this.location, false));
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

            generateFileStart(generate);
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    } // PythonToJavaConvert (Constructor)

    /*
     * Method: 'generateFileStart'
     * Arguments: 
     * None
     * boolean main - states if the specified file will have a main method
     * 
     * Description: Generates the class defenition as well as either the main
     * method or a default constructor.
     */
    public void generateFileStart(){
        this.generateFileStart(false);
    } 
    public void generateFileStart(boolean main){
        try {
            PrintWriter printWrite = new PrintWriter(new FileWriter(this.location, true));

            /* Adds class definition to file */
            printWrite.println("public class " + this.fileName + " {");
            /* 
             *If 'main' is set to true, a main method will be generated for the 
             * specifed file
             */
            if(main){
                printWrite.println("    public static void main(String[] args) {");
            } else {
                /* If main is set to false, an empty constructor is generated instead */
                printWrite.println("public " + this.fileName + "{}");
            }

            printWrite.close();
        } catch(Exception e) {
            System.out.println("ERROR: generateFileStart - " + e);
        }
    }

    /*
     * Method: 'lineComment'
     * Arguments: 
     * String text - text being read for comments.
     * 
     * Description: Reads 'text' for Python comment notation: '#' then adds the
     * comment to 'comment' which is then formated for Java comment notation.
     * COMMENTS: '#'
     *
     * Cases:
     * # alone
     * # with satement after
     * # multiple
     * # after statement
     * # in a string
     */

    public String[] lineComment(String text){
        /* Sets character counter */
        int singleQuote = 0;
        int doubleQuote = 0;

        /* Defines 'line' and 'comment' */
        String line = "";
        String comment = "";

        for(int c = 0; c < text.length(); c++){
            singleQuote += FileUtility.calcSingleQuote(text, c);
            doubleQuote += FileUtility.calcDoubleQuote(text, c);
            /*
             * If the character that is being looked at is the comment notator, '#'
             * it is checked if there are any currently open containing symbols.
             * if there are none, then, the character is apart of a comment
             * and every charcter after can be added to 'comment'.
             * otherwise, it is apart of the code and is ignored. 
             */
            if(text.charAt(c) == '#') {
                /* All containing characters are accounted for */
                if(singleQuote % 2 == 0 && doubleQuote % 2 == 0) {
                    /*
                     * At this point, all characters up to a valid comment
                     * notator should be added to 'line' all that are not
                     * are apart of 'comment'
                     */
                    for(int ch = c; ch < text.length(); ch++){
                        comment += text.charAt(ch);
                    }
                    /* Adds Java comment notation to line */
                    comment = "/* " + comment + " */";
                    break;
                /* If above check fails, the '#' is in a quote and is added to 'line' */
                } else {
                    line += text.charAt(c);
                }
            } else {
                /* If the current character is not a '#', add character to 'line' */
                line += text.charAt(c);
            }
        } // for

        /* Resets 'text' to be everything not included in 'comment' */
        text = line;
        /* Resets 'line' */
        line = "";
        
        String[] returnString = new String[2];
        returnString[0] = text;
        returnString[1] = comment;
        return returnString;
    } // lineComment

    /*
     * Method: 'linePrint'
     * Arguments: 
     * String text - text being read for print statements.
     * 
     * Description: Reads 'text' for Python print statements and then converts
     * contained string and statement to Java
     *
     * PRINT STATEMENTS: 'print()'
     * Cases:
     * print() default
     * print() with concatenation using a plus, '+'
     * print() with concatenation using a comma, ','
     * print() with a variable definition contained.                            X
     * print() with variable casting - Should be addressed in different check   X
     * print() inside of a string. 
     */
    public String linePrint(String text){
        /* Sets character counter */
        int singleQuote = 0;
        int doubleQuote = 0;

        /* Defines 'line' */
        String line = "";

        for(int c = 0; c < text.length(); c++){
            singleQuote += FileUtility.calcSingleQuote(text, c);
            doubleQuote += FileUtility.calcDoubleQuote(text, c);

            /* If all quotes are acounted for */
            if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                try {
                    /* Confirms that the 'text' contains a print statement */
                    if(text.charAt(c) == 'p' && text.charAt(c + 1) == 'r' && text.charAt(c + 2) == 'i' && text.charAt(c + 3) == 'n' && text.charAt(c + 4) == 't' && text.charAt(c + 5) == '('){
                        for(int ch = c + 6; ch < text.length(); ch++){
                            /* Searches for containing characters. */
                            if(text.charAt(ch) == '\'') {
                                singleQuote++;
                            } else if(text.charAt(ch) == '\"') {
                                doubleQuote++;
                            } else {
                                try {
                                    /*
                                     * Removes count from 'singleQuote' if it is
                                     * a escape character
                                     */
                                    if(text.charAt(ch) == '\'' && text.charAt(c - 1) == '\\'){
                                        singleQuote--;
                                    }
                                } catch(Exception e){}
                                try {
                                    /*
                                     * Removes count from 'doubleQuote' if it is
                                     * a escape character
                                     */

                                    if(text.charAt(ch) == '\"' && text.charAt(c - 1) == '\\'){
                                        doubleQuote--;
                                    }
                                } catch(Exception e){}
                            }

                            /*
                             * If there is a ')' not inside of quotes, the 
                             * current statment is being closed.
                             */
                            if(singleQuote % 2 == 0 && doubleQuote % 2 == 0 && text.charAt(ch) == ')'){
                                line += text.charAt(ch);
                            } else {
                                /*
                                 * Checks if all quotes are closed and a comma is
                                 * found. Changes comma out for a '+' and a ' '
                                 */
                                if(singleQuote % 2 == 0 && doubleQuote % 2 == 0 && text.charAt(ch) == ','){
                                    line += " + \" \" + ";
                                } else {
                                    line += text.charAt(ch);
                                }
                            }
                        } // for
                        line = "System.out.println(" + line;
                    } // if
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERROR: Out Of Bounds: print()");
                    break;
                }
            } // if
        } // for
        return line;
    } // linePrint

    /*
     * Method: 'ifStatement'
     * Arguments: 
     * String text - line being converted containing an if statement
     * 
     * Description: Reads 'text' for possible Python 'if' statements and converts
     * the statement to Java.
     Steps:
     1) confirm that 'text' contains a real 'if' statement and not in a string
     2) discover place in 'text' where the 'if' statement is
     3) increment 'tabCounter' +1
     4) translate 'if' statement's contents
     5) add syntax
     Cases:
     1) if a key symbol is in a string
     2) different names for opporators
     3) checking if strings are equal
     */
    public String ifStatement(String text){
        int singleQuote = 0;
        int doubleQuote = 0;

        String line = "";
        boolean isIf = false;
        
        int c = 0;
        int ch = 0;
        /* Loops through 'text' to confirm the presence of an 'if' statement */
        for(; c < text.length(); c++){
            singleQuote += FileUtility.calcSingleQuote(text, c);
            doubleQuote += FileUtility.calcDoubleQuote(text, c);
            /* if all ' and " are acounted for */
            if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                try{
                    /* Checks for 'elif' statements which will run 'ifStatement' */
                    if(text.charAt(c) == 'e' && text.charAt(c+1) == 'l' && text.charAt(c+2) == 'i' && text.charAt(c+3) == 'f' && text.charAt(c+4) == ' '){
                        this.tabCounter--;
                        break;
                    /* checks if 'text' contains a real 'if' statement */
                    } else if(text.charAt(c) == 'i' && text.charAt(c+1) == 'f' && text.charAt(c+2) == ' '){
                        isIf = true;
                        break;
                    }
                } catch(Exception e){}
            }
        } // for
        if(isIf){
            tabCounter++;
            /* Loops through the now confirmed 'if' statement */
            for(ch = c+3; ch < text.length(); ch++){
                singleQuote += FileUtility.calcSingleQuote(text, c);
                doubleQuote += FileUtility.calcDoubleQuote(text, c);
                /* If all quotes are closed */
                if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                    /* indicates the end of the 'if' statement */
                    if(text.charAt(ch) == ':'){
                        /* Adds closing to 'if' statement */
                        line += "){";
                        ch++;
                        break;
                    }
                } // if
                line += text.charAt(ch);
            } // for
            /* adds the 'if' to the "if statement" */
            line = "if(" + line;
        } // if
        /* cuts anything extra from the line */
        // String str = text.substring(ch, text.length() - 1);
        return line;
    } // ifStatement

    /*
     * Method: 'elifStatement'
     * Arguments: 
     * String text - line being converted containing an if statement
     * 
     * Description: Reads 'text' for possible Python 'else' statements and converts
     * the statement to Java.
     */
    public String elifStatement(String text){
        int singleQuote = 0;
        int doubleQuote = 0;

        String line = "";

        boolean isElIf = false;
        
        int c = 0;
        int ch = 0;
        /* Loops through 'text' to confirm the presence of an 'elif' statement */
        for(; c < text.length(); c++){
            singleQuote += FileUtility.calcSingleQuote(text, c);
            doubleQuote += FileUtility.calcDoubleQuote(text, c);

            /* if all ' and " are acounted for */
            if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                try{
                    /* checks if 'text' contains a real 'if' statement */
                    if(text.charAt(c) == 'e' && text.charAt(c+1) == 'l' && text.charAt(c+2) == 'i' && text.charAt(c+3) == 'f' && text.charAt(c+4) == ' '){
                        isElIf = true;
                        break;
                    }
                } catch(Exception e){}
            }
        } // for
        if(isElIf){
            // tabCounter++;
            /* Loops through the now confirmed 'elif' statement */
            try{
                for(ch = c+5; ch < text.length(); ch++){
                    /* counts quotes */
                    singleQuote += FileUtility.calcSingleQuote(text, c);
                    doubleQuote += FileUtility.calcDoubleQuote(text, c);

                    /* If all quotes are closed */
                    if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                        /* indicates the end of the 'if' statement */
                        if(text.charAt(ch) == ':'){
                            /* Adds closing to 'if' statement */
                            line += "){";
                            tabCounter++;
                            break;
                        }
                    }
                    line += text.charAt(ch);
                } // for
            } catch(Exception e){}
            /* adds the 'if' to the "if statement" */
            line = "else if(" + line;
        } // if
        /* cuts anything extra from the line */
        // String str = text.substring(ch, text.length() - 1);
        return line;
    } // elifStatement

    /*
     * Method: 'elseStatement'
     * Arguments: 
     * String text - line being converted containing an if statement
     * 
     * Description: Reads 'text' for possible Python 'elif' statements and converts
     * the statement to Java.
     */
    public String elseStatement(String text){
        int singleQuote = 0;
        int doubleQuote = 0;

        for(int c = 0; c < text.length(); c++){
            singleQuote += FileUtility.calcSingleQuote(text, c);
            doubleQuote += FileUtility.calcDoubleQuote(text, c);

            try{
                /* Confirms the presence of an else statement */
                if(text.charAt(c) == 'e' && text.charAt(c+1) == 'l' && text.charAt(c+2) == 's' && text.charAt(c+3) == 'e' && text.charAt(4) == ':'){
                    tabCounter++;
                    return "else {";
                }
            } catch(Exception e){
                return text;
            }
        }

        return text;
    } // elseStatement

    /*
     * Method: 'addLineEnding'
     * Arguments: 
     * String line - line being read for possable need of ';'
     * 
     * Description: Reads converted 'line' and adds ';' at the end of any lines 
     * that need it.
     */
    public String addLineEnding(String line){
        /* Adds semicolon to the end of the statement */
        try {
            /*
             * Loops through 'line' starting at the back and loops through 'line'
             * to find where to put the ';'
             */
            for(int s = line.length() - 1; s >= 0; s--){
                /* If the current character is anything but a blank space or a ')' */
                if(line.charAt(s) != ')' && line.charAt(s) != ' '){
                    break;
                } else if(line.charAt(s) == ')') {
                    /*
                     * If a ')' is found and it is the last character in 'line',
                     * then ';' is added to the end without issue.
                     */
                    if(s == line.length() - 1){
                        line += ";";
                    /*
                     * If a ')' is found and it is not the last charater in 'line'
                     * then something is after it and 'line' must be split in order
                     * to add the ';' where it should be.
                     */
                    } else {
                        try {
                            String s1 = line.substring(0, s + 1);
                            String s2 = line.substring(s + 1, line.length() - 1);
                            line = s1 + ";" + s2;
                        } catch(Exception e) {
                            line += ";";
                        }
                    }
                    break;
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: ';' - " + e);
        }
        return line;
    } // addLineEnding

    public String addTabs(){
        String tabs = "";
        for(int t = 0; t < tabCounter * this.spacing; t++){
            tabs += " ";
        }
        return tabs;
    } // addTabs

    /*
     * Method: 'closeFile'
     * Arguments: None
     * 
     * Description: Closes out a file by adding the appropriate number of '}'
     */
    public void closeFile(){
        String tabs = "";
        /*
         * Each tab equates to a new scope, so '}' only need to be placed equal
         * to the number of 'tabCounter'
         */
        for(int f = this.tabCounter; f > 0; f--){
            tabs = "";
            /* Adds spaces before '}' */
            for(int t = 1; t <= (this.tabCounter-1) * 4; t++){
                tabs += " ";
            }
            this.tabCounter--;
            this.saveTextToFile(tabs + "}");
        }
    } // closeFile

    /*
     * Method: 'checkScope'
     * Arguments: 
     * String text - line being checked for scope
     * 
     * Description: checks 'tabCounter' against the number of tabs found in the
     * Python file. If 'tabCounter' is greater, then the scope has reduced and
     * a '}' needs to be added.
     */
    public boolean checkScope(String text){
        int spaces = 0;
        for(int s = 0; s < text.length(); s++){
            if(text.charAt(s) == ' '){
                spaces++;
            } else {
                break;
            }
        }
        /*
         * If the number of spaces divided by 'this.spacing' is greater than
         * 'this.tabCounter' - 2 (-2 acounts for the class declaration and the  
         * main method or the constructor that is found in every Java file.),
         * then the scope of the code has decreased and a '}' needs to be added.
         */
        if(spaces/this.spacing < this.tabCounter - 2){
            this.tabCounter--;
            /*
             * 'else' and 'elif' statements are added after '}' as such, they need
             * to be added on the same line.
             */
            if(text.contains("elif") && text.contains(":")){
                this.saveTextToFile(this.addTabs() + "} " + this.elifStatement(text));
                return true;
            } else if(text.contains("else") && text.contains(":")){
                this.saveTextToFile(this.addTabs() + "} " + this.elseStatement(text));
                return true;
            } else {
                this.saveTextToFile(this.addTabs() + "}");
            }
        }
        return false;
    } // checkScope

    /*
     * Method: 'cleanFile'
     * Arguments: 
     * String text - line being cleaned
     * 
     * Description: preemptively searches the Python line for Python specific
     * key words and converts them to their Java equivalent.
     */
    public String cleanFile(String text){
        int singleQuote = 0;
        int doubleQuote = 0;

        String line = "";

        for(int c = 0; c < text.length(); c++){
            try{
                singleQuote += FileUtility.calcSingleQuote(text, c);
                doubleQuote += FileUtility.calcDoubleQuote(text, c);
                /* if all single quotes and double quotes are acounted for */
                if(singleQuote % 2 == 0 && doubleQuote % 2 == 0){
                    /* Not Statements */
                    if(text.charAt(c) == 'n' && text.charAt(c+1) == 'o' && text.charAt(c+2) == 't'){
                        line += "!";
                        c += 3;
                    /* And Statements */
                    } else if(text.charAt(c) == 'a' && text.charAt(c+1) == 'n' && text.charAt(c+2) == 'd'){
                        line += "&&";
                        c += 3;
                    /* Or Statement */
                    } else if(text.charAt(c) == 'o' && text.charAt(c+1) == 'r'){
                        line += "||";
                        c += 2;
                    /* True */
                    } else if(text.charAt(c) == 'T' && text.charAt(c+1) == 'r' && text.charAt(c+2) == 'u' && text.charAt(c+3) == 'e'){
                        line += "true";
                        c += 4;
                    /* False */
                    } else if(text.charAt(c) == 'F' && text.charAt(c+1) == 'a' && text.charAt(c+2) == 'l' && text.charAt(c+3) == 's' && text.charAt(c+4) == 'e'){
                        line += "false";
                        c += 5;
                    /* Casting: String */
                    } else if(text.charAt(c) == 's' && text.charAt(c+1) == 't' && text.charAt(c+2) == 'r' && text.charAt(c+3) == '('){
                        line += "(String)";
                        c += 4;
                        for(; c < text.length(); c++){
                            if(text.charAt(c) == ')'){
                                c++;
                                break;
                            } else {
                                line += text.charAt(c);
                            }
                        }
                    /* Casting: Integer */
                    } else if(text.charAt(c) == 'i' && text.charAt(c+1) == 'n' && text.charAt(c+2) == 't' && text.charAt(c+3) == '('){
                        boolean convert = true;
                        try{
                            if(text.charAt(c-2) == 'p' && text.charAt(c-1) == 'r'){
                                convert = false;
                            }
                        } catch(Exception e){}
                        if(convert){
                            line += "(Integer)";
                            c += 4;
                            for(; c < text.length(); c++){
                                if(text.charAt(c) == ')'){
                                    c++;
                                    break;
                                } else {
                                    line += text.charAt(c);
                                }
                            }
                        }
                    /* Casting: Float */
                    } else if(text.charAt(c) == 'f' && text.charAt(c+1) == 'l' && text.charAt(c+2) == 'o' && text.charAt(c+3) == 'a' && text.charAt(c+3) == 't' && text.charAt(c+3) == '('){
                        line += "(Double)";
                        c += 6;
                        for(; c < text.length(); c++){
                            if(text.charAt(c) == ')'){
                                c++;
                                break;
                            } else {
                                line += text.charAt(c);
                            }
                        }
                    }
                }
            }catch(Exception e){}
            line += text.charAt(c);
        } // for
        return line;
    } // cleanFile

    public void saveTextToFile(String text){
        try {
            PrintWriter printWrite = new PrintWriter(new FileWriter(this.location, true));
            
            printWrite.println(text);
            printWrite.close();
        } catch(Exception e) {
            System.out.println("ERROR: saveTextToFile failed");
        }
    } // saveTextToFile
} // PythonToJavaConvert (Class)
