package model;

/**
 * Created by Florea on 12/8/2015.
 */
public class GraphValue {
    private String month;
    private String year;
    private float amount;

    public GraphValue(String month, String year, float amount) {
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
