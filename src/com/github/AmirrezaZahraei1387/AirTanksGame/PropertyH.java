package com.github.AmirrezaZahraei1387.AirTanksGame;

import com.github.AmirrezaZahraei1387.AirTanksGame.Character.LocaState;

public class PropertyH {
    public LocaState[] enemies;
    public LocaState human;

    public PropertyH(){}

    public void setAll(LocaState[] enemies, LocaState human){
        this.enemies = enemies;
        this.human = human;
    }
}
