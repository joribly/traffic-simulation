package layout.samples;

import layout.*;

public class TwoThreeTransition {


    public static void main(String [] args) {

        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        RoadSegment roadSegment1 = new RoadSegment(
                RoadLayout.FOUR_LANE_STD,
                rp1,
                new RoadPoint(rp1).dY(20.0),
                new RoadPoint(rp1).dY(40.0));

        RoadSegment roadSegment2 = new RoadSegment(
                RoadLayout.SIX_LANE_STD,
                new RoadPoint(rp1).dY(60.0),
                new RoadPoint(rp1).dY(80.0),
                new RoadPoint(rp1).dY(100.0));

        Transition transition = new Transition();
        // in order, e.g. clockwise
        transition.addRoadSegment(roadSegment1, End.B);
        transition.addRoadSegment(roadSegment2, End.A);

        transition.mate(roadSegment1, roadSegment2);

        transition.testLaneConnections();

    }
}
