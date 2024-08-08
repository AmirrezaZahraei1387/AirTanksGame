package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimContracts;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimationExecutor;

import java.awt.Point;
import javax.swing.JComponent;
import java.awt.Rectangle;
import org.ietf.jgss.Oid;

public abstract class LocaState extends JComponent {
    private int currentHealth;
    private int gunDamage;

    private int speed;

    protected LocaState(int currentHealth, int gunDamage, int speed){
        this.currentHealth = currentHealth;
        this.gunDamage = gunDamage;
        this.speed = speed;
    }

    public abstract Rectangle getHullBound();
    public abstract Rectangle getWeaponBound();

    public abstract Point getHullLoc();
    public abstract Point getWeaponLoc();

    public int getHealth(){
        return currentHealth;
    }

    public int getGunDamage(){
        return gunDamage;
    }

    public int getSpeed(){
        return speed;
    }

    public void setHealth(int health){
        currentHealth = health;
    }

    public void setGunDamage(int gunDamage){
        this.gunDamage = gunDamage;
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
