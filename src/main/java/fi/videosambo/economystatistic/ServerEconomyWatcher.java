package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.sql.ServerEconomyData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;

public class ServerEconomyWatcher extends BukkitRunnable {
    @Override
    public void run() {
        updateEconomy();
    }

    public void updateEconomy() {
        double amount = 0;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            amount += Main.getEconomy().getBalance(player);
        }
        System.out.println("Updated servereconomy of " + Bukkit.getOfflinePlayers().length);
        long now = System.currentTimeMillis();
        Main.getTasklist().addServerEconomyToTasklist(new ServerEconomyData(new Timestamp(now), amount));
    }
}
