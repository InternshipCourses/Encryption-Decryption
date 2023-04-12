package encryptdecrypt.NewPattern;

public class DoubleIO  implements IO {

    private final IO in;
    private final IO out;

    public DoubleIO(IO in, IO out) {
        this.in = in;
        this.out = out;
    }


    @Override
    public String read() {
        return in.read();
    }

    @Override
    public void write(String date) {
        out.write(date);
    }
}
