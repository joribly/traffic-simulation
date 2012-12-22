package layout;

import static  layout.Direction.*;

public enum RoadItemType {

    LANE_STD(20.0, true, false, false, STRAIGHT),
    LANE_STD_PLUS_LEFT_RIGHT_TURN(20.0, true, false, false, STRAIGHT_AND_LEFT_RIGHT_TURN),
    LANE_STD_PLUS_LEFT_TURN(20.0, true, false, false, STRAIGHT_AND_LEFT_TURN),
    LANE_STD_PLUS_RIGHT_TURN(20.0, true, false, false, STRAIGHT_AND_RIGHT_TURN),
    LANE_STD_LEFT_TURN_ONLY(20.0, true, false, false, LEFT_TURN_ONLY),
    DIVIDER_SOLID(0.0, false, true, false, NA),
    DIVIDER_DOTTED(0.0, false, true, false, NA),
    DIVIDER_DOTTED_SOLID(0.0, false, true, false, NA),
    DIVIDER_SOLID_DOTTED(0.0, false, true, false, NA),
    MEDIAN_STD(10.0, false, false, true, NA),
    HEADING_TO(0.0, false, false, false, TO),
    HEADING_FROM(0.0, false, false, false, FROM),
    HEADING_TO_FROM(0.0, false, false, false, TO_FROM);

    private double width;
    private boolean isLane;
    private boolean isMedian;
    private boolean isDivider;
    private Direction direction;

    RoadItemType(double width, boolean isLane, boolean isDivider, boolean isMedian, Direction direction) {
        this.width = width;
        this.isLane = isLane;
        this.isDivider = isDivider;
        this.isMedian = isMedian;
        this.direction = direction;
    }

    public boolean isLane() {
        return isLane;
    }

    public boolean isMedian() {
        return isMedian;
    }

    public boolean isDivider() {
        return isDivider;
    }

    public boolean isHeading() {
        return direction == TO || direction == FROM || direction == TO_FROM;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getWidth() {
        return width;
    }

    public String toString() {
        if(direction == NA) return name();
        return name() + " (" + direction + ")";
    }
}
