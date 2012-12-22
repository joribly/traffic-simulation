package layout;

import java.util.ArrayList;
import java.util.List;

public class RoadSegment {

    private static int _id = 0;

    private static int A = 0;
    private static int B = 1;

    private List<RoadPoint> pointList;
    private RoadLaneLayout layout;
    private Intersection[] intersections;
    private int id;

    public RoadSegment(RoadLaneLayout aLayout, RoadPoint ... points) {
        pointList = new ArrayList<RoadPoint>();
        intersections = new Intersection[2];
        layout = aLayout;
        addRoadPoints(points);
        attachInitialIntersectionToEnd(A);
        attachInitialIntersectionToEnd(B);
        id = ++_id;
    }

    public int getId() {
        return id;
    }

    public void updateIntersection(Intersection intersection, int end) {
        intersections[end] = intersection;
    }

    private void addRoadPoints(RoadPoint ... points) {
        for(RoadPoint point: points) {
            pointList.add(point);
        }
    }

    private void attachInitialIntersectionToEnd(int end) {
        setEndIntersection(end, new Intersection());
    }

    private void setEndIntersection(int end, Intersection intersection) {
        intersections[end] = intersection;
        intersections[end].addRoadSegmentEnd(new RoadSegmentEnd(this,end));
    }

    public Intersection getIntersection(int end) {
        return intersections[end];
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
        sb.append(intersections[A]);
        sb.append(intersections[B]);
        return sb.toString();
    }


}
