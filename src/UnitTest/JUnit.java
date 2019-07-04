package UnitTest;

import Model.*;
import Server.Server;
import Server.ServerHandler;
import com.sun.tools.javac.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JUnit {

    private static Socket client;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private User user = new User();

    @BeforeClass
    public static void setConnection(){
        Server.start();
        try {
            client = new Socket("localhost", 8888);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Before
    public void createUser(){
        user.setUsername("Ali");
        user.setPassword("Ab123456");
        user.setUserRequest(UserRequest.siginUp);
        user.setOutputStream(out);
        user.setInputStream(in);
        user.setBlockedList(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setInboxMessages(new ArrayList<>());
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user);
            user.getOutputStream().flush();
            in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @After
    public void deleteUser(){
        ServerHandler.users.clear();
    }

    @Test
    public void signUpTest(){
        user.setUserRequest(UserRequest.siginUp);
        user.setUsername("taghi");
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user);
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String temp = (String) user.getInputStream().readObject();
            assertEquals("accepted",temp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        user.setUserRequest(UserRequest.siginUp);
        user.setUsername("Ali");
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user);
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String temp = (String) user.getInputStream().readObject();
            assertEquals("rejected",temp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void signInTest(){
        user.setUsername("Ali");
        user.setPassword("Ab123456");
        user.setInputStream(in);
        user.setOutputStream(out);
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user.getUsername() + ":" +user.getPassword());
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User temp = null;
        try {
            temp = (User) user.getInputStream().readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(temp);
        assertEquals(user.getUsername(),temp.getUsername());
    }

    @Test
    public void handleBlockTest(){
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user.getGmailAdress() + " block " + "hosein");
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            refreshUser();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(user.getBlockedList().size(),1);
    }

    @Test
    public void handleUnblockTest(){
         user.getBlockedList().add("hosein");
        assertEquals(user.getBlockedList().size(),1);
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user.getGmailAdress() + " unblock " + "hosein");
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            refreshUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(user.getBlockedList().size(),0);
    }

    @Test
    public void refreshTest(){
        user.setUserRequest(UserRequest.Refresh);
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user);
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User temp = null;
        try {
            temp = (User) user.getInputStream().readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(temp);
        assertEquals(temp.getUsername(),user.getUsername());
    }

    @Test
    public void deleteTest(){
        user.setUserRequest(UserRequest.Delete);
        user.setInboxMessages(new ArrayList<>());
        user.getInboxMessages().add(new UserChat());
        assertEquals(user.getInboxMessages().size(),1);
        user.getInboxMessages().clear();
        try {
            user.getOutputStream().reset();
            user.getOutputStream().writeObject(user);
            user.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            refreshUser();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(user);
        assertEquals(user.getInboxMessages().size(),0);
    }

    @Test
    public void sendMessageTest() throws IOException, ClassNotFoundException {
        Socket client2 = new Socket("localhost",8888);
        User newUser = new User();
        newUser.setUsername("Taghi");
        newUser.setInboxMessages(new ArrayList<>());
        newUser.setUserRequest(UserRequest.siginUp);
        newUser.setOutputStream(new ObjectOutputStream(client2.getOutputStream()));
        newUser.setInputStream(new ObjectInputStream(client2.getInputStream()));
        newUser.setSentMessages(new ArrayList<>());
        newUser.setBlockedList(new ArrayList<>());
        newUser.getOutputStream().writeObject(newUser);
        String outString = (String) newUser.getInputStream().readObject();
        Message message = new Message();
        message.setText("salaaaaam");
        message.setRequestServerType(RequestServerType.Send);
        message.setGetterEmail(newUser.getGmailAdress());
        message.setSubject("test1");
        message.setSenderEmail(user.getGmailAdress());
        user.getOutputStream().reset();
        user.getOutputStream().writeObject(message);
        user.getOutputStream().flush();
        User temp2;
        temp2 = (User) user.getInputStream().readObject();
        temp2.setOutputStream(user.getOutputStream());
        temp2.setInputStream(user.getInputStream());
        user = temp2;

        User temp = null;
        user.setUserRequest(UserRequest.Refresh);
        newUser.getOutputStream().reset();
        newUser.getOutputStream().writeObject(user);
        newUser.getOutputStream().flush();
        temp = (User) newUser.getInputStream().readObject();
        temp.setInputStream(in);
        temp.setOutputStream(out);
        newUser = temp;
        assertEquals(user.getSentMessages().size(),1);
        assertEquals(user.getInboxMessages().size(),1);
        assertEquals(newUser.getInboxMessages().size(),1);
    }



    public void refreshUser() throws IOException, ClassNotFoundException {
        User temp = null;
        user.setUserRequest(UserRequest.Refresh);
        user.getOutputStream().reset();
        user.getOutputStream().writeObject(user);
        user.getOutputStream().flush();
        temp = (User) user.getInputStream().readObject();
        temp.setInputStream(in);
        temp.setOutputStream(out);
        user = temp;
    }


}
