package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.BulletExecutor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class UserAirTank extends AirTankBase implements KeyListener{

    private final int margin;
    private final Dimension windowSize;

    private BulletExecutor bulletExecutor;
    private boolean shot;

    public UserAirTank(int margin, Dimension windowSize, Point pos,
                       BufferedImage hull_img, BufferedImage weapon_img,
                       int currentHealth, Bullet bullet, int speed) {

        super(windowSize, hull_img, weapon_img, currentHealth, bullet, speed);
        setPos(pos);
        this.windowSize = new Dimension(windowSize);
        this.margin = margin;
        this.shot = false;
    }

    private Point predictMove(PosMoves move){

        int speed = getSpeed();

        Point currPos = new Point(getHullLoc());

        switch (move){
            case UP -> currPos.y -= speed;
            case DOWN -> currPos.y += speed;
            case LEFT -> currPos.x -= speed;
            case RIGHT -> currPos.x += speed;
        }

        return currPos;
    }

    private boolean moveT(PosMoves move){
        if(!isInBound(predictMove(move)))
            return false;
        super.moveT(move, true);
        repaint(1);
        return true;
    }

    private boolean isInBound(Point currentPos){

        return currentPos.x >= margin &&
                currentPos.x <= windowSize.width - margin - getHullBound().width
                &&
               currentPos.y >= margin &&
                currentPos.y <= windowSize.height - margin - getHullBound().height;
    }

    public void setBulletExecutor(BulletExecutor executor){
        this.bulletExecutor = executor;
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyChar()) {
            case 'w':
                moveT(PosMoves.UP);
                break;
            case 's':
                moveT(PosMoves.DOWN);
                break;
            case 'a':
                moveT(PosMoves.LEFT);
                break;
            case 'd':
                moveT(PosMoves.RIGHT);
                break;
            case ' ':
                if (!shot) {
                    Rectangle weapon = getWeaponBound();
                    bulletExecutor.shoot(
                            getBullet(),
                            new Point(weapon.x + weapon.width / 2, weapon.y),
                            true, this);
                    shot = true;
                }

                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == ' ')
            shot = false;
    }
}
