package encryptdecrypt.cypher.impl;

import encryptdecrypt.cypher.Cypher;

public class ShiftCypher implements Cypher {

    private final int key;

    public ShiftCypher(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String rawData) {
        StringBuilder result = new StringBuilder();
        final char[] inputArray = rawData.toCharArray();
        for (char currentCharacter : inputArray) {
            if (Character.isAlphabetic(currentCharacter)) { // checking if its a valid letter
                final char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                final char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';
                if((currentCharacter + key) > endLetter) {
                    // new Key to use at the beginning of the alphabet
                    int tempKey = key - (endLetter - currentCharacter) - 1;
                    currentCharacter = (char) (tempKey + startLetter);
                } else {
                    currentCharacter = (char) (currentCharacter + key);
                }
            }
            result.append(currentCharacter);
        }
        return result.toString();    }

    @Override
    public String decrypt(String rawData) {
        StringBuilder result = new StringBuilder() ;
        final char[] inputArray = rawData.toCharArray();
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
}
