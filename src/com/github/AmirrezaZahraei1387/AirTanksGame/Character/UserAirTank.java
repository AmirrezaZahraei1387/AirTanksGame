package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.BulletExecutor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class UserAirTank extends AirTankBase implements KeyListener{

    private int margin;
    private Dimension windowSize;

    private BulletExecutor bulletExecutor;

    public UserAirTank(int margin, Dimension windowSize, Point pos,
                       BufferedImage hull_img, BufferedImage weapon_img,
                       int currentHealth, int bulletId, int speed) {

        super(hull_img, weapon_img, currentHealth, bulletId, speed);
        setPos(pos);
        this.windowSize = new Dimension(windowSize);
        this.margin = margin;
    }

    private boolean moveT(PosMoves move, int speed){
        if(!isInBound())
            return false;
        super.moveT(move, speed, true);
        return true;
    }

    private boolean isInBound(){
        Point currentPos = getHullLoc();
        return currentPos.x >= margin && currentPos.x <= windowSize.width - margin
                &&
               currentPos.y >= margin && currentPos.y <= windowSize.height - margin;
    }

    public void setBulletExecutor(BulletExecutor executor){
        this.bulletExecutor = executor;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyChar()){
            case 'w':
                moveT(PosMoves.UP, super.getSpeed());
                break;
            case 's':
                moveT(PosMoves.DOWN, super.getSpeed());
                break;
            case 'a':
                moveT(PosMoves.LEFT, super.getSpeed());
                break;
            case 'd':
                moveT(PosMoves.RIGHT, super.getSpeed());
                break;
            case ' ':
                Rectangle weapon = getWeaponBound();
                bulletExecutor.shoot(
                        getBulletId(),
                        new Point(weapon.x + weapon.width / 2, weapon.y),
                        true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
