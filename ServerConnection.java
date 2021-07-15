import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.exit;


public class ServerConnection implements Runnable{
    public Socket server;
     BufferedReader in;
    private PrintWriter out;

    public ServerConnection(Socket s) throws IOException {
        this.server = s;
         in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        //create avenue to send message to the client
         out = new PrintWriter(s.getOutputStream(), true);
    }


    @Override
    public void run() {
        while (true)
        {
            String val = "";

            //output the message gotten from the server
            try {
                System.out.println( in.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        if((in.readLine()).equalsIgnoreCase("terminate")){
            server.close();
            in.close();
            break;
        }
        System.out.println("Connection closed");
        exit(0);
         */
    }
}
