package layout;

public class Lane {
    private boolean indexed;
    private boolean canGoLeft;
    private boolean canGoRight;
    private boolean canGoStraight;
    private boolean canChangeLaneLeft;
    private boolean canChangeLaneRight;
    private Travel travel;
    private double width;

    private Lane() {
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

    public Lane travel(Travel travel) {
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
    public Lane setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getWidth() {
        return width;
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
        StringBuffer sb = new StringBuffer();
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
}
