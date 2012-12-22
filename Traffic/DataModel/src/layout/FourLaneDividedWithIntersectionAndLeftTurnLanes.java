package layout;

public class FourLaneDividedWithIntersectionAndLeftTurnLanes {


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

        Intersection intersection = new Intersection();
        // in order, e.g. clockwise
        intersection.addRoadSegment(roadSegment1, End.B);
        intersection.addRoadSegment(roadSegment3, End.B);
        intersection.addRoadSegment(roadSegment2, End.A);
        intersection.addRoadSegment(roadSegment4, End.A);

        System.out.println(roadSegment1);
        System.out.println(roadSegment2);

        System.out.println(roadSegment3);
        System.out.println(roadSegment4);


    }
}
