package layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoadSegment {

    private static int _id = 0;

    private List<RoadPoint> pointList;
    private RoadLayout layout;
    private HashMap<End, Intersection>intersectionMap;
    private int id;

    public RoadSegment(RoadLayout aLayout, RoadPoint ... points) {
        pointList = new ArrayList<RoadPoint>();
        intersectionMap = new HashMap<End, Intersection>();
        layout = aLayout;
        addRoadPoints(points);
        id = ++_id;
    }

    public int getId() {
        return id;
    }

    private void addRoadPoints(RoadPoint ... points) {
        for(RoadPoint point: points) {
            pointList.add(point);
        }
    }

    public RoadPoint getEndPoint(End end) {
        if(end == End.A) return pointList.get(0);
        return pointList.get(pointList.size()-1);
    }

    public void setEndIntersection(End end, Intersection intersection) {
        intersectionMap.put(end, intersection);
    }

    public Intersection getIntersection(End end) {
        return intersectionMap.get(end);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("road segment " + id + "\n");
        int count = 0;
        for(RoadPoint point: pointList) {
            count++;
            sb.append("point #" + count + " " + point + "\n");
        }
        sb.append(layout);
        if(intersectionMap.get(End.A) != null)sb.append(" end " + End.A + " " + intersectionMap.get(End.A));
        if(intersectionMap.get(End.B) != null)sb.append(" end " + End.B + " " + intersectionMap.get(End.B));
        return sb.toString();
    }

    public RoadPoint getRandomRoadPoint() {
        return pointList.get((int) (Math.random() * pointList.size()));
    }

    public Lane getRandomLane() {
        return layout.getLane((int) (Math.random() * layout.getNumberOfLanes()));
    }

    public End end(RoadPoint roadPoint) {
        if(getEndPoint(End.A) == roadPoint)return End.A;
        if(getEndPoint(End.B) == roadPoint)return End.B;
        return End.NA;
    }
}
