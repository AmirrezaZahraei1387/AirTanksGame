package com.github.AmirrezaZahraei1387.AirTanksGame.Anim;

import java.awt.Graphics;
import java.awt.Point;

class AnimationJob {
    private final int id; // animation's id in the animation map
    private final Point point;
    private int currentFrame; // index of the current frame
    private long prevTime; // the previous time in milliseconds the animation was shown

    {
        currentFrame = 0;
        prevTime = 0;
    }

    public AnimationJob(int id, Point point){
        this.id = id;
        this.point = new Point(point);
    }

    public int getId(){
        return id;
    }

    public void draw(Graphics g2d, Animation anim){
        if(!isFinished(anim)){
            long currentTime = System.currentTimeMillis();

            if(currentTime - prevTime <= anim.getTimePerFrame()){
                g2d.drawImage(anim.get(currentFrame), point.x, point.y, null);
            }else{
                ++currentFrame;
                prevTime = currentTime;
            }
        }
    }

    public boolean isFinished(Animation anim){
        return currentFrame == anim.size();
    }
}
