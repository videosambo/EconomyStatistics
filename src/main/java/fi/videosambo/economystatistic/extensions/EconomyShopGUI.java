package fi.videosambo.economystatistic.extensions;

import fi.videosambo.economystatistic.Main;
import fi.videosambo.economystatistic.ServerEconomyWatcher;
import fi.videosambo.economystatistic.sql.ItemEconomyData;
import me.gypopo.economyshopgui.api.events.PostTransactionEvent;
import me.gypopo.economyshopgui.util.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class EconomyShopGUI implements Listener {

    private ServerEconomyWatcher economyWatcher;

    public EconomyShopGUI(ServerEconomyWatcher economyWatcher) {
        this.economyWatcher = economyWatcher;
    }

    @EventHandler
    public void playerPostTransaction(PostTransactionEvent e) {
        ItemStack item = e.getItemStack();
        Player client = e.getPlayer();
        double price = e.getPrice();
        ItemEconomyData data = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (e.getTransactionType().equals(Transaction.Mode.BUY))
            data = new ItemEconomyData(now, item.getType(), price, EventType.BUY, client, "EconomyShopGUI");
        if (e.getTransactionType().equals(Transaction.Mode.SELL))
            data = new ItemEconomyData(now, item.getType(), price, EventType.SELL, client, "EconomyShopGUI");
        if (data != null)
            Main.getTasklist().addItemEconomyToTasklist(data);
        if (economyWatcher != null)
            economyWatcher.updateEconomy();
    }

}
