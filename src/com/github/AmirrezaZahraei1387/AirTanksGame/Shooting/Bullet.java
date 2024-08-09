package com.github.AmirrezaZahraei1387.AirTanksGame.Shooting;

import java.awt.image.BufferedImage;

public class Bullet {
    public final BufferedImage bullet_img;
    public final int bulletDamage;
    public final int speed;
    public final int distance;
    public final int shootEvery;

    public Bullet(BufferedImage bullet_img, int bulletDamage, int speed, int distance, int shootEvery){
        this.bullet_img = bullet_img;
        this.bulletDamage = bulletDamage;
        this.speed = speed;
        this.distance = distance;
        this.shootEvery = shootEvery;
    }

}
