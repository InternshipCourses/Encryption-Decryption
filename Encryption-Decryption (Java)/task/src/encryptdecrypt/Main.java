package encryptdecrypt;

import encryptdecrypt.NewPattern.App;
import encryptdecrypt.configuration.AbstractConfig;

public class Main {
    public static void main(String[] args) {
        new App(new AbstractConfig(args)).run();
    }
}
