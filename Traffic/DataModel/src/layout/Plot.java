package layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashSet;

public class Plot {

    private static PrintStream ps;
    public static void init() {
        try {
            ps = new PrintStream(new FileOutputStream(new File("C:/Temp/myRoad.svg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void line(double [] xy1, double [] xy2, String rgbColor) {
        out("<line x1=\"" +
                xy1[0] + "\" y1=\"" +
                xy1[1] + "\" x2=\"" +
                xy2[0] + "\" y2=\"" +
                xy2[1] + "\" style=\"stroke:rgb(" + rgbColor + ");stroke-width:2\" />");
    }

    public static void out(String str) {
        ps.println(str);
    }

    public static void close() {
        ps.close();
    }

    public static void doIt(Road ... roads) {
        Plot.init();
        Plot.out("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
        HashSet<Transition>transitions = new HashSet<Transition>();
        for(Road road:  roads) {
            road.plotSegments();
            for(Transition transition: road.getTransitions()) {
                transitions.add(transition); // eliminate duplicates
            }
        }
        for(Transition transition: transitions) {
            transition.plot();
        }
        Plot.out("</svg>");
        Plot.close();
    }
}
