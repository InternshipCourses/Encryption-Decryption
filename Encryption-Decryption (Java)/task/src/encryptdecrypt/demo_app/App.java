package encryptdecrypt.demo_app;

import encryptdecrypt.configuration.Config;

public class App implements Runnable {
    private final Config config;

    public App(Config config) {
       this.config = config;
    }

    @Override
    public void run() {
        String inputData = config.getInput().read();
        config.getOutput().write(inputData);
    }

}
