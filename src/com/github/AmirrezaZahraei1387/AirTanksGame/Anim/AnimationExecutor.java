package com.github.AmirrezaZahraei1387.AirTanksGame.Anim;

import javax.swing.Timer;
import javax.swing.JComponent;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class AnimationExecutor extends JComponent{

    private final Map<Integer, Animation> animations;
    private ArrayList<AnimationJob> jobs;
    private final Timer timer;

    public AnimationExecutor(Map<Integer, Animation> animations){
        this.timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(1);
            }
        });

        this.animations = animations;
        this.jobs = new ArrayList<>();
    }

    public void addAnim(int id, Point point){
        synchronized (jobs) {
            jobs.add(new AnimationJob(id, point));
        }
    }

    @Override
    public void paintComponent(Graphics g2d){
        synchronized (jobs) {
            for (int i = 0; i < jobs.size(); ++i) {
                Animation ch = animations.get(jobs.get(i).getId());
                jobs.get(i).draw(g2d, ch);

                if (jobs.get(i).isFinished(ch))
                    jobs.remove(i);
            }
        }
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}
