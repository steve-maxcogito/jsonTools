package com.maxcogito.JsonTools;

import org.bouncycastle.util.encoders.Hex;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScanAPIObj {

    private String UrlAPI = null;
    private String fileOutPath = null;
    private String defaultAPI = null;
    private String defaultPath = null;
    private String finalWritePath = null;


    public String getFinalWritePath() {
        return finalWritePath;
    }

    public void setFinalWritePath(String finalWritePath) {
        this.finalWritePath = finalWritePath;
    }

    public boolean isFILEPATH_SET() {
        return FILEPATH_SET;
    }

    public void setFILEPATH_SET(boolean FILEPATH_SET) {
        this.FILEPATH_SET = FILEPATH_SET;
    }

    public String getCurrWorkDirPath() {
        return currWorkDirPath;
    }

    public void setCurrWorkDirPath(String currWorkDirPath) {
        this.currWorkDirPath = currWorkDirPath;
    }

    private String currWorkDirPath = null;
    private boolean API_SET = false;
    private boolean FILEPATH_SET = false;

    public boolean isAPI_SET() {
        return API_SET;
    }

    public void setAPI_SET(boolean API_SET) {
        this.API_SET = API_SET;
    }

    private int itemCnt = 0;
    public static List<UserControlObj> workList = new ArrayList<UserControlObj>();

    public String getDefaultAPI() {
        return defaultAPI;
    }

    public void setDefaultAPI(String defaultAPI) {
        this.defaultAPI = defaultAPI;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    ScanAPIObj(String API, String fileOutput, int itemCount, String defaultUrl, String defaultPth, String currWorkDirPath, boolean apiSet, boolean FILEPATH_SET){
        this.UrlAPI = API;
        this.fileOutPath = fileOutput;
        this.itemCnt = itemCount;
        this.defaultAPI = defaultUrl;
        this.defaultPath = defaultPth;
        this.currWorkDirPath = currWorkDirPath;
        this.API_SET = apiSet;
        this.FILEPATH_SET = FILEPATH_SET;
    }

    public String getUrlAPI() {
        return UrlAPI;
    }

    public void setUrlAPI(String urlAPI) {
        UrlAPI = urlAPI;
    }

    public String getFileOutPath() {
        return fileOutPath;
    }

    public void setFileOutPath(String fileOutPath) {
        this.fileOutPath = fileOutPath;
    }

    public List<UserControlObj> getWorkList() {
        if(this.workList ==null){
            this.workList = new ArrayList<UserControlObj>();
        }
        return workList;
    }

    public void addToWorkList(UserControlObj item){
        this.workList.add(item);
    }
    public int workCnt(){
        return(this.workList.size());
    }
    public void removeWorkItem(int itemCnt){
        this.workList.remove(itemCnt);
    }

    public void setWorkList(List<UserControlObj> workList) {
        this.workList = workList;
    }

    public int getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(int itemCnt) {
        this.itemCnt = itemCnt;
    }

    public void writeAPIData(String httpsURL, String OutputPath) throws IOException {
        int bytesRead_total = 0;
        int bytesWritten_total = 0;

        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        BufferedInputStream in = new BufferedInputStream(is);

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(OutputPath))) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesRead_total += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWritten_total += bytesRead;
            }
        }
        System.out.println("Total bytes read : "+bytesRead_total);
        System.out.println("Total bytes written : "+bytesWritten_total);

    }


    public static String HashWithBouncyCastle(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        final String sha256hex = new String(Hex.encode(hash));
        return sha256hex;
    }

    public String userInput(BufferedReader br, String prompt) throws IOException {

        String line = "";
        int go = 1;

        while(!line.equals("q") && go != 0){
            if(prompt != null){
                System.out.println(" Enter "+prompt);
            }
            else{
                System.out.println("Enter data :");
            }
            line = br.readLine();
            System.out.println("data is: "+line);
            go = 0;
        }
        return(line);
    }

    public String verifyUrlStr(String Url){
        String line = Url;
        if(line.startsWith("https://")){
            System.out.println("This is your URL:"+line);
            System.out.println("Please verify that it is syntactically correct and contains the proper parameters.");
            if(line.contains("?")||line.contains("=")){
                System.out.println("URL contains parameters.");
            }
            else{
                System.out.println("URL does not contain parameters, this can be fine, just verify that this is correct.");
            }
        }
        else{
            System.out.println("Valid URL start with 'https://'\n");
            System.out.println("Please verify the URL and re-enter a valid URL.\n");
            return("invalid");
        }
        return("valid");
    }

    public void viewAPI(String API) throws IOException {
        String httpsURL = API;
        String inputLine;
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        try {

            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
            }

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    //These are the "MAIN MENU" functions
    // - MAIN MENU Itself
    //-ENTERURL
    //-VERIFYURL
    //-ENTERFILE PATH
    //-VERIFYPATH

    public UserControlObj mainMenu(BufferedReader br) throws IOException {
        UserControlObj conObj = new UserControlObj();
        String userInput = null;
        System.out.println("**********************************************************************************************");
        System.out.println("******************* WELCOME to the Main Menu of the API APP **********************************");
        System.out.println("**********************************************************************************************");
        System.out.println("******************* Please select one of the following choice: *******************************");
        System.out.println("******************* Select '1' to view an API ************************************************");
        System.out.println("**********************************************************************************************");
        System.out.println("********** (Defaults will be shown and a URL can be entered and then verified) ***************");
        System.out.println("**********************************************************************************************");
        System.out.println("******************* Select '2' to write an API to disk in JSON format ************************");
        System.out.println("**********************************************************************************************");
        System.out.println("********** (Defaults will be shown and a Directory path can be entered and then verified) ****");
        System.out.println("**********************************************************************************************");
        userInput = this.userInput(br, "choice");
        System.out.println("User Input in Main menu is: "+userInput);
        conObj.setUserInput(userInput);
        System.out.println("CONOBJ User Input in Main menu is: "+conObj.getUserInput());

        if(userInput.equals("1")){
            System.out.println("User Input in Main menu CONTAINS: "+userInput);
            conObj.uAct = UserActions.ENTERURL;
            conObj.uParams = UserParams.USE_DEFAULTURL;
            conObj.setUserInput(userInput);
            conObj.setNextAction("ENTERURL");

            if(!conObj.isApiSet()){
                System.out.println("API NOT SET sending the user to the enter URL option.");
                conObj.setApiSet(false);
            }
        }
        else{
            if(userInput.equals("2")) {
                System.out.println("Sending the user to the enter FILE PATH option.");
                conObj.uAct = UserActions.ENTERFILEPTH;
                conObj.setUserInput(userInput);
                conObj.setNextAction("ENTERFILEPTH");
                conObj.uParams = UserParams.USE_DEFAULTPTH;
                if(!conObj.isFilepthSet()){
                    System.out.println("Filepth not set, sending the user to the enter file path option.");
                    conObj.setFilepthSet(false);
                }
            }
            else{
                if(userInput.contains("q")){
                    System.out.println("Sending the user to the QUIT option.");
                    conObj.uAct = UserActions.QUIT;
                    conObj.setUserInput(userInput);
                    conObj.setNextAction("QUIT");
                    conObj.uParams = UserParams.USE_QUITACTION;
                }
                else{
                    System.out.println("Please enter '1' to ENTER URL, '2' to ENTER a FILE PATH or 'q' to QUIT the application.");
                    System.out.println("Sending the user to the MAIN MENU option.");
                    conObj.uAct = UserActions.MENU;
                    conObj.setUserInput(userInput);
                    conObj.setNextAction("MENU");
                    conObj.uParams = UserParams.USE_REENTER;
                }
            }
        }

        System.out.println("***********************************************************************************");
        System.out.println("Returning from MAIN MENU with a value of: "+conObj.uAct);
        System.out.println("In addition with a user Prams value of: "+conObj.uParams);
        System.out.println("***********************************************************************************\n");
        return(conObj);
    }

    public UserControlObj enterURL(BufferedReader br,UserControlObj conState) throws IOException {

        UserControlObj conObj = new UserControlObj();
        String userInput = null;

        System.out.println("Inside enterURL with ConState (User Direction) of: "+conState.uDirection);

        if(conState.uDirection == UserParams.USE_FILEOUTPUTONLY){
            System.out.println("User setting File Path Only");
            System.out.println("User has set filepath and file name to: "+conState.getOutputPath()+" File: "+conState.getOutFile());
            conObj.setOutputPath(conState.getOutputPath());
            conObj.setOutFile(conState.getOutFile());
            System.out.println("User has transferred filepath and file name to: "+conObj.getOutputPath()+" File: "+conObj.getOutFile());
        }

        String line = "";
        String validUrl = this.UrlAPI;
        char choice = 'z';
        int go = 1;

        System.out.println("First we want to verify that we have the correct API to capture.");
        System.out.println("Current default URL for the API is."+validUrl);
        System.out.println("Please enter '1' to use the default API value.");
        System.out.println("*****************************************************************");
        System.out.println("Please enter '2' to enter a new API value.");
        System.out.println("*****************************************************************");

        userInput = "go";

        while(!userInput.contains("q") && go == 1){
            userInput = this.userInput(br,"URL Choice");
            System.out.println("User Input (enterurl) in Main menu is: "+userInput+" with length of: "+userInput.length());
            if(userInput.equals("1")){
                conObj.setUserInput(userInput);
                conObj.setNextAction("VERIFYURL");
                System.out.println("User Input is to use DEFAULT URL : "+conObj.getUserInput());
                conObj.uAct = UserActions.VERIFYURL;
                conObj.uParams = UserParams.USE_DEFAULTURL;
                conObj.setApiSet(true);
                go = 0;
            }
            else {
                if (userInput.equals("2")) {
                    System.out.println("Please enter a new URL :");
                    userInput = this.userInput(br, "new URL");
                    conObj.uAct = UserActions.VERIFYURL;
                    conObj.setUserInput(userInput);
                    conObj.setNextAction("VERIFYURL");
                    conObj.uParams = UserParams.USE_NEWURL;
                    conObj.setApiSet(false);
                    go = 0;
                }
                else {
                    if (userInput.equals("q")) {
                        conObj.uAct = UserActions.QUIT;
                        conObj.setUserInput(userInput);
                        conObj.setNextAction("QUIT");
                        conObj.uParams = UserParams.USE_QUITACTION;
                        conObj.setApiSet(false);
                    } else {
                        System.out.println("Please enter: '1' or '2' -or- 'q', your choice: " + userInput + "  is NOT valid.");
                        conObj.uAct = UserActions.ENTERURL;
                        conObj.setUserInput(userInput);
                        conObj.setNextAction("ENTERURL");
                        conObj.uParams = UserParams.USE_REENTER;
                        conObj.setApiSet(false);
                        go =0;
                    }
                }
            }
        }

        return(conObj);

    }

    public UserControlObj verifyURL(BufferedReader br,UserControlObj conState) throws IOException {
        UserControlObj conObj = new UserControlObj();
        String APIUrl = this.getUrlAPI();
        System.out.println("API URL (AFTER GETTER) inside verifyURL is: "+APIUrl);
        System.out.println("Inside verifyURL with ConState (User Direction) of: "+conState.uDirection);
        System.out.println("Inside verifyURL with ConState (FilePATH) of: "+conState.getOutputPath());
        System.out.println("Inside verifyURL with ConState (FileNAME) of: "+conState.getOutFile());
        conObj.setOutputPath(conState.getOutputPath());
        conObj.setOutFile(conState.getOutFile());
        System.out.println("Inside verifyURL with ConObj (FilePATH) of: "+conObj.getOutputPath());
        System.out.println("Inside verifyURL with ConObj (FileNAME) of: "+conObj.getOutFile());


        String result = this.verifyUrlStr(APIUrl);
        System.out.println("RESULT Value from verifyURL: "+result);
        if(result.equals("valid")){
            conObj.uAct = UserActions.CONFIRMURL;
            conObj.setNextAction("CONFIRMURL");
            conObj.setUserInput("NONE");
            System.out.println("Value of default API string: "+this.UrlAPI);
            System.out.println("API URL inside verifyURL is: "+APIUrl);
        }
        else{
            System.out.println("Invalid URL. Please try again.");
            System.out.println("Sending user to the ENTERURL state.");
            conObj.uAct = UserActions.ENTERURL;
            conObj.setNextAction("ENTERURL");
            conObj.setUserInput("NONE REQUIRED - RE-ENTER URL");
        }
        return(conObj);
    }
    public UserControlObj enterFilePath(BufferedReader br, String promptText) throws IOException{
        UserControlObj conObj = new UserControlObj();
        String filePath = this.getDefaultPath();
        String userFile = null;
        String userInput = null;
        String userHomeDir = System.getProperty("user.home");
        String userCurDir = System.getProperty("user.dir");
        String dataDir = "/JsonAPIOutput";

        String line = "";


        StringBuilder strBhome = new StringBuilder();
        StringBuilder strBcurr = new StringBuilder();

        strBhome.append(userHomeDir);
        strBhome.append(dataDir);
        strBcurr.append(userCurDir);
        strBcurr.append(dataDir);

        System.out.println("Enter File Path method with default path value: "+filePath);

        System.out.println("*******************************************************************************");
        System.out.println("************ Please  select one of the options below:  ************************");
        System.out.println("************ Select '1' to use the default path for file output ***************");
        System.out.println("************ Default path: "+filePath+"  **************************************");
        System.out.println("************ Current working directory path: "+strBcurr.toString()+" **********");
        System.out.println("************ Home directory path: "+strBhome.toString()+"  ********************");
        System.out.println("************ Select '2' to enter a path for file output ***********************");
        System.out.println("*******************************************************************************");
        System.out.println("************ After selecting a path location directory ************************");
        System.out.println("************ The user can select a sub-directory in this path *****************");
        System.out.println("************* which can be specific to the part of the API accessed ***********");
        System.out.println("************* for example: 'OHLC' can be added to one of the paths  ***********");
        System.out.println("*****(example) shown above: "+strBhome.toString()+"/OHLC **********************");
        System.out.println("*******************************************************************************");
        System.out.println("*** Please enter '1' (default/working) -or- '2' (home) filepath option ********");
        System.out.println("*******************************************************************************");

        int go = 1;
        userInput = "go";

        while(!userInput.contains("q")&&go==1){

            userInput = this.userInput(br, "choice");

            if(userInput.equals("1")){
                System.out.println("In enterFILEPATH. User selects the default working directory path for storing output. "+strBcurr.toString());
                conObj.uAct = UserActions.CONFIRMFILEPTH;
                conObj.uParams = UserParams.USE_CURRENTPTH;
                go = 0;
            }else{
                if(userInput.equals("2")){
                    System.out.println("In enterFILEPATH. User selects the user's home directory path for storing output."+strBhome.toString());
                    conObj.uAct = UserActions.CONFIRMFILEPTH;
                    conObj.uParams = UserParams.USE_HOMEDIR;
                    go = 0;
                }else{
                    if(userInput.equals("q")){
                        System.out.println("User has selected 'q' so the enter filepath option processing ends.");
                        System.out.println("User will be sent back to MAIN MENU to select another option or quit the application entirely.");
                        conObj.uAct = UserActions.MENU;
                        go =0;
                    }else {
                        System.out.println("Please enter '1' for current working directory, '2' for home directory or 'q' to exit the filepath selection process.");
                    }
                }
            }



        }

        //  conObj.uAct = UserActions.CONFIRMFILEPTH;
        // conObj.uParams = UserParams.USE_DEFAULTPTH;

        System.out.println("*********Sending User to Confirm File Path - will finish later********");

        return(conObj);
    }

    public UserControlObj nameAPIPath(BufferedReader br, String promptText) throws IOException{

        UserControlObj conObj = new UserControlObj();
        String filePath = null;
        String userInput = null;
        StringBuilder strBfinalPath = new StringBuilder();
        int go = 1;

        System.out.println("************ Arriving here after selecting a path location directory **********");
        System.out.println("************ The user now can select a sub-directory in this path *************");
        System.out.println("************* which can be specific to the part of the API accessed ***********");
        System.out.println("************* for example: 'OHLC' can be added to one of the paths  ***********");
        System.out.println("*****(example): "+this.getFileOutPath()+"/OHLC ********************");
        System.out.println("*******************************************************************************");
        System.out.println("*** Please enter a sub-directory filepath option ********");
        System.out.println("*******************************************************************************");

        filePath = this.getFileOutPath();
        userInput = this.userInput(br,promptText);
        System.out.println("User entered:  "+userInput+" as a sub-directory path to use for this api.");

        if(userInput.length() > 0 && userInput != null){
            System.out.println("User entered a string of length: "+ userInput.length()+"  with a value of: "+userInput);
            strBfinalPath.append(filePath);
            strBfinalPath.append("/");
            strBfinalPath.append(userInput);
            System.out.println("Final PATH will be set to: "+strBfinalPath.toString());
            conObj.uAct = UserActions.BUILDAPIPTH;
            conObj.setUserInput(strBfinalPath.toString());
            conObj.setNextAction("BUILDAPIPATH");
        }
        else{
            conObj.uAct = UserActions.NAMEAPIPTH;//Send the flow back here to name the PATH again until it is valid
        }
        return(conObj);
    }

    public UserControlObj buildAPIPath(BufferedReader br, String pathStr) throws IOException{
        UserControlObj conObj = new UserControlObj();
        StringBuilder strBfile = new StringBuilder();
        StringBuilder strBpath = new StringBuilder();

        long time = System.currentTimeMillis();
        String nameFile = Long.toString(time);
        java.sql.Timestamp timeStamp = new Timestamp(time);
        java.sql.Date date = new java.sql.Date(timeStamp.getTime());
        System.out.println("Date String: "+date);
        System.out.println("Time string: "+timeStamp);

        strBfile.append(pathStr);
        strBfile.append("/");
        strBfile.append(nameFile);
        String finalName = null;
        try {
            finalName = HashWithBouncyCastle(strBfile.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("Filename to add to PATH for naming the API output: "+finalName);

        strBpath.append(pathStr);
        strBpath.append("/");
        strBpath.append(date);

        Path dirPath = Paths.get(strBpath.toString());

        System.out.println("Final Directory path before filename is added: "+dirPath.toString());

        strBpath.append("/");
        strBpath.append(finalName);
        strBpath.append(".json");


        System.out.println("Full final path name to add to PATH for naming the API output: "+strBpath.toString());

        Path finalOutPathF = Paths.get(strBpath.toString());

        if(!Files.exists(dirPath)){
            Files.createDirectories(dirPath);
            System.out.println("Created directories in the path: "+dirPath.toString());
        }

        conObj.setOutputPath(dirPath.toString());
        conObj.setOutFile(finalOutPathF.toString());

        System.out.println("Inside buildAPIPTH, building path for: "+this.getFileOutPath());
        System.out.println("Inside buildAPIPTH, building path for: "+finalOutPathF.toString());

        conObj.setUserInput("BUILD PATH");

        return(conObj);
    }

    public UserControlObj confirmFinal(BufferedReader br, String pathStr) throws IOException {
        UserControlObj conObj = new UserControlObj();
        conObj.setOutputPath(pathStr);
        String userInput = null;
        int go = 1;


        System.out.println("Please confirm your final choices for the API URL and the File Output Information");
        System.out.println("*******************************************************************************************");
        System.out.println("Your choice of API URL at this time is (you may have not set it yet) : "+this.getUrlAPI());
        System.out.println("If you did not set it yet this is a default, you can choose to enter a new URL below.");
        System.out.println("*******************************************************************************************");

        System.out.println("Your choice of FILE OUTPUT PATH at this time is : "+conObj.getOutputPath());
        System.out.println("*******************************************************************************************");
        System.out.println("If you want to use these settings for FILE PTH and URL/API enter '1'");
        System.out.println("*******************************************************************************************");
        System.out.println("If you want to re-enter the settings for URL/API enter '2'");
        System.out.println("*******************************************************************************************");
        System.out.println("If you want to re-enter the settings for File Path alone enter '3'");
        System.out.println("*******************************************************************************************");
        System.out.println("If you want to re-enter the settings for API/URL and File Path enter '4'");
        System.out.println("*******************************************************************************************");
        System.out.println("If you select '4' you will go back to the main menu and can set each of these individually");
        System.out.println("*******************************************************************************************");

        userInput = "go";
        while(!userInput.contains("q")&&go==1){
            userInput = this.userInput(br,"choice");
            if(userInput.equals("1")){
                System.out.println("User has approved the current settings. Writing API output for:");
                System.out.println("**** API URL : "+this.getUrlAPI());
                System.out.println("**** File output PATH : "+conObj.getOutputPath());
                this.setFileOutPath(conObj.getOutputPath());
                conObj.uAct = UserActions.WRITEAPI;
                go = 0;
            }
            else{
                if(userInput.equals("2")){
                    System.out.println("User elects to change API/URL setting. CURRENT API setting :");
                    System.out.println("**** API URL : "+this.getUrlAPI());
                    System.out.println("**** File output PATH : "+conObj.getOutputPath());
                    conObj.uAct = UserActions.ENTERURL;
                    go = 0;
                }
                else{
                    if(userInput.equals("3")){
                        System.out.println("User elects to change FILEPATH setting. CURRENT FILEPATH setting :");
                        System.out.println("**** (Just as reminder) API : "+this.getUrlAPI());
                        System.out.println("**** Current File output PATH : "+conObj.getOutputPath());
                        conObj.uAct = UserActions.ENTERFILEPTH;
                        go = 0;
                    }
                    else{
                        System.out.println("User elects to change FILEPATH AND API/URL settings. CURRENT FILEPATH/URL setting :");
                        System.out.println("**** API : "+this.getUrlAPI());
                        System.out.println("**** Current File output PATH : "+conObj.getOutputPath());
                        conObj.uAct = UserActions.MENU;
                        go = 0;
                    }
                }
            }
        }

        conObj.uDirection = UserParams.USE_FILEOUTPUTONLY;

        return(conObj);
    }

    public static void main(String args[]) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String defaultURL = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=2000";
        String userHomeDir = System.getProperty("user.home");
        String userCurrDir = System.getProperty("user.dir");
        String userInput = "/home/stevenakers/JsonAPIOutput";
        String APIKey = "3739122ae9ce45cb15ebe7260c9565e616456cd06cadaaa4757bb7f18c8e1db5";
        int itemCnt = 0;

        String dataDir = "/JsonAPIOutput";

        StringBuilder strBhome = new StringBuilder();
        StringBuilder strBcurr = new StringBuilder();

        strBhome.append(userHomeDir);
        strBhome.append(dataDir);
        strBcurr.append(userCurrDir);
        strBcurr.append(dataDir);

        ScanAPIObj scanObj = new ScanAPIObj(defaultURL,strBhome.toString(),itemCnt,defaultURL,strBcurr.toString(), strBcurr.toString(), false,false);

        System.out.println("After Constructor my default paths (current working and HOME) are: (working) "+strBcurr.toString()+" HOME: "+strBhome.toString());
        System.out.println("Default path: "+scanObj.getDefaultPath()+" CurrentWorkPath:  "+scanObj.getCurrWorkDirPath()+" Home PATH: "+scanObj.getFileOutPath());
        String userScan = null;

        System.out.println("User home directory: "+userHomeDir);

        System.out.println("Scan Object created :");
        System.out.println("Scan object default URL: "+scanObj.getUrlAPI());
        System.out.println("Scan object default File Output path: "+scanObj.getFileOutPath());

        UserControlObj controlObj = new UserControlObj();
        controlObj.uAct = UserActions.MENU;
        controlObj.uParams = UserParams.USE_FILEANDAPINOTSET;
        UserActions uAction = controlObj.uAct;
        controlObj.API_SET = false;
        controlObj.FILEPTH_SET = false;

        System.out.println("uAction value: "+uAction);
        //Initialize the worklist with the MAIN MENU option
        int listSize = workList.size();
        workList.add(controlObj);
        UserControlObj tmpresultObj;
        int limit =0;

        for(;limit< 15;) {
            System.out.println("Work list size/length: " + listSize);
            UserControlObj tmpCntl = workList.get(listSize);
            UserControlObj resultObj = new UserControlObj();
            System.out.println("List ENUM entry from LIST: " + tmpCntl.uAct);
            limit++;

            switch (tmpCntl.uAct) {

                case MENU:
                    System.out.println("I will display the main menu now.");
                    resultObj = scanObj.mainMenu(br);
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    System.out.println("New workList size: "+workList.size());
                    System.out.println("Result of Main Menu call: " + resultObj.getUserInput());
                    System.out.println("Result next action: " + resultObj.getNextAction());
                    workList.add(resultObj);//SET UP next step in the processing
                    System.out.println("New workList size: "+workList.size());
                    break;
                case ENTERURL:
                    System.out.println("Default API URL is: "+scanObj.getUrlAPI());
                    System.out.println("CASE ENTERURL - Enter '1' to accept the default URL, -or- '2' to enter a new one:");
                    UserControlObj tmpObj = workList.get(listSize);
                    resultObj = scanObj.enterURL(br,tmpObj);

                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    System.out.println("New workList size: "+workList.size());
                    System.out.println("Result of enter URL call: " + resultObj.getUserInput());
                    System.out.println("Result next action: " + resultObj.getNextAction());
                    workList.add(resultObj);//SET UP next step in the processing
                    System.out.println("New workList size: "+workList.size());
                    System.out.println("UserActions uAct value is: "+resultObj.uAct);
                    System.out.println("UserParams value is: "+resultObj.uParams);
                    if(resultObj.uParams == UserParams.USE_DEFAULTURL){
                        String UrlAPI = scanObj.getDefaultAPI();
                        scanObj.setUrlAPI(UrlAPI);
                        scanObj.setAPI_SET(true);
                        System.out.println("In MAIN and user wants to use: "+resultObj.uParams);
                        System.out.println("setting DEFAULT URL: "+scanObj.getUrlAPI());
                    }
                    else{
                        scanObj.setUrlAPI(resultObj.getUserInput());
                        System.out.println("END OF ENTERURL and APIURL is: "+scanObj.getUrlAPI());
                        System.out.println("User Action is to : "+resultObj.uAct);
                        System.out.println("In MAIN and user wants to use: "+resultObj.uParams);
                    }
                    System.out.println("END OF ENTERURL and APIURL is: "+scanObj.getUrlAPI());
                    //1limit = 10;
                    break;
                case VERIFYURL:
                    System.out.println("SENT USER HERE TO VERIFY URL. API URL is: "+scanObj.getUrlAPI());
                    userScan = scanObj.getUrlAPI();
                    if(!(userScan == null)){
                        scanObj.setUrlAPI(userScan);
                    }
                    tmpresultObj = workList.get(listSize);

                    System.out.println("In CONFIRM URL and tmpresultObj has a path value of:"+tmpresultObj.getOutputPath());
                    System.out.println("In MAIN VERIFY URL and tmpresultObj has a file value of:"+tmpresultObj.getOutFile());
                    resultObj = scanObj.verifyURL(br,tmpresultObj);
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    System.out.println("New workList size: "+workList.size());
                    System.out.println("Result of verifyURL call: " + resultObj.getUserInput());
                    System.out.println("Result next action: " + resultObj.getNextAction());
                    if(resultObj.uAct==UserActions.ENTERURL){
                        System.out.println("Re-setting URL API to default from current value: "+scanObj.getUrlAPI());
                        scanObj.setUrlAPI(defaultURL);
                        System.out.println("RESET VALUE (after RESETTING has taken place): "+scanObj.getUrlAPI());
                    }
                    workList.add(resultObj);//SET UP next step in the processing
                    System.out.println("New workList size: "+workList.size());
                    //limit = 10;
                    break;
                case CONFIRMURL:
                    System.out.println("Sent to Confirm URL.");
                    UserControlObj tempcontrolObj;
                    tempcontrolObj = workList.get(listSize);
                    System.out.println("In CONFIRM URL and tempcontrolObj has a file value of:"+tempcontrolObj.getOutFile());
                    System.out.println("In CONFIRM URL and tempcontrolObj has a file value of:"+tempcontrolObj.getOutputPath());
                    if(tempcontrolObj.uDirection == UserParams.USE_FILEOUTPUTONLY){
                        System.out.println("Using this for FILE OUTPUT only.");
                        resultObj.uAct = UserActions.WRITEAPI;
                        resultObj.setOutFile(tempcontrolObj.getOutFile());
                        resultObj.setOutputPath(tempcontrolObj.getOutputPath());
                    }
                    else{
                        scanObj.viewAPI(scanObj.getUrlAPI());
                        resultObj.uAct = UserActions.MENU;
                    }
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    workList.add(resultObj);
                    //limit = 10;
                    break;
                case ENTERFILEPTH:
                    System.out.println("Enter '1' to accept the default PATH, -or- '2' to enter a new one:");
                    resultObj = scanObj.enterFilePath(br,"path");
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    resultObj.uAct = UserActions.CONFIRMFILEPTH;
                    workList.add(resultObj);
                    // limit = 10;
                    break;
                case CONFIRMFILEPTH:
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    if(resultObj.uParams == UserParams.USE_CURRENTPTH){
                        System.out.println("Setting file output path to use current working directory: "+scanObj.getDefaultPath());
                        scanObj.setFileOutPath(scanObj.getDefaultPath());
                        System.out.println("After setting output path we have: "+scanObj.getFileOutPath());
                    }
                    else{
                        System.out.println("Output path is designated as using the user HOME directory: "+scanObj.getFileOutPath());
                    }
                    resultObj.uAct = UserActions.NAMEAPIPTH;
                    workList.add(resultObj);
                    //limit = 10;
                    break;
                case NAMEAPIPTH:
                    workList.remove(listSize);//REMOVE old item from worklist, make room for new item
                    System.out.println("In main, (NAMEAPIPTH)  where user will be asked to enter a sub-directory to NAME the API output (so to speak)");
                    resultObj = scanObj.nameAPIPath(br,"API Name (for sub-directory) :");
                    System.out.println("Setting API NAME into FILE PATH: "+resultObj.getUserInput());
                    System.out.println("NEXT ACTION in resultObj: "+resultObj.uAct);
                    scanObj.setFileOutPath(resultObj.getUserInput());
                    System.out.println("Getting final Out Path before building: "+scanObj.getFileOutPath());
                    resultObj.uAct = UserActions.BUILDAPIPTH;
                    workList.add(resultObj);
                    //limit=10;
                    break;
                case BUILDAPIPTH:
                    tmpresultObj = workList.remove(listSize);
                    String filePth = scanObj.getFileOutPath();
                    System.out.println("In main, BUILDAPIPTH case, user params set to:"+tmpresultObj.uParams);
                    resultObj = scanObj.buildAPIPath(br,filePth);
                    System.out.println("Final PATH set, on to confirm PATH and URL and then to write API output.");
                    System.out.println("File OutputPath from result object: "+resultObj.getOutputPath());
                    System.out.println("File Output FILE from result object: "+resultObj.getOutFile());
                    System.out.println("Value of limit in for loop: "+limit);
                    resultObj.uAct = UserActions.CONFIRMFINAL;
                    workList.add(resultObj);
                    //limit=10;
                    break;
                case CONFIRMFINAL:
                    tmpresultObj = workList.get(listSize);
                    workList.remove(listSize);
                    System.out.println("Moving on to confirm final choices.");
                    System.out.println("File Output FILE from result (tmpresult) object: "+tmpresultObj.getOutFile());
                    System.out.println("File Output FILE from result object: "+resultObj.getOutFile());
                    System.out.println("Final selected output PATH: "+tmpresultObj.getOutputPath());
                    System.out.println("Final selected output FILE: "+tmpresultObj.getOutFile());
                    resultObj = scanObj.confirmFinal(br,tmpresultObj.getOutputPath());
                    resultObj.setOutputPath(tmpresultObj.getOutputPath());
                    resultObj.setOutFile(tmpresultObj.getOutFile());
                    System.out.println("CONFIRM FINAL output: "+resultObj.uDirection);
                    workList.add(resultObj);
                    //limit = 10;
                    break;
                case WRITEAPI:
                    System.out.println("Made it to WRITEAPI");
                    tmpresultObj = workList.get(listSize);
                    workList.remove(listSize);
                    System.out.println("Moving on to confirm final choices.");
                    System.out.println("Final selected output PATH: "+tmpresultObj.getOutputPath());
                    System.out.println("Final selected output FILE: "+tmpresultObj.getOutFile());
                    scanObj.setFinalWritePath(tmpresultObj.getOutFile());
                    System.out.println("Final selected output file from Scan OBJ: "+scanObj.getFileOutPath());
                    System.out.println("Final PATH with final FILE from SCAN OBJ: "+scanObj.getFinalWritePath());
                    scanObj.writeAPIData(scanObj.getUrlAPI(),scanObj.getFinalWritePath());
                    workList.add(resultObj);
                    limit = 15;
                    break;
                case QUIT:
                    System.out.println("I will stop the loop now as USER indicates the QUIT Action");
                    limit=15;
                    break;
                default:
                    System.out.println("Please enter 'MENU' or 'QUIT'");
                    break;
            }

        }
        //scanObj.controlLoop();
        br.close();
        return;

    }

}
