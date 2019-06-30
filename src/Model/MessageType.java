package Model;

import java.io.Serializable;

public enum MessageType implements Serializable {
    Send,Reply,Forward;
    private static final long serialVersionUID = 5L;
}
