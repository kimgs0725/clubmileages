package triple.assignment.clubmileage.model.points;

public enum PointHistoryType {
    REDEEM("REDEEM"), EARN("EARN"), EXPIRE("EXPIRE");

    private String value;

    private PointHistoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
