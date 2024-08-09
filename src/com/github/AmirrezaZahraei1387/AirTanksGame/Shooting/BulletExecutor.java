package com.github.AmirrezaZahraei1387.AirTanksGame.Shooting;


import com.github.AmirrezaZahraei1387.AirTanksGame.Character.LocaState;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.Map;

public class BulletExecutor extends JComponent {

    private ArrayList<LocaState> enemies;
    private LocaState player;

    private Dimension windowSize;

    private final Timer timer;

    private final Map<Integer, Bullet> bullets;
    private final ArrayList<BulletJob> jobs;

    private final int marginHit;

    {
        jobs = new ArrayList<>();
    }

    public BulletExecutor(Map<Integer, Bullet> bullets,
                          ArrayList<LocaState> enemies,
                          LocaState player, Dimension dimension, int marginHit){
        this.bullets = bullets;
        this.enemies = enemies;
        this.player = player;
        this.windowSize = new Dimension(dimension);

        this.marginHit = marginHit;

        this.timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(1);
            }
        });
    }

    public void shoot(int id, Point startPoint, boolean isForward){
        synchronized (jobs) {
            jobs.add(new BulletJob(startPoint, isForward, bullets.get(id)));
        }
    }

    @Override
    public void paintComponent(Graphics g2d){
        synchronized (jobs){
            for(int i = 0; i < jobs.size(); ++i){

                BulletJob currJob = jobs.get(i);

                currJob.draw(g2d);

                if(currJob.isForward()) { // the bullet in going from player towards the enemies
                    int index = (int) Math.ceil((double) (enemies.size() * currJob.getPoint().x) / windowSize.width) - 1;

                    LocaState enemy = enemies.get(index);

                    if(enemy.isDead())
                        continue;

                    if (currJob.doesHit(enemy.getHullBound(), marginHit)) {
                        System.out.println(enemy.getHealth());
                        enemy.decHealth(currJob.getBullet().bulletDamage);
                        jobs.remove(i);
                    }

                }else{ // a bullet has been shot from enemy to the player

                    if(currJob.doesHit(player.getHullBound(), marginHit)){
                        player.decHealth(currJob.getBullet().bulletDamage);
                        jobs.remove(i);
                    }
                }

                if(currJob.isFinished())
                    jobs.remove(i);
            }
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return windowSize;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}
