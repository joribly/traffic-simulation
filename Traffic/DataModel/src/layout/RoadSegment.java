package layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoadSegment {

    private static int _id = 0;

    private final List<RoadPoint> pointList;
    private final RoadLayout layout;
    private final HashMap<End, Transition> transitionMap;
    private final int id;

    public RoadSegment(RoadLayout aLayout) {
        pointList = new ArrayList<RoadPoint>();
        transitionMap = new HashMap<End, Transition>();
        layout = aLayout;
        id = ++_id;
    }

    public RoadSegment(RoadLayout aLayout, RoadPoint ... points) {
        this(aLayout);
        addRoadPoints(points);
    }

    public int getId() {
        return id;
    }

    public void addRoadPoints(RoadPoint ... points) {
        Collections.addAll(pointList, points);
    }

    RoadPoint getEndPoint(End end) {
        if(end == End.A) return pointList.get(0);
        return pointList.get(pointList.size()-1);
    }

    public void setEndTransition(End end, Transition transition) {
        transitionMap.put(end, transition);
    }

    public List<Lane> getLaneList(Travel travel) {
        return layout.getLaneList(travel);
    }

    public List<Lane> getUTurnLaneList(Travel travel) {
        if(travel == Travel.FROM) {
            return getLaneList(Travel.TO);
        }
        else {
            return getLaneList(Travel.FROM);
        }
    }

    public End end(RoadPoint roadPoint) {
        if(getEndPoint(End.A) == roadPoint)return End.A;
        if(getEndPoint(End.B) == roadPoint)return End.B;
        return End.NA;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("road segment ").append(id).append("\n");
        int count = 0;
        for(RoadPoint point: pointList) {
            count++;
            sb.append("point #").append(count).append(" ").append(point).append("\n");
        }
        sb.append(layout);
        if(transitionMap.get(End.A) != null) sb.append(" end " + End.A + " ").append(transitionMap.get(End.A));
        if(transitionMap.get(End.B) != null) sb.append(" end " + End.B + " ").append(transitionMap.get(End.B));
        return sb.toString();
    }

    public void plot(Lane lane, End end) {
        RoadPoint point = getEndPoint(end);
        double offset = layout.getLaneCenterPointOffset(lane);
        point.plotLocation(offset);

    }
}
