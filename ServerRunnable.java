import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerRunnable implements Runnable{

    //define variables and constructors
    protected Socket clientSocket = null;

    public ServerRunnable(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public Socket getSocket() {
        return this.clientSocket;
    }

    int stopCounter[] = new int[] {0,0,0,0};

    @Override
    public void run() {

        try {
            while (true){


            //get message from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //create avenue to send message to the client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            //save message gotten from client in the variable
            String val = in.readLine();
            int numb = Integer.parseInt(in.readLine());

            //create a boolean variable
            Boolean flag = true;
            String line[] = val.split(" ");
            if (line[0].equalsIgnoreCase("send")) {
                if (line[2].equalsIgnoreCase("stop")) {

                    //get the process id
                    int temp = Integer.parseInt(line[1]);

                    //increment the number of times the unique process was told to stop
                    stopCounter[temp]++;

                    //send terminate to a process when all other processes have told it to stop
                    if (stopCounter[temp] == 2 ) {
                        sendTo(SocketServer.processes, temp, "Terminate", numb);
                        flag = false;
                    }
                }
                //if not told to stop, send the message to the other process(s)
                if (flag) {

                    //get the unique process id
                    int pid = Integer.parseInt(line[1]);

                    //if 0 send to every process
                    if (pid == 0)
                    {
                        sendToAll(SocketServer.processes,line[2], numb);
                    }
                    else {
                        sendTo(SocketServer.processes, pid, line[2], numb);
                    }
                }

            }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }




    public void sendTo(ArrayList<ServerRunnable> temp, int id, String msg, int pid) throws IOException {

        Socket s = temp.get(id-1).getSocket();
        //create avenue to send message to the client
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        //display message gotten from client
        System.out.println("Passed through server");

        //send message to the client
        out.println("From process " + pid +": "+ msg);
    }

    public void sendToAll(ArrayList<ServerRunnable> s, String msg, int pid) throws IOException {
        int size = SocketServer.processes.size();
        for(int i =1; i <= size; i++)
        {
            if(i == pid)
            {

            }
            else
                sendTo(s, i,msg, pid);

        }
    }
}

