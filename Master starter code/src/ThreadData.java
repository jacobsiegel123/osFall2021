package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadData {
    final int id;
    final Socket socket;
    final ObjectInputStream in;
    final ObjectOutputStream out;


    public ThreadData(int id, Socket s) throws IOException {
        this.id = id;
        this.socket = s;
        this.in = new ObjectInputStream(s.getInputStream());
        this.out = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(this == o)
            return true;

        if(this.getClass() == o.getClass()){
            return this.id == ((ThreadData)o).id;
        }

        return false;
    }
}
