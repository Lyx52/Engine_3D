package Engine.Utils;

public class Triangle {
    Vector3[] TrianglePoint;

    public Triangle(Vector3 p0, Vector3 p1, Vector3 p2) {
        TrianglePoint = new Vector3[3];
        TrianglePoint[0] = p0;
        TrianglePoint[1] = p1;
        TrianglePoint[2] = p2;
    }
    public Vector3 getFirst() {
        return TrianglePoint[0];
    }
    public Vector3 getSecond() {
        return TrianglePoint[1];
    }
    public Vector3 getThird() {
        return TrianglePoint[2];
    }
}
