package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.events.BalanceChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlayerEconomyUpdate extends BukkitRunnable implements Listener {

    private HashMap<OfflinePlayer, Double> accounts;
    private HashMap<OfflinePlayer, Double> history;

    public PlayerEconomyUpdate() {
        accounts = new HashMap<>();
        history = new HashMap<>();
        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
            accounts.put(p, Main.getEconomy().getBalance(p));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!accounts.containsKey(e.getPlayer()))
            accounts.put(e.getPlayer(), Main.getEconomy().getBalance(e.getPlayer()));
    }

    @Override
    public void run() {
        if (history.size() <= 0) {
            history = (HashMap<OfflinePlayer, Double>) accounts.clone();
        }
        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
            if(Main.getEconomy().getBalance(p) != accounts.get(p)){
                accounts.remove(p);
                accounts.put(p, Main.getEconomy().getBalance(p));
            }
        }
        for (Map.Entry<OfflinePlayer, Double> set : accounts.entrySet()) {
            double currentBalance = set.getValue();
            double previousBalance = history.get(set.getKey());
            if (currentBalance != previousBalance) {
                double change = currentBalance - previousBalance;
                BalanceChangeEvent event = new BalanceChangeEvent(set.getKey(), currentBalance, change);
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
        history = (HashMap<OfflinePlayer, Double>) accounts.clone();
    }
}
