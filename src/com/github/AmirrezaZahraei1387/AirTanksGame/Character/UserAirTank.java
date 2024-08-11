package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.BulletExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.HitDetection;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.HitDetector;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import javax.swing.Timer;

public class UserAirTank extends AirTankBase implements KeyListener{

    private final int margin;
    private final Dimension windowSize;

    private BulletExecutor bulletExecutor;
    private boolean shot;

    private HashSet<Integer> keysPressed;
    private HitDetection hitDetection;

    {
        keysPressed = new HashSet<>();
    }

    Timer keyPreformer;

    public UserAirTank(int margin, Dimension windowSize, Point pos,
                       BufferedImage hull_img, BufferedImage weapon_img,
                       int currentHealth, Bullet bullet, int speed) {

        super(windowSize, hull_img, weapon_img, currentHealth, bullet, speed);
        setPos(pos);
        this.windowSize = new Dimension(windowSize);
        this.margin = margin;
        this.shot = false;

        keyPreformer = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Integer c: keysPressed){
                    switch (c){
                        case KeyEvent.VK_W:
                            moveT(PosMoves.UP);
                            break;
                        case KeyEvent.VK_S:
                            moveT(PosMoves.DOWN);
                            break;
                        case KeyEvent.VK_A:
                            moveT(PosMoves.LEFT);
                            break;
                        case KeyEvent.VK_D:
                            moveT(PosMoves.RIGHT);
                            break;
                    }
                }
            }
        });
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

        if(!hitDetection.isMoveOkF(getHullBound()))
            super.rollbackMoveT(move, true);
        else {
            repaint(1);
            return true;
        }

        return false;
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

    public void setHitDetection(HitDetection hitDetection){
        this.hitDetection = hitDetection;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if(isFinished())
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keysPressed.add(KeyEvent.VK_W);
                break;
            case KeyEvent.VK_S:
                keysPressed.add(KeyEvent.VK_S);
                break;
            case KeyEvent.VK_A:
                keysPressed.add(KeyEvent.VK_A);
                break;
            case KeyEvent.VK_D:
                keysPressed.add(KeyEvent.VK_D);
                break;
            case KeyEvent.VK_SPACE:
                if (!shot) {
                    Rectangle weapon = getWeaponBound();
                    bulletExecutor.shoot(
                            new Point(weapon.x + weapon.width / 2, weapon.y),
                            true, this);
                    shot = true;
                }

                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            shot = false;
        keysPressed.remove(e.getKeyCode());
    }

    public void start(){
        keyPreformer.start();
    }

    public void stop(){
        keyPreformer.stop();
    }
}
