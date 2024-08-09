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

    protected LocaState(int currentHealth, Bullet bullet, int speed){
        this.currentHealth = currentHealth;
        this.bullet = bullet;
        this.speed = speed;
        this.prevTimeShoot = 0;
    }

    protected void kill(){
        currentHealth = 0;
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

    public void decHealth(int damage){currentHealth -= damage;}

    public boolean isDead(){
        return currentHealth <= 0;
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
