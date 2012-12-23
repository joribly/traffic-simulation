package layout;

import java.util.*;

public class Intersection {

    private Map<RoadSegment, RoadSegmentConnection> roadSegmentConnectionMap;

    public Intersection() {
        roadSegmentConnectionMap = new LinkedHashMap<RoadSegment, RoadSegmentConnection>();
    }

    public void addRoadSegment(RoadSegment roadSegment, End end) {
        roadSegmentConnectionMap.put(roadSegment, new RoadSegmentConnection(roadSegment, end));
        roadSegment.setEndIntersection(end, this);
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

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" connects to  ");
        for(RoadSegmentConnection roadSegmentConnection : roadSegmentConnectionMap.values()) {
            sb.append("\n     " + roadSegmentConnection);
        }
        sb.append("\n");
        return sb.toString();
    }

    static class RoadSegmentConnection {
        RoadSegment roadSegment;
        End end;
        private RoadSegmentConnection mateRoadSegmentConnection;

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
    }
}
