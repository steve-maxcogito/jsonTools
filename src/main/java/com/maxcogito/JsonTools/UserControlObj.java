package com.maxcogito.JsonTools;

public class UserControlObj {

    private String UserInput;

    public String getUserInput() {
        return UserInput;
    }
    public void setUserInput(String userInput) {
        UserInput = userInput;
    }

    private String outFile;

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    private String outputPath;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    private String nextAction;

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public UserControlObj(String userInput, String nextAction, boolean apiSet,boolean filePthset) {
        UserInput = userInput;
        nextAction = nextAction;
        API_SET = apiSet;
        FILEPTH_SET = filePthset;
    }

    public UserControlObj() {
    }

    public static UserActions uAct;

    public static UserParams uParams;

    public static UserParams uDirection;

    public static boolean isApiSet() {
        return API_SET;
    }

    public static void setApiSet(boolean apiSet) {
        API_SET = apiSet;
    }

    public static boolean API_SET = false;
    public static boolean FILEPTH_SET = false;

    public static boolean isFilepthSet() {
        return FILEPTH_SET;
    }

    public static void setFilepthSet(boolean filepthSet) {
        FILEPTH_SET = filepthSet;
    }

    public static void main(String[] args){

        String userInput = null;
        UserActions actions = UserActions.MENU;
        UserParams param = UserParams.USE_CURRENTPTH;

        System.out.println(actions);
        System.out.println(param);

        actions = UserActions.ENTERURL;
        System.out.println(actions);
        param = UserParams.USE_DEFAULTURL;
        uAct = UserActions.MENU;

        System.out.println("Param for DEFAULT URL: "+param);

        actions = UserActions.valueOf("VERIFYURL");

        if(actions.getValue()!=null){
            System.out.println("VALUE found via STRING lookup: "+actions);
        }

        param = UserParams.valueOf("USE_CURRENTPTH");

        if(param.getValue()!=null){
            System.out.println("Param VALUE found via STRING lookup: "+param);
        }

        System.out.println("Value of uACT in UserControlObj: "+uAct);


    }



}
