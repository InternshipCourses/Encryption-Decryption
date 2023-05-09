package encryptdecrypt;

import encryptdecrypt.demo_app.App;
import encryptdecrypt.configuration.ConfigImpl;

public class Main {
    public static void main(String[] args) {
        new App(new ConfigImpl(args)).run();
    }
}
