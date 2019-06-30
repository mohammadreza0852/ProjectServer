package Model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Message implements Serializable {
    private static final long serialVersionUID = 7;
    private String senderEmail;
    private String getterEmail;
    private String messageDate = new MessageDate().getDate();
    private String subject;
    private String text;
    private ArrayList<String> fileNames;
    private ArrayList<byte[]> files;
    private MessageType messageType;
    private boolean isImportant;
    private boolean hasBeenRead;
    private RequestServerType requestServerType;

    public RequestServerType getRequestServerType() {
        return requestServerType;
    }

    public void setRequestServerType(RequestServerType requestServerType) {
        this.requestServerType = requestServerType;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    public ArrayList<byte[]> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<byte[]> files) {
        this.files = files;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(senderEmail, message.senderEmail) &&
                Objects.equals(getterEmail, message.getterEmail) &&
                Objects.equals(messageDate, message.messageDate) &&
                Objects.equals(subject, message.subject) &&
                Objects.equals(text, message.text) &&
                Objects.equals(attachedFile, message.attachedFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderEmail, getterEmail, messageDate, subject, text, attachedFile);
    }

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
