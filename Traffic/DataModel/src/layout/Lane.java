package layout;

public class Lane {

    private static int _id = 0;

    private boolean canGoLeft;
    private boolean canGoRight;
    private boolean canGoStraight;
    private boolean canChangeLaneLeft;
    private boolean canChangeLaneRight;
    private Travel travel;
    private double width;
    private final int id;

    private Lane() {
        id = ++_id;
    }

    public int getId() {
        return id;
    }

    public static Lane createStandardTo() {
        return (new Lane()).setWidth(20.0).travel(Travel.TO);
    }

    public static Lane createStandardFrom() {
        return (new Lane()).setWidth(20.0).travel(Travel.FROM);
    }

    Lane travel(Travel travel) {
        this.travel = travel;
        return this;
    }

    public Lane setCanGoLeft() {
        canGoLeft = true;
        return this;
    }
    public Lane setCanGoRight() {
        canGoRight = true;
        return this;
    }
    public Lane setCanGoStraight() {
        canGoStraight = true;
        return this;
    }
    public Lane setCanChangeLaneRight() {
        canChangeLaneRight = true;
        return this;
    }
    public Lane setCanChangeLaneLeft() {
        canChangeLaneLeft = true;
        return this;
    }
    Lane setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public boolean isTo() {
        return travel == Travel.TO;
    }

    public boolean isFrom() {
        return travel == Travel.FROM;
    }

    public boolean canGoLeft() {
        return canGoLeft;
    }

    public boolean canGoRight() {
        return canGoRight;
    }

    public boolean canGoStraight() {
        return canGoStraight;
    }

    public boolean canChangeLaneLeft() {
        return canChangeLaneLeft;
    }

    public boolean canChangeLaneRight() {
        return canChangeLaneRight;
    }

    public Travel getTravel() {
        return travel;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        String t = (travel.name() + "  ").substring(0,4);
        sb.append(id).append(" [").append(t).append("] ");
        if(canGoLeft)sb.append(":TL");
        if(canGoRight)sb.append(":TR");
        if(canGoStraight)sb.append(":SS");
        if(canChangeLaneLeft)sb.append(":SL") ;
        if(canChangeLaneRight)sb.append(":SR");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lane lane = (Lane) o;

        return id == lane.id;

    }

    @Override
    public int hashCode() {
        return id;
    }


}
