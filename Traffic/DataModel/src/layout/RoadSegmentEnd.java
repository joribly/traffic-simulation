package layout;

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
