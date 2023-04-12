package encryptdecrypt.NewPattern;

import encryptdecrypt.Cypher;
import encryptdecrypt.CypherFactory;
import encryptdecrypt.cypherImps.UnicodeCypher;

public class App implements Runnable {

// private final Input in;
// private final Output out;
// private final Config config;

    private Input fromWhereWeAreGettingDate;
    private Output placeToPassEncryptedData;
    public App(Input fromWhereWeAreGettingDate, Output placeToPassEncryptedData) {
        this.placeToPassEncryptedData = placeToPassEncryptedData;
        this.fromWhereWeAreGettingDate = fromWhereWeAreGettingDate;
    }

    public App(Config config) {
        fromWhereWeAreGettingDate = config.getInput();
        placeToPassEncryptedData = config.getOutput();
    }

    @Override
    public void run() {
        placeToPassEncryptedData.write(fromWhereWeAreGettingDate.read());
//        Cypher currentCypher = CypherFactory.getCypher(config.getAlgorithm(), config.getKey());
//        IO doSomething = CypherFactory.getEncryptOrDecryptDataToFile(config.getMode(), new FileIO(config.getInput()),currentCypher);
//        doSomething.write(config.getData());
    }

}
