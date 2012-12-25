package layout;

public class Lane {

    private static int _id = 0;

    private boolean indexed;
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

    public Lane index() {
        indexed = true;
        return this;
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

    private enum Travel {
        TO,
        FROM,
        TO_FROM
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(id).append(" ");
        sb.append(travel);
        if(canGoLeft)sb.append(" : Left");
        if(canGoRight)sb.append(" : Right");
        if(canGoStraight)sb.append(" : Straight");
        if(canChangeLaneLeft && canChangeLaneRight)sb.append(" : left-right lane change allowed") ;
        else if(canChangeLaneLeft)sb.append(" : left lane change allowed") ;
        else if(canChangeLaneRight)sb.append(" : right lane change allowed");
        if(indexed)sb.append(" (indexed)");
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
