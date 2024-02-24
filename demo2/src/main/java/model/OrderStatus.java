package model;

public enum OrderStatus {
    READY("ready"),
    PREPARING("preparing"),
    ACCEPTED("accepted");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static OrderStatus fromString(String text) {
        for (OrderStatus os : OrderStatus.values()) {
            if (os.status.equalsIgnoreCase(text)) {
                return os;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

