package Engine.Display;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame
        Frame;

    private Canvas
        Canvas;

    private int
        width,
        height;

    private String
        title;

    public Display(String title, int width, int height) {
        this.title = title;
        this.height = height;
        this.width = width;

        createDisplay();
    }
    private void createDisplay() {
        Frame = new JFrame(title);
        Frame.setSize(width,height);
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setVisible(true);

        Canvas = new Canvas();
        Canvas.setPreferredSize(new Dimension(width,height));
        Canvas.setMinimumSize(new Dimension(width,height));
        Canvas.setMaximumSize(new Dimension(width,height));
        Canvas.setFocusable(false);

        Frame.add(Canvas);
        Frame.pack();
    }

    public JFrame getFrame() {
        return Frame;
    }

    public java.awt.Canvas getCanvas() {
        return Canvas;
    }
}
