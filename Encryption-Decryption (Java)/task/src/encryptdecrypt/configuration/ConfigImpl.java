package encryptdecrypt.configuration;

import encryptdecrypt.input_output.impl.FileIO;
import encryptdecrypt.input_output.Input;
import encryptdecrypt.input_output.Output;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static encryptdecrypt.cypher.CypherProcessing.encryptOrDecryptData;
import static encryptdecrypt.cypher.CypherProcessing.getCypher;
import static java.util.Collections.unmodifiableMap;

public class ConfigImpl implements Config{

    private static final Logger logger = Logger.getLogger(ConfigImpl.class.getName());
    private static final Map<String, String> DEFAULT;
    private static final String MODE_KEY = "-mode";
    private static final String DATA_KEY = "-data";
    private static final String CYPHER_KEY = "-key";
    private static final String ALGORITHM_KEY = "-alg";
    private static final String INPUT_FILE_KEY = "-in";
    private static final String OUTPUT_FILE_KEY = "-out";
    static {
        final var map = new HashMap<String, String>();
        map.put(CYPHER_KEY, "0");
        map.put(MODE_KEY,"enc");
        map.put(ALGORITHM_KEY,"shift");
        map.put(DATA_KEY,"");
        map.put(INPUT_FILE_KEY,"");
        map.put(OUTPUT_FILE_KEY,"");
        DEFAULT = unmodifiableMap(map);
    }

    private final Map<String, String> operatorKeyAndValue;

    public ConfigImpl(String[] args) {
       this.operatorKeyAndValue = assignKeyToValue(args);
    }

    public Map<String, String> assignKeyToValue(String[] args) {

        Map<String, String> newOperatorKeyAndValue = new HashMap<>();

        int i = 0;
        while (i < args.length) {
            if (DEFAULT.containsKey(args[i])) {
                if (args[i + 1].charAt(0) != '-') {
                    newOperatorKeyAndValue.put(args[i], args[i + 1]);
                    i += 2;
                } else {
                    newOperatorKeyAndValue.put(args[i], DEFAULT.get(args[i]));
                    i++;
                }
            } else {
                i++;
            }
        }

        for (Map.Entry<String, String> defaultMap: DEFAULT.entrySet()) {
            newOperatorKeyAndValue.putIfAbsent(defaultMap.getKey(), defaultMap.getValue());
        }

        return newOperatorKeyAndValue;
    }

    public String dataToCypher(String inputData){

        if ("".equals(inputData)) {
            inputData = new  FileIO(operatorKeyAndValue.get(INPUT_FILE_KEY)).read();
            if ("".equals(inputData)){
                logger.info("No data pass so there is nothing to Operate on");
            }
        }
        return inputData;
    }

    @Override
    public Input getInput() {

        return ()-> encryptOrDecryptData(operatorKeyAndValue.get(MODE_KEY),
                        dataToCypher(operatorKeyAndValue.get(DATA_KEY)),
                        getCypher(operatorKeyAndValue.get(ALGORITHM_KEY),
                                Integer.parseInt(operatorKeyAndValue.get(CYPHER_KEY))));
    }

    @Override
    public Output getOutput() {

        return (operatorKeyAndValue.get(OUTPUT_FILE_KEY).equals("")) ?
                input -> logger.info(input) :
                input -> new FileIO(operatorKeyAndValue.get(OUTPUT_FILE_KEY)).write(input);
    }
}
