package layout;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Road {

    private String name;
    private LinkedHashMap<String, RoadSegment> roadSegmentMap;
    private LinkedList<Transition> transitions;
    private RoadSegment lastRoadSegment;
    private String  lastName;

    public Road(String name) {
        roadSegmentMap = new LinkedHashMap<String,RoadSegment>();
        transitions = new LinkedList<Transition>();
        this.name = name;
    }

    public Road addNewSegment(String name, RoadLayout layout, RoadPoint ... points)  {
        addNewSegment(name, layout).lastRoadSegment.addRoadPoints(points);
        return this;
    }

    public Road addNewSegment(String name, RoadLayout layout) {
        RoadSegment roadSegment = new RoadSegment(layout);
        if(roadSegmentMap.size() > transitions.size()) {
            // add internal transition
            Transition transition = new Transition("T: " + lastRoadSegment.getId() + " -> " + roadSegment.getId());
            transitions.add(transition);
        }
        if(lastRoadSegment != null){
            Transition transition = transitions.getLast();
            transitions.getLast().mate(lastRoadSegment, roadSegment);
        }
        roadSegmentMap.put(name, roadSegment);
        lastRoadSegment = roadSegment;
        lastName = name;
        return this;
    }

    public RoadSegment getRoadSegment(String name) {
        return roadSegmentMap.get(name);
    }

    public Road addTransition(Transition transition) {
        transitions.add(transition);
        return this;
    }

    public void defineTransitionLaneConnections() {
        for(Transition transition: transitions) {
//            System.out.println("\n********************   Transition: " + transition.getName());
            transition.defineLaneConnections();
        }
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void plotSegments() {
        for(RoadSegment roadSegment: roadSegmentMap.values()) {
            roadSegment.plot();
        }
    }
}
