package Server;

import Model.Message;
import Model.User;
import Model.UserRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final int requestPort = 8888;
    public static final java.lang.String serverIP = "localhost";
    private static ServerSocket requestServerSocket;

    public static void main(java.lang.String[] args) {
        Server.start();
    }

    public static void start() {
        try {
            requestServerSocket = new ServerSocket(requestPort);
            Thread serverThread = new Thread(new Server(), "Server Thread");
            serverThread.start();
        } catch (IOException e) {
            // ignore it
        }
    }

    @Override
    public void run() {
        while (!requestServerSocket.isClosed()) {
            try {
                new Thread(new ServerRunner(requestServerSocket.accept()), "Server Runner").start();
            } catch (IOException e) {
                // ignore it
            }
        }
    }
}

class ServerRunner implements Runnable{
    private Socket serverSocket;
    private ServerHandler serverHandler;

    public ServerRunner(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Object clientReq = null;
        try {
            serverHandler = new ServerHandler(serverSocket,
                    new ObjectInputStream(serverSocket.getInputStream()),
                    new ObjectOutputStream(serverSocket.getOutputStream()));

            while (true) {
                clientReq = serverHandler.getInputStream().readObject();
                if (clientReq instanceof String && ((String) clientReq).contains("get image")){
                    serverHandler.handlePicture(((String) clientReq).substring(10));
                }
                else if (clientReq instanceof String && ((String) clientReq).contains(":")){
                    String[] temp = ((String) clientReq).split(":");
                    serverHandler.handleSignIn(temp[0],temp[1]);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.siginUp){
                    serverHandler.handleSignUp((User) clientReq);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.Refresh){
                    serverHandler.refreshUser((User) clientReq);
                }

                else if (clientReq instanceof Message){
                    serverHandler.sendMessage((Message) clientReq);
                }

            }
        } catch (IOException e) {
            /* Ignore it */
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
        //    userDisconnect();
        }
    }
}
