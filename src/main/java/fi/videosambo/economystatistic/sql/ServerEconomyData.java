package fi.videosambo.economystatistic.sql;

import java.sql.Timestamp;

public class ServerEconomyData {

    private Timestamp timestamp;
    private double balance;

    public ServerEconomyData(Timestamp timestamp, double balance) {
        this.timestamp = timestamp;
        this.balance = balance;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getBalance() {
        return balance;
    }
}
