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

    private final int MARGIN_BETWEEN_TANKS;
    private final int enemyCount;

    private final ArrayList<Long> enemiesAdd;
    private final ArrayList<ArrayList<EnemyAirTank>> enemies;

    private final Dimension windowSize;
    private final int maxWidth;
    private final int maxHeight;
    private final int margin;
    private final int actualEnemyWidth;

    private final Timer timer;
    private final Random rand;

    // the resources and bounds on properties
    private final BufferedImage[] hulls;
    private final BufferedImage[] weapons;
    private final Bullet[] bullets;
    private final int MAX_HEALTH;
    private final int MIN_HEALTH;
    private final int MAX_SPEED;
    private final int MIN_SPEED;

    private final int MAX_TIME_SHOOT;
    private final int MIN_TIME_SHOOT;
    private final int MAX_TIME_RUN;
    private final int MIN_TIME_RUN;
    private final int MAX_TIME_ADD;
    private final int MIN_TIME_ADD;

    private BulletExecutor bulletExecutor;

    public EnemyCreator(Dimension windowSize, int maxWidth, int maxHeight,
                        int margin,
                        BufferedImage[] hulls, BufferedImage[] weapons,
                        Bullet[] bullets,
                        int MAX_HEALTH, int MIN_HEALTH,
                        int MAX_SPEED, int MIN_SPEED,
                        int MAX_TIME_SHOOT, int MIN_TIME_SHOOT,
                        int MAX_TIME_RUN, int MIN_TIME_RUN,
                        int MAX_TIME_ADD, int MIN_TIME_ADD,
                        int enemyCount){

        this.windowSize = windowSize;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.margin = margin;
        this.hulls = hulls;
        this.weapons = weapons;
        this.bullets = bullets;
        this.MAX_HEALTH = MAX_HEALTH;
        this.MIN_HEALTH = MIN_HEALTH;
        this.MAX_SPEED = MAX_SPEED;
        this.MIN_SPEED = MIN_SPEED;

        this.MAX_TIME_SHOOT = MAX_TIME_SHOOT;
        this.MIN_TIME_SHOOT = MIN_TIME_SHOOT;

        this.MAX_TIME_ADD = MAX_TIME_ADD;
        this.MIN_TIME_ADD = MIN_TIME_ADD;

        this.MAX_TIME_RUN = MAX_TIME_RUN;
        this.MIN_TIME_RUN = MIN_TIME_RUN;

        this.enemyCount = enemyCount;

        this.rand = new Random();

        // partitioning the game window
        int length = windowSize.width - 2 * margin;
        this.actualEnemyWidth = maxWidth + 2 * margin;

        enemies = new ArrayList<>(length / actualEnemyWidth);

        for(int i = 0; i < length / actualEnemyWidth; ++i){
            enemies.add(new ArrayList<>());
        }

        this.enemiesAdd = new ArrayList<>(enemies.size());

        for(int i = 0; i < enemies.size(); ++i) {
            this.enemiesAdd.add(0L);
        }

        timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < enemies.size(); ++i) {

                    // the time info of the current column of enemies
                    long currentTime = System.currentTimeMillis();

                    // running
                    for(int j = 0; j < enemies.get(i).size(); ++j){

                        if(enemies.get(i).get(j).isFinished()){
                            enemies.get(i).remove(j);
                            continue;
                        }

                        if (currentTime - enemies.get(i).get(j).prevRun >=
                                rand.nextLong(MIN_TIME_RUN, MAX_TIME_RUN + 1)) {

                            enemies.get(i).get(j).prevRun = currentTime;

                            int forward = j - 1;
                            int distance = -1;

                            if (forward >= 0)
                                distance = Math.abs(
                                        enemies.get(i).get(j).getHullLoc().y
                                                -
                                                enemies.get(i).get(forward).getHullLoc().y
                                );

                            if (distance > MARGIN_BETWEEN_TANKS || distance == -1)
                                enemies.get(i).get(j).moveT(windowSize);
                        }
                    }

                    // shooting.
                    // only the first row is allow to shoot
                    if(!enemies.get(i).isEmpty()) {
                        EnemyAirTank enemy = enemies.get(i).getFirst();

                        if(currentTime - enemy.prevShoot >= rand.nextLong(MIN_TIME_SHOOT, MAX_TIME_SHOOT+1) && rand.nextBoolean()){
                            enemy.prevShoot = currentTime;
                            enemy.shoot(bulletExecutor);
                        }
                    }

                    // adding the enemies
                    if(currentTime - enemiesAdd.get(i) >= rand.nextLong(MIN_TIME_ADD, MAX_TIME_ADD+1) && rand.nextBoolean()){
                        enemiesAdd.set(i, currentTime);

                        if(canPut(i)) {
                            EnemyAirTank enemyAirTank = createRandomEnemy(i);
                            enemies.get(i).addLast(enemyAirTank);
                        }
                    }
                }
                repaint(1);
            }
        });

        MARGIN_BETWEEN_TANKS = 20 + maxHeight;
    }

    private EnemyAirTank createRandomEnemy(int index) {

        Point point = new Point(margin +
                index *
                        actualEnemyWidth +
                rand.nextInt(actualEnemyWidth - maxWidth),
                margin);

        EnemyAirTank enemy = new EnemyAirTank(
                hulls[rand.nextInt(hulls.length)],
                weapons[rand.nextInt(weapons.length)],
                point,
                windowSize,
                rand.nextInt(MIN_HEALTH, MAX_HEALTH + 1),
                bullets[rand.nextInt(bullets.length)],
                rand.nextInt(MIN_SPEED, MAX_SPEED + 1)
        );

        enemy.prevShoot = System.currentTimeMillis();
        enemy.prevRun = System.currentTimeMillis();

        for(int i = 0; i < enemiesAdd.size(); ++i)
            if(rand.nextBoolean()){
                enemiesAdd.set(i, System.currentTimeMillis());
            }

        return enemy;
    }

    public void serBulletExecutor(BulletExecutor executor){
        this.bulletExecutor = executor;
    }

    @Override
    public void paintComponent(Graphics g2d){
        for (ArrayList<EnemyAirTank> enemy : enemies) {
            for (EnemyAirTank enemyAirTank : enemy) {
                enemyAirTank.paintComponent(g2d);
            }
        }
    }

    public ArrayList<ArrayList<LocaState>> getLocaStates(){

        ArrayList<ArrayList<LocaState>> locaStates = new ArrayList<>();

        for (ArrayList<EnemyAirTank> deque : enemies) {
            locaStates.add((ArrayList<LocaState>)(ArrayList<?>) deque);
        }


        return locaStates;
    }

    private boolean canPut(int i){
        if(enemies.get(i).isEmpty())
            return true;
        EnemyAirTank tank = enemies.get(i).getLast();

        int distance = tank.getHullLoc().y - margin;

        if(distance > MARGIN_BETWEEN_TANKS)
            return true;
        return false;
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


