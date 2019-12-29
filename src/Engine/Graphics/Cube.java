package Engine.Graphics;

import Engine.Utils.Mesh;
import Engine.Utils.Triangle;
import Engine.Utils.Vector3;

import java.awt.*;

public class Cube {

    private float
        width,
        height,
        length,
        x,y,z;

    private Mesh
        meshCube;
    public Cube(CameraProjection camera, float x, float y, float z, float width, float height, float length) {
        meshCube = new Mesh(new Triangle[] {
                //X,Y,Z
                //South
                new Triangle(new Vector3(x,y,z), new Vector3(x,y + height,z), new Vector3(x + width,y + height,z)),
                new Triangle(new Vector3(x,y,z), new Vector3(x + width,y + height,z), new Vector3(x + width,y,z)),

                //East
                new Triangle(new Vector3(x + width,y,z), new Vector3(x + width,y + height,z), new Vector3(x + width,y + height,z + length)),
                new Triangle(new Vector3(x + width,y,z), new Vector3(x + width,y + height,z + length), new Vector3(x + width,y,z + length)),

                //North
                new Triangle(new Vector3(x + width,y,z + length), new Vector3(x + width,y + height,z + length), new Vector3(x,y + height,z + length)),
                new Triangle(new Vector3(x + width,y,z + length), new Vector3(x,y + height,z + length), new Vector3(x,y,z + length)),

                //West
                new Triangle(new Vector3(x,y,z + length), new Vector3(x,y + height,z + length), new Vector3(x,y + height,z)),
                new Triangle(new Vector3(x,y,z + length), new Vector3(x,y + height,z), new Vector3(x,y,z)),

                //Top
                new Triangle(new Vector3(x,y + height,z), new Vector3(x,y + height,z + length), new Vector3(x + width,y + height,z + length)),
                new Triangle(new Vector3(x,y + height,z), new Vector3(x + width,y + height,z + length), new Vector3(x + width,y + height,z)),

                //Bottom
                new Triangle(new Vector3(x + width,y,z + length), new Vector3(x,y,z + length), new Vector3(x,y,z)),
                new Triangle(new Vector3(x + width,y,z + length), new Vector3(x,y,z), new Vector3(x + width,y,z)),
        });

    }

    public Mesh getMeshCube() {
        return meshCube;
    }
}
