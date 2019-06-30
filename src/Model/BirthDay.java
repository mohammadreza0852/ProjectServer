package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class BirthDay implements Serializable {
    private static final long serialVersionUID = 55L;
    private int day;
    private int month;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int calAge(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return ( calendar.get(Calendar.YEAR) - year ) * 360 + ( calendar.get(Calendar.MONTH) - month ) * 30;
    }

}
