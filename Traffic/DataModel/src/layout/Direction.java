package layout;

public enum Direction {
    LEFT("L-Turn"),
    STRAIGHT("Straight"),
    RIGHT("R-Turn"),
    U("U-Turn"),
    ALL("All");

    private String debugInfo;

    Direction(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public String getDebugInfo() {
        return debugInfo;
    }
}
