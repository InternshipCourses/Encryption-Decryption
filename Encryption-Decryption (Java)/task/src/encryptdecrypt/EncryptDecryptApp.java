package encryptdecrypt;

import encryptdecrypt.NewPattern.IO;

import java.util.Map;
import java.util.logging.Logger;

import static encryptdecrypt.CypherFactory.encryptOrDecryptData;
import static encryptdecrypt.CypherFactory.getCypher;
import static encryptdecrypt.DefaultConfigSettings.*;

public class EncryptDecryptApp {
    private static final  Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Map<String, String> operator;

    public EncryptDecryptApp(Map<String, String> stringStringMap) {
        this.operator = stringStringMap;
    }

    public void start() {
        String inputData = dataToCypher(operator.get(DATA_KEY));

        String result = encryptOrDecryptData(operator.get(MODE_KEY),
                inputData, getCypher(operator.get(ALGORITHM_KEY),Integer.parseInt(operator.get(CYPHER_KEY))));
        new AppFileImp().write(operator.get(OUTPUT_FILE_KEY), result);
    }

    public String dataToCypher(String inputData){
        if (inputData.equals("")) {
            inputData = new AppFileImp().read(operator.get(INPUT_FILE_KEY));
            if (inputData.equals("")){
                logger.info("No data pass so there is nothing to Operate on");
            }
        }
        return inputData;
    }


}
