package layout;

import java.util.LinkedList;
import java.util.List;

public enum RoadLayout {

    /*
     * always ordered from, then to  - also note, RoadLayouts/Lanes are reused. not specific instances for a RoadSegment
     */
    FOUR_LANE_STD(
            Lane.createStandardFrom().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight().index(),
            Lane.createStandardTo().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight().index(),
            Lane.createStandardTo().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    );

    private LinkedList<Lane> toLanes;
    private LinkedList<Lane> fromLanes;

    RoadLayout(Lane ... lanes) {
        toLanes = new LinkedList<Lane>();
        fromLanes = new LinkedList<Lane>();
        for(Lane lane: lanes) {
            if(lane.isFrom())fromLanes.add(lane);
            if(lane.isTo())toLanes.add(lane);
        }
    }

    public Lane getLane(int index) {
        if(index < 0 || index >= getNumberOfLanes()) return null;
        if(index < fromLanes.size()) {
            return fromLanes.get(index);
        }
        return toLanes.get(index - fromLanes.size());
    }

    public int getNumberOfLanes() {
        return fromLanes.size() + toLanes.size();
    }

    public List<Lane> getUTurnDestinationLanes(Lane lane) {
        if(lane == toLanes.getFirst()) return fromLanes;
        if(lane == fromLanes.getLast()) return toLanes;
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name() + " has " + getNumberOfLanes() + " lanes \n");
        int laneNumber = 0;
        for(Lane lane: fromLanes) {
            laneNumber++;
            sb.append(lane + "\n");
        }
        for(Lane lane: toLanes) {
            laneNumber++;
            sb.append(lane + "\n");
        }
        return sb.toString();
    }
}
