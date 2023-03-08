package encryptdecrypt;

import java.io.File;

import java.io.FileWriter;
import java.util.*;

import static java.util.Collections.unmodifiableMap;

public class Main {

    private final static Map<String, String> DEFAULT;

    static {

        final var map = new HashMap<String, String>();
        map.put("-key", "0");
        map.put("-mode","enc");
        map.put("-alg","shift");
        map.put("-data","");
        map.put("-in","");
        map.put("-out","");
        DEFAULT = unmodifiableMap(map);

    }

    public static String encryption(String input, int key){
        StringBuilder result = new StringBuilder() ;

        char[] characterArray = input.toCharArray();

        char firstCharacter = ' '; // using 'empty space' the first (32)
        char lastCharacter = '~' ; // using '~' has the last (126)

        for (char current: characterArray) {

            if ((current + key ) > lastCharacter){

                int tempKey = key - (lastCharacter - current);
                current = (char) (tempKey + firstCharacter);

            }else{

                current = (char) (current + key);

            }

            result.append(current) ;
        }

        return result.toString();
    }

    public static String decryption(String input, int key){
        StringBuilder result = new StringBuilder();

        char[] characterArray = input.toCharArray();

        char firstCharacter = ' '; // using 'empty space' the first (32)
        char lastCharacter = '~' ; // using '~' has the last (126)

        for (char currentCharacter : characterArray){

            if ((currentCharacter - key ) < firstCharacter){

                int tempKey = key - (currentCharacter - firstCharacter) ;
                currentCharacter = (char) ( lastCharacter - tempKey );

            }else{

                currentCharacter = (char) (currentCharacter - key);

            }

            result.append(currentCharacter);
        }

        return result.toString();
    }

    public static String encryptionShift(String input, int key){
        StringBuilder result = new StringBuilder();

        char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {

            if(Character.isAlphabetic(currentCharacter) ){ // checking if its a valid letter

                char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';

                if((currentCharacter + key) > endLetter){
                    // new Key to use at the beginning of the alphabet
                    int tempKey = key - (endLetter - currentCharacter) - 1;
                    currentCharacter = (char) (tempKey + startLetter);
                }else{
                    currentCharacter = (char) (currentCharacter + key);
                }
            }

            result.append(currentCharacter);
        }
        return  result.toString();
    }

    public static String decryptionShift(String input, int key){
        StringBuilder result = new StringBuilder() ;
        char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {

            if(Character.isAlphabetic(currentCharacter)){ // checking if its a valid letter

                char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';

                if((currentCharacter - key) < startLetter){

                    int tempKey = (key + (startLetter - currentCharacter) -1) ;
                    currentCharacter = (char) (endLetter - tempKey );

                }else{

                    currentCharacter = (char) (currentCharacter - key);

                }
            }

            result.append(currentCharacter);
        }
        return  result.toString();
    }

    private static StringBuilder cypherOperation(Map<String, String> operator, String inputData) {
        StringBuilder result = new StringBuilder();

        int inputKey = Integer.parseInt(operator.get("-key")) ;

        switch (operator.getOrDefault("-alg", "shift")) {

            case "shift" -> {

                switch (operator.getOrDefault("-mode", "enc")) {

                    case "enc" -> result = new StringBuilder(encryptionShift(inputData, inputKey));
                    case "dec" -> result = new StringBuilder(decryptionShift(inputData, inputKey));

                }
            }
            case "unicode" -> result = switch (operator.getOrDefault("-mode", "enc")) {

                case "enc" -> new StringBuilder(encryption(inputData, inputKey));
                case "dec" -> new StringBuilder(decryption(inputData, inputKey));
                default -> result;

            };
        }
        return result;
    }

    private static Map<String, String> assignKeyToValue(String[] args, Map<String, String> operator) {

        for (int i = 0; i < args.length;) {

            if (DEFAULT.containsKey(args[i])) {
                try {

                    if (args[i + 1].charAt(0) != '-') {
                        operator.put(args[i], args[i + 1]);
                        i += 2;
                    } else {
                        operator.put(args[i], DEFAULT.get(args[i]));
                        i++;
                    }

                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
                    operator.put(args[i], DEFAULT.get(args[i]));
                    break;
                }
            }else {
                i++;
            }
        }


        for (Map.Entry<String, String> defaultMap: DEFAULT.entrySet()){
            operator.putIfAbsent(defaultMap.getKey(), defaultMap.getValue());
        }



        return operator;
    }

    public static void main(String[] args) {

        Map<String, String> operator = new HashMap<>();

        assignKeyToValue(args, operator);

        String inputData = operator.get("-data");

        if (operator.get("-data").equals("")) {

            try {

                File xFile = new File(operator. get("-in"));

                Scanner scannerFile = new Scanner(xFile);

                while (scannerFile.hasNext()){

                    inputData = scannerFile.nextLine();

                }

            } catch (Exception e) {

                System.out.println("Please prove a valid input :" + e.getMessage());

                return;

            }
        }


        StringBuilder result = cypherOperation(operator, inputData);

        try {

            FileWriter fileWriter = new FileWriter(operator.get("-out"));
            fileWriter.write(result.toString());
            fileWriter.close();

            System.out.println("Save completed");

        } catch (Exception e) {

            System.out.println(result);

        }

    }


}
