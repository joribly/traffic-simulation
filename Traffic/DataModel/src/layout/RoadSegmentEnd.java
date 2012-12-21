package layout;

/**
* Created with IntelliJ IDEA.
* User: Blystone
* Date: 12/20/12
* Time: 9:27 PM
* To change this template use File | Settings | File Templates.
*/
class RoadSegmentEnd {
    RoadSegment roadSegment;
    int end;
    public RoadSegmentEnd(RoadSegment roadSegment, int end) {
        this.roadSegment = roadSegment;
        this.end = end;
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public int getEnd() {
        return end;
    }

    public String toString() {
        String [] AB = new String[] {"A","B"};
        return roadSegment.getId() + " (" + AB[getEnd()] + ")";
    }
}
