package fi.videosambo.economystatistic.sql;

import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;

public class PlayerEconomyData extends DataObject{

    private final OfflinePlayer player;
    private double balance;

    public PlayerEconomyData(Timestamp timestamp, OfflinePlayer player, double balance) {
        super(timestamp, balance);
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
