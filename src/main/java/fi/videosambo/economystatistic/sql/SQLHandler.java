package fi.videosambo.economystatistic.sql;

import fi.videosambo.economystatistic.extensions.EventType;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.sql.*;
import java.util.ArrayList;

public class SQLHandler {

    private SQLConnection sql;
    private Connection connection;

    public SQLHandler() {
        sql = new SQLConnection();
        connection = sql.getConnection();
        createTables();
    }

    public void insertServerEconomyRecord(Timestamp timestamp, Double balance) {
        String query = "INSERT INTO `ServerEconomy` (insert_time, balance) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, timestamp);
            statement.setDouble(2, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertItemEconomyRecord(Timestamp timestamp, Material itemType, Double price, OfflinePlayer player, EventType type, String plugin) {
        String query = "INSERT INTO ItemEconomy (insert_time, item, price, player, type, plugin) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, timestamp);
            statement.setString(2, itemType.toString());
            statement.setDouble(3, price);
            statement.setString(4, player.getUniqueId().toString());
            statement.setInt(5, type.getType());
            statement.setString(6, plugin);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPlayerEconomyRecord(Timestamp timestamp, OfflinePlayer player, Double balance) {
        String query = "INSERT INTO PlayerEconomy (insert_time, uuid, balance) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, timestamp);
            statement.setString(2, player.getUniqueId().toString());
            statement.setDouble(3, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void createTables() {
        ArrayList<String> quaries = new ArrayList<>();
        quaries.add("CREATE TABLE IF NOT EXISTS `PlayerEconomy`(\n" +
                "    `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "    `insert_time` TIMESTAMP NOT NULL,\n" +
                "    `uuid` TEXT(32) NOT NULL,\n" +
                "    `balance` DECIMAL NOT NULL,\n" +
                "    PRIMARY KEY(id)); ");
        quaries.add("CREATE TABLE IF NOT EXISTS `ServerEconomy`(\n" +
                "    `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "    `insert_time` TIMESTAMP NOT NULL,\n" +
                "    `balance` DECIMAL NOT NULL,\n" +
                "    PRIMARY KEY(id)); ");
        quaries.add("CREATE TABLE IF NOT EXISTS `ItemEconomy`(\n" +
                "    `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "    `insert_time` TIMESTAMP NOT NULL,\n" +
                "    `item` TEXT NOT NULL,\n" +
                "    `price` DECIMAL NOT NULL,\n" +
                "    `player` TEXT(32),\n" +
                "    `type` INT NOT NULL DEFAULT 0,\n" +
                "    `plugin` TEXT,\n" +
                "    PRIMARY KEY(id)\n" +
                ");");
        try {
            for (String s : quaries) {
                Statement statement = connection.createStatement();
                statement.execute(s);
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
