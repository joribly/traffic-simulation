package layout;

import static layout.RoadElement.*;

public enum RoadLayout {

    FOUR_LANE_STD(
            HEADING_FROM,
            DIVIDER_SOLID,
            LANE_STD_PLUS_RIGHT_TURN,
            DIVIDER_DOTTED,
            LANE_STD_PLUS_LEFT_TURN,
            HEADING_TO,
            DIVIDER_SOLID,
            LANE_STD_PLUS_LEFT_TURN,
            DIVIDER_DOTTED,
            LANE_STD_PLUS_RIGHT_TURN,
            DIVIDER_SOLID),

    FOUR_LANE_STD_WITH_LEFT_TURN_LANES(
            HEADING_FROM,
            DIVIDER_SOLID,
            LANE_STD,
            DIVIDER_DOTTED,
            LANE_STD,
            HEADING_TO,
            DIVIDER_SOLID,
            LANE_STD_LEFT_TURN_ONLY,
            DIVIDER_DOTTED,
            LANE_STD,
            DIVIDER_DOTTED,
            LANE_STD,
            DIVIDER_SOLID);


    private RoadElement[] layout;
    private int count;

    RoadLayout(RoadElement... aLayout) {
        layout = aLayout;
        count = 0;
        for(RoadElement item: layout) {
            if(item.isLane())count++;
        }
    }

    public int getNumberOfLanes() {
        return count;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name() + " has " + count + " lanes \n");
        int laneNumber = 0;
        Direction direction = Direction.STRAIGHT;
        for(RoadElement item: layout) {
            if(item.isHeading()) {
                direction = item.getDirection();
            }
            else if(item.isLane()) {
                laneNumber++;
                sb.append(item + " #" + laneNumber + " width = " + item.getWidth() + " ("+direction+")\n");
            }
            else {
                sb.append(item + "\n");
            }
        }
        return sb.toString();
    }
}
