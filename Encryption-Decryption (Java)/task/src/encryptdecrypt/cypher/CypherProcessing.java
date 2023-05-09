package encryptdecrypt.cypher;

import encryptdecrypt.cypher.impl.ChuckNorrisCypher;
import encryptdecrypt.cypher.impl.ShiftCypher;
import encryptdecrypt.cypher.impl.UnicodeCypher;

public class CypherProcessing {

    private CypherProcessing() {
    }
    /*
    * Returning the requested cypher to use*/
    public static Cypher getCypher(String cypherType, int key){
        return switch (cypherType){
            case "chuck" -> new ChuckNorrisCypher();
            case "unicode" -> new UnicodeCypher(key);
            default -> new ShiftCypher(key);
        };
    }

    /*
    *
    * Checking if the cypher is to encrypt or decrypt, if none is selected the cypher with encrypt by default
    * */
    public static String encryptOrDecryptData(String  mode, String inputData,Cypher cypherType) {
        return (mode.equals("dec")) ? cypherType.decrypt(inputData) :  cypherType.encrypt(inputData);
    }

}
