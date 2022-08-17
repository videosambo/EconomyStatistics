package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.extensions.ChestShop;
import fi.videosambo.economystatistic.extensions.EconomyShopGUI;
import fi.videosambo.economystatistic.extensions.QuickShopRemake;
import fi.videosambo.economystatistic.sql.SQLHandler;
import fi.videosambo.economystatistic.sql.SQLTasklist;
import fi.videosambo.economystatistic.webserver.HttpServer;
import fi.videosambo.economystatistic.webserver.request.HttpRequestBuilder;
import fi.videosambo.economystatistic.webserver.request.HttpRequestParser;
import fi.videosambo.economystatistic.webserver.request.HttpRequestRouter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class Main extends JavaPlugin {

    private static Economy economy;
    private static SQLHandler sqlHandler;
    private static SQLTasklist tasklist;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        sqlHandler = new SQLHandler();
        tasklist = new SQLTasklist(sqlHandler);
        //Test sql
        ServerEconomyWatcher economyWatcher = new ServerEconomyWatcher();
        PlayerEconomyUpdate playerUpdate = new PlayerEconomyUpdate();
        getServer().getPluginManager().registerEvents(playerUpdate, this);
        playerUpdate.runTaskTimer(this, 0, 20);
        getServer().getPluginManager().registerEvents(new PlayerEconomyWatcher(economyWatcher), this);
        if (getServer().getPluginManager().getPlugin("QuickShop-Reremake") != null)
            getServer().getPluginManager().registerEvents(new QuickShopRemake(economyWatcher), this);
        if (getServer().getPluginManager().getPlugin("EconomyShopGUI") != null)
            getServer().getPluginManager().registerEvents(new EconomyShopGUI(economyWatcher), this);
        if (getServer().getPluginManager().getPlugin("ChestShop") != null)
            getServer().getPluginManager().registerEvents(new ChestShop(economyWatcher), this);
        economyWatcher.runTaskTimer(this, 0, 1200);
        tasklist.runTaskTimer(this, 0, 10);
        //web
        try {
            HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
            ServerSocket serverSocket = new ServerSocket(8080);
            HttpRequestParser requestParser = new HttpRequestParser(requestBuilder);
            HttpRequestRouter requestRouter = new HttpRequestRouter("C:\\xampp\\htdocs\\economystatistics", "index.html");
            ExecutorService executor = Executors.newFixedThreadPool(7);
            HttpServer server = new HttpServer(serverSocket, requestParser, requestRouter, executor);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static SQLHandler getSqlHandler() {
        return sqlHandler;
    }

    public static SQLTasklist getTasklist() {
        return tasklist;
    }
}
