/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NT;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.*;
import java.awt.*;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.awt.event.*;
import javax.media.j3d.GraphicsConfigTemplate3D;

/**
 *
 * @author Nihanth
 */
public class HelloWorld extends Applet implements Runnable {

    double curr_X = 0.5;
    double curr_Y = 0.77;
    double curr_Z = 0;
    double width = 256;
    int width2 = 0;
    int x = 0;
    int counter = 0;
    GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas = new Canvas3D (gc);
    SimpleUniverse su = new SimpleUniverse(canvas);
    BranchGroup scene = new BranchGroup();
    Transform3D rotateX = new Transform3D();
    Transform3D rotateY = new Transform3D();
    Transform3D rotateZ = new Transform3D();
    TransformGroup objRotate = new TransformGroup();
    ColorCube cube = new ColorCube(0.3);

    public HelloWorld() {

        setBackground(Color.BLACK);
        GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
        template.setSceneAntialiasing(GraphicsConfigTemplate3D.PREFERRED);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.addChild(cube);
        rotateX.rotX(curr_X);
        rotateY.rotY(curr_Y);
        rotateZ.rotZ(curr_Z);
        rotateX.mul(rotateY);
        rotateX.mul(rotateZ);
        objRotate.setTransform(rotateX);
        scene.addChild(objRotate);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        scene.compile();
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(scene);
        su.getViewer().getView().setSceneAntialiasingEnable(true);
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        curr_Y -= 0.03;
                    break;
                    case KeyEvent.VK_RIGHT:
                        curr_Y += 0.03;
                    break;
                    case KeyEvent.VK_UP:
                        curr_X -= 0.03;
                    break;
                    case KeyEvent.VK_DOWN:
                        curr_X += 0.03;
                    break;
                    case KeyEvent.VK_COMMA:
                        curr_Z += 0.03;
                    break;
                    case KeyEvent.VK_PERIOD:
                        curr_Z -= 0.03;
                    break;
                    case KeyEvent.VK_PAGE_UP:
                        if (width < getWidth() * 4) {
                            x -= 3;
                            width += 6;
                            counter++;
                        }
                    break;
                    case KeyEvent.VK_PAGE_DOWN:
                        if (width > getWidth() / 4) {
                            x += 3;
                            width -= 6;
                            counter--;
                        }
                    break;
                }
                rotateX.rotX(curr_X);
                rotateY.rotY(curr_Y);
                rotateZ.rotZ(curr_Z);
                rotateX.mul(rotateY);
                rotateX.mul(rotateZ);
                objRotate.setTransform(rotateX);
                canvas.setSize((int)(width), getHeight());
                canvas.setLocation(x, 0);
            }
        });
    }

    public static void main(String args[]) {
        new MainFrame(new HelloWorld(), 256, 256);
    }

    public void run() {
        while (true) {
            if (width2 != getWidth()) {
                width2 = getWidth();
                synchronized (this) {
                    width = width2;
                    width += 6 * counter;
                    x = 3 * counter;
                }
            }
        }
    }

}
