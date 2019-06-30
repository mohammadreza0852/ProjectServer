package Server;

import Model.*;

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
                if (clientReq instanceof String && ((String) clientReq).contains("block")){
                    String[] temp = ((String) clientReq).split(" ");
                    if (temp[1].equals("block")) {
                        serverHandler.handleBlock(temp[0],temp[2]);
                    }
                    else  if (temp[1].equals("unblock")) {
                        serverHandler.handleUnblock(temp[0],temp[2]);
                    }
                }
                else if (clientReq instanceof String && ((String) clientReq).contains("get image")){
                    serverHandler.handlePicture(((String) clientReq).substring(10));
                }
                else if (clientReq instanceof String && ((String) clientReq).contains(":")){
                    String[] temp = ((String) clientReq).split(":");
                    System.out.println(temp[0] + " sign in");
                    System.out.println(new MessageDate().getDate());
                    serverHandler.handleSignIn(temp[0],temp[1]);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.siginUp){
                    System.out.println(((User) clientReq).getUsername() + " register " + ((User) clientReq).getUserImage());
                    System.out.println(new MessageDate().getDate());
                    serverHandler.handleSignUp((User) clientReq);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.Connect){
                    System.out.println(((User) clientReq).getUsername() + " connect");
                    System.out.println(new MessageDate().getDate());
                    serverHandler.handleSignUp((User) clientReq);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.Delete){
                    serverHandler.deleteMessage((User) clientReq);
                }
                else if (clientReq instanceof User && ((User) clientReq).getUserRequest() == UserRequest.Refresh){
                    serverHandler.refresh((User) clientReq);
                }
                else if (clientReq instanceof Message && ((Message) clientReq).getRequestServerType() == RequestServerType.Send){
                    serverHandler.sendMessage((Message) clientReq);
                }
                else if (clientReq instanceof Message && ((Message) clientReq).getRequestServerType() == RequestServerType.Read){
                    System.out.println(((Message) clientReq).getSenderEmail() + " read");
                    System.out.println("message: " + ((Message) clientReq).getSubject()
                            + " " + ((Message) clientReq).getSenderEmail() + " as " + "read");
                    System.out.println(new MessageDate().getDate());
                }
                else if (clientReq instanceof Message && ((Message) clientReq).getRequestServerType() == RequestServerType.Unread){
                    System.out.println(((Message) clientReq).getSenderEmail() + " unread");
                    System.out.println("message: " + ((Message) clientReq).getSubject()
                            + " " + ((Message) clientReq).getSenderEmail() + " as " + "unread");
                    System.out.println(new MessageDate().getDate());
                }
                else if (clientReq instanceof Message && ((Message) clientReq).getRequestServerType() == RequestServerType.Mark){
                    System.out.println(((Message) clientReq).getSenderEmail() + " important");
                    System.out.println("message: " + ((Message) clientReq).getSubject()
                            + " " + ((Message) clientReq).getSenderEmail() + " as " + "important");
                    System.out.println(new MessageDate().getDate());
                }
                else if (clientReq instanceof Message && ((Message) clientReq).getRequestServerType() == RequestServerType.Unmark){
                    System.out.println(((Message) clientReq).getSenderEmail() + " unimportant");
                    System.out.println("message: " + ((Message) clientReq).getSubject()
                            + " " + ((Message) clientReq).getSenderEmail() + " as " + "unimportant");
                    System.out.println(new MessageDate().getDate());
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
