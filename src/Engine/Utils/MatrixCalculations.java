package Engine.Utils;

public class MatrixCalculations {

    public static void MultiplyMatrixVector(Vector3 vec1, Vector3 vec2, Mat4x4 mat) {
        vec2.setX(vec1.getX() * mat.getMat()[0][0] + vec1.getY() * mat.getMat()[1][0] + vec1.getZ() * mat.getMat()[2][0] + mat.getMat()[3][0]);
        vec2.setY(vec1.getX() * mat.getMat()[0][1] + vec1.getY() * mat.getMat()[1][1] + vec1.getZ() * mat.getMat()[2][1] + mat.getMat()[3][1]);
        vec2.setZ(vec1.getX() * mat.getMat()[0][2] + vec1.getY() * mat.getMat()[1][2] + vec1.getZ() * mat.getMat()[2][2] + mat.getMat()[3][2]);

        float w = vec1.getX() * mat.getMat()[0][3] + vec1.getY() * mat.getMat()[1][3] + vec1.getZ() * mat.getMat()[2][3] + mat.getMat()[3][3];

        if (w != 0.0f) {
            vec2.setX(vec2.getX() / w);
            vec2.setY(vec2.getY() / w);
            vec2.setZ(vec2.getZ() / w);
        }
    }
}
