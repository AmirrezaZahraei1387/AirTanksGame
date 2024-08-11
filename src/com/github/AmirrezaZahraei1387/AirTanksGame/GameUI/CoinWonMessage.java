package com.github.AmirrezaZahraei1387.AirTanksGame.GameUI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;

public class CoinWonMessage {
    private JLabel label;
    private Dimension windowSize;

    public CoinWonMessage(ImageIcon icon, Font font, Dimension windowSize) {
        label = new JLabel(icon, JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        label.setIcon(icon);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setIconTextGap(10); // Adjust gap between text and icon as needed
        this.windowSize = windowSize;
    }

    public void initLabel(Frame frame){
        frame.setLayout(null);
        frame.add(this.label);
        frame.pack();
        label.setVisible(false);
        frame.setLayout(new BorderLayout());
    }

    public void show(int coins){
        label.setText("You Earned " + String.valueOf(coins));
        label.setBounds(new Rectangle(
                windowSize.width / 2 - label.getPreferredSize().width / 2,
                windowSize.height / 2 - label.getPreferredSize().height / 2,
                label.getPreferredSize().width,
                label.getPreferredSize().height));
        label.setVisible(true);
    }
}
