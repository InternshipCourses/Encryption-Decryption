package encryptdecrypt.configuration;

import encryptdecrypt.input_output_data.impl.FileIO;
import encryptdecrypt.input_output_data.Input;
import encryptdecrypt.input_output_data.Output;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static encryptdecrypt.cypherImps.CypherFactory.encryptOrDecryptData;
import static encryptdecrypt.cypherImps.CypherFactory.getCypher;
import static java.util.Collections.unmodifiableMap;

public class AbstractConfig implements Config {
    private final Logger logger = Logger.getLogger(AbstractConfig.class.getName());
    public static final Map<String, String> DEFAULT;
    public static final String MODE_KEY = "-mode";
    public static final String DATA_KEY = "-data";
    public static final String CYPHER_KEY = "-key";
    public static final String ALGORITHM_KEY = "-alg";
    public static final String INPUT_FILE_KEY = "-in";
    public static final String OUTPUT_FILE_KEY = "-out";

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

    public AbstractConfig(String[] args) {
        assignKeyToValue(args);
    }

    private Map<String, String> operationMap;

    public void assignKeyToValue(String[] args) {
        Map<String, String> newOperatorKeyAndValue = new HashMap<>();
        int i = 0;
        while (i < args.length) {
            if (DEFAULT.containsKey(args[i]) /*&& (i+2) < args.length*/) {
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

        this.operationMap = newOperatorKeyAndValue;
    }


    public String dataToCypher(){
        String inputData = operationMap.get(DATA_KEY);
        if (inputData.equals("")) {
            inputData = new FileIO(operationMap.get(INPUT_FILE_KEY)).read();
            if (inputData.equals("")){
                logger.warning("No Data was entered to Encrypted");
            }
        }
        return inputData;
    }

    @Override
    public Input getInput() {

        return () -> encryptOrDecryptData(operationMap.get(MODE_KEY),
                dataToCypher(),
                getCypher(operationMap.get(ALGORITHM_KEY),Integer.parseInt(operationMap.get(CYPHER_KEY))));
    }

    @Override
    public Output getOutput() {
        if (operationMap.get(OUTPUT_FILE_KEY).equals("")){
            return logger::info;
        } else {
            return input -> new FileIO(operationMap.get(OUTPUT_FILE_KEY)).write(input);
        }
    }

}
