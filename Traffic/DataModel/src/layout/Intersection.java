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
        RoadSegmentConnection connection = roadSegmentConnectionMap.get(roadSegment1);
        if(connection != null) {
            connection.setMate(roadSegment2);
        }
        /*
         * if roadSegment2 has no mate yet, mate it to roadSegment1
         */
        connection = roadSegmentConnectionMap.get(roadSegment2);
        if(connection != null) {
            if(!connection.hasMate()) {
                connection.setMate(roadSegment1);
            }
        }
    }

    public RoadSegment getMateRoadSegment(RoadSegment roadSegment) {
        return roadSegmentConnectionMap.get(roadSegment).getMate();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" connects to  ");
        for(RoadSegmentConnection roadSegmentConnection : roadSegmentConnectionMap.values()) {
            sb.append(roadSegmentConnection + ", ");
        }
        sb.append("\n");
        return sb.toString();
    }

    static class RoadSegmentConnection {
        RoadSegment roadSegment;
        End end;
        private RoadSegment mateRoadSegment;

        public RoadSegmentConnection(RoadSegment roadSegment, End end) {
            this.roadSegment = roadSegment;
            this.end = end;
        }

        public String toString() {
            return roadSegment.getId() + "-" + end;
        }

        public boolean hasMate() {
            return mateRoadSegment != null;
        }

        public RoadSegment getMate() {
            return mateRoadSegment;
        }

       public void setMate(RoadSegment mateRoadSegment) {
           this.mateRoadSegment = mateRoadSegment;
        }
    }
}
