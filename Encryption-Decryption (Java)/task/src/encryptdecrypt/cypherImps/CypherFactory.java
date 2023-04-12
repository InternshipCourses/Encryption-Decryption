package encryptdecrypt.cypherImps;

import encryptdecrypt.Cypher;

public class CypherFactory {
    public static Cypher getCypher(String cypherType, int key){
        return switch (cypherType){
            /*shift cypher is the default cypher*/
            case "unicode" -> new UnicodeCypher(key);
            case "chuck" -> new ChuckNorrisCypher();
            default -> new ShiftCypher(key);
        };
    }

    public static String encryptOrDecryptData(String  mode, String inputData,Cypher cypherType) {
        return switch (mode) {
           /* case "enc" ->  This is the default selection*/
            case "dec" -> cypherType.decrypt(inputData);
            default -> cypherType.encrypt(inputData);
        };
    }

}
