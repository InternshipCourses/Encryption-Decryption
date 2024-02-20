package encryptdecrypt.cypher;

public interface Cypher {

    String encrypt(String rawData);
    String decrypt(String rawData);

}
