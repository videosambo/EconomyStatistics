package fi.videosambo.economystatistic.sql;

import java.sql.Timestamp;
import java.util.Comparator;

public abstract class DataObject{

    protected Timestamp timestamp;
    protected double value;

    public DataObject(Timestamp timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getBalance() {
        return value;
    }

    public double getPrice() {
        return value;
    }
}
