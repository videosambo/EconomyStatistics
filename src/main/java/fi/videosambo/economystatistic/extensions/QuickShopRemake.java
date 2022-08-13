package fi.videosambo.economystatistic.extensions;

import fi.videosambo.economystatistic.Main;
import fi.videosambo.economystatistic.ServerEconomyWatcher;
import fi.videosambo.economystatistic.sql.ItemEconomyData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.api.event.ShopSuccessPurchaseEvent;

import java.sql.Timestamp;

public class QuickShopRemake implements Listener {

    private ServerEconomyWatcher economyWatcher;

    public QuickShopRemake(ServerEconomyWatcher economyWatcher) {
        this.economyWatcher = economyWatcher;
    }

    @EventHandler
    public void onPurhcase(ShopSuccessPurchaseEvent e) {
        double price = e.getBalanceWithoutTax();
        OfflinePlayer client = Bukkit.getOfflinePlayer(e.getPurchaser());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Main.getTasklist().addItemEconomyToTasklist(new ItemEconomyData(now, e.getShop().getItem().getType(), price, EventType.BUY, client, "QuickShopRemake"));
        if (economyWatcher != null)
            economyWatcher.updateEconomy();
    }
}
