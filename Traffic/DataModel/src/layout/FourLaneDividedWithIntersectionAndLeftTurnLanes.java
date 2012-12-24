package layout;

public class FourLaneDividedWithIntersectionAndLeftTurnLanes {


    public static void main(String [] args) {

        /*       ------------
         *       |     2 --------
         *       |     b     |   |
         *       |     a     |   |
         *       3 a-b   a-b 4   |
         *             b         |
         *             a         |
         *             1 --------
         */
        RoadPoint rp1 = new RoadPoint(0.0,0.0,0.0,1.0,0.0,0.0);

        RoadSegment roadSegment1 = new RoadSegment(
                RoadLayout.FOUR_LANE_STD,
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

        RoadSegment roadSegment = roadSegment1;
        do {
            System.out.println(roadSegment);
            FourLaneDividedWithIntersectionAndLeftTurnLanes.test(roadSegment, 3);
            roadSegment = transition.getNextRoadSegment(roadSegment);
        }while(roadSegment.getId() != roadSegment1.getId());


    }
    public static void test(RoadSegment roadSegment, int count) {
        RoadPoint roadPoint;
        Lane lane;
        End end;
        Transition transition;
        for(int i=0; i<count; i++) {
            roadPoint = roadSegment.getRandomRoadPoint();
            if((end = roadSegment.end(roadPoint)) != End.NA) {
                transition = roadSegment.getTransition(end);
                if(transition != null) {
                   lane = roadSegment.getRandomLane();
                   if(transition.roadSegmentLaneHasChoices(roadSegment, lane)) {
                      System.out.println(roadPoint  + " " + lane + " has choices");
                   }
                }
            }
        }
        System.out.println();
    }
}
