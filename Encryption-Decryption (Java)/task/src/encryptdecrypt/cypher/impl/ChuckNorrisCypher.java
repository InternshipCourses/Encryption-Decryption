package encryptdecrypt.cypher.impl;

import encryptdecrypt.cypher.Cypher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ChuckNorrisCypher implements Cypher {

    private final Logger logger = Logger.getLogger(ChuckNorrisCypher.class.getName());

    @Override
    public String decrypt(String rawData) {
        String errorMsg = "Encoded string is not valid.";

        StringBuilder stringBuilder = convertNorrisCodeToStringOfBinary(rawData.split(" "));
        if (stringBuilder == null) {
            logger.info("The String inputted has the wrong formatted");
            return errorMsg;
        }

        List<String> arrayOfBinary = convertStringOfBinaryToListOfBinary(stringBuilder);
        if (arrayOfBinary.isEmpty()){
            logger.info(errorMsg);
            return errorMsg;
        }

        final StringBuilder result = new StringBuilder();
        arrayOfBinary.forEach(e -> {
            long character = Long.valueOf(e, 2);
            result.append((char)character);
        });

        return result.toString();
    }

    @Override
    public String encrypt(String rawData) {
        StringBuilder result = new StringBuilder();

        char[] binaryCodeArray = ConvertCharacterArrayToBinaryArray(rawData.toCharArray())
                                .toString()
                                .toCharArray();

        for (int currentIndex = 0; currentIndex < binaryCodeArray.length; currentIndex++) {
            if (binaryCodeArray[currentIndex] == '0') {
                result.append(" 00 0");
            } else if (binaryCodeArray[currentIndex] == '1') {
                result.append(" 0 0");
            }

            int nextElementIndex = currentIndex + 1;
            while ( nextElementIndex < binaryCodeArray.length
                    && binaryCodeArray[nextElementIndex] == binaryCodeArray[currentIndex]) {
                result.append("0");
                nextElementIndex++;
            }
            currentIndex = nextElementIndex - 1;
        }

        return result.toString().trim();
    }

    private static List<String> convertStringOfBinaryToListOfBinary(StringBuilder stringBuilder) {
        List<String> arrays = new ArrayList<>();
        int end = Math.min(stringBuilder.length(), 7);
        int index = 0;
        while (index < stringBuilder.length()) {
            String str = stringBuilder.substring(index, end);
            if (end - index != 7) {
                return Collections.emptyList();
            }

            arrays.add(str);
            index = end;

            if (stringBuilder.length() < end + 7) {
                end = stringBuilder.length();
            } else {
                end += 7;
            }
        }
        return arrays;
    }


    private static StringBuilder convertNorrisCodeToStringOfBinary(String[] strings) {
        if (strings.length % 2 != 0){
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i += 2 ){ // looping to change to binary
            if (strings[i].equals("0")){
                stringBuilder.append(strings[i+1].replace("0","1"));
            } else if (strings[i].equals("00")){
                stringBuilder.append(strings[i+1]);
            }
        }
        return stringBuilder;
    }



    private StringBuilder ConvertCharacterArrayToBinaryArray(char[] code) {
        StringBuilder binaryCode = new StringBuilder();
        for (char c : code) {
            binaryCode.append(String.format("%07d", Integer.valueOf(Integer.toBinaryString(c))));
        }
        return binaryCode;
    }
}
