package Server;

import Model.Message;
import Model.User;
import Model.UserChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private static ArrayList<User> users = new ArrayList<>();

    ServerHandler(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void handleSignUp(User client){
        users.add(client);
    }

    public void handleSignIn(String username,String password){
        boolean foundUser = false;
        for (int i = 0; i < users.size(); i++) {
            User temp = users.get(i);
            if (temp.getUsername().equals(username) && temp.getPassword().equals(password)){
                try {
                    foundUser = true;
                    outputStream.writeObject(users.get(i));
                    outputStream.flush();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!foundUser){
            try {
                outputStream.writeObject(null);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message clientReq) {
        boolean recieverAvailable = false;
        User sender = null;
        User reciever = null;
        for (int i = 0; i < users.size(); i++) {
            if (clientReq.getSenderEmail().equals(users.get(i).getGmailAdress())){
                sender = users.get(i);
            }
            if (clientReq.getGetterEmail().equals(users.get(i).getGmailAdress())){
                reciever = users.get(i);
                recieverAvailable = true;
            }
        }
        if (recieverAvailable){
            boolean chatExists = false;
            for (int i = 0; i < sender.getInboxMessages().size(); i++) {
                if (sender.getInboxMessages().get(i).getContactEmail().equals(clientReq.getGetterEmail())
                        && clientReq.getSubject().equals(sender.getInboxMessages().get(i).getSubject())){
                    chatExists = true;
                    sender.getInboxMessages().get(i).setSomeText(clientReq.getText().substring(0,5));
                    System.out.println(sender.getInboxMessages().get(i).getSomeText());
                    sender.getInboxMessages().get(i).getChatMessages().add(clientReq);
                }
            }
            if (chatExists) {
                for (int i = 0; i < reciever.getInboxMessages().size(); i++) {
                    if (reciever.getInboxMessages().get(i).getContactEmail().equals(clientReq.getSenderEmail())
                            && clientReq.getSubject().equals(reciever.getInboxMessages().get(i).getSubject())){
                        reciever.getInboxMessages().get(i).setSomeText(clientReq.getText().substring(0,5));
                        reciever.getInboxMessages().get(i).getChatMessages().add(clientReq);
                    }
                }
            }
            if (!chatExists){
                UserChat userChat = new UserChat();
                userChat.setChatMessages(new ArrayList<>());
                userChat.setContactEmail(clientReq.getGetterEmail());
                userChat.setSomeText(clientReq.getText().substring(0,5));
                userChat.setUserEmail(sender.getGmailAdress());
                userChat.setSubject(clientReq.getSubject());
                userChat.getChatMessages().add(clientReq);
                sender.getInboxMessages().add(userChat);
                UserChat recieverChat = new UserChat();
                recieverChat.setChatMessages(new ArrayList<>());
                recieverChat.setSomeText(clientReq.getText().substring(0,5));
                recieverChat.setUserEmail(reciever.getGmailAdress());
                recieverChat.setContactEmail(sender.getGmailAdress());
                recieverChat.getChatMessages().add(clientReq);
                recieverChat.setSubject(clientReq.getSubject());
                reciever.getInboxMessages().add(recieverChat);
            }
        }
        else {
           Message error = new Message();
           error.setGetterEmail(sender.getGmailAdress());
           error.setSenderEmail("mailerdaemon@gmail.com");
           error.setSubject("error");
           error.setText("reciever with " + clientReq.getGetterEmail() + " email adress is not available");
           boolean chatExists = false;
            for (int i = 0; i < sender.getInboxMessages().size(); i++) {
                if (sender.getInboxMessages().get(i).getContactEmail().equals(error.getSenderEmail())
                        && sender.getInboxMessages().get(i).getSubject().equals(error.getSubject())){
                    chatExists = true;
                    sender.getInboxMessages().get(i).getChatMessages().add(error);
                }
            }

            if (!chatExists){
                UserChat userChat = new UserChat();
                userChat.setChatMessages(new ArrayList<>());
                userChat.setContactEmail(error.getSenderEmail());
                userChat.setSomeText(error.getText().substring(0,5));
                userChat.setUserEmail(sender.getGmailAdress());
                userChat.setSubject(error.getSubject());
                userChat.getChatMessages().add(clientReq);
                userChat.getChatMessages().add(error);
                sender.getInboxMessages().add(userChat);
            }
        }
        boolean sentMessageExist = false;
        for (int i = 0; i < sender.getSentMessages().size(); i++) {
            if (sender.getSentMessages().get(i).getContactEmail().equals(clientReq.getGetterEmail())){
                sender.getSentMessages().get(i).getChatMessages().add(clientReq);
                sender.getSentMessages().get(i).setSomeText(clientReq.getText().substring(0,5));
                sentMessageExist = true;
            }
        }
        if (!sentMessageExist){
            UserChat userChat = new UserChat();
            userChat.setChatMessages(new ArrayList<>());
            userChat.setContactEmail(clientReq.getGetterEmail());
            userChat.setSomeText(clientReq.getText().substring(0,5));
            userChat.setUserEmail(sender.getGmailAdress());
            userChat.setSubject(clientReq.getSubject());
            userChat.getChatMessages().add(clientReq);
            sender.getSentMessages().add(userChat);
        }
        try {
            outputStream.reset();
            outputStream.writeObject(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handlePicture(String substring) {
        boolean picFound = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getGmailAdress().equals(substring)){
                try {
                    picFound = true;
                    outputStream.reset();
                    outputStream.writeObject(users.get(i));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!picFound){
            try {
                outputStream.reset();
                outputStream.writeObject(null);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshUser(User clientReq) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(clientReq.getUsername())){
                users.set(i,clientReq);
            }
        }
    }
}