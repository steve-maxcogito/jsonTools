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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class RestAPIAccess {

    private static String defaultApi = null;
    private static String apiKey = null;

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        RestAPIAccess.apiKey = apiKey;
    }

    public static String getDefaultApi() {
        return defaultApi;
    }

    public static void setDefaultApi(String defaultApi) {
        RestAPIAccess.defaultApi = defaultApi;
    }

    public static String HashWithBouncyCastle(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        final String sha256hex = new String(Hex.encode(hash));
        return sha256hex;
    }

    public static File ReturnFileforAPI(String filepathLoc){
       // File jsonFile = null;
        String inputLine = new String();
        String userHome = System.getProperty("user.home");
        Path apiKeyPath = Paths.get(userHome);

        System.out.println("The path string : "+inputLine+"  was entered.");
        Path filePath = apiKeyPath.resolve(inputLine);
        System.out.println("Resulting in a working path for the JSON file of: "+filePath.toString());
        long time = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("LocalDateTime: " + localDateTime);
        System.out.println("Date from LDT: "+localDateTime.toLocalDate());
        //filePath.resolve(localDateTime.toLocalDate().toString());
        Path finalFilePath = filePath.resolve(localDateTime.toLocalDate().toString());
        System.out.println("Final file Path: "+finalFilePath.toString());
        String hashFileName = new String();
        hashFileName = finalFilePath.toString()+localDateTime.toString();
        String jsonFilename = null;

        try {
            jsonFilename = HashWithBouncyCastle(hashFileName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("JSON file name: "+jsonFilename);
        StringBuilder strB = new StringBuilder();
        strB.append(finalFilePath.toString());
        strB.append("/");
        strB.append(jsonFilename);
        strB.append(".json");

        System.out.println("Will now attempt to make the following file appear with all sub-directores: "+strB.toString());
        try {
            Files.createDirectories(Path.of(finalFilePath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File jsonFile = new File(strB.toString());


        return(jsonFile);
    }
//Returns full PATH object and file name of JSON file
    private static Path returnPathtoJSONFile(Path initPath, String userInput){
        initPath.resolve(userInput);
        long time = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        //filePath.resolve(localDateTime.toLocalDate().toString());
        Path finalFilePath = initPath.resolve(localDateTime.toLocalDate().toString());

        String hashFileName = new String();
        hashFileName = finalFilePath.toString()+localDateTime.toString();
        String jsonFilename = null;

        try {
            jsonFilename = HashWithBouncyCastle(hashFileName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        StringBuilder strb = new StringBuilder();
        strb.append(jsonFilename);
        strb.append(".json");

        Path completePath = finalFilePath.resolve(strb.toString());

        try {
            Files.createDirectories(finalFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(Files.exists(completePath)){
            System.out.println("File path :"+completePath+" exists");
        }
        return(completePath);
    }
    public static void writeAPIData(String httpsURL, String OutputPath) throws IOException {
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

    public static void main(String [] args){
        String collectAPI = null;
        String apiKey = "3739122ae9ce45cb15ebe7260c9565e616456cd06cadaaa4757bb7f18c8e1db5";
        String userHome = System.getProperty("user.home");
        String inputLine = new String();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        Path apiKeyPath = Paths.get(userHome);

        System.out.println("Current HOME directory: "+apiKeyPath.toString());
        System.out.println("Please enter a path where the JSON API output can be stored: ");
        inputLine = scanner.nextLine();
        System.out.println("The path string : "+inputLine+"  was entered.");

        Path filePath = apiKeyPath.resolve(inputLine);
        Path testPath = returnPathtoJSONFile(filePath,inputLine);

        System.out.println("Resulting in a working path for the JSON file of: "+testPath.toString());
        System.out.println("Please enter a URL for the API to supply the data to write to this file:");

        inputLine = scanner.nextLine();

        System.out.println("User entered an API of: "+inputLine);

        try {
            writeAPIData(inputLine,testPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
