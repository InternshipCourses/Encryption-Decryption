package encryptdecrypt.configuration;

import encryptdecrypt.input_output.Input;
import encryptdecrypt.input_output.Output;

public interface Config {
    Input getInput();
    Output getOutput();
}
