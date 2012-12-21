package layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blystone
 * Date: 12/9/12
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Intersection {

    private List<RoadSegmentEnd> roadSegmentEndList;

    public Intersection() {
        roadSegmentEndList = new ArrayList<RoadSegmentEnd>();
    }

    public void addRoadSegmentEnd(RoadSegmentEnd roadSegmentEnd) {
        roadSegmentEndList.add(roadSegmentEnd);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" connecting ");
        for(RoadSegmentEnd roadSegmentEnd : roadSegmentEndList) {
            sb.append(roadSegmentEnd + " ");
        }
        sb.append("\n");
        return sb.toString();
    }

}
