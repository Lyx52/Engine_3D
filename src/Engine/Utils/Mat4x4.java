package Engine.Utils;

public class Mat4x4 {
    float[][] mat;

    public Mat4x4() {
        mat = new float[4][4];
    }
    public float[][] getMat() {
        return mat;
    }

    public void setMat(int index1, int index2, float value) {
        this.mat[index1][index2] = value;
    }
}
