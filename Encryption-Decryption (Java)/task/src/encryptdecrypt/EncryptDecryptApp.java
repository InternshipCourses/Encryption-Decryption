package encryptdecrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import static encryptdecrypt.CypherFactory.encryptOrDecryptData;
import static encryptdecrypt.CypherFactory.getCypher;
import static java.util.Collections.unmodifiableMap;

public class EncryptDecryptApp {
    private static final  Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final  Map<String, String> DEFAULT;
    public static final String MODE_KEY = "-mode"; // Ask if since they are final if using public will cause a problem
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
        map.put(OUTPUT_FILE_KEY,"encryptdecrypt/Output_File.txt");
        DEFAULT = unmodifiableMap(map);
    }

    private final Map<String, String> operator;

    public EncryptDecryptApp(String[] args) { // Todo make a config file
        this.operator = assignKeyToValue(args);
    }

    public void start() {
        String inputData = operator.get(DATA_KEY);

        if (operator.get(DATA_KEY).equals("")) {
            inputData = new AppFileImp().read(operator.get(INPUT_FILE_KEY));
            if (inputData.equals("")){
                logger.info("No data pass so there is nothing to Operate on");
                return;
            }
        }
        String result = encryptOrDecryptData(operator.get(MODE_KEY), inputData, Integer.parseInt(operator.get(CYPHER_KEY)),getCypher(operator.get(ALGORITHM_KEY)));
        new AppFileImp().write(operator.get(OUTPUT_FILE_KEY), result);
    }

    private  Map<String, String> assignKeyToValue(String[] args) {
        Map<String, String> newOperatorKeyAndValue = new HashMap<>();
        int i = 0;
        while (i < args.length) {
            if (DEFAULT.containsKey(args[i]) && (i+2) < args.length) {
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

}
