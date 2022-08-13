package fi.videosambo.economystatistic.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class BalanceChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private OfflinePlayer accountOwner;
    private double balanceAfterTransaction;
    private double balanceChange;

    public BalanceChangeEvent(OfflinePlayer accountOwner, double balanceAfterTransaction, double balanceChange) {
        this.accountOwner = accountOwner;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.balanceChange = balanceChange;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public OfflinePlayer getAccountOwner() {
        return accountOwner;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public double getBalanceChange() {
        return balanceChange;
    }
}
