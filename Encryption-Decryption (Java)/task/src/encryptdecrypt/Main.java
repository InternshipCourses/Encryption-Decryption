package encryptdecrypt;

import java.io.File;

import java.io.FileWriter;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Collections.unmodifiableMap;

public class Main {


    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static final char FIRST_CHARACTER = ' '; // using 'empty space' the first (32)
    static final char LAST_CHARACTER = '~' ; // using '~' has the last (126)
    static final  Map<String, String> DEFAULT;

    static final String MODE_KEY = "-mode";
    static final String DATA_KEY = "-data";

    static {
        final var map = new HashMap<String, String>();
        map.put("-key", "0");
        map.put(MODE_KEY,"enc");
        map.put("-alg","shift");
        map.put(DATA_KEY,"");
        map.put("-in","");
        map.put("-out","");
        DEFAULT = unmodifiableMap(map);
    }

    public static String encryption(String input, int key) {
        StringBuilder result = new StringBuilder() ;

        final char[] characterArray = input.toCharArray();

        for (char currentCharacter: characterArray) {
            if ((currentCharacter + key ) > LAST_CHARACTER) { //Checking if the letter to shift to is more than the last character
                int tempKey = key - (LAST_CHARACTER - currentCharacter);//new key to use at with the first character to get the new letter
                currentCharacter = (char) (tempKey + FIRST_CHARACTER);
            } else {
                currentCharacter = (char) (currentCharacter + key);
            }
            result.append(currentCharacter) ;
        }
        return result.toString();
    }

    public static String decryption(String input, int key) {
        StringBuilder result = new StringBuilder();

        final char[] characterArray = input.toCharArray();

        for (char currentCharacter : characterArray) {
            if ((currentCharacter - key ) < FIRST_CHARACTER) { //Checking if the letter to shift to is less than the first character
                int tempKey = key - (currentCharacter - FIRST_CHARACTER);//new key to use at with the last character to get the new letter
                currentCharacter = (char) ( LAST_CHARACTER - tempKey );
            } else {
                currentCharacter = (char) (currentCharacter - key);
            }
            result.append(currentCharacter);
        }
        return result.toString();
    }

    public static String encryptionShift(String input, int key) {
        StringBuilder result = new StringBuilder();

        final char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {
            if (Character.isAlphabetic(currentCharacter)) { // checking if its a valid letter
                final char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                final char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';
                if((currentCharacter + key) > endLetter) {
                    // new Key to use at the beginning of the alphabet
                    int tempKey = key - (endLetter - currentCharacter) - 1;
                    currentCharacter = (char) (tempKey + startLetter);
                }else {
                    currentCharacter = (char) (currentCharacter + key);
                }
            }
            result.append(currentCharacter);
        }
        return result.toString();
    }

    public static String decryptionShift(String input, int key) {
        StringBuilder result = new StringBuilder() ;
        final char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {
            if (Character.isAlphabetic(currentCharacter)) { // checking if its a valid letter
                final char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                final char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';
                if ((currentCharacter - key) < startLetter) {
                    //new key to use at the end of the alphabet
                    int tempKey = (key + (startLetter - currentCharacter) -1);
                    currentCharacter = (char) (endLetter - tempKey );
                } else {
                    currentCharacter = (char) (currentCharacter - key);
                }
            }
            result.append(currentCharacter);
        }
        return  result.toString();
    }


    public static StringBuilder cypherOperation(Map<String, String> operator, String inputData) {

        StringBuilder result = new StringBuilder();

        int inputKey = Integer.parseInt(operator.get("-key")) ;

        switch (operator.get("-alg")) {
            case "shift" -> result = shiftEncryptionDecryptionOperation(operator, inputData, inputKey);
            case "unicode" -> result = unicodeEncryptionDecryptionOperation(operator, inputData, inputKey);
            default ->{/*nothing will be done*/}
        }
        return result;
    }

    private static StringBuilder unicodeEncryptionDecryptionOperation(Map<String, String> operator, String inputData, int inputKey) {
        return switch (operator.get(MODE_KEY)) {
            case "enc" -> new StringBuilder(encryption(inputData, inputKey));
            case "dec" -> new StringBuilder(decryption(inputData, inputKey));
            default -> new StringBuilder();
        };
    }

    private static StringBuilder shiftEncryptionDecryptionOperation(Map<String, String> operator, String inputData, int inputKey) {
        return switch (operator.get(MODE_KEY)) {
            case "enc" -> new StringBuilder(encryptionShift(inputData, inputKey));
            case "dec" -> new StringBuilder(decryptionShift(inputData, inputKey));
            default -> new StringBuilder();
        };
    }

    private static void assignKeyToValue(String[] args, Map<String, String> operator) {
        int i = 0;
        while (i < args.length) {
            if (DEFAULT.containsKey(args[i]) && (i+2) < args.length) {
                if (args[i + 1].charAt(0) != '-') {
                    operator.put(args[i], args[i + 1]);
                    i += 2;
                } else {
                    operator.put(args[i], DEFAULT.get(args[i]));
                    i++;
                }
            } else {
                i++;
            }
        }
        for (Map.Entry<String, String> defaultMap: DEFAULT.entrySet()) {
            operator.putIfAbsent(defaultMap.getKey(), defaultMap.getValue());
        }

    }

    public static void main(String[] args) {
        Map<String, String> operator = new HashMap<>();

        assignKeyToValue(args, operator);

        String inputData = operator.get(DATA_KEY);

        if (operator.get(DATA_KEY).equals("")) {
            File xFile = new File(operator.get("-in"));
            try (Scanner scannerFile = new Scanner(xFile)) {
                while (scannerFile.hasNext()) {
                    inputData = scannerFile.nextLine();
                }
                if (inputData.equals("")) {
                    logger.info("Please prove a valid input!!!");
                    return;
                }
            } catch (Exception e) {
                logger.info("Please prove a valid input!!!");
            }
        }

        StringBuilder result = cypherOperation(operator, inputData);
        try (FileWriter fileWriter = new FileWriter(operator.get("-out"))) {
            fileWriter.write(result.toString());
        } catch (Exception e) {
            logger.info(String.valueOf(result));
        } finally {
            logger.info("Save completed");
        }
    }


}
