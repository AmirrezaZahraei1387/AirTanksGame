package com.github.AmirrezaZahraei1387.AirTanksGame.Character;

import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.BulletExecutor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class EnemyAirTank extends AirTankBase{

    public long prevRun;
    public long prevShoot;

    public EnemyAirTank(BufferedImage hull_img, BufferedImage weapon_img,
                        Point pos, Dimension windowSize,
                        int currentHealth, Bullet bullet, int speed) {
        super(windowSize, hull_img, weapon_img, currentHealth, bullet, speed);
        super.setPos(pos);

        this.prevRun = 0;
        this.prevShoot = 0;
    }

    public boolean moveT(Dimension windowSize){
        Point hullLoc = new Point(super.getHullLoc());

        if(hullLoc.y < windowSize.height){
            super.moveT(PosMoves.UP, false);
            return true;
        }

        // we are out of bounds and we kill this tank automatically
        this.setPassed();
        return false;
    }

    public boolean shoot(BulletExecutor executor){

        Rectangle weaponBound = getWeaponBound();

        return executor.shoot(
                new Point(weaponBound.x + weaponBound.width / 2,
                          weaponBound.y),
                false,
                this);

    }
}
