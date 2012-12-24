package layout;

import java.util.*;

public class Intersection {

    private Map<RoadSegment, RoadSegmentConnection> roadSegmentConnectionMap;
    private RoadSegmentConnection previousRoadSegmentConnection;
    private RoadSegmentConnection firstRoadSegmentConnection;

    public Intersection() {
        roadSegmentConnectionMap = new LinkedHashMap<RoadSegment, RoadSegmentConnection>();
        firstRoadSegmentConnection = previousRoadSegmentConnection = null;
    }

    public void addRoadSegment(RoadSegment roadSegment, End end) {
        roadSegment.setEndIntersection(end, this);
        RoadSegmentConnection roadSegmentConnection = new RoadSegmentConnection(roadSegment, end);
        roadSegmentConnectionMap.put(roadSegment, roadSegmentConnection);
        doubleLinkRoadSegmentConnection(roadSegmentConnection);
    }

    private void doubleLinkRoadSegmentConnection(RoadSegmentConnection roadSegmentConnection) {
        if(previousRoadSegmentConnection != null) {
            previousRoadSegmentConnection.setNext(roadSegmentConnection);
            roadSegmentConnection.setPrevious(previousRoadSegmentConnection);
            roadSegmentConnection.setNext(firstRoadSegmentConnection);
        }
        else {
            firstRoadSegmentConnection = roadSegmentConnection;
        }
        previousRoadSegmentConnection = roadSegmentConnection;
    }

    public void mate(RoadSegment roadSegment1, RoadSegment roadSegment2){
        RoadSegmentConnection connection1 = roadSegmentConnectionMap.get(roadSegment1);
        RoadSegmentConnection connection2 = roadSegmentConnectionMap.get(roadSegment2);
        if(connection1 == null || connection2 == null)return;
        connection1.setMate(connection2);
        if(!connection2.hasMate()) {
            connection2.setMate(connection1);
        }
    }

    public RoadSegment getMateRoadSegment(RoadSegment roadSegment) {
        return roadSegmentConnectionMap.get(roadSegment).getMate().getRoadSegment();
    }

    public RoadSegment getNextRoadSegment(RoadSegment roadSegment) {
        return roadSegmentConnectionMap.get(roadSegment).getNext().getRoadSegment();
    }

    public RoadSegment getPreviousRoadSegment(RoadSegment roadSegment) {
        return roadSegmentConnectionMap.get(roadSegment).getPrevious().getRoadSegment();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" connects to  ");
        for(RoadSegmentConnection roadSegmentConnection : roadSegmentConnectionMap.values()) {
            sb.append("\n     " + roadSegmentConnection);
        }
        sb.append("\n");
        return sb.toString();
    }

    public boolean roadSegmentLaneHasChoices(RoadSegment roadSegment, Lane lane) {
        /*
         * what exists in the intersection, and what can the lane do?
         */
        return false;
    }

    class RoadSegmentConnection {
        RoadSegment roadSegment;
        End end;
        private RoadSegmentConnection mateRoadSegmentConnection;
        private RoadSegmentConnection previousRoadSegmentConnection, nextRoadSegmentConnection;

        public RoadSegmentConnection(RoadSegment roadSegment, End end) {
            this.roadSegment = roadSegment;
            this.end = end;
        }

        public String toString() {
            if(!hasMate())return roadSegment.getId() + "-" + end;
            return  roadSegment.getId() + "-" + end + "[mate: " +
                    mateRoadSegmentConnection.getRoadSegment().getId() + "-" + mateRoadSegmentConnection.getEnd() + "]";
        }

        public End getEnd() {
            return end;
        }

        public boolean hasMate() {
            return mateRoadSegmentConnection != null;
        }

        public RoadSegmentConnection getMate() {
            return mateRoadSegmentConnection;
        }

        public RoadSegment getRoadSegment() {
            return roadSegment;
        }

       public void setMate(RoadSegmentConnection mateRoadSegmentConnection) {
           this.mateRoadSegmentConnection = mateRoadSegmentConnection;
        }

        public void setNext(RoadSegmentConnection roadSegmentConnection) {
            nextRoadSegmentConnection = roadSegmentConnection;
        }

        public void setPrevious(RoadSegmentConnection roadSegmentConnection) {
            previousRoadSegmentConnection = roadSegmentConnection;
        }

        public RoadSegmentConnection getNext() {
            return nextRoadSegmentConnection;
        }

        public RoadSegmentConnection getPrevious() {
            return previousRoadSegmentConnection;
        }
    }

}
