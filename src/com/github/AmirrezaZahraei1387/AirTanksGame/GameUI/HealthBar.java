package com.github.AmirrezaZahraei1387.AirTanksGame.GameUI;

import com.github.AmirrezaZahraei1387.AirTanksGame.Character.LocaState;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;


public class HealthBar extends JComponent{

    private final Dimension windowSize;

    private final Timer timer;

    private final RoundRectangle2D bar_back;
    private RoundRectangle2D bar_front;
    private RoundRectangle2D full_transparent;

    private static final Color BAR_BACK_COLOR = new Color(248/255f, 136/255f, 104/255f, 0.98f);
    private static final Color BAR_FRONT_COLOR = new Color(255, 255, 255);

    public HealthBar(Dimension windowSize, LocaState player,
                     int init_heath, int length, int margin){
        this.windowSize = windowSize;


        bar_back = new RoundRectangle2D.Double(
                (double) (windowSize.width - length) / 2,
                windowSize.height - margin - 30,
                length,
                30,
                4,
                4
        );

        bar_front = new RoundRectangle2D.Double(
                (double) (windowSize.width - length) / 2 + 5,
                windowSize.height - margin - 25,
                length - 10,
                20,
                5,
                5
        );

        full_transparent = new RoundRectangle2D.Double(
                (double) (windowSize.width - length) / 2 + 5,
                windowSize.height - margin - 25,
                length - 10,
                20,
                5,
                5
        );

        final int[] h = {init_heath};
        double remove_unit = (double) (length - 10) / init_heath;

        timer = new Timer(5, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(player.getHealth() >= 0) {
                    bar_front = new RoundRectangle2D.Double(
                            (double) (windowSize.width - length) / 2 + 5,
                            windowSize.height - margin - 25,
                            remove_unit * player.getHealth(),
                            20,
                            5,
                            5
                    );
                    repaint(1);
                }else{
                    bar_front = new RoundRectangle2D.Double(
                            (double) (windowSize.width - length) / 2 + 5,
                            windowSize.height - margin - 25,
                            0,
                            20,
                            5,
                            5
                    );
                    repaint(1);
                }
            }
        });

    }

    @Override
    public void paintComponent(Graphics _g2d) {

        Graphics2D g2d = (Graphics2D) _g2d;

        g2d.setColor(BAR_BACK_COLOR);
        g2d.fill(bar_back);

        g2d.setColor(new Color(0,0,0));
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(full_transparent);

        g2d.setColor(BAR_FRONT_COLOR);
        g2d.fill(bar_front);
    }

    @Override
    public Dimension getPreferredSize() {
        return windowSize;
    }

    @Override
    public Dimension getMinimumSize() {
        return windowSize;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}
