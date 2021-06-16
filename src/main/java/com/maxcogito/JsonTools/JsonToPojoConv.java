package com.maxcogito.JsonTools;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

public class JsonToPojoConv {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        System.out.println("Please enter the packageName to be used for the generated Class: ");
        String inputPackageName = scanner.nextLine();
        System.out.println("User Input for Package Name: " + inputPackageName);
        System.out.println("Please enter the pathname to the json output file to use for converting an object: ");
        String inputPathName = scanner.nextLine();
        System.out.println("User Input for pathname that contains the JSON file : " + inputPathName);
        System.out.println("Please enter the filename of the JSON file that will be used for conversion:");
        String inputJsonFileName = scanner.nextLine();
        System.out.println("Json file to be converted into a Pojo: "+inputJsonFileName);
        System.out.println("Please enter the path for the output directory where the pojo should be written:");
        String inputPojoPathName = scanner.nextLine();
        System.out.println("User input for pathname to Pojo output directory: "+inputPojoPathName);

        Path inputJsonpath = Paths.get(inputPathName);
        Path inputJsonFile = inputJsonpath.resolve(inputJsonFileName);
        Path outputPojo = Paths.get(inputPojoPathName);

        System.out.println("Json file to convert to Pojo :"+inputJsonFile.toString());
        System.out.println("Output directory for converted Pojo: "+outputPojo.toString());
        //First make the output Directory and the parent directory structure
        File outputDir = new File(outputPojo.toString());

        if (outputDir.mkdirs())
        {
            System.out.println("Output Pojo directory and parent directories created: "+outputDir.toString());
        }
        else{
            if(outputDir.exists()){
                System.out.println("Output Directory already exists.");
            }
            else{
                System.out.println("Directory cannot be created.");
            }
        }

        //Now create a File object to use to read the input JSON file to pass to the converter

        File inputJson = new File(inputJsonFile.toString());

        if (inputJson.exists())
        {
            System.out.println("Input json file: "+inputJson.toString()+" exists, continue to conversion process");

            try {
                new JsonToPojoConv().convert2JSON(inputJson.toURI().toURL(), outputDir, inputPackageName, inputJson.getName().replace(".json", ""));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Encountered issue while converting to pojo: "+e.getMessage());
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Json input file does not exist. Returning without a conversion to POJO.");
        }
// /home/stevenakers/JsonAPIOutput/price/2021-02-26
///home/stevenakers/JsonAPIOutput/cryptocompare/OHLC/2021-01-03
        // cryptocompareOHLC.json
        // /home/stevenakers/APIOutput



  ///home/stevenakers/JsonAPIOutput/cryptocompare/ohlc/2021-01-05
        // /home/stevenakers/Documents/eclipse-projects/home/sakers/eclipse-workspace/JsonTools
        // /home/stevenakers/APIOutput

    }


    public void convert2JSON(URL inputJson, File outputPojoDirectory, String packageName, String className) throws IOException{
        JCodeModel codeModel = new JCodeModel();

        URL source = inputJson;

        System.out.println("Inside convertoPojo and absolute path of output will be: "+outputPojoDirectory.getAbsolutePath().toString());

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
            public SourceType getSourceType(){
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, className, packageName, source);

        codeModel.build(outputPojoDirectory);
    }


}
