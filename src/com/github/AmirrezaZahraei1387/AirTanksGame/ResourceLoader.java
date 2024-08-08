package com.github.AmirrezaZahraei1387.AirTanksGame;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {

    /*
    returns the maximum scale factor that can be used to fit an
    object of size target into standard
     */
    static public double getFitScale(Dimension target, Dimension standard){
        double w_scale = standard.width / (double) target.width;
        double h_scale = standard.height / (double) target.height;

        return Math.min(w_scale, h_scale);
    }

    static public double getFitScale(int w, int h, Dimension standard){
        return getFitScale(new Dimension(w, h), standard);
    }

    /*
    scale the img to fit into the standard.
     */
    static public BufferedImage scaleImage(BufferedImage img, Dimension standard){

        double scale = getFitScale(img.getWidth(), img.getHeight(), standard);

        AffineTransformOp scaler = new AffineTransformOp(
                AffineTransform.getScaleInstance(scale, scale),
                AffineTransformOp.TYPE_BILINEAR);

        return scaler.filter(img, null);
    }

    static public BufferedImage loadImage(String path, Dimension standard) throws IOException {
        BufferedImage bufferedImage;
        bufferedImage = ImageIO.read(new File(path));

        if(standard != null)
            bufferedImage = scaleImage(bufferedImage, standard);

        return bufferedImage;
    }

    static public BufferedImage[] loadAll(String[] paths, Dimension standard) throws IOException {

        BufferedImage[] images = new BufferedImage[paths.length];

        for(int i = 0; i < paths.length; ++i)
            images[i] = loadImage(paths[i], standard);

        return images;
    }
}
















