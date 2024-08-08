package com.github.AmirrezaZahraei1387.AirTanksGame.Shooting;

import com.github.AmirrezaZahraei1387.AirTanksGame.PropertyH;

import java.util.Map;
import java.util.ArrayList;
import java.util.Timer;

public class BulletManager {
    private final Map<Integer, Bullet> bullets;
    private ArrayList<BulletJob> jobs;
    private Timer timer;

    public BulletManager(Map<Integer, Bullet> bullets){
        this.bullets = bullets;
    }
}
