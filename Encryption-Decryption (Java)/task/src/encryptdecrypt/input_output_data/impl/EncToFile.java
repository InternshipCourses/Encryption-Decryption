package encryptdecrypt.input_output_data.impl;

import encryptdecrypt.Cypher;
import encryptdecrypt.input_output_data.IO;

public class EncToFile implements IO {

    private IO fileIO;
    private Cypher cypher;

    //Todo Add a key that allow taking a input decrypting before encrypting with a new cypher
    public EncToFile(IO newFileIO, Cypher cypherToUse) {
        this.fileIO = newFileIO;
        this.cypher = cypherToUse;
    }

    @Override
    public String read() {
        return cypher.decrypt(fileIO.read());
    }

    @Override
    public void write(String date) {
        fileIO.write(cypher.encrypt(date));
    }

}
