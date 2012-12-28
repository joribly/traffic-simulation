package layout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum RoadLayout {

    /*
     * always ordered from, then to  - also note, RoadLayouts/Lanes are reused. not specific instances for a RoadSegment
     */
    FOUR_LANE_STD(
            Lane.createStandardFrom().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    ), SIX_LANE_STD(
            Lane.createStandardFrom().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanChangeLaneRight().setCanChangeLaneLeft(),
            Lane.createStandardTo().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
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

    public List<Lane> getUTurnDestinationLanes(Lane lane) {
        if(lane == toLanes.getFirst()) return fromLanes;
        if(lane == fromLanes.getFirst()) return toLanes;
        return null;
    }

    public LinkedList<Lane> getLaneList(Travel travel) {
        if(travel == Travel.FROM)return fromLanes;
        return toLanes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name()).append(" has ").append(getNumberOfLanes()).append(" lanes \n");
        int laneNumber = 0;
        for(Lane lane: fromLanes) {
            laneNumber++;
            sb.append(lane).append("\n");
        }
        for(Lane lane: toLanes) {
            laneNumber++;
            sb.append(lane).append("\n");
        }
        return sb.toString();
    }

}
