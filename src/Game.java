import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimContracts;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.Animation;
import com.github.AmirrezaZahraei1387.AirTanksGame.Anim.AnimationExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.Character.EnemyCreator;
import com.github.AmirrezaZahraei1387.AirTanksGame.Character.UserAirTank;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.BackGround;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.CoinWonMessage;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.HealthBar;
import com.github.AmirrezaZahraei1387.AirTanksGame.GameUI.MoneyShower;
import com.github.AmirrezaZahraei1387.AirTanksGame.ResourceLoader;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.Bullet;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.BulletExecutor;
import com.github.AmirrezaZahraei1387.AirTanksGame.hurtS.HitDetector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Game {

    private final static Dimension windowSize = new Dimension(1000, 700);

    private final static Dimension standardTank = new Dimension(100, 100);
    private final static Dimension standardBullet = new Dimension(30, 30);
    private final static Dimension standardCoinNotify = new Dimension(30, 30);

    private final static Dimension standardCoinFinal = new Dimension(50, 50);

    private final static int HEALTH_BAR_LENGTH = 400;
    private final static long WAIT_BEFORE_FINISH = 1000;

    private final static int MAX_BULLET_DAMAGE_ENEMY = 5;
    private final static int MIN_BULLET_DAMAGE_ENEMY = 3;

    private final static int MAX_BULLET_DAMAGE_PLAYER = 7;
    private final static int MIN_BULLET_DAMAGE_PLAYER = 5;

    private final static  int MAX_ENEMY_HEALTH = 11;
    private final static int MIN_ENEMY_HEALTH = 7;

    private final static int MAX_PLAYER_HEALTH = 30;
    private final static int MIN_PLAYER_HEALTH = 25;

    private final static int MAX_PLAYER_SPEED = 8;
    private final static int MIN_PLAYER_SPEED = 7;

    private final static int MAX_ENEMY_SPEED = 6;
    private final static int MIN_ENEMY_SPEED = 5;

    private final static int MAX_BULLET_SPEED_ENEMY = 6;
    private final static int MIN_BULLET_SPEED_ENEMY = 5;

    private final static int MAX_BULLET_SPEED_PLAYER = 7;
    private final static int MIN_BULLET_SPEED_PLAYER = 5;

    private final static int MAX_BULLET_DIST_ENEMY = 450;
    private final static int MIN_BULLET_DIST_ENEMY = 400;

    private final static int MAX_BULLET_DIST_PLAYER = 600;
    private final static int MIN_BULLET_DIST_PLAYER = 500;

    private final static long MAX_SHOOT_EVERY_ENEMY = 2100;
    private final static long MIN_SHOOT_EVERY_ENEMY = 2000;

    private final static long MAX_SHOOT_EVERY_PLAYER = 130;
    private final static long MIN_SHOOT_EVERY_PLAYER = 100;

    private final static int MAX_ENEMY_NUMBER = 40;
    private final static int MIN_ENEMY_NUMBER = 30;

    // game components
    private final AnimationExecutor animationExecutor;
    private final UserAirTank user;
    private final EnemyCreator creator;
    private final BulletExecutor bulletExecutor;
    private final HitDetector hitDetector;


    // game UI components
    private HealthBar healthBar;
    private MoneyShower moneyShower;
    private CoinWonMessage coinWonMessage;
    private BackGround backGround;

    private Random random = new Random();
    private final Timer timer;
    private JFrame frame;
    private boolean finish = false;
    private long prevTime = 0;

    public Game() throws IOException {

        // setting up the animations
        HashMap<Integer, Animation> animations = new HashMap<>();


        animations.put(
                AnimContracts.DESTROY, new Animation(
                        ResourceLoader.loadAll(
                                getAllNames("data/animations/explosion"),
                                standardTank
                        ),
                        500
                )
        );
        animations.put(
                AnimContracts.HURT,
                new Animation(ResourceLoader.loadAll(
                        getAllNames("data/animations/hurt"),
                        standardTank),
                        500)
        );
        animationExecutor = new AnimationExecutor(animations, windowSize);

        String[] bulletPaths = getAllNames("data/bullets");
        Bullet[] bulletsForCreator = createEnemyBullets(
                ResourceLoader.loadAll(bulletPaths,
                        standardBullet, 180.0));
        Bullet bulletUser = createPlayerBullet(
                ResourceLoader.loadImage(
                        bulletPaths[random.nextInt(bulletPaths.length)],
                                standardBullet
                )
        );

        String[] hulls_paths = getAllNames("data/hulls");
        String[] weapons_paths = getAllNames("data/weapons");

        user = new UserAirTank(
                10,
                windowSize,
                new Point(windowSize.width / 2 - standardTank.width / 2,
                        windowSize.height - standardTank.height - 10),
                ResourceLoader.loadImage(hulls_paths[random.nextInt(
                        hulls_paths.length)], standardTank),
                ResourceLoader.loadImage(weapons_paths[random.nextInt(
                        weapons_paths.length)], standardTank),
                random.nextInt(MIN_PLAYER_HEALTH, MAX_PLAYER_HEALTH),
                bulletUser,
                random.nextInt(MIN_PLAYER_SPEED, MAX_PLAYER_SPEED)
        );

        creator = new EnemyCreator(
                windowSize,
                standardTank.width,
                standardTank.height,
                10,
                user,
                ResourceLoader.loadAll(hulls_paths, standardTank, 180.0),
                ResourceLoader.loadAll(weapons_paths, standardTank, 180.0),
                bulletsForCreator,
                MAX_ENEMY_HEALTH,
                MIN_ENEMY_HEALTH,
                MAX_ENEMY_SPEED,
                MIN_ENEMY_SPEED,
                random.nextInt(MIN_ENEMY_NUMBER, MAX_ENEMY_NUMBER + 1),
                5,
                2,
                100,
                500,
                200,
                300
        );

        bulletExecutor = new BulletExecutor(
                creator.getLocaStates(),
                user,
                animationExecutor,
                windowSize,
                10,
                20
        );

        hitDetector = new HitDetector(
                creator.getLocaStates(),
                user,
                windowSize
        );

        user.setBulletExecutor(bulletExecutor);
        creator.serBulletExecutor(bulletExecutor);

        user.setHitDetection(hitDetector);
        creator.setHitDetection(hitDetector);

        healthBar = new HealthBar(
                windowSize,
                user,
                user.getHealth(),
                HEALTH_BAR_LENGTH,
                20
        );

        moneyShower = new MoneyShower(
                new ImageIcon(ResourceLoader.loadImage("data/coin.png",
                        standardCoinNotify)),
                new Font("data/font.otf", Font.BOLD, 30),
                creator,
                20,
                0,
                1,
                2
        );

        coinWonMessage = new CoinWonMessage(
                new ImageIcon(ResourceLoader.loadImage("data/coin.png",
                        standardCoinFinal)),
                new Font("data/font.otf", Font.BOLD, 50),
                windowSize
        );

        String[] background_paths = getAllNames("data/backgrounds");
        String s = background_paths[
                random.nextInt(background_paths.length)];
        System.out.println(Arrays.toString(background_paths));
        backGround = new BackGround(
                ResourceLoader.loadImage(s, windowSize),
                windowSize
        );

        timer = new Timer(4, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((user.isFinished() || creator.isFinished()) && !finish){
                    finish = true;
                    prevTime = System.currentTimeMillis();
                }

                if(finish) {
                    if(System.currentTimeMillis() - prevTime >= WAIT_BEFORE_FINISH) {
                        stopAll();
                        coinWonMessage.show(moneyShower.getCoins());
                    }
                }
            }
        });

    }

    public void launch(){
        setUpGame();
        startAll();
        frame.setVisible(true);
    }

    private void setUpGame(){
        frame = new JFrame("Air Tanks Game");
        moneyShower.initLabel(frame);
        coinWonMessage.initLabel(frame);
        frame.add(healthBar);
        frame.pack();
        frame.add(animationExecutor);
        frame.pack();
        frame.add(creator);
        frame.pack();
        frame.add(user);
        frame.pack();
        frame.add(bulletExecutor);
        frame.pack();
        frame.add(backGround);
        frame.pack();
        frame.addKeyListener(user);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                startAll();
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(false);
    }

    private void startAll(){
        timer.start();
        creator.start();
        user.start();
        animationExecutor.start();
        bulletExecutor.start();
        healthBar.start();
        moneyShower.start();
    }

    private void stopAll(){
        timer.stop();
        creator.stop();
        user.stop();
        animationExecutor.stop();
        bulletExecutor.stop();
        healthBar.stop();
        moneyShower.stop();
    }

    private Bullet[] createEnemyBullets(BufferedImage[] images){
        Bullet[] bullets = new Bullet[images.length];

        for(int i = 0; i < images.length; ++i){
            bullets[i] = new Bullet(
                    images[i],
                    random.nextInt(MIN_BULLET_DAMAGE_ENEMY, MAX_BULLET_DAMAGE_ENEMY),
                    random.nextInt(MIN_BULLET_SPEED_ENEMY, MAX_BULLET_SPEED_ENEMY),
                    random.nextInt(MIN_BULLET_DIST_ENEMY, MAX_BULLET_DIST_ENEMY),
                    random.nextLong(MIN_SHOOT_EVERY_ENEMY, MAX_SHOOT_EVERY_ENEMY)
            );
        }

        return bullets;
    }

    private Bullet createPlayerBullet(BufferedImage image){
        return new Bullet(
                image,
                random.nextInt(MIN_BULLET_DAMAGE_PLAYER, MAX_BULLET_DAMAGE_PLAYER),
                random.nextInt(MIN_BULLET_SPEED_PLAYER, MAX_BULLET_SPEED_PLAYER),
                random.nextInt(MIN_BULLET_DIST_PLAYER, MAX_BULLET_DIST_PLAYER),
                random.nextLong(MIN_SHOOT_EVERY_PLAYER, MAX_SHOOT_EVERY_PLAYER)
        );
    }

    private String[] getAllNames(String path){

        File f = new File(path);
        String[] names = f.list();

        for(int i = 0; i < names.length; ++i)
            names[i] = f.getPath() + "\\" + names[i];

        return names;
    }
}
