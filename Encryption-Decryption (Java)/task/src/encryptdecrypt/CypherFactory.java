package encryptdecrypt;

import encryptdecrypt.cypherImps.ShiftCypher;
import encryptdecrypt.cypherImps.UnicodeCypher;

public class CypherFactory {
    public static Cypher getCypher(String cypherType){
        return switch (cypherType){
            /*case "shift" -> new ShiftCypher(); */
            case "unicode" -> new UnicodeCypher();
            default -> new ShiftCypher();
        };
    }

    public static String encryptOrDecryptData(String  mode, String inputData, int inputKey,Cypher cypherType) {
        return switch (mode) {
           /* case "enc" ->  This is the default selection*/
            case "dec" -> cypherType.decrypt(inputData, inputKey);
            default -> cypherType.encrypt(inputData, inputKey);
        };
    }

}
