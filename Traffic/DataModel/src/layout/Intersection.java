package layout;

import java.util.ArrayList;
import java.util.List;

public class Intersection {

    private List<RoadSegmentEnd> roadSegmentEndList;

    public Intersection() {
        roadSegmentEndList = new ArrayList<RoadSegmentEnd>();
    }

    public void addRoadSegment(RoadSegment roadSegment, End end) {
        roadSegmentEndList.add(new RoadSegmentEnd(roadSegment, end));
        roadSegment.setEndIntersection(end, this);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" connects to  ");
        for(RoadSegmentEnd roadSegmentEnd : roadSegmentEndList) {
            sb.append(roadSegmentEnd + ", ");
        }
        sb.append("\n");
        return sb.toString();
    }

    static class RoadSegmentEnd {
        RoadSegment roadSegment;
        End end;

        public RoadSegmentEnd(RoadSegment roadSegment, End end) {
            this.roadSegment = roadSegment;
            this.end = end;
        }

        public String toString() {
            return roadSegment.getId() + "-" + end;
        }
    }
}
