package encryptdecrypt.demo_app;

import encryptdecrypt.cypher.Cypher;
import encryptdecrypt.input_output.impl.IO;

public class EncryptToFile implements IO {

    private IO fileIO;
    private Cypher cypher;

    public EncryptToFile(IO fileIO, Cypher cypher) {
        this.fileIO = fileIO;
        this.cypher = cypher;
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
