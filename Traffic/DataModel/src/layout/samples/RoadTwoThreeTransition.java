package layout.samples;

import layout.Road;
import layout.RoadLayout;
import layout.RoadPoint;

public class RoadTwoThreeTransition {


    public static void main(String [] args) {

        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        Road road = new Road("four->six lane").
                addNewSegment("four-lane", RoadLayout.FOUR_LANE_STD).
                addNewSegment("six-lane", RoadLayout.SIX_LANE_STD);

        road.getRoadSegment("four-lane").addRoadPoints(
                rp1,
                new RoadPoint(rp1).dY(20.0),
                new RoadPoint(rp1).dY(40.0));

        road.getRoadSegment("six-lane").addRoadPoints(
                new RoadPoint(rp1).dY(60.0),
                new RoadPoint(rp1).dY(80.0),
                new RoadPoint(rp1).dY(100.0));

        road.testTransitions();
    }
}
