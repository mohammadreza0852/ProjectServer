package Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageDate implements Serializable {
    private static final long serialVersionUID = 20L;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();
    public String getDate() {
        return dateFormat.format(date);
    }

}
