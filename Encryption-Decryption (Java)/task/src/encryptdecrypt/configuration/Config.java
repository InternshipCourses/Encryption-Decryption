package encryptdecrypt.configuration;

import encryptdecrypt.input_output_data.Input;
import encryptdecrypt.input_output_data.Output;

public interface Config {
    Input getInput();
    Output getOutput();

}
