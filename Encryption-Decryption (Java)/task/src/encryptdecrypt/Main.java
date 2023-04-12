package encryptdecrypt;

import static encryptdecrypt.DefaultConfigSettings.*;
public class Main {
    public static void main(String[] args) {
        new EncryptDecryptApp(assignKeyToValue(args)).start();
    }
}
