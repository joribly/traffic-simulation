package layout;

public enum RoadLayout {

    FOUR_LANE_STD(
            Lane.createStandardFrom().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight().index(),
            Lane.createStandardTo().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight().index(),
            Lane.createStandardTo().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    );

    private Lane[] lanes;

    RoadLayout(Lane ... lanes) {
        this.lanes = lanes;
    }

    public Lane getLane(int index) {
        if(index < 0 || index >= lanes.length) return null;
        return lanes[index];
    }

    public int getNumberOfLanes() {
        return lanes.length;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name() + " has " + lanes.length + " lanes \n");
        int laneNumber = 0;
        for(Lane lane: lanes) {
            laneNumber++;
            sb.append(" #" + laneNumber + " " + lane + "\n");
        }
        return sb.toString();
    }
}
