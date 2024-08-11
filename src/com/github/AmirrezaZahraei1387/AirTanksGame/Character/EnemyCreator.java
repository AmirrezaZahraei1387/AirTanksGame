package com.github.AmirrezaZahraei1387.AirTanksGame.Character;


import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.BulletExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.CreatorStatus;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.HitDetection;

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

public class EnemyCreator extends JComponent implements CreatorStatus {

    private final int MARGIN_BETWEEN_TANKS;

    private final int ENEMY_COUNT;
    private int enemyCount;

    private final ArrayList<ArrayList<EnemyAirTank>> enemies;
    private final LocaState player;

    private final Dimension windowSize;
    private final int maxWidth;
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


    private BulletExecutor bulletExecutor;
    private HitDetection hitDetection;

    private final long MIN_TIME_RUN;
    private final long MAX_TIME_RUN;

    private final long MIN_TIME_SHOOT;
    private final long MAX_TIME_SHOOT;

    private int activeRowNum = 0;

    private int passed;
    private int killed;

    public EnemyCreator(Dimension windowSize, int maxWidth, int maxHeight,
                        int margin, LocaState player,
                        BufferedImage[] hulls, BufferedImage[] weapons,
                        Bullet[] bullets,
                        int MAX_HEALTH, int MIN_HEALTH,
                        int MAX_SPEED, int MIN_SPEED,
                        int enemy_number,
                        int max_row_active,
                        int max_col_active,
                        long MIN_TIME_SHOOT, long MAX_TIME_SHOOT,
                        long MIN_TIME_RUN, long MAX_TIME_RUN){

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
        this.player = player;

        this.MAX_TIME_RUN = MAX_TIME_RUN;
        this.MIN_TIME_RUN = MIN_TIME_RUN;

        this.MAX_TIME_SHOOT = MAX_TIME_SHOOT;
        this.MIN_TIME_SHOOT = MIN_TIME_SHOOT;

        this.killed = 0;
        this.passed = 0;

        this.enemyCount = enemy_number;
        this.ENEMY_COUNT = enemy_number;

        this.rand = new Random();

        // partitioning the game window
        int length = windowSize.width - 2 * margin;
        this.actualEnemyWidth = maxWidth + 2 * margin;

        enemies = new ArrayList<>(length / actualEnemyWidth);


        for(int i = 0; i < length / actualEnemyWidth; ++i){
            enemies.add(new ArrayList<>());
        }

        timer = new Timer(5, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < enemies.size(); ++i) {

                    long currentTime = System.currentTimeMillis();

                    // running
                    for(int j = 0; j < enemies.get(i).size(); ++j){

                        if(enemies.get(i).get(j).isFinished()){

                            if(enemies.get(i).get(j).isDead())
                                ++killed;
                            else
                                ++passed;

                            enemies.get(i).remove(j);
                            continue;
                        }

                        if (currentTime - enemies.get(i).get(j).prevRun >=
                                rand.nextLong(MIN_TIME_RUN, MAX_TIME_RUN + 1)
                                &&
                                rand.nextBoolean()
                        ) {

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
                                enemies.get(i).get(j).moveT(windowSize, hitDetection);
                        }
                    }

                    // shooting.
                    // only the first row is allow to shoot
                    if(!enemies.get(i).isEmpty()) {
                        EnemyAirTank enemy = enemies.get(i).getFirst();

                        if(currentTime - enemy.prevShoot
                                >= rand.nextLong(MIN_TIME_SHOOT, MAX_TIME_SHOOT+1)
                        &&
                        rand.nextBoolean()) {
                            enemy.prevShoot = currentTime;
                            enemy.shoot(bulletExecutor);
                        }
                    }

                    // adding the enemies
                    if(enemyCount > 0) {

                        if (activeRowNum >= max_row_active)
                            continue;

                        if (enemies.get(i).size() >= max_col_active)
                            continue;

                        if (canPut(i) && rand.nextBoolean()) {
                            EnemyAirTank enemyAirTank = createRandomEnemy(i);
                            if(!enemyAirTank.getHullBound().intersects(player.getHullBound())) {
                                enemies.get(i).addLast(enemyAirTank);
                                --enemyCount;
                            }
                        }
                    }
                }
                repaint(1);
            }
        });

        MARGIN_BETWEEN_TANKS = this.margin + maxHeight;
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

        enemy.prevRun = System.currentTimeMillis() +
                rand.nextLong(MIN_TIME_RUN, MAX_TIME_RUN + 1);
        enemy.prevShoot = System.currentTimeMillis() +
                rand.nextLong(MIN_TIME_SHOOT, MAX_TIME_SHOOT + 1);

        return enemy;
    }

    public void serBulletExecutor(BulletExecutor executor){
        this.bulletExecutor = executor;
    }

    public void setHitDetection(HitDetection hitDetection){
        this.hitDetection = hitDetection;
    }

    @Override
    public void paintComponent(Graphics g2d){

        activeRowNum = 0;

        for (ArrayList<EnemyAirTank> enemy : enemies) {

            if(!enemy.isEmpty())
                ++activeRowNum;

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


    @Override
    public boolean isFinished(){
        return getRemains() <= 0;
    }

    @Override
    public int getKilled(){
        return killed;
    }

    @Override
    public int getPassed(){
        return passed;
    }

    @Override
    public int getWholeCount(){
        return ENEMY_COUNT;
    }

    @Override
    public int getRemains(){
        return ENEMY_COUNT - killed - passed;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}


