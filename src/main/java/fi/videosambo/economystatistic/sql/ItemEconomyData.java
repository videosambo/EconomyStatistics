package fi.videosambo.economystatistic.sql;

import fi.videosambo.economystatistic.extensions.EventType;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;

public class ItemEconomyData extends DataObject {

    private final Material material;
    private final EventType type;
    private final OfflinePlayer player;
    private final String plugin;

    public ItemEconomyData(Timestamp timestamp, Material material, double price, EventType type, OfflinePlayer player, String plugin) {
        super(timestamp, price);
        this.material = material;
        this.type = type;
        this.player = player;
        this.plugin = plugin;
    }

    public Material getMaterial() {
        return material;
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
