package Model;

import java.io.Serializable;

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

        return 2019 - year;
    }

}
