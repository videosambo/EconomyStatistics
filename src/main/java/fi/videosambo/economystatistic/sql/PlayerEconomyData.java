package fi.videosambo.economystatistic.sql;

import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;

public class PlayerEconomyData {

    private Timestamp timestamp;
    private OfflinePlayer player;
    private double balance;

    public PlayerEconomyData(Timestamp timestamp, OfflinePlayer player, double balance) {
        this.timestamp = timestamp;
        this.player = player;
        this.balance = balance;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public double getBalance() {
        return balance;
    }
}
