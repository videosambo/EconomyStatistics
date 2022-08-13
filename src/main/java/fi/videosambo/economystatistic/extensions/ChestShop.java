package fi.videosambo.economystatistic.extensions;

import com.Acrobot.ChestShop.Database.Account;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import fi.videosambo.economystatistic.Main;
import fi.videosambo.economystatistic.ServerEconomyWatcher;
import fi.videosambo.economystatistic.sql.ItemEconomyData;
import fi.videosambo.economystatistic.sql.ServerEconomyData;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ChestShop implements Listener {

    private ServerEconomyWatcher economyWatcher;

    public ChestShop(ServerEconomyWatcher economyWatcher) {
        this.economyWatcher = economyWatcher;
    }

    @EventHandler
    public void onTransaction(TransactionEvent e) {
        ItemStack[] item = e.getStock();
        OfflinePlayer client = e.getClient();
        Account ownerAccount = e.getOwnerAccount();
        BigDecimal price = e.getExactPrice();
        ItemEconomyData data = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (e.getTransactionType().equals(TransactionEvent.TransactionType.SELL)) {
            for (ItemStack i : item) {
                data = new ItemEconomyData(now, i.getType(), (price.doubleValue() / item.length), EventType.SELL, client, "ChestShop");
            }
        }
        if (e.getTransactionType().equals(TransactionEvent.TransactionType.BUY)) {
            for (ItemStack i : item) {
                data = new ItemEconomyData(now, i.getType(), (price.doubleValue() / item.length), EventType.BUY, client, "ChestShop");
            }
        }
        if (data != null)
            Main.getTasklist().addItemEconomyToTasklist(data);
        if (economyWatcher != null)
            economyWatcher.updateEconomy();
    }

}
