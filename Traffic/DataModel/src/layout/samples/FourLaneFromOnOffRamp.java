package layout.samples;

import layout.*;

public class FourLaneFromOnOffRamp {

    /*
     *        | |
     *       4| |5 off
     *        |
     *       3|
     *        | |2 on
     *       1| |
     */

    public static void main(String [] args) {

        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        RoadSegment roadSegment1 = new RoadSegment(
                RoadLayout.FOUR_LANE_DIVIDED,
                rp1,
                new RoadPoint(rp1).dY(20.0),
                new RoadPoint(rp1).dY(40.0));

        RoadSegment roadSegment2On = new RoadSegment(
                RoadLayout.ON_RAMP_TO,
                new RoadPoint(rp1).dX(20.0),
                new RoadPoint(rp1).dX(20.0).dY(20.0),
                new RoadPoint(rp1).dX(20.0).dY(40.0));

        RoadSegment roadSegment3 = new RoadSegment(
                RoadLayout.FOUR_LANE_DIVIDED_PLUS_TO_ON_OFF_RAMP_LANE,
                new RoadPoint(rp1).dY(40.0),
                new RoadPoint(rp1).dY(60.0),
                new RoadPoint(rp1).dY(80.0));

        RoadSegment roadSegment4 = new RoadSegment(
                RoadLayout.FOUR_LANE_DIVIDED,
                new RoadPoint(rp1).dY(80.0),
                new RoadPoint(rp1).dY(100.0),
                new RoadPoint(rp1).dY(120.0));

        RoadSegment roadSegment5Off = new RoadSegment(
                RoadLayout.ONE_LANE_TO,
                new RoadPoint(rp1).dX(20.0).dY(80.0),
                new RoadPoint(rp1).dX(20.0).dY(100.0),
                new RoadPoint(rp1).dX(20.0).dY(120.0));

        RoadSegment deadEnd6 = new RoadSegment(
                RoadLayout.DEAD_END);

        RoadSegment deadEnd7 = new RoadSegment(
                RoadLayout.DEAD_END);

        Transition transitionOn = new Transition("T-on");
        Transition transitionOff = new Transition("T-off");
        // in order, e.g. clockwise
        transitionOn.addRoadSegment(roadSegment1, End.B);
        transitionOn.addRoadSegment(deadEnd6, End.A);
        transitionOn.addRoadSegment(roadSegment3, End.A);
        transitionOn.addRoadSegment(roadSegment2On, End.B);
        transitionOn.mate(roadSegment1, roadSegment3);
        transitionOn.mate(roadSegment2On, deadEnd6);

        transitionOff.addRoadSegment(roadSegment3, End.B);
        transitionOff.addRoadSegment(deadEnd7, End.A);
        transitionOff.addRoadSegment(roadSegment4, End.A);
        transitionOff.addRoadSegment(roadSegment5Off, End.A);
        transitionOff.mate(roadSegment3, roadSegment4);
        transitionOff.mate(deadEnd7, roadSegment5Off);


        transitionOn.testLaneConnections();
        transitionOff.testLaneConnections();

    }
}
