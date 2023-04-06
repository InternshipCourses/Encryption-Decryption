package encryptdecrypt;

import encryptdecrypt.NewPattern.DecryptToFile;
import encryptdecrypt.NewPattern.EncryptToFile;
import encryptdecrypt.NewPattern.FileIO;
import encryptdecrypt.NewPattern.IO;
import encryptdecrypt.cypherImps.ShiftCypher;
import encryptdecrypt.cypherImps.UnicodeCypher;

public class CypherFactory {
    public static Cypher getCypher(String cypherType,int key){
        return switch (cypherType){
            /*case "shift" -> new ShiftCypher(); */
            case "unicode" -> new UnicodeCypher(key);
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

    public static IO getEncryptOrDecryptDataToFile(String  mode, IO fileIO, Cypher cypherType) {
        return switch (mode) {
            /* case "enc" ->  This is the default selection*/
            case "dec" -> new DecryptToFile(fileIO,cypherType);
            default -> new EncryptToFile(fileIO,cypherType);
        };
    }

}
