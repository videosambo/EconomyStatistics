package fi.videosambo.economystatistic;

import fi.videosambo.economystatistic.sql.DataObject;
import fi.videosambo.economystatistic.sql.ServerEconomyData;

import java.util.ArrayList;

public class EconomyDataObject {

    private final String optionJSON;
    private final ArrayList<DataObject> objects;

    public EconomyDataObject(String optionJSON) {
        this.optionJSON = optionJSON;
        this.objects = new ArrayList<>();
    }

    public void addDataObject(DataObject object) {
        objects.add(object);
    }

    public EconomyDataObject(String optionJSON, ArrayList objects) {
        this.optionJSON = optionJSON;
        this.objects = objects;
    }

    public String getOptionJSON() {
        return optionJSON;
    }

    public ArrayList<DataObject> getObjects() {
        return objects;
    }
}
