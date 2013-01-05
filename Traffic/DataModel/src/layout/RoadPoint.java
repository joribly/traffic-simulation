package layout;

import org.apache.commons.math.geometry.Vector3D;
import javax.vecmath.Point3d;

public class RoadPoint {

    private static int _id = 0;

    private Vector3D orientation;
    private final Point3d origin;
    private int id;

    public RoadPoint(RoadPoint roadPoint) {
        orientation = new Vector3D(roadPoint.orientation.getAlpha(), roadPoint.orientation.getDelta());
        origin = new Point3d(roadPoint.origin);
        id = ++_id;
    }

    public RoadPoint clone() {
        return new RoadPoint(this);
    }

    public RoadPoint(double x, double y, double z, double vx, double vy, double vz) {
        origin = new Point3d(x, y, z);
        orientation = new Vector3D(vx, vy, vz);
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "(id=" + id + ") origin: " + origin + " orientation " + orientation;
    }

    public RoadPoint dX(double dx) {
        return clone()._dX(dx);
    }

    public RoadPoint dY(double dy) {
        return clone()._dY(dy);
    }

    public RoadPoint dA(double da) {
        return clone()._dA(da);
    }

    private RoadPoint _dX(double dx) {
        shiftOrigin(dx, 0.0, 0.0);
        return this;
    }

    private RoadPoint _dY(double dy) {
        shiftOrigin(0.0, dy, 0.0);
        return this;
    }

    private RoadPoint _dA(double da) {
        orientation = new Vector3D(orientation.getAlpha() + Math.toRadians(da) , 0.0);
        return this;
    }

    private void shiftOrigin(double dx, double dy, double dz) {
        origin.add(new Point3d(dx, dy, dz));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadPoint roadPoint = (RoadPoint) o;

        if (id != roadPoint.id) return false;
        if (orientation != null ? !orientation.equals(roadPoint.orientation) : roadPoint.orientation != null)
            return false;
        return !(origin != null ? !origin.equals(roadPoint.origin) : roadPoint.origin != null);

    }

    @Override
    public int hashCode() {
        int result = orientation != null ? orientation.hashCode() : 0;
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    public double [] getXYLocation(double offset) {

        Vector3D delta = orientation.scalarMultiply(offset);
        return new double[] {10*(origin.x + delta.getX()), 300 -10*(origin.y + delta.getY())} ;
    }
}
