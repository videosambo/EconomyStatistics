package fi.videosambo.economystatistic.sql;

import java.sql.Timestamp;

public class ServerEconomyData extends DataObject {
    public ServerEconomyData(Timestamp timestamp, double balance) {
        super(timestamp, balance);
    }
}
