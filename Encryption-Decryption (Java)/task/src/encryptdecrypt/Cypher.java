package encryptdecrypt;

public interface Cypher {

    String encrypt(String rawData,int key);
    String decrypt(String rawData,int key);

}
