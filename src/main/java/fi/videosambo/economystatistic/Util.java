package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.sql.DataObject;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Util {


    public static String capitalizeEveryWord(String value) {
        value = value.toLowerCase();
        char[] charArray = value.toCharArray();
        boolean foundSpace = true;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        return String.valueOf(charArray);
    }

    public static String getPlayerListAsHTML() {
        OfflinePlayer[] players = Bukkit.getServer().getOfflinePlayers();
        StringBuilder html = new StringBuilder();
        for (OfflinePlayer player : players) {
            String line = "<li><a href=\"player.html?player=" + player.getUniqueId() + "\" class=\"nav-link text-white\"><img src=\"https://mc-heads.net/avatar/" + player.getUniqueId() + "\" style=\"height:20px;\" class=\"mr-2\">" + player.getName() + "</a></li>\n";
            html.append(line);
        }
        return html.toString();
    }

    public static String getItemListAsHTML() {
        ArrayList<Material> materials = Main.getSqlHandler().getMaterialList();
        StringBuilder html = new StringBuilder();
        for (Material mat : materials) {
            String line = "<li><a href=\"item.html?item=" + mat.toString().toLowerCase() + "\" class=\"nav-link text-white\"><img src=\"https://minecraftitemids.com/item/32/" + mat.toString().toLowerCase() + ".png\" style=\"height:20px;\" class=\"mr-2\">" + Util.capitalizeEveryWord(mat.toString().toLowerCase().replace("_", " ")) + "</a></li>\n";
            html.append(line);
        }
        return html.toString();
    }

    public static double getPrecentualChange(EconomyDataObject object, int day) {
        EconomyDataObject trimmed = getFromRange(object, day);
        double v1 = trimmed.getObjects().get(0).getBalance();
        double v2 = trimmed.getObjects().get(trimmed.getObjects().size() - 1).getBalance();
        return ((v2-v1)/v1)*100;
    }

    private static EconomyDataObject getFromRange(EconomyDataObject object, int days) {
        ArrayList<DataObject> economyObjects = object.getObjects();
        economyObjects.sort(Comparator.comparing(DataObject::getTimestamp));
        LocalDateTime ldt = LocalDateTime.ofInstant(new Date(System.currentTimeMillis()).toInstant(), ZoneId.systemDefault()).minusDays(days);
        economyObjects.removeIf(current -> current.getTimestamp().toLocalDateTime().isBefore(ldt));
        return new EconomyDataObject(object.getOptionJSON(), economyObjects);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
