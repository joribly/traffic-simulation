package layout;

import org.apache.commons.math.geometry.Vector3D;
import sun.net.www.protocol.mailto.MailToURLConnection;

import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;

/**
 * Created with IntelliJ IDEA.
 * User: Blystone
 * Date: 12/2/12
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoadPoint {
    Vector3D orientation;
    Point3d origin;

    public RoadPoint() {

    }

    public RoadPoint(RoadPoint roadPoint) {
        orientation = new Vector3D(roadPoint.orientation.getAlpha(), roadPoint.orientation.getDelta());
        origin = new Point3d(roadPoint.origin);
    }

    public RoadPoint(double x, double y, double z, double vx, double vy, double vz) {
        origin = new Point3d(x, y, z);
        orientation = new Vector3D(vx, vy, vz);
    }

    public String toString() {
        return "origin: " + origin + " orientation " + orientation;
    }

    public RoadPoint dX(double dx) {
        shiftOrigin(dx, 0.0, 0.0);
        return this;
    }

    public RoadPoint dY(double dy) {
        shiftOrigin(0.0, dy, 0.0);
        return this;
    }

    public RoadPoint dA(double da) {
        orientation.add(new Vector3D(Math.toRadians(da), 0.0));
        return this;
    }

    private void shiftOrigin(double dx, double dy, double dz) {
        origin.add(new Point3d(dx, dy, dz));
    }
}
