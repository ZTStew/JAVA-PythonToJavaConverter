package variableDirectory;

public class VariableFloat extends Variable {
    private String value;

    public VariableFloat(String name, Integer line, String val){
        super("Double " + name, line);
        this.value = val;
    }
    public VariableFloat(){}

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