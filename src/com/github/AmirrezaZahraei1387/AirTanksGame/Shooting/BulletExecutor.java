package com.github.AmirrezaZahraei1387.AirTanksGame.Shooting;


import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimContracts;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimationExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.Character.LocaState;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JComponent;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class BulletExecutor extends JComponent {

    private ArrayList<ArrayList<LocaState>> enemies;
    private LocaState player;

    private Dimension windowSize;

    private final Timer timer;

    private final ArrayList<BulletJob> jobs;

    private final int marginHit_side;
    private final int marginHit_topBottom;

    private final AnimationExecutor animationExecutor;
    {
        jobs = new ArrayList<>();
    }

    public BulletExecutor(ArrayList<ArrayList<LocaState>> enemies,
                          LocaState player, AnimationExecutor executor, Dimension dimension,
                          int marginHit_side, int marginHit_topBottom){
        this.enemies = enemies;
        this.player = player;
        this.windowSize = new Dimension(dimension);

        this.marginHit_side = marginHit_side;
        this.marginHit_topBottom = marginHit_topBottom;
        this.animationExecutor = executor;

        this.timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(1);
            }
        });
    }

    public BulletExecutor(ArrayList<ArrayList<LocaState>> enemies,
                          LocaState player,
                          AnimationExecutor executor,
                          Dimension dimension){
        this(enemies, player, executor, dimension, 0, 0);
    }

    public boolean shoot(Point startPoint,
                      boolean isForward,
                      LocaState player){
        synchronized (jobs) {
            if(player.canShoot()) {
                jobs.add(new BulletJob(startPoint, isForward, player.getBullet()));
                return true;
            }
            return false;
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

                    for(int j = 0; j < enemies.get(index).size(); ++j){
                        shootBullet(i, currJob, enemies.get(index).get(j));
                    }

                }else{ // a bullet has been shot from enemy to the player
                    shootBullet(i, currJob, player);
                }

                if(currJob.isFinished())
                    try {
                        jobs.remove(i);
                    }catch (IndexOutOfBoundsException e){}

            }
        }
    }

    private void shootBullet(int i, BulletJob currJob, LocaState player){

        if(!player.isFinished() && currJob.doesHit(
                player.getHullBound(),
                marginHit_side,
                marginHit_topBottom)){
            player.decHealth(currJob.getBullet().bulletDamage);
            jobs.remove(i);

            if(player.isFinished()){
                animationExecutor.addAnim(
                        AnimContracts.DESTROY,
                        new Point(player.getHullLoc()));
            }else{
                animationExecutor.addAnim(
                        AnimContracts.HURT,
                        player.getHullLoc());
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
