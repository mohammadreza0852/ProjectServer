package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class UserChat implements Serializable {
    private static final long serialVersionUID = 88L;
    private String userEmail;
    private String contactEmail;
    private RequestServerType requestServerType;

    public RequestServerType getRequestServerType() {
        return requestServerType;
    }

    public void setRequestServerType(RequestServerType requestServerType) {
        this.requestServerType = requestServerType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChat userChat = (UserChat) o;
        return Objects.equals(userEmail, userChat.userEmail) &&
                Objects.equals(contactEmail, userChat.contactEmail) &&
                Objects.equals(chatMessages, userChat.chatMessages) &&
                Objects.equals(someText, userChat.someText) &&
                Objects.equals(subject, userChat.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, contactEmail, chatMessages, someText, subject);
    }

    private ArrayList<Message> chatMessages;
    private String someText;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public ArrayList<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getSomeText() {
        return someText;
    }

    public void setSomeText(String someText) {
        this.someText = someText;
    }
}
