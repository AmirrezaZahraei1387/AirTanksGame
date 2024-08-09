package com.github.AmirrezaZahraei1387.AirTanksGame.Shooting;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

class BulletJob {
    private final int id;
    private final Point startPoint;
    private Point point;
    private int speed_prefix;

    public BulletJob(int id, Point startPoint, boolean isForward, Bullet bullet){
        this.id = id;
        this.point = new Point(startPoint);
        this.point.x -= bullet.bullet_img.getWidth() / 2;
        this.startPoint = new Point(startPoint);
        this.speed_prefix = (isForward ? 1 : -1);
    }

    public int getId(){
        return id;
    }

    public Point getPoint(){return point;}

    public boolean doesHit(Rectangle rect1){
        return (point.x > rect1.x && point.x < rect1.x + rect1.width
                &&
                point.y > rect1.y && point.y < rect1.y + rect1.height);
    }

    public void draw(Graphics g2d, Bullet bullet){
        if(!isFinished(bullet)) {
            g2d.drawImage(bullet.bullet_img, point.x, point.y, null);
            point.y += speed_prefix * bullet.speed;
        }
    }

    public boolean isFinished(Bullet bullet){
        return Math.abs(point.y - startPoint.y) > bullet.distance;
    }

    public boolean isForward(){
        return speed_prefix == 1;
    }
}
