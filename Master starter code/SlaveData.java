import java.io.IOException;
import java.net.Socket;

public class SlaveData extends ThreadData{
    final boolean typeA;

    public SlaveData(int id, Socket s, boolean typeA) throws IOException {
        super(id, s);
        this.typeA = typeA;
    }
}
