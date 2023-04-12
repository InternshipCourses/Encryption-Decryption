package encryptdecrypt.cypherImps;

import encryptdecrypt.Cypher;


public class UnicodeCypher implements Cypher {
    private static final char FIRST_CHARACTER = ' '; // using 'empty space' the first (32)
    private static final char LAST_CHARACTER = '~' ; // using '~' has the last (126)

    private final int key;

    public UnicodeCypher(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String rawData) {
        StringBuilder result = new StringBuilder() ;
        final char[] characterArray = rawData.toCharArray();
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

    @Override
    public String decrypt(String rawData) {
        StringBuilder result = new StringBuilder();

        final char[] characterArray = rawData.toCharArray();

        for (char currentCharacter : characterArray) {
            if ((currentCharacter - key ) < FIRST_CHARACTER) { //Checking if the letter to shift to is less than the first character
                int tempKey = key - (currentCharacter - FIRST_CHARACTER);//new key to use at with the last character to get the new letter
                currentCharacter = (char) ( LAST_CHARACTER - tempKey );
            } else {
                currentCharacter = (char) (currentCharacter - key);
            }
            result.append(currentCharacter);
        }
        return result.toString();    }
}
