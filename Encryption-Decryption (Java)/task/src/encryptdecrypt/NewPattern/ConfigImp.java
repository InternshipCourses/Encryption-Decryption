package encryptdecrypt.NewPattern;

import encryptdecrypt.AppFileImp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import static encryptdecrypt.DefaultConfigSettings.*;

public class ConfigImp implements Config {

    private final Map<String, String> argMap;

    public ConfigImp(Map<String, String> stringStringMap) {
        this.argMap = stringStringMap;
    }

   /*  @Override
   public String getInput() {
        return argMap.get(INPUT_FILE_KEY);
    }

    @Override
    public String getOutput() {
        return argMap.get(OUTPUT_FILE_KEY);
    }

    @Override
    public int getKey() {
        return Integer.parseInt(argMap.get(CYPHER_KEY));
    }

    @Override
    public String getMode() {
        return argMap.get(MODE_KEY);
    }

    @Override
    public String getAlgorithm() {
        return argMap.get(ALGORITHM_KEY);
    }

    @Override
    public String getData() {
        return dataToCypher(argMap.get(DATA_KEY));
    }*/

    public String dataToCypher(String inputData){


        if (inputData.equals("")) {
            inputData = new AppFileImp().read(argMap.get(INPUT_FILE_KEY));
            if (inputData.equals("")){
//                logger.info("No data pass so there is nothing to Operate on");
            }
        }
        return inputData;
    }
    @Override
    public Input getInput() {
        return null ;
    }

    @Override
    public Output getOutput() {
        return null;
    }


}
