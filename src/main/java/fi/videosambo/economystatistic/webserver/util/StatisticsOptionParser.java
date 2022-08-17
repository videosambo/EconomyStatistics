package fi.videosambo.economystatistic.webserver.util;

import fi.videosambo.economystatistic.EconomyDataObject;
import fi.videosambo.economystatistic.Main;
import fi.videosambo.economystatistic.sql.DataObject;
import fi.videosambo.economystatistic.sql.ItemEconomyData;
import fi.videosambo.economystatistic.sql.ServerEconomyData;
import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StatisticsOptionParser {

    public EconomyDataObject getItemData(Material material, int scale) {
        ArrayList<ItemEconomyData> economyData = Main.getSqlHandler().selectItemEconomyRecord(material, scale);
        System.out.println(economyData.size());
        JSONObject options = new JSONObject();
        JSONObject chart = new JSONObject();
        JSONArray series = new JSONArray();
        JSONObject graph = new JSONObject();
        JSONArray graphData = new JSONArray();
        JSONObject xaxis = new JSONObject();

        chart.put("type", "line");
        chart.put("height", "50%");
        chart.put("width", "100%");

        graph.put("name", "Value"); //TODO lang
        for (ItemEconomyData e : economyData) {
            JSONArray o = new JSONArray();
            o.add(e.getTimestamp().getTime());
            o.add(e.getPrice());
            graphData.add(o);
        }
        graph.put("data", graphData);
        series.add(graph);
        xaxis.put("type", "datetime");
        options.put("chart", chart);
        options.put("series", series);
        options.put("xaxis", xaxis);
        return new EconomyDataObject(options.toString(),economyData);
    }

    public EconomyDataObject getServerEconomyData() {
        ArrayList<ServerEconomyData> economyData = Main.getSqlHandler().selectServerEconomyRecord(7);
        JSONObject options = new JSONObject();
        JSONObject chart = new JSONObject();
        JSONArray series = new JSONArray();
        JSONObject graph = new JSONObject();
        JSONArray graphData = new JSONArray();
        JSONObject xaxis = new JSONObject();

        chart.put("type", "line");
        chart.put("height", "50%");
        chart.put("width", "100%");

        graph.put("name", "Balance"); //TODO lang
        for (ServerEconomyData e : economyData) {
            JSONArray o = new JSONArray();
            o.add(e.getTimestamp().getTime());
            o.add(e.getBalance());
            graphData.add(o);
        }
        graph.put("data", graphData);
        series.add(graph);
        xaxis.put("type", "datetime");
        options.put("chart", chart);
        options.put("series", series);
        options.put("xaxis", xaxis);
        return new EconomyDataObject(options.toString(),economyData);
    }
}
