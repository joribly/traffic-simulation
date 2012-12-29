package layout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum RoadLayout {

    /*
     * always ordered from, then to  - also note, RoadLayouts/Lanes are reused. not specific instances for a RoadSegment
     */
    FOUR_LANE_STD(
            Lane.createStandardFrom(2).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom(1).setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(1).setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(2).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    ),FOUR_LANE_TWO_LEFT_TURN_LANES(
            Lane.createStandardFrom(2).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom(1).setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(1).setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(2).setCanGoLeft().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardTo(3).setCanGoStraight().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardTo(4).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    ), SIX_LANE_STD(
            Lane.createStandardFrom(3).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom(2).setCanGoStraight().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom(1).setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(1).setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo(2).setCanGoStraight().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardTo(3).setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    );

    private final LinkedList<Lane> toLanes;
    private final LinkedList<Lane> fromLanes;
    //TODO handle TO_FROM case

    RoadLayout(Lane ... lanes) {
        /*
         * lane lists uniformly go from innermost lane to outermost lane,
         * even though constructor is by convention from - outer to inner, to - inner to outer.
         */
        toLanes = new LinkedList<Lane>();
        fromLanes = new LinkedList<Lane>();
        for(Lane lane: lanes) {
            if(lane.isFrom())fromLanes.push(lane);
            if(lane.isTo())toLanes.add(lane);
        }
    }

    public int getNumberOfLanes() {
        return fromLanes.size() + toLanes.size();
    }

    public LinkedList<Lane> getLaneList(Travel travel) {
        if(travel == Travel.FROM)return fromLanes;
        return toLanes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name()).append(" has ").append(getNumberOfLanes()).append(" lanes \n");
        for(Lane lane: fromLanes) {
            sb.append(lane).append("\n");
        }
        for(Lane lane: toLanes) {
            sb.append(lane).append("\n");
        }
        return sb.toString();
    }

}
