package Engine.Utils;

public class Mesh {
    Triangle[] triangles;
    public Mesh(Triangle[] tris) {
        triangles = tris;
    }
    public Triangle getTriangle(int index) {
        return triangles[index];
    }
    public Triangle[] getTriangles() {
        return triangles;
    }
}
