package fi.videosambo.economystatistic.sql;

import fi.videosambo.economystatistic.extensions.EventType;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;

public class ItemEconomyData {

    private Timestamp timestamp;
    private Material material;
    private double price;
    private EventType type;
    private OfflinePlayer player;
    private String plugin;

    public ItemEconomyData(Timestamp timestamp, Material material, double price, EventType type, OfflinePlayer player, String plugin) {
        this.timestamp = timestamp;
        this.material = material;
        this.price = price;
        this.type = type;
        this.player = player;
        this.plugin = plugin;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }

    public EventType getType() {
        return type;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getPlugin() {
        return plugin;
    }
}
