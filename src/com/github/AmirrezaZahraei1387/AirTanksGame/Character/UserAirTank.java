package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

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

    public UserAirTank(int margin, Dimension windowSize, Point pos,
                       BufferedImage hull_img, BufferedImage weapon_img,
                       int currentHealth, int bulletId, int speed) {

        super(hull_img, weapon_img, currentHealth, bulletId, speed);
        setPos(pos);
        this.windowSize = new Dimension(windowSize);
        this.margin = margin;
    }

    private boolean moveT(PosMoves move){
        if(!isInBound(predictMove(move, true)))
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
    public Dimension getPreferredSize() {
        return windowSize;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyChar()){
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
