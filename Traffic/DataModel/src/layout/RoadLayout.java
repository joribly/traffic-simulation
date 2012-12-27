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

    private final LinkedList<Lane> toLanes;
    private final LinkedList<Lane> fromLanes;
    //TODO handle TO_FROM case

    RoadLayout(Lane ... lanes) {
        toLanes = new LinkedList<Lane>();
        fromLanes = new LinkedList<Lane>();
        for(Lane lane: lanes) {
            if(lane.isFrom())fromLanes.add(lane);
            if(lane.isTo())toLanes.add(lane);
        }
    }

    public Lane getIndexedLane(Travel travel) {
        LinkedList<Lane> lanes = getLaneList(travel);
        for(Lane lane: lanes) {
            if(lane.isIndexed())return lane;
        }
        return null;
    }

    public Lane getClampedLane(int index) {
        index = Math.min(Math.max(0,index), getNumberOfLanes()-1);
        if(index < fromLanes.size()) {
            return fromLanes.get(index);
        }
        return toLanes.get(index - fromLanes.size());
    }

    private Lane getClampedTravelLane(int index, Travel travel) {
        LinkedList<Lane> lanes = getLaneList(travel);
        index = Math.min(Math.max(0,index), lanes.size()-1);
        return lanes.get(index);

    }

    public int getNumberOfLanes() {
        return fromLanes.size() + toLanes.size();
    }

    public List<Lane> getUTurnDestinationLanes(Lane lane) {
        if(lane == toLanes.getFirst()) return fromLanes;
        if(lane == fromLanes.getLast()) return toLanes;
        return null;
    }

    public LinkedList<Lane> getLaneList(Travel travel) {
        if(travel == Travel.FROM)return fromLanes;
        return toLanes;
    }

    public int getOffsetFromIndex(Lane aLane) {
        Travel travel = aLane.getTravel();
        LinkedList<Lane> lanes = getLaneList(travel);
        return lanes.indexOf(aLane) - lanes.indexOf(getIndexedLane(travel));
    }

    public static Lane getMateLane(RoadLayout roadLayout, RoadLayout mateRoadLayout, Lane lane) {
        Lane mateIndexedLane = mateRoadLayout.getIndexedLane(lane.getTravel());
        if(lane.isIndexed()) {
            return mateIndexedLane;
        }
        else {
            Travel travel = lane.getTravel();
            int offset = roadLayout.getOffsetFromIndex(lane);
            int index = mateRoadLayout.getLaneList(travel).indexOf(mateIndexedLane);
            return mateRoadLayout.getClampedTravelLane((index + offset), travel);
        }
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
