package layout;

import static  layout.Direction.*;

public enum RoadElement {

    LANE_STD(20.0, Type.LANE, STRAIGHT),
    LANE_STD_PLUS_LEFT_RIGHT_TURN(20.0, Type.LANE, STRAIGHT_AND_LEFT_RIGHT_TURN),
    LANE_STD_PLUS_LEFT_TURN(20.0, Type.LANE, STRAIGHT_AND_LEFT_TURN),
    LANE_STD_PLUS_RIGHT_TURN(20.0, Type.LANE, STRAIGHT_AND_RIGHT_TURN),
    LANE_STD_LEFT_TURN_ONLY(20.0, Type.LANE, LEFT_TURN_ONLY),
    DIVIDER_SOLID(0.0, Type.DIVIDER, NA),
    DIVIDER_DOTTED(0.0, Type.DIVIDER, NA),
    DIVIDER_DOTTED_SOLID(0.0, Type.DIVIDER, NA),
    DIVIDER_SOLID_DOTTED(0.0, Type.DIVIDER, NA),
    MEDIAN_STD(10.0, Type.MEDIAN, NA),
    HEADING_TO(0.0, Type.HEADING, TO),
    HEADING_FROM(0.0, Type.HEADING, FROM),
    HEADING_TO_FROM(0.0, Type.HEADING, TO_FROM);

    private enum Type {
        HEADING,
        LANE,
        DIVIDER,
        MEDIAN
    }

    private double width;
    private Direction direction;
    private Type type;

    RoadElement(double width, Type type, Direction direction) {
        this.width = width;
        this.type = type;
        this.direction = direction;
    }

    public boolean isLane() {
        return type == Type.LANE;
    }

    public boolean isMedian() {
        return type == Type.MEDIAN;
    }

    public boolean isDivider() {
        return type == Type.DIVIDER;
    }

    public boolean isHeading() {
        return type == Type.HEADING;
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
