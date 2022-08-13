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
}
