package Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageDate implements Serializable {
    private static final long serialVersionUID = 20L;
    private Calendar calendar = Calendar.getInstance();
    private Date date = calendar.getTime();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    String formattedDate = dateFormat. format(date);

    public String getDate() {
        return formattedDate;
    }

}
