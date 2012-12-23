package layout;

public enum RoadLayout {

    FOUR_LANE_STD(
            Lane.createStandardFrom().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft(),
            Lane.createStandardFrom().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanGoLeft().setCanChangeLaneRight(),
            Lane.createStandardTo().setCanGoStraight().setCanGoRight().setCanChangeLaneLeft()
    );

    private Lane[] lanes;
    private int count;

    RoadLayout(Lane ... lanes) {
        this.lanes = lanes;
        count = lanes.length;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name() + " has " + count + " lanes \n");
        int laneNumber = 0;
        for(Lane lane: lanes) {
            laneNumber++;
            sb.append(" #" + laneNumber + " " + lane + "\n");
        }
        return sb.toString();
    }
}
