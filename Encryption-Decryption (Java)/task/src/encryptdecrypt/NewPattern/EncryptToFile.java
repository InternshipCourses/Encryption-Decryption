package encryptdecrypt.NewPattern;

import encryptdecrypt.Cypher;

public class EncryptToFile implements IO {

    private IO fileIO;
    private Cypher cypher;

    public EncryptToFile(IO fileIO, Cypher cypher) {
        this.fileIO = fileIO;
        this.cypher = cypher;
    }

    @Override
    public String read() {
        return fileIO.read();
    }

    @Override
    public void write(String date) {
        fileIO.write(cypher.encrypt(date));
    }

}
