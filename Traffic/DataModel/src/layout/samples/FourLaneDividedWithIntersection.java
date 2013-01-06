package layout.samples;

import layout.*;

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
         *  10 a         b   11 a         b
         */

        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,0.0,-1.0,0.0);
        RoadPoint rp2 = rp1.dX(40.0).dY(-50.0).dA(90.0);
        RoadPoint rp3 = rp1.dY(-40.0);
        RoadPoint rp4 = rp3.dX(65);
        RoadPoint rp5 = rp4.dA(90.0).dY(5.0);
        RoadPoint rp6 = rp5.dA(-90.0).dX(-10.0).dY(2);

        Transition intersection = new Transition("Broad and Lee");
        Transition southIntersection = new Transition("South and Lee");
        Transition oneWaySplitIntersection = new Transition("One Way Split");

        Road leeHighway = new Road("Lee Highway")
                .addNewSegment(
                        "southDeadEnd",
                        RoadLayout.DEAD_END,
                        rp2)
                .addTransition(southIntersection)
                .addNewSegment(
                        "southBase",
                        RoadLayout.FOUR_LANE_STD,
                        rp2.dY(10.0), rp2.dY(20.0))
                .addNewSegment(
                        "southIntersection",
                        RoadLayout.FOUR_LANE_DIVIDED_WITH_TO_LEFT_TURN_LANE,
                        rp2.dY(30.0), rp2.dY(40.0))
                .addTransition(intersection)
                .addNewSegment(
                        "northIntersection",
                        RoadLayout.FOUR_LANE_DIVIDED_WITH_FROM_LEFT_TURN_LANE,
                        rp2.dY(50.0), rp2.dY(60.0))
                .addNewSegment(
                        "northBase",
                        RoadLayout.FOUR_LANE_DIVIDED,
                        rp2.dY(70.0), rp2.dY(80.0));


        Road broadStreet = new Road("Broad Street")
                .addNewSegment(
                        "westBase",
                        RoadLayout.FOUR_LANE_DIVIDED,
                        rp1.dX(10.0), rp1.dX(20.0))
                .addNewSegment(
                        "westIntersection",
                        RoadLayout.FOUR_LANE_DIVIDED_WITH_TO_LEFT_TURN_LANE,
                        rp1.dX(30.0), rp1.dX(40.0))
                .addTransition(intersection)
                .addNewSegment(
                        "eastIntersection",
                        RoadLayout.FOUR_LANE_DIVIDED_WITH_FROM_LEFT_TURN_LANE,
                        rp1.dX(50.0), rp1.dX(60.0))
                .addNewSegment(
                        "eastBase",
                        RoadLayout.FOUR_LANE_DIVIDED,
                        rp1.dX(70.0), rp1.dX(80.0));

        Road southStreet = new Road("South Street")
                .addNewSegment(
                        "southWest",
                        RoadLayout.TWO_LANE_STD,
                        rp3.dX(10.0), rp3.dX(40.0))
                .addTransition(southIntersection)
                .addNewSegment(
                        "southEast",
                        RoadLayout.TWO_LANE_STD,
                        rp3.dX(50.0), rp4, rp4.dA(30.0), rp4.dA(60.0), rp4.dA(90.0), rp5)
                .addTransition(oneWaySplitIntersection)
                .addNewSegment(
                        "southEastDeadEnd",
                        RoadLayout.DEAD_END,
                        rp5.dY(2.0));

        Road toSouthFromWestOneWay = new Road("one-way-east")
                .addNewSegment(
                        "a",
                        RoadLayout.ONE_LANE_FROM,
                        rp6, rp6.dX(10.0))
                .addTransition(oneWaySplitIntersection)
                .addNewSegment(
                        "b",
                        RoadLayout.ONE_LANE_FROM,
                        rp6.dX(14.0), rp6.dX(22.0));

        broadStreet.defineTransitionLaneConnections();
        leeHighway.defineTransitionLaneConnections();
        southStreet.defineTransitionLaneConnections();
        toSouthFromWestOneWay.defineTransitionLaneConnections();

        Plot.doIt(broadStreet, leeHighway, southStreet, toSouthFromWestOneWay);
    }
}
