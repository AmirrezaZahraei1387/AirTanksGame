package com.github.AmirrezaZahraei1387.AirTanksGame.Character;


import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.Shooting.BulletExecutor;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class EnemyCreator extends JComponent {
    private ArrayList<EnemyAirTank> enemies;

    private Dimension windowSize;
    private int maxWidth;
    private int margin;
    private int actualEnemyWidth;

    private Timer timer;
    private Random rand;

    // the resources and bounds on properties
    private final BufferedImage[] hulls;
    private final BufferedImage[] weapons;
    private final Bullet[] bullets;
    private final int MAX_HEALTH;
    private final int MIN_HEALTH;
    private final int MAX_SPEED;
    private final int MIN_SPEED;

    private BulletExecutor bulletExecutor;

    public EnemyCreator(Dimension windowSize, int maxWidth, int margin,
                        BufferedImage[] hulls, BufferedImage[] weapons,
                        Bullet[] bullets,
                        int MAX_HEALTH, int MIN_HEALTH,
                        int MAX_SPEED, int MIN_SPEED){

        this.windowSize = windowSize;
        this.maxWidth = maxWidth;
        this.margin = margin;
        this.hulls = hulls;
        this.weapons = weapons;
        this.bullets = bullets;
        this.MAX_HEALTH = MAX_HEALTH;
        this.MIN_HEALTH = MIN_HEALTH;
        this.MAX_SPEED = MAX_SPEED;
        this.MIN_SPEED = MIN_SPEED;

        this.rand = new Random();

        // partitioning the game window
        int length = windowSize.width - 2 * margin;
        this.actualEnemyWidth = maxWidth + 2 * margin;
        enemies = new ArrayList<>(length / actualEnemyWidth);

        for(int i = 0; i < length / actualEnemyWidth; ++i){
            enemies.add(createRandomEnemy(i));
        }

        timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(1);
            }
        });
    }


    private EnemyAirTank createRandomEnemy(int index) {

        Point point = new Point(margin +
                index *
                        actualEnemyWidth +
                rand.nextInt(actualEnemyWidth - maxWidth),
                margin);

        return new EnemyAirTank(
                hulls[rand.nextInt(hulls.length)],
                weapons[rand.nextInt(weapons.length)],
                point,
                windowSize,
                rand.nextInt(MIN_HEALTH, MAX_HEALTH + 1),
                bullets[rand.nextInt(bullets.length)],
                rand.nextInt(MIN_SPEED, MAX_SPEED + 1)
        );
    }

    public void serBulletExecutor(BulletExecutor executor){
        this.bulletExecutor = executor;
    }

    @Override
    public void paintComponent(Graphics g2d){
        for(int i = 0; i < enemies.size(); ++i){
            if(rand.nextInt(100) > 80)
                enemies.get(i).moveT(windowSize);

            if(rand.nextBoolean()) {
                enemies.get(i).shoot(this.bulletExecutor);
            }

            if(enemies.get(i).isDead()) {
                enemies.set(i, createRandomEnemy(i));
            }

            enemies.get(i).paintComponent(g2d);
        }
    }

    public ArrayList<? extends LocaState> getLocaStates(){
        return enemies;
    }

    @Override
    public Dimension getPreferredSize(){
        return windowSize;
    }

    @Override
    public Dimension getMinimumSize(){
        return windowSize;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}


