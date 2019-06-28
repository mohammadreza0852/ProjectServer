package Model;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 7;
    private String senderEmail;
    private String getterEmail;
    private String messageDate = new MessageDate().getDate();
    private String subject;
    private String text;
    private File attachedFile;

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getGetterEmail() {
        return getterEmail;
    }

    public void setGetterEmail(String getterEmail) {
        this.getterEmail = getterEmail;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public File getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(File attachedFile) {
        this.attachedFile = attachedFile;
    }
}
