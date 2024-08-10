package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.Bullet;

import java.awt.Point;
import javax.swing.JComponent;
import java.awt.Rectangle;

public abstract class LocaState extends JComponent {
    private int currentHealth;
    private Bullet bullet;
    private int speed;
    private long prevTimeShoot;
    private int state;

    private static final int DEAD = 0;
    private static final int PASSED = 1;
    private static final int ALIVE = 2;

    protected LocaState(int currentHealth, Bullet bullet, int speed){
        this.currentHealth = currentHealth;
        this.bullet = bullet;
        this.speed = speed;
        this.prevTimeShoot = 0;
        this.state = ALIVE;
    }

    public abstract Rectangle getHullBound();
    public abstract Rectangle getWeaponBound();

    public abstract Point getHullLoc();
    public abstract Point getWeaponLoc();

    public Bullet getBullet(){
        return bullet;
    }

    public int getSpeed(){
        return speed;
    }

    public void decHealth(int damage){
        currentHealth -= damage;
        if(currentHealth <= 0)
            state = DEAD;
    }

    public int getHealth(){
        return currentHealth;
    }

    public void setPassed(){
        this.state = PASSED;
    }

    public boolean isFinished(){
        return state == DEAD || state == PASSED;
    }

    public boolean isDead(){
        return state == DEAD;
    }

    public boolean isAlive(){
        return state == ALIVE;
    }

    public boolean isPassed(){
        return state == PASSED;
    }

    public boolean canShoot(){

        long currentTime = System.currentTimeMillis();

        if(System.currentTimeMillis() - prevTimeShoot > bullet.shootEvery) {
            prevTimeShoot = currentTime;
            return true;
        }else{
            return false;
        }
    };
}
