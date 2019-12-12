package variableDirectory;

public class VariableBoolean extends Variable {
    private Boolean value;
    private String valueString;

    // public VariableBoolean(String name, Integer line, String val){
    //     super(name, line);
    //     this.value = val;
    // }
    public VariableBoolean(String name, Integer line, String val){
        super("Boolean " + name, line);
        this.value = convertToBoolean(val);
    }
    public VariableBoolean(){}

    public String equalsSyn(){
        return this.value + " ==";
    }

    public Boolean convertToBoolean(String val){
        if(val.equals("t") || val.equals("T") || val.equals("true") || val.equals("True")){
            return true;
        } else {
            return false;
        }
    }

    public Boolean getValue(){
        return this.value;
    }
    public void setValue(String val, Integer line){
        addLineNumber(line);
        this.valueString = val;
    }
}