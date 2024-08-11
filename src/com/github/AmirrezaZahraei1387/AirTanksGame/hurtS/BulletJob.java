package com.github.AmirrezaZahraei1387.AirTanksGame.hurtS;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

class BulletJob {
    private final Bullet bullet;
    private final Point startPoint;
    private final Point point;
    private final int speed_prefix;

    public BulletJob(Point startPoint, boolean isForward, Bullet bullet){
        this.bullet = bullet;
        this.point = new Point(startPoint);
        this.point.x -= bullet.bullet_img.getWidth() / 2;
        this.startPoint = new Point(startPoint);
        this.speed_prefix = (isForward ? -1 : 1);
    }

    public Bullet getBullet(){
        return bullet;
    }

    public Point getPoint(){return point;}

    public boolean doesHit(Rectangle rect1, int margin_side, int margin_topbottom){

        return (point.x >= rect1.x + margin_side &&
                    point.x <= rect1.x + rect1.width - margin_side
                &&
                point.y >= rect1.y + margin_topbottom &&
                    point.y <= rect1.y + rect1.height - margin_topbottom)
                ||
                (point.x + bullet.bullet_img.getWidth() >= rect1.x + margin_side &&
                        point.x + bullet.bullet_img.getWidth() <= rect1.x + rect1.width - margin_side
                        &&
                 point.y + bullet.bullet_img.getHeight() >= rect1.y + margin_topbottom &&
                        point.y + bullet.bullet_img.getHeight() <= rect1.y + rect1.height - margin_topbottom);
    }

    public void draw(Graphics g2d){
        if(!isFinished()) {
            g2d.drawImage(bullet.bullet_img, point.x, point.y, null);
            point.y += speed_prefix * bullet.speed;
        }
    }

    public boolean isFinished(){
        return Math.abs(point.y - startPoint.y) > bullet.distance;
    }

    public boolean isForward(){
        return speed_prefix == -1;
    }
}
