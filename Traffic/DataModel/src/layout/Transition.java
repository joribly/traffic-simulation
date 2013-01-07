package layout;

import sun.security.tools.policytool.PolicyTool;

import java.util.*;

public class Transition {

    private static boolean debug=false;

    private final Map<RoadSegment, RoadSegmentConnection> roadSegmentConnectionMap;
    private RoadSegmentConnection previousRoadSegmentConnection;
    private RoadSegmentConnection firstRoadSegmentConnection;
    private String name;
    private ArrayList<Choice>choices;
    private ArrayList<RoadSegmentConnection>roadSegmentConnectionPairs;
    private boolean connectionsOrdered;


    public Transition(String name) {
        roadSegmentConnectionMap = new LinkedHashMap<RoadSegment, RoadSegmentConnection>();
        choices = new ArrayList<Choice>();
        roadSegmentConnectionPairs = new ArrayList<RoadSegmentConnection>();
        firstRoadSegmentConnection = previousRoadSegmentConnection = null;
        this.name = name;
    }

    private void doubleLinkRoadSegmentConnection(RoadSegmentConnection roadSegmentConnection) {
        if(previousRoadSegmentConnection != null) {
            linkRoadSegmentConnection(previousRoadSegmentConnection, roadSegmentConnection);
            linkRoadSegmentConnection(roadSegmentConnection, firstRoadSegmentConnection);
        }
        else {
            firstRoadSegmentConnection = roadSegmentConnection;
        }
        previousRoadSegmentConnection = roadSegmentConnection;
    }

    private void linkRoadSegmentConnection(RoadSegmentConnection a, RoadSegmentConnection b) {
        a.setNext(b);
        b.setPrevious(a);
    }

    public String getName() {
        return name;
    }

    public void mate(RoadSegment roadSegment1, RoadSegment roadSegment2){
        addRoadSegmentToPairings(roadSegment1, End.B);
        addRoadSegmentToPairings(roadSegment2, End.A);
        RoadSegmentConnection connection1 = getConnection(roadSegment1);
        RoadSegmentConnection connection2 = getConnection(roadSegment2);
        if(connection1 != null && connection2 != null) {
            connection1.setMate(connection2);
            connection2.setMate(connection1);
        }
    }

    private Transition addRoadSegmentToPairings(RoadSegment roadSegment, End end) {
        roadSegment.setEndTransition(end, this);
        RoadSegmentConnection roadSegmentConnection = new RoadSegmentConnection(roadSegment, end);
        roadSegmentConnectionMap.put(roadSegment, roadSegmentConnection);
        roadSegmentConnectionPairs.add(roadSegmentConnection);
        return this;
    }

    private void orderConnections() {
        for(int j=0; j <= 1; j++) {
           for(int i=j; i<roadSegmentConnectionPairs.size(); i+=2) {
              doubleLinkRoadSegmentConnection(roadSegmentConnectionPairs.get(i));
           }
        }
        connectionsOrdered = true;
    }


    public RoadSegment getMateRoadSegment(RoadSegment roadSegment) {
        return getConnection(roadSegment).getMate().getRoadSegment();
    }

    public boolean roadSegmentLaneHasChoices(RoadSegment roadSegment, Lane lane) {
        End end = getEnd(roadSegment);
        if(lane.isTo()) {
            if(end == End.A)return false;
        }
        if(lane.isFrom()) {
            if(end == End.B)return false;
        }
        return true;
    }

    public End getEnd(RoadSegment roadSegment) {
        return getConnection(roadSegment).getEnd();
    }

    public RoadSegment nextRoadSegment(RoadSegment roadSegment) {
        return getConnection(roadSegment).getNext().getRoadSegment();
    }

    private RoadSegmentConnection getConnection(RoadSegment roadSegment) {
        return roadSegmentConnectionMap.get(roadSegment);
    }

    public RoadSegment previousRoadSegment(RoadSegment roadSegment) {
        return getConnection(roadSegment).getPrevious().getRoadSegment();
    }

    public List<Lane> getPossibleLaneList(RoadSegment roadSegment, Lane lane, RoadSegment mateRoadSegment, Direction direction) {
        if(direction == Direction.U) {
            return roadSegment.getUTurnLaneList(lane.getTravel());
        }
        List<Lane>laneList = roadSegment.getLaneList(lane.getTravel());
        List<Lane>mateLaneList = getLeavingLaneList(mateRoadSegment);
        List<Lane>resultLaneList = new ArrayList<Lane>();
        int laneIndex= laneList.indexOf(lane);
        int mateCheckIndex = getMateIndexForLane(laneList, laneIndex, mateLaneList, direction);
        if(mateCheckIndex >= 0) {
            int testCheckIndex, laneCheckIndex=laneIndex;
            resultLaneList.add(mateLaneList.get(mateCheckIndex));
            while (true) {

                if(laneIndex == 0) { // innermost, pick up all left turn lanes and 1st straight  lane
                    testCheckIndex = getNextLaneIndex(mateLaneList, mateCheckIndex, Direction.ALL);
                    if(laneList.size() > 1 && mateLaneList.get(mateCheckIndex).canGoStraight())break;
                }
                else {
                    testCheckIndex = getNextLaneIndex(mateLaneList, mateCheckIndex, Direction.STRAIGHT, Direction.RIGHT);
                    laneCheckIndex = getNextLaneIndex(laneList, laneCheckIndex, direction);
                }
                if(testCheckIndex == mateCheckIndex)break;
                if(laneCheckIndex != laneIndex)break;

                mateCheckIndex = testCheckIndex;
                resultLaneList.add(mateLaneList.get(mateCheckIndex));
            }
        }
        return resultLaneList;
    }

    private int getMateIndexForLane(List<Lane>laneList, int laneIndex, List<Lane>mateLaneList, Direction direction) {
        int laneCheckIndex = -1;
        int mateCheckIndex = -1;
        do {
            laneCheckIndex = getNextLaneIndex(laneList, laneCheckIndex, direction);
            if(laneIndex == 0) {
                mateCheckIndex = getNextLaneIndex(mateLaneList, mateCheckIndex, Direction.ALL);
            }
            else {
                mateCheckIndex = getNextLaneIndex(mateLaneList, mateCheckIndex, Direction.STRAIGHT, Direction.RIGHT);
            }
        }while(laneCheckIndex != laneIndex);
        return mateCheckIndex;
    }

    private int getNextLaneIndex(List<Lane>lanes, int index, Direction ... directions) {
        // returns next index, until no more, then returns the input value
        int oldIndex = index;
        for(int i = ++index; i< lanes.size(); i++) {
            for(Direction direction: directions) {
               if(lanes.get(i).canGo(direction))return i;
            }
        }
        return oldIndex;
    }

    private List<Lane> getLeavingLaneList(RoadSegment roadSegment) {
        End roadSegmentEnd = getEnd(roadSegment);
        if(roadSegmentEnd == End.A) {
            return roadSegment.getLaneList(Travel.TO);
        }
        else {
            return roadSegment.getLaneList(Travel.FROM);
        }
    }

    private List<Lane> getEnteringLaneList(RoadSegment roadSegment) {
        End roadSegmentEnd = getEnd(roadSegment);
        if(roadSegmentEnd == End.A) {
            return roadSegment.getLaneList(Travel.FROM);
        }
        else {
            return roadSegment.getLaneList(Travel.TO);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" connects to  ");
        for(RoadSegmentConnection roadSegmentConnection : roadSegmentConnectionMap.values()) {
            sb.append("\n     ").append(roadSegmentConnection);
        }
        sb.append("\n");
        return sb.toString();
    }

    public void plot() {
        for(Choice choice: choices) {
            choice.plot();
        }
    }

    public void defineLaneConnections() {
        if(!connectionsOrdered) {
            orderConnections();
        }
        for(RoadSegment roadSegment : roadSegmentConnectionMap.keySet()) {
            defineConnectionsForRoadSegment(roadSegment);
        }
    }
    private void defineConnectionsForRoadSegment(RoadSegment roadSegment)  {
        List<Lane>enteringLaneList;
        if(debug==true)System.out.println("\n" + roadSegment);
        enteringLaneList = getEnteringLaneList(roadSegment);
        for(Lane enteringLane: enteringLaneList) {
            if(roadSegmentLaneHasChoices(roadSegment, enteringLane)) {
                defineConnectionsForRoadSegmentLane(roadSegment, enteringLane);
            }
        }
    }
    private void defineConnectionsForRoadSegmentLane(RoadSegment roadSegment, Lane lane) {
        RoadSegment connectionRoadSegment, mateRoadSegment = getMateRoadSegment(roadSegment);
        if(lane.canGoLeft()) {
            if(lane.isInner()) {
                addToChoices(Direction.U, roadSegment, lane, roadSegment);
            }
            connectionRoadSegment = roadSegment;
            while((connectionRoadSegment = nextRoadSegment(connectionRoadSegment)) != mateRoadSegment)  {
                addToChoices(Direction.LEFT, roadSegment, lane, connectionRoadSegment);
            }
        }
        if(lane.canGoStraight()) {
            addToChoices(Direction.STRAIGHT, roadSegment, lane, mateRoadSegment);
        }
        if(lane.canGoRight()) {
            connectionRoadSegment = roadSegment;
            while((connectionRoadSegment = previousRoadSegment(connectionRoadSegment)) != mateRoadSegment)  {
                addToChoices(Direction.RIGHT, roadSegment, lane, connectionRoadSegment);
            }
        }
    }

    private void addToChoices(Direction direction, RoadSegment roadSegment, Lane lane, RoadSegment connectionRoadSegment) {
        End end = getEnd(roadSegment);
        End connectionEnd = getEnd(connectionRoadSegment);
        List<Lane>connectionLaneList = getPossibleLaneList(roadSegment, lane, connectionRoadSegment, direction);
        String debugInfo = direction.getDebugInfo();
        for(Lane connectionLane: connectionLaneList) {
            if(debug==true)System.out.println("lane " + lane.getId() + ": " + debugInfo + " to roadSegment " + connectionRoadSegment.getId() + " lane " + connectionLane + " (end=" + connectionEnd + ")");
            if(!debugInfo.startsWith("U"))choices.add(new Choice(roadSegment, lane, end, connectionRoadSegment, connectionLane, connectionEnd));
        }
    }

    class Choice {

        ChoicePoint fromPoint,toPoint;
        public Choice(
                RoadSegment roadSegmentFrom, Lane laneFrom, End endFrom,
                RoadSegment roadSegmentTo, Lane laneTo, End endTo) {
             fromPoint = new ChoicePoint(roadSegmentFrom, laneFrom, endFrom);
             toPoint = new ChoicePoint(roadSegmentTo, laneTo, endTo);

        }

        private void plot() {
            double [] xy1 = fromPoint.roadSegment.getLaneEndXYLocation(fromPoint.lane, fromPoint.end);
            double [] xy2 = toPoint.roadSegment.getLaneEndXYLocation(toPoint.lane, toPoint.end);
            Plot.line(xy1, xy2, fromPoint.lane.getColor());
        }

    }

    class ChoicePoint {
        private RoadSegment roadSegment;
        private Lane lane;
        private End end;

        public ChoicePoint(RoadSegment roadSegment, Lane lane, End end) {
            this.roadSegment = roadSegment;
            this.lane = lane;
            this.end = end;
        }
    }

    class RoadSegmentConnection {
        private final RoadSegment roadSegment;
        private final End end;
        private RoadSegmentConnection mateRoadSegmentConnection;
        private RoadSegmentConnection previousRoadSegmentConnection, nextRoadSegmentConnection;

        public RoadSegmentConnection(RoadSegment roadSegment, End end) {
            this.roadSegment = roadSegment;
            this.end = end;
        }

        public String toString() {
            if(!hasMate())return roadSegment.getId() + "-" + end;
            return  roadSegment.getId() + "-" + end + "[mate: " +
                    mateRoadSegmentConnection.getRoadSegment().getId() + "-" + mateRoadSegmentConnection.getEnd() + "]";
        }

        public End getEnd() {
            return end;
        }

        public boolean hasMate() {
            return mateRoadSegmentConnection != null;
        }

        public RoadSegmentConnection getMate() {
            return mateRoadSegmentConnection;
        }

        public RoadSegment getRoadSegment() {
            return roadSegment;
        }

       public void setMate(RoadSegmentConnection mateRoadSegmentConnection) {
           this.mateRoadSegmentConnection = mateRoadSegmentConnection;
        }

        public void setNext(RoadSegmentConnection roadSegmentConnection) {
            nextRoadSegmentConnection = roadSegmentConnection;
        }

        public void setPrevious(RoadSegmentConnection roadSegmentConnection) {
            previousRoadSegmentConnection = roadSegmentConnection;
        }

        public RoadSegmentConnection getNext() {
            return nextRoadSegmentConnection;
        }

        public RoadSegmentConnection getPrevious() {
            return previousRoadSegmentConnection;
        }
    }

}
