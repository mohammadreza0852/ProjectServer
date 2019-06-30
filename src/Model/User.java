package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 10;

    private transient ObjectInputStream inputStream;
    private transient ObjectOutputStream outputStream;

    private String firstName;
    private String lastName;
    private BirthDay birthDay;
    private String gmailAdress;
    private String username;
    private String password;
    private int age;
    private String phoneNumber;
    private RequestServerType requestServerType;
    private ArrayList<String> blockedList;

    public ArrayList<String> getBlockedList() {
        return blockedList;
    }

    public void setBlockedList(ArrayList<String> blockedList) {
        this.blockedList = blockedList;
    }

    public RequestServerType getRequestServerType() {
        return requestServerType;
    }

    public void setRequestServerType(RequestServerType requestServerType) {
        this.requestServerType = requestServerType;
    }

    public ArrayList<Message> getOutbox() {
        return Outbox;
    }

    public void setOutbox(ArrayList<Message> outbox) {
        Outbox = outbox;
    }

    private ArrayList<Message> Outbox;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(inputStream, user.inputStream) &&
                Objects.equals(outputStream, user.outputStream) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDay, user.birthDay) &&
                Objects.equals(gmailAdress, user.gmailAdress) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                gender == user.gender &&
                Objects.equals(userImageURL, user.userImageURL) &&
                Objects.equals(sentMessages, user.sentMessages) &&
                Objects.equals(inboxMessages, user.inboxMessages) &&
                Arrays.equals(bufferImage, user.bufferImage) &&
                userRequest == user.userRequest;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(inputStream, outputStream, firstName, lastName, birthDay, gmailAdress, username, password, age, phoneNumber, gender, userImageURL, sentMessages, inboxMessages, userRequest);
        result = 31 * result + Arrays.hashCode(bufferImage);
        return result;
    }

    private Gender gender;
    private String userImageURL;
    private ArrayList<UserChat> sentMessages;
    private ArrayList<UserChat> inboxMessages;
    private byte[] bufferImage = new byte[2048];
    private UserRequest userRequest;

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public ArrayList<UserChat> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<UserChat> getInboxMessages() {
        return inboxMessages;
    }

    public byte[] getBufferImage() {
        return bufferImage;
    }

    public void setBufferImage(byte[] bufferImage) {
        this.bufferImage = bufferImage;
    }

    public void setInboxMessages(ArrayList<UserChat> inboxMessages) {
        this.inboxMessages = inboxMessages;
    }

    public void setSentMessages(ArrayList<UserChat> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public String getUserImage() {
        return userImageURL;
    }

    public void setUserImage(String userImage) {
        this.userImageURL = userImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BirthDay getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(BirthDay birthDay) {
        this.birthDay = birthDay;
    }

    public String getGmailAdress() {
        gmailAdress = username + "@gmail.com";
        return gmailAdress;
    }

    public void setGmailAdress(String gmailAdress) {
        this.gmailAdress = gmailAdress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return birthDay.calAge();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }


}
