import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimContracts;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.Animation;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimationExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.Character.EnemyCreator;
import com.github.AmirrezaZahraei1387.AirTanksGame.Character.UserAirTank;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.CoinWonMessage;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.HealthBar;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.MoneyShower;
import com.github.AmirrezaZahraei1387.AirTanksGame.ResourceLoader;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.BulletExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.HitDetector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        Game game = new Game();
        game.launch();
    }
}
