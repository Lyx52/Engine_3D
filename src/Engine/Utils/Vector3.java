package Engine.Utils;

public class Vector3 {
    private float
        x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //GETTERS/SETTERS
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void multiplyZ(float multiZ) {
        this.z *= multiZ;
    }
    public void multiplyX(float multiX) {
        this.x *= multiX;
    }
    public void multiplyY(float multiY) {
        this.y *= multiY;
    }

    public void divZ(float divZ) {
        this.z /= divZ;
    }
    public void divX(float divX) {
        this.x /= divX;
    }
    public void divY(float divY) {
        this.y /= divY;
    }

    public void addZ(float addZ) {
        this.z += addZ;
    }
    public void addX(float addX) {
        this.x += addX;
    }
    public void addY(float addY) {
        this.y += addY;
    }

    public void subZ(float subZ) {
        this.z -= subZ;
    }
    public void subX(float subX) {
        this.x -= subX;
    }
    public void subY(float subY) {
        this.y -= subY;
    }
}