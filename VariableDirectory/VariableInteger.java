package variableDirectory;

public class VariableInteger extends Variable {
    private String value;

    public VariableInteger(String name, Integer line, String val){
        super("Integer " + name, line);
        this.value = val;
    }
    public VariableInteger(){}

    public String equalsSyn(){
        return this.value + " ==";
    }

    public String getValue(){
        return this.value;
    }
    public void setValue(String val, Integer line){
        addLineNumber(line);
        this.value = val;
    }
}