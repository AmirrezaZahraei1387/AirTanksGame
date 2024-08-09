package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class AirTankBase extends LocaState{
    private final BufferedImage hull_img;
    private final BufferedImage weapon_img;

    private Point hull_loc;
    private Point weapon_loc;

    protected AirTankBase(BufferedImage hull_img, BufferedImage weapon_img,
                          int currentHealth, int bulletId, int speed) {
        super(currentHealth, bulletId, speed);

        this.hull_img = hull_img;
        this.weapon_img = weapon_img;

        hull_loc = new Point();
        weapon_loc = new Point();
    }

    @Override
    public void paintComponent(Graphics g2d){
        if(!isDead()) {
            g2d.drawImage(hull_img, hull_loc.x, hull_loc.y, this);
            g2d.drawImage(weapon_img, weapon_loc.x, weapon_loc.y, this);
        }
        //else
        //  the window is clear
    }

    @Override
    public Rectangle getHullBound() {
        return new Rectangle(hull_loc.x, hull_loc.y, hull_img.getWidth(), hull_img.getHeight());
    }

    @Override
    public Rectangle getWeaponBound() {
        return new Rectangle(weapon_loc.x, weapon_loc.y, weapon_img.getWidth(), weapon_img.getHeight());
    }

    @Override
    public Point getHullLoc() {
        return hull_loc;
    }

    @Override
    public Point getWeaponLoc() {
        return weapon_loc;
    }

    protected void setPos(Point point){
        hull_loc = point;
        setWeaponLoc();
    }

    protected void moveT(PosMoves move, boolean isForward){

        int speed = getSpeed();
        if(!isForward) speed *= -1;

        switch (move){
            case UP -> hull_loc.y -= speed;
            case DOWN -> hull_loc.y += speed;
            case LEFT -> hull_loc.x -= speed;
            case RIGHT -> hull_loc.x += speed;
        }

        setWeaponLoc();
    }

    protected Point predictMove(PosMoves move, boolean isForward){

        int speed = getSpeed();
        if(!isForward) speed *= -1;

        Point currPos = new Point(hull_loc);

        switch (move){
            case UP -> currPos.y -= speed;
            case DOWN -> currPos.y += speed;
            case LEFT -> currPos.x -= speed;
            case RIGHT -> currPos.x += speed;
        }

        return currPos;
    }

    private void setWeaponLoc(){
        weapon_loc.x = hull_loc.x + hull_img.getWidth() / 2 - weapon_img.getWidth() / 2;
        weapon_loc.y = hull_loc.y + hull_img.getHeight() / 2 - weapon_img.getHeight() / 2;
    }
}
