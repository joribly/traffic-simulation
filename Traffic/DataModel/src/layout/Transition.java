package layout;

import java.util.*;

public class Transition {

    private final Map<RoadSegment, RoadSegmentConnection> roadSegmentConnectionMap;
    private RoadSegmentConnection previousRoadSegmentConnection;
    private RoadSegmentConnection firstRoadSegmentConnection;

    public Transition() {
        roadSegmentConnectionMap = new LinkedHashMap<RoadSegment, RoadSegmentConnection>();
        firstRoadSegmentConnection = previousRoadSegmentConnection = null;
    }

    public void addRoadSegment(RoadSegment roadSegment, End end) {
        roadSegment.setEndTransition(end, this);
        RoadSegmentConnection roadSegmentConnection = new RoadSegmentConnection(roadSegment, end);
        roadSegmentConnectionMap.put(roadSegment, roadSegmentConnection);
        doubleLinkRoadSegmentConnection(roadSegmentConnection);
    }

    private void doubleLinkRoadSegmentConnection(RoadSegmentConnection roadSegmentConnection) {
        if(previousRoadSegmentConnection != null) {
            previousRoadSegmentConnection.setNext(roadSegmentConnection);
            roadSegmentConnection.setPrevious(previousRoadSegmentConnection);
            roadSegmentConnection.setNext(firstRoadSegmentConnection);
            firstRoadSegmentConnection.setPrevious(roadSegmentConnection);
        }
        else {
            firstRoadSegmentConnection = roadSegmentConnection;
        }
        previousRoadSegmentConnection = roadSegmentConnection;
    }

    public void mate(RoadSegment roadSegment1, RoadSegment roadSegment2){
        RoadSegmentConnection connection1 = getConnection(roadSegment1);
        RoadSegmentConnection connection2 = getConnection(roadSegment2);
        if(connection1 == null || connection2 == null)return;
        connection1.setMate(connection2);
        if(!connection2.hasMate()) {
            connection2.setMate(connection1);
        }
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

    public Lane getStraightLane(RoadSegment roadSegment, Lane lane) {
        RoadLayout roadLayout = roadSegment.getLayout();
        RoadLayout mateRoadLayout = getMateRoadSegment(roadSegment).getLayout();
        return RoadLayout.getMateLane(roadLayout, mateRoadLayout, lane);
    }

    public List<Lane> getTurnLanes(RoadSegment turnRoadSegment) {
        End turnRoadSegmentEnd = getEnd(turnRoadSegment);
        if(turnRoadSegmentEnd == End.A) {
            return turnRoadSegment.getLayout().getLaneList(Travel.FROM);
        }
        else if(turnRoadSegmentEnd == End.B) {
            return turnRoadSegment.getLayout().getLaneList(Travel.TO);
        }
        return null;
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

    public void testLaneConnections() {
        End end,turnEnd;
        List<Lane>laneList,turnLaneList;
        Lane straightLane;
        for(RoadSegment roadSegment : roadSegmentConnectionMap.keySet()) {
            System.out.println("\n" + roadSegment);
            laneList = null;
            end = getEnd(roadSegment);
            if(end == End.A) {
                laneList = roadSegment.getLayout().getLaneList(Travel.FROM);
            }
            else if(end == End.B) {
                laneList = roadSegment.getLayout().getLaneList(Travel.TO);
            }
            if(laneList != null) {
               for(Lane lane: laneList) {
                   if(roadSegmentLaneHasChoices(roadSegment, lane)) {
                       RoadSegment turnRoadSegment, mateRoadSegment = getMateRoadSegment(roadSegment);
                       if(lane.canGoLeft()) {
                           laneList = roadSegment.getLayout().getUTurnDestinationLanes(lane);
                           if(laneList != null) {
                               for(Lane uTurnLane: laneList) {
                                   System.out.println("lane " + lane.getId() + ": U-Turn   to roadSegment " + roadSegment.getId() + " lane " + uTurnLane + " (end=" + end + ")");
                               }
                           }
                           turnRoadSegment = roadSegment;
                           while((turnRoadSegment = nextRoadSegment(turnRoadSegment)) != mateRoadSegment)  {
                               turnEnd = getEnd(turnRoadSegment);
                               turnLaneList = getTurnLanes(turnRoadSegment);
                               for(Lane leftTurnLane: turnLaneList) {
                                   System.out.println("lane " + lane.getId() + ": L-turn   to roadSegment " + turnRoadSegment.getId() + " lane " + leftTurnLane + " (end=" + turnEnd + ")");
                               }
                           }
                       }
                       if(lane.canGoStraight()) {
                           straightLane = getStraightLane(roadSegment, lane);
                           End mateEnd = getEnd(mateRoadSegment);
                           System.out.println("lane " + lane.getId() + ": Straight to roadSegment " + mateRoadSegment.getId() + " lane " + straightLane + " (end=" +  mateEnd + ")");
                       }
                       if(lane.canGoRight()) {
                           turnRoadSegment = roadSegment;
                           while((turnRoadSegment = previousRoadSegment(turnRoadSegment)) != mateRoadSegment)  {
                               turnEnd = getEnd(turnRoadSegment);
                               turnLaneList = getTurnLanes(turnRoadSegment);
                               for(Lane rightTurnLane: turnLaneList) {
                                   System.out.println("lane " + lane.getId() + ": R-turn   to roadSegment " + turnRoadSegment.getId() + " lane " + rightTurnLane + " (end=" + turnEnd + ")");
                               }
                           }
                       }
                   }
               }
            }
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
