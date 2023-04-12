package encryptdecrypt.NewPattern;

import java.util.Scanner;

import static encryptdecrypt.DefaultConfigSettings.assignKeyToValue;

public class TestingMain {
    public static void main(String[] args) {
//        new App(new ConfigImp(assignKeyToValue(args))).run();

        new App(new Input() {
            @Override
            public String read() {
                return new Scanner(System.in).nextLine();
            }
        }, new Output() {
            @Override
            public void write(String input) {
                System.out.println(input);
            }
        });
    }
}
