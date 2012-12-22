package layout;

public enum Attachment {

    AA(0,0),
    AB(0,1),
    BA(1,0),
    BB(1,1);

    private int from;
    private int to;

    Attachment(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void connect(RoadSegment roadSegmentFrom, RoadSegment roadSegmentTo)  {
        Intersection intersectionFrom = roadSegmentFrom.getIntersection(from);
        RoadSegmentEnd roadSegmentEnd = new RoadSegmentEnd(roadSegmentTo, to);
        intersectionFrom.addRoadSegmentEnd(roadSegmentEnd);
        roadSegmentTo.updateIntersection(intersectionFrom, to);

    }
}
