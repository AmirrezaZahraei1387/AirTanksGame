package com.github.AmirrezaZahraei1387.AirTanksGame.Anim;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] images; // the images that will make up this Animation
    private final long timePerFrame; // the time to wait in milliseconds for each image


    public Animation(BufferedImage[] images, long timePerFrame) {
        this.images = images;
        this.timePerFrame = timePerFrame;
    }

    public int size(){
        return images.length;
    }

    public BufferedImage get(int i){
        return images[i];
    }

    public long getTimePerFrame(){
        return timePerFrame;
    }
}
