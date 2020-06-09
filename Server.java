//Server Program for Concurrent Socket Server
//Miringen Zefi / David Munera
//CNT4504

import java.io.*;
import java.net.*;

public class Server {
 
    public static void main(String[] args) {
       if (args.length < 1) return;
 
       int port = Integer.parseInt(args[0]);
       
       try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
 
                new ServerThread(socket).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
             String text;
             String s;
       
                  //while (true) {
                      System.out.println("New Request Recieved!");
       
                      InputStream in = socket.getInputStream();
                      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
       
                      OutputStream out = socket.getOutputStream();
                      PrintWriter writer = new PrintWriter(out, true);
                      
                      text = reader.readLine();
                            try {
                     	         // run the Unix command
                                 // using the Runtime exec method:
                                 Process unix = Runtime.getRuntime().exec(text);
                                 BufferedReader stdInput = new BufferedReader(new InputStreamReader(unix.getInputStream()));
                                 s = stdInput.readLine();
                                 System.out.println("Here is the output of the command requested!");
                                 while (s != null) 
                                 {
                                     System.out.println(s);
                                     writer.println(s);
                                     s = stdInput.readLine();
                                 }
                             }
                             catch (IOException e) {
                                 System.out.println("Try Catch Exception: ");
                                 e.printStackTrace();
                                 System.exit(-1);
                             }
                  socket.close();
                  //}      
                  } catch (IOException ex) {
                  System.out.println("Server exception: " + ex.getMessage());
                  ex.printStackTrace();
              }
    }
}