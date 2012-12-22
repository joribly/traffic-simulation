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
        attachIntersection(A);
        attachIntersection(B);
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

    private void attachIntersection(int end) {
        Intersection intersection = new Intersection();
        intersection.addRoadSegmentEnd(new RoadSegmentEnd(this,end));
        intersections[end] = intersection;
    }

    public void connect(RoadSegment roadSegment, Attachment attachment) {
        RoadSegmentEnd roadSegmentEnd = new RoadSegmentEnd(roadSegment, attachment.getTo());
        Intersection intersection = intersections[attachment.getFrom()];
        intersection.addRoadSegmentEnd(roadSegmentEnd);
        roadSegment.updateIntersection(intersection, attachment.getTo());
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
