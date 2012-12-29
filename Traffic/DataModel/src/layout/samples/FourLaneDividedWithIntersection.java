package layout.samples;

import layout.*;

import java.util.List;

public class FourLaneDividedWithIntersection {


    public static void main(String [] args) {

        /*
         *             2
         *             b
         *             a
         *       3 a-b   a-b 4
         *             b
         *             a
         *             1
         */
        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        RoadSegment roadSegment1 = new RoadSegment(
                RoadLayout.FOUR_LANE_TWO_LEFT_TURN_LANES,
                rp1,
                new RoadPoint(rp1).dY(20.0),
                new RoadPoint(rp1).dY(40.0));

        RoadSegment roadSegment2 = new RoadSegment(
                RoadLayout.FOUR_LANE_STD,
                new RoadPoint(rp1).dY(60.0),
                new RoadPoint(rp1).dY(80.0),
                new RoadPoint(rp1).dY(100.0));

        RoadPoint rp2 = new RoadPoint(rp1).dX(-40.0).dY(40.0).dA(90.0);

        RoadSegment roadSegment3 = new RoadSegment(
                RoadLayout.FOUR_LANE_STD,
                rp2,
                new RoadPoint(rp2).dX(20.0),
                new RoadPoint(rp2).dX(40.0));


        RoadSegment roadSegment4 = new RoadSegment(
                RoadLayout.FOUR_LANE_STD,
                new RoadPoint(rp2).dX(60.0),
                new RoadPoint(rp2).dX(80.0),
                new RoadPoint(rp2).dX(100.0));

        Transition transition = new Transition();
        // in order, e.g. clockwise
        transition.addRoadSegment(roadSegment1, End.B);
        transition.addRoadSegment(roadSegment3, End.B);
        transition.addRoadSegment(roadSegment2, End.A);
        transition.addRoadSegment(roadSegment4, End.A);

        transition.mate(roadSegment1, roadSegment2);
        transition.mate(roadSegment3, roadSegment4);

        transition.testLaneConnections();

    }
}
