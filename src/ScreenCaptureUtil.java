import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenCaptureUtil implements KeyListener{
    private final JFrame frame;
    private int counter;
    private final String path;
    private final String name;
    private Robot robot;

    private boolean captured = false;

    public ScreenCaptureUtil(JFrame frame, String path, String name) {
        this.frame = frame;
        this.path = path;
        counter = 0;
        this.name = name;

        try {
            this.robot = new Robot();
        } catch (AWTException ignored) {}
    }

    private void capture() throws IOException {
        BufferedImage img = robot.createScreenCapture(new Rectangle(frame.getX(), frame.getY(),
                frame.getWidth(), frame.getHeight()));

        ImageIO.write(img, "png",
                new File(path + "/" + name + String.valueOf(counter)  + ".png"));

        ++counter;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F && !captured) {
            try {
                capture();
            } catch (IOException ignored) {}
            captured = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F) {
            captured = false;
        }
    }
}
