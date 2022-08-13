package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.events.BalanceChangeEvent;
import fi.videosambo.economystatistic.sql.PlayerEconomyData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Timestamp;

public class PlayerEconomyWatcher implements Listener {

    ServerEconomyWatcher economyWatcher;

    public PlayerEconomyWatcher(ServerEconomyWatcher economyWatcher) {
        this.economyWatcher = economyWatcher;
    }

    @EventHandler
    public void onBalanceChange(BalanceChangeEvent e) {
        long now = System.currentTimeMillis();
        Main.getTasklist().addPlayerEconomyToTasklist(new PlayerEconomyData(new Timestamp(now), e.getAccountOwner(), e.getBalanceAfterTransaction()));
        economyWatcher.updateEconomy();
    }
}
