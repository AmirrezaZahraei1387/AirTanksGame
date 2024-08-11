package com.github.AmirrezaZahraei1387.AirTanksGame.hurtS;

import com.github.AmirrezaZahraei1387.AirTanksGame.Character.LocaState;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;


public class HitDetector implements HitDetection{

    private final ArrayList<ArrayList<LocaState>> enemies;
    private final LocaState player;
    private final Dimension windowSize;

    public HitDetector(
            ArrayList<ArrayList<LocaState>> enemies,
            LocaState player,
            Dimension windowSize
    ){
        this.enemies = enemies;
        this.player = player;
        this.windowSize = windowSize;
    }

    @Override
    public boolean isMoveOkF(Rectangle rect) {
        int index = (int) Math.ceil((double) (enemies.size() * rect.x) / windowSize.width) - 1;
        boolean x1 = resolveRow(index, rect);
        boolean x2 = true;
        if(index + 1 < enemies.size())
            x2 = resolveRow(index + 1, rect);
        return x1 && x2;
    }

    private boolean resolveRow(int row, Rectangle rect){

        for(int i = 0; i < enemies.get(row).size(); ++i){
            if(rect.intersects(enemies.get(row).get(i).getHullBound()))
                return false;
        }

        return true;
    }

    @Override
    public boolean isMoveOkA(Rectangle rect) {
        return !rect.intersects(player.getHullBound());
    }
}
