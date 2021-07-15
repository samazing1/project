import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Client Process 1.


public class client {
    public static void main(String[] args) throws IOException {

        //define variables
        String localhost = args[0];
        int portNumber = 42210;
        Socket clientSocket;


        clientSocket = new Socket(localhost, portNumber);
        ServerConnection ServerConn = new ServerConnection(clientSocket);
        Scanner type = new Scanner(System.in);

        //start the thread listening for messages
       new Thread(ServerConn).start();
        while (true) {

            try {
                    //ask the user for their command
                    System.out.println("What is your command ?");

                    //save the command
                    String msg = type.nextLine();

                    //create IO stream
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    //send message to the server
                    if(msg.equalsIgnoreCase("terminate")){
                        clientSocket.close();
                    }
                    out.println(msg);
                    out.println(args [1]);


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

