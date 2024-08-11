package com.github.AmirrezaZahraei1387.AirTanksGame.GameUI;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BackGround extends JComponent {
    private BufferedImage background;
    private Dimension dimension;

    public BackGround(BufferedImage image, Dimension dimension){
        this.background = image;
        this.dimension = dimension;
    }

    @Override
    public void paintComponent(Graphics g2d){
        g2d.drawImage(background, 0, 0, dimension.width, dimension.height, this);
    }

    @Override
    public Dimension getPreferredSize(){
        return dimension;
    }

    @Override
    public Dimension getMinimumSize(){
        return dimension;
    }
}
