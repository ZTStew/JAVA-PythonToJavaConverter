
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
    private String varName;
    /* 'varValue' tracks the current value of the variable found in the .py file */
    private Object varValue;
    /* 'varType' records the type of variable being handled. */
    private String varType;
    /* 'lineNumber' records the lines where the variable is found in the .java file */
    private int[] lineNumber;


    public Variable(){

    }
}