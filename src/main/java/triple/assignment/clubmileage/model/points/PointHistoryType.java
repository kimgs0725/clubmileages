package triple.assignment.clubmileage.model.points;

public enum PointHistoryType {
    DEDUCTION("DEDUCTION"), EARN("EARN");

    private String value;

    private PointHistoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
