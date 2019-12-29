package Engine.Graphics;

import Engine.InputManager.KeyManager;
import Engine.Utils.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
public class CameraProjection {
    private int
        width,
        height;

    private float
        x,y,z,
        theta = 0,
        zFar,
        zNear,
        fov,
        aspectRatio;

    private Mat4x4
        matZ, matX, matProj;

    public ArrayList<Triangle> getTriangles() {
        return triangles;
    }

    private ArrayList<Triangle>
        triangles,
        raster;


    public CameraProjection(float x, float y, float z, int fov, int width, int height, float zFar, float zNear) {
        triangles = new ArrayList<>();
        raster = new ArrayList<>();
        matZ = new Mat4x4();
        matX = new Mat4x4();
        matProj = new Mat4x4();

        aspectRatio = (float) width / (float) height;

        this.x = x;
        this.y = y;
        this.z = z;
        this.zFar = zFar;
        this.zNear = zNear;
        this.fov = (float) (1.0f / Math.tan(fov * 0.5f / 180.0f * Math.PI));
        this.width = width;
        this.height = height;

        matProj.setMat(0,0,aspectRatio * this.fov);
        matProj.setMat(1,1, this.fov);
        matProj.setMat(2,2, zFar / (zFar - zNear));
        matProj.setMat(3,2, (-zFar * zNear) / (zFar - zNear));
        matProj.setMat(2,3, 1.0f);
        matProj.setMat(3,3, 0.0f);
    }
    public void tick(KeyManager km) {
        raster.clear();
        if (km.getKey(KeyEvent.VK_D)) {
            theta += 0.01f;
        } else if (km.getKey(KeyEvent.VK_A)) {
            theta -= 0.01f;
        }
        matZ = new Mat4x4();
        matX = new Mat4x4();

        //Z Rotation
        matZ.setMat(0,0,(float) Math.cos(theta));
        matZ.setMat(0,1,(float) Math.sin(theta));
        matZ.setMat(1,0,(float) -Math.sin(theta));
        matZ.setMat(1,1,(float) Math.cos(theta));
        matZ.setMat(2,2,1);
        matZ.setMat(3,3,1);

        //X Rotation
        matX.setMat(0,0,1);
        matX.setMat(1,1,(float) Math.cos(theta * 0.5f));
        matX.setMat(1,2,(float) Math.sin(theta * 0.5f));
        matX.setMat(2,1,(float) -Math.sin(theta * 0.5f));
        matX.setMat(2,2,(float) Math.cos(theta * 0.5f));
        matX.setMat(3,3,1);
    }
    public void render(Graphics g) {
        for (Triangle tri : triangles) {
            Triangle
                triProjected = new Triangle(new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(0,0,0)),
                triTranslated = new Triangle(new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(0,0,0)),
                triRotatedZ = new Triangle(new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(0,0,0)),
                triRotatedZX = new Triangle(new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(0,0,0));

            // Rotate in Z-Axis
            MatrixCalculations.MultiplyMatrixVector(tri.getFirst(), triRotatedZ.getFirst(), matZ);
            MatrixCalculations.MultiplyMatrixVector(tri.getSecond(), triRotatedZ.getSecond(), matZ);
            MatrixCalculations.MultiplyMatrixVector(tri.getThird(), triRotatedZ.getThird(), matZ);

            // Rotate in X-Axis
            MatrixCalculations.MultiplyMatrixVector(triRotatedZ.getFirst(), triRotatedZX.getFirst(), matX);
            MatrixCalculations.MultiplyMatrixVector(triRotatedZ.getSecond(), triRotatedZX.getSecond(), matX);
            MatrixCalculations.MultiplyMatrixVector(triRotatedZ.getThird(), triRotatedZX.getThird(), matX);

            // Offset into the screen
            triTranslated = triRotatedZX;
            triTranslated.getFirst().addZ(8);
            triTranslated.getSecond().addZ(8);
            triTranslated.getThird().addZ(8);

            //Normalize surface
            Vector3 normal = new Vector3(0,0,0), line1 = new Vector3(0,0,0), line2 = new Vector3(0,0,0);

            line1.setX(triTranslated.getSecond().getX() - triTranslated.getFirst().getX());
            line1.setY(triTranslated.getSecond().getY() - triTranslated.getFirst().getY());
            line1.setZ(triTranslated.getSecond().getZ() - triTranslated.getFirst().getZ());

            line2.setX(triTranslated.getThird().getX() - triTranslated.getFirst().getX());
            line2.setY(triTranslated.getThird().getY() - triTranslated.getFirst().getY());
            line2.setZ(triTranslated.getThird().getZ() - triTranslated.getFirst().getZ());

            normal.setX(line1.getY() * line2.getZ() - line1.getZ() * line2.getY());
            normal.setY(line1.getZ() * line2.getX() - line1.getX() * line2.getZ());
            normal.setZ(line1.getX() * line2.getY() - line1.getY() * line2.getX());

            float l = (float) Math.sqrt(normal.getX() * normal.getX() + normal.getY() * normal.getY() + normal.getZ() * normal.getZ());
            normal.divX(1);
            normal.divY(1);
            normal.divZ(1);

            if (normal.getX() * (triTranslated.getFirst().getX() - x) + normal.getY() * (triTranslated.getFirst().getY() - y) + normal.getZ() * (triTranslated.getFirst().getZ() - z) < 0.0f) {

                Vector3 light = new Vector3(0,0,-1);

                float light_l = (float) Math.sqrt(light.getX() * light.getX() + light.getY() * light.getY() + light.getZ() * light.getZ());
                light.divX(1);
                light.divY(1);
                light.divZ(1);

                float dp = normal.getX() * light.getX() + normal.getY() * light.getY() + normal.getZ() * light.getZ();

                // Project triangles from 3D --> 2D
                MatrixCalculations.MultiplyMatrixVector(triTranslated.getFirst(), triProjected.getFirst(), matProj);
                MatrixCalculations.MultiplyMatrixVector(triTranslated.getSecond(), triProjected.getSecond(), matProj);
                MatrixCalculations.MultiplyMatrixVector(triTranslated.getThird(), triProjected.getThird(), matProj);

                // Scale into view
                triProjected.getFirst().addX(1.0f);
                triProjected.getFirst().addY(1.0f);

                triProjected.getSecond().addX(1.0f);
                triProjected.getSecond().addY(1.0f);

                triProjected.getThird().addX(1.0f);
                triProjected.getThird().addY(1.0f);

                triProjected.getFirst().multiplyX(0.5f * width);
                triProjected.getFirst().multiplyY(0.5f * height);

                triProjected.getSecond().multiplyX(0.5f * width);
                triProjected.getSecond().multiplyY(0.5f * height);

                triProjected.getThird().multiplyX(0.5f * width);
                triProjected.getThird().multiplyY(0.5f * height);

                raster.add(triProjected);
//            System.out.println(triProjected.getFirst().getX() + " " + triProjected.getFirst().getY()  + " " +
//                            triProjected.getSecond().getX()  + " " +  triProjected.getSecond().getY()  + " " +
//                    triProjected.getThird().getX()  + " " +  triProjected.getThird().getY());
            }
            raster.sort(new SortTriangles());
            for (Triangle projected : raster) {
                drawTriangle(g,
                        projected.getFirst().getX(), projected.getFirst().getY(),
                        projected.getSecond().getX(), projected.getSecond().getY(),
                        projected.getThird().getX(), projected.getThird().getY(), Color.BLACK
                );
            }
        }
    }
    public void drawTriangle(Graphics g, float x0, float y0, float x1, float y1, float x2, float y2, Color c) {
        g.setColor(c);
        g.drawLine((int) x0,(int) y0,(int) x1,(int) y1);
        g.drawLine((int) x1,(int) y1,(int) x2,(int) y2);
        g.drawLine((int) x2,(int) y2,(int) x0,(int) y0);
    }
    public void addTriangles(Mesh mesh) {
        for (Triangle tri : mesh.getTriangles()) {
            triangles.add(tri);
        }
    }
}
