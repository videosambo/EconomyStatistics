package fi.videosambo.economystatistic.sql;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;

public class SQLTasklist extends BukkitRunnable {

    private ArrayList<ItemEconomyData> item;
    private ArrayList<PlayerEconomyData> player;
    private ArrayList<ServerEconomyData> server;

    SQLHandler handler;

    public SQLTasklist(SQLHandler handler) {
        this.handler = handler;
        item = new ArrayList<>();
        player = new ArrayList<>();
        server = new ArrayList<>();
    }

    @Override
    public void run() {
        Iterator<ItemEconomyData> itemIterator = item.iterator();
        Iterator<PlayerEconomyData> playerIterator = player.iterator();
        Iterator<ServerEconomyData> serverIterator = server.iterator();

        while (itemIterator.hasNext()) {
            ItemEconomyData itemEconomyData = itemIterator.next();
            handler.insertItemEconomyRecord(itemEconomyData.getTimestamp(), itemEconomyData.getMaterial(), itemEconomyData.getPrice(), itemEconomyData.getPlayer(), itemEconomyData.getType(), itemEconomyData.getPlugin());
            itemIterator.remove();
        }
        while (playerIterator.hasNext()) {
            PlayerEconomyData playerEconomyData = playerIterator.next();
            handler.insertPlayerEconomyRecord(playerEconomyData.getTimestamp(), playerEconomyData.getPlayer(), playerEconomyData.getBalance());
            playerIterator.remove();
        }
        while (serverIterator.hasNext()) {
            ServerEconomyData serverEconomyData = serverIterator.next();
            handler.insertServerEconomyRecord(serverEconomyData.getTimestamp(), serverEconomyData.getBalance());
            serverIterator.remove();
        }
    }

    public void addItemEconomyToTasklist(ItemEconomyData data) {
        boolean add = true;
        for (ItemEconomyData itemEconomyData : item) {
            if (itemEconomyData.getMaterial().equals(data.getMaterial()) &&
                    itemEconomyData.getPlayer().equals(data.getPlayer()) &&
                    itemEconomyData.getPrice() == data.getPrice() &&
                    itemEconomyData.getPlugin().equals(data.getPlugin()) &&
                    itemEconomyData.getTimestamp().equals(data.getTimestamp()))
                add = false;
        }
        if (add)
            item.add(data);
    }

    public void addPlayerEconomyToTasklist(PlayerEconomyData data) {
        boolean add = true;
        for (PlayerEconomyData playerEconomyData : player) {
            if (playerEconomyData.getPlayer().equals(data.getPlayer()) &&
            playerEconomyData.getBalance() == data.getBalance() &&
            playerEconomyData.getTimestamp().equals(data.getTimestamp()))
                add = false;
        }
        if (add)
            player.add(data);
    }

    public void addServerEconomyToTasklist(ServerEconomyData data) {
        boolean add = true;
        for (ServerEconomyData serverEconomyData : server) {
            if (serverEconomyData.getBalance() == data.getBalance() &&
            serverEconomyData.getTimestamp().equals(data.getTimestamp()))
                add = false;
        }
        if (add)
            server.add(data);
    }
}
