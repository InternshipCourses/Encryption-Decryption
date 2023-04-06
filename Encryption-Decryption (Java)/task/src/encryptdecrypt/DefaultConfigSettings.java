package encryptdecrypt;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class DefaultConfigSettings {
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
        map.put(OUTPUT_FILE_KEY,"encryptdecrypt/Output_File.txt");
        DEFAULT = unmodifiableMap(map);
    }

    public static Map<String, String> assignKeyToValue(String[] args) {
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
        return newOperatorKeyAndValue;
    }

}
