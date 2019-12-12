package variableDirectory;

import java.util.ArrayList;

/*
 * Class: Variable
 * Description: 'Variable' will be instantiated each time a new variable is found
 * in the python file being converted. Each variable will take the name of the
 * specified variable as well as its value and what the variable was last recorded
 * to be (String, Number, Boolean, Object).
 * 
 * Each new instance of 'Variable' will then be written to the .java file that
 * corresponds with the variables declaration. As the .py file continues to be
 * converted if a variable type changes, it will be changed in the instance of
 * 'Variable' that matches to resemble the type.
 *
 * 'Variable' will also keep track of where in the .java file being written to
 * the specific variable is found so that the file can be over written in the
 * case of a change to the variable typing.
 *
 * Each variable found in the .java file being written to will then be cast to
 * the variable type recorded in each variable before the file is finalized. 
 */
public class Variable {
    /* 'varName' records the name of the variable found in the .py file */
    private String varName = "Object ";
    /* 'lineNumber' records the lines where the variable is found in the .java file */
    private ArrayList<Integer> lineNumber = new ArrayList<Integer>();


    public Variable(String name, Integer line){
        this.varName = name;
        this.lineNumber.add(line);
    }
    public Variable(){}

    /* Getters */

    public String getVarName(){
        return this.varName;
    }
    public ArrayList<Integer> getLineNumber(){
        return lineNumber;
    }

    /* Setters */
    public void setVarName(String var){
        this.varName = var;
    }
    public void addLineNumber(int line){
        this.lineNumber.add(line);
    }
}