package com.github.AmirrezaZahraei1387.AirTanksGame.GameUI;

import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.CreatorStatus;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MoneyShower{

    private final JLabel label;

    private static final Color TEXT_COLOR = Color.WHITE;

    private final Timer timer;

    private int coins;
    private int prev_passed_count;
    private int prev_killed_count;

    public MoneyShower(ImageIcon icon,
                       Font font,
                       CreatorStatus creatorStatus,
                       int margin,
                       int init_coins,
                       int add_coins,
                       int remove_coins) {

        this.label = new JLabel(String.valueOf(init_coins), JLabel.CENTER);
        label.setFont(font);
        label.setLocation(margin, margin);
        label.setForeground(TEXT_COLOR);
        label.setIcon(icon);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setIconTextGap(10); // Adjust gap between text and icon as needed
        label.setBounds(new Rectangle(margin,
                margin,
                label.getPreferredSize().width,
                label.getPreferredSize().height));
        this.coins = init_coins;
        this.prev_passed_count = creatorStatus.getPassed();
        this.prev_killed_count = creatorStatus.getKilled();


        timer = new Timer(5, e -> {

            int k = creatorStatus.getKilled() - prev_killed_count;
            int p = creatorStatus.getPassed() - prev_passed_count;

            if(k > 0) {
                coins += k * add_coins;
                label.setText(String.valueOf(coins));
                label.setSize(label.getPreferredSize());
            }

            if(p > 0){
                coins -= p * remove_coins;
                coins = Math.max(coins, 0);
                label.setText(String.valueOf(coins));
            }

            if(k > 0) {
                prev_killed_count += k;
            }if(p > 0)
                prev_passed_count += p;
        });
    }

    public void initLabel(Frame frame){
        frame.setLayout(null);
        frame.add(this.label);
        frame.pack();
        label.setVisible(true);
        frame.setLayout(new BorderLayout());
    }


    public void start(){
        timer.start();
    }

    public int getCoins(){
        return coins;
    }

    public void stop(){
        timer.stop();
    }
}
