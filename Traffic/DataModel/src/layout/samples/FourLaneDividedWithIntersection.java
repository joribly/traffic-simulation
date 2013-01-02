package layout.samples;

import layout.*;

import java.util.List;

public class FourLaneDividedWithIntersection {


    public static void main(String [] args) {

        /*
         *                 b
         *                 a
         *                 8
         *
         *                 b
         *                 a
         *                 7
         *
         *   1 a-b  2 a-b     3 a-b  4 a-b
         *
         *                 b
         *                 a
         *                 6
         *
         *                 b
         *                 a
         *                 5
         */

        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        Transition intersection = new Transition("Broad and Lee");

        Road broadStreet = new Road("Broad Street")
                .addNewSegment("westBase", RoadLayout.FOUR_LANE_DIVIDED)
                .addNewSegment("westIntersection", RoadLayout.FOUR_LANE_DIVIDED_WITH_LEFT_TURN_LANES)
                .addTransition(intersection)
                .addNewSegment("eastIntersection", RoadLayout.FOUR_LANE_DIVIDED_WITH_LEFT_TURN_LANES)
                .addNewSegment("eastBase", RoadLayout.FOUR_LANE_DIVIDED);

        broadStreet.getRoadSegment("westBase").addRoadPoints(
                new RoadPoint(rp1).dX(10.0),
                new RoadPoint(rp1).dX(20.0));

        broadStreet.getRoadSegment("westIntersection").addRoadPoints(
                new RoadPoint(rp1).dX(30.0),
                new RoadPoint(rp1).dX(40.0));

        broadStreet.getRoadSegment("eastIntersection").addRoadPoints(
                new RoadPoint(rp1).dX(50.0),
                new RoadPoint(rp1).dX(60.0));

        broadStreet.getRoadSegment("eastBase").addRoadPoints(
                new RoadPoint(rp1).dX(70.0),
                new RoadPoint(rp1).dX(80.0));

        RoadPoint rp2 = new RoadPoint(rp1).dX(40.0).dY(-40.0).dA(90.0);

        Road leeHighway = new Road("Lee Highway")
                .addNewSegment("southBase", RoadLayout.FOUR_LANE_DIVIDED)
                .addNewSegment("southIntersection", RoadLayout.FOUR_LANE_DIVIDED_WITH_LEFT_TURN_LANES)
                .addTransition(intersection)
                .addNewSegment("northIntersection", RoadLayout.FOUR_LANE_DIVIDED_WITH_LEFT_TURN_LANES)
                .addNewSegment("northBase", RoadLayout.FOUR_LANE_DIVIDED);

        leeHighway.getRoadSegment("southBase").addRoadPoints(
                new RoadPoint(rp2).dY(10.0),
                new RoadPoint(rp2).dY(20.0));

        leeHighway.getRoadSegment("southIntersection").addRoadPoints(
                new RoadPoint(rp2).dY(30.0),
                new RoadPoint(rp2).dY(40.0));

        leeHighway.getRoadSegment("northIntersection").addRoadPoints(
                new RoadPoint(rp2).dY(50.0),
                new RoadPoint(rp2).dY(60.0));

        leeHighway.getRoadSegment("northBase").addRoadPoints(
                new RoadPoint(rp2).dY(70.0),
                new RoadPoint(rp2).dY(80.0));

        intersection
                .addRoadSegment(leeHighway.getRoadSegment("southIntersection"), End.A)
                .addRoadSegment(broadStreet.getRoadSegment("westIntersection"), End.B)
                .addRoadSegment(leeHighway.getRoadSegment("northIntersection"), End.B)
                .addRoadSegment(broadStreet.getRoadSegment("eastIntersection"), End.A);

        intersection.mate(
                leeHighway.getRoadSegment("southIntersection"),
                leeHighway.getRoadSegment("northIntersection"));

        intersection.mate(
                broadStreet.getRoadSegment("eastIntersection"),
                broadStreet.getRoadSegment("westIntersection"));

        broadStreet.testTransitions();
        leeHighway.testTransitions();
    }
}
