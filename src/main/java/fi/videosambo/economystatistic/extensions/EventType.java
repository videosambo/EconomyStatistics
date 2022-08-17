package fi.videosambo.economystatistic.extensions;

public enum EventType {
    BUY(1),
    SELL(0);

    private final Integer type;

    EventType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static EventType getFromInt(int i) {
        if (i == 0)
            return EventType.SELL;
        if (i == 1)
            return EventType.BUY;
        return null;
    }
}
