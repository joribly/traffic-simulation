package layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoadSegment {

    private static int _id = 0;

    private List<RoadPoint> pointList;
    private RoadLayout layout;
    private HashMap<End, Transition> transitionMap;
    private int id;

    public RoadSegment(RoadLayout aLayout, RoadPoint ... points) {
        pointList = new ArrayList<RoadPoint>();
        transitionMap = new HashMap<End, Transition>();
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

    public void setEndTransition(End end, Transition transition) {
        transitionMap.put(end, transition);
    }

    public Transition getTransition(End end) {
        return transitionMap.get(end);
    }

    public RoadLayout getLayout() {
        return layout;
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
        if(transitionMap.get(End.A) != null)sb.append(" end " + End.A + " " + transitionMap.get(End.A));
        if(transitionMap.get(End.B) != null)sb.append(" end " + End.B + " " + transitionMap.get(End.B));
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
