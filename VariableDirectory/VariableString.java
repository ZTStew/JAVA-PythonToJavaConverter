package variableDirectory;

public class VariableString extends Variable {
    private String value;

    public VariableString(String name, Integer line, String val){
        super("String " + name, line);
        this.value = val;
    }
    public VariableString(){}

    public String equalsSyn(){
        return this.value + ".equals(";
    }

    public String getValue(){
        return this.value;
    }
    public void setValue(String val, Integer line){
        addLineNumber(line);
        this.value = val;
    }
}