package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimContracts;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimationExecutor;

import java.awt.Point;
import javax.swing.JComponent;
import java.awt.Rectangle;

public abstract class LocaState extends JComponent {
    private int currentHealth;
    private int bulletId;

    private int speed;

    protected LocaState(int currentHealth, int bulletId, int speed){
        this.currentHealth = currentHealth;
        this.bulletId = bulletId;
        this.speed = speed;
    }

    public abstract Rectangle getHullBound();
    public abstract Rectangle getWeaponBound();

    public abstract Point getHullLoc();
    public abstract Point getWeaponLoc();

    public int getHealth(){
        return currentHealth;
    }

    public int getBulletId(){
        return bulletId;
    }

    public int getSpeed(){
        return speed;
    }

    public void setHealth(int health){
        currentHealth = health;
    }

    public void setBulletId(int bulletId){
        this.bulletId = bulletId;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void decHealth(int damage){currentHealth -= damage;}

    public boolean isDead(){
        return currentHealth <= 0;
    }

    public void destroy(AnimationExecutor executor){
        // playing the destroy animation
        executor.addAnim(AnimContracts.DESTROY, getHullLoc());
    }
}
