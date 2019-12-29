package Engine.Utils;

import java.util.Comparator;

public class SortTriangles implements Comparator<Triangle> {
    @Override
    public int compare(Triangle t1, Triangle t2) {
        float z1 = (t1.getFirst().getZ() + t1.getSecond().getZ() + t1.getThird().getZ()) / 3.0f;
        float z2 = (t2.getFirst().getZ() + t2.getSecond().getZ() + t2.getThird().getZ()) / 3.0f;
        return parseInt(z1 > z2);
    }
    public int parseInt(boolean b) {
        if (b == true) {
            return 1;
        } else return 0;
    }
}
