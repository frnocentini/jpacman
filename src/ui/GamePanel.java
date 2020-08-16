package ui;

import callbacks.GameEventListener;
import callbacks.GameLoop;
import constants.Constants;
import image.Image;
import image.ImageFactory;
import model.*;
import sound.SoundPlayer;
import utility.CoordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_ENTER;
import static sound.Sound.*;
import static utility.State.*;

public class GamePanel extends JPanel {

    private GameMainFrame frame;
    private GameEventListener gameEventListener;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    boolean inGame;
    private Timer timer;
    private boolean pacmanStart;
    private long startTime;
    private long portalTime;
    private int level;
    private int lives;
    private int lifeCounter;
    private boolean munch;
    private int consecutiveGhosts;
    private ImageIcon smallPanel;
    private JLabel pointsLabel;
    private JLabel readyLabel;
    private JLabel gameOverLabel;
    private JLabel levelLabel;
    private JLabel highScoreLabel;
    private JLabel livesLabel;
    private JLabel livesNumLabel;
    private ArrayList<ImageIcon> livesIcons;
    private boolean pacmanDead;

    public GamePanel(GameMainFrame frame, int level, int highScore, int lives){
        this.frame = frame;
        initializeVariables(level,highScore,lives);
        initializeLayout();
    }

    private void initializeVariables(int level, int highScore, int lives) {
        CoordManager.populateMaze();
        this.lifeCounter = 1;
        this.level = level;
        this.lives = lives;
        System.out.println(level);
        this.inGame = true;
        this.pacmanStart = false;
        this.timer = new Timer(Constants.GAME_SPEED,new GameLoop(this));
        this.timer.start();
        this.munch = true;
        SoundPlayer.stopAll();
        SoundPlayer.playMusic(GAME_START);
        this.startTime = this.portalTime = System.currentTimeMillis();
        FruitManager.setGameStart();
        this.pacman = new Pacman();
        this.ghosts = new ArrayList<>();
        Pinky pinky = new Pinky(this.pacman);
        Blinky blinky = new Blinky(this.pacman);
        Inky inky = new Inky(this.pacman,blinky);
        Clyde clyde = new Clyde(this.pacman);
        this.ghosts.add(clyde);
        this.ghosts.add(inky);
        this.ghosts.add(pinky);
        this.ghosts.add(blinky);
        this.levelLabel = new JLabel("");
        levelLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        levelLabel.setBounds(165,420,100,20);
        levelLabel.setForeground(Color.WHITE);
        add(levelLabel);
        this.pointsLabel = new JLabel("Points: "+this.pacman.getPoints());
        pointsLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        pointsLabel.setBounds(10,420,200,20);
        pointsLabel.setForeground(Color.WHITE);
        add(pointsLabel);
        this.highScoreLabel = new JLabel("High Score: "+highScore);
        highScoreLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        highScoreLabel.setBounds(10,438,200,20);
        highScoreLabel.setForeground(Color.WHITE);
        add(highScoreLabel);
        this.readyLabel = new JLabel("Ready!");
        readyLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        readyLabel.setBounds(173,420,100,20);
        readyLabel.setForeground(Color.YELLOW);
        add(readyLabel);
        this.gameOverLabel = new JLabel("");
        gameOverLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        gameOverLabel.setBounds(155,420,100,20);
        gameOverLabel.setForeground(Color.RED);
        add(gameOverLabel);
        this.livesLabel = new JLabel("Lives:");
        livesLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        livesLabel.setBounds(165,438,100,20);
        livesLabel.setForeground(Color.WHITE);
        add(livesLabel);
        this.livesNumLabel = new JLabel("");
        livesNumLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        livesNumLabel.setBounds(220,438,100,20);
        livesNumLabel.setForeground(Color.WHITE);
        add(livesNumLabel);
        this.livesIcons = new ArrayList<>();
        ImageIcon lifeIcon = ImageFactory.createImage(Image.LIFE);
        for(int i=0;i<lives;i++){
            livesIcons.add(lifeIcon);
        }
        FruitManager.initialize();
        FruitManager.chooseFruit(level);
    }

    private void restartLevel(){
        SoundPlayer.stopAll();
        this.pacmanStart = false;
        levelLabel.setText("");
        readyLabel.setText("Ready!");
        System.out.println(level);
        this.inGame = true;
        this.munch = true;
        this.startTime = System.currentTimeMillis();
        this.portalTime = System.currentTimeMillis();
        FruitManager.setGameStart();
        this.pacman.returnToSpawnPoint();
        for(Ghost ghost : this.ghosts) {
            ghost.returnToSpawnPoint(this.level);
            ghost.setPausedTime(0);
        }
        System.gc();
        timer.start();
    }

    private void initializeLayout() {
        this.gameEventListener = new GameEventListener(this);
        addKeyListener(this.gameEventListener);
        this.smallPanel = ImageFactory.createImage(Image.SMALL_PANEL);
        setFocusable(true);
        setLayout(null);
        requestFocusInWindow();
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g){
        if(inGame){
            drawSmallPanel(g);
            drawPoints();
            drawLives(g);
            drawPills(g);
            drawPowerPills(g);
            drawPortals(g);
            drawFruit(g);
            drawPacman(g);
            drawGhosts(g);
        } else{
            if(timer.isRunning()){
                timer.stop();
            }
        }
        //Metodo che si assicura che tutto si sia aggiornato
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLives(Graphics g) {
        if(lives < 6){
            livesNumLabel.setText("");
            for(int i=0;i<this.livesIcons.size();i++){
                g.drawImage(this.livesIcons.get(i).getImage(),205+(i*12),442,this);
            }
        } else if (lives != 0) {
            g.drawImage(this.livesIcons.get(0).getImage(),205,442,this);
            livesNumLabel.setText(""+lives);
        }
    }

    private void drawSmallPanel(Graphics g) {
        g.drawImage(this.smallPanel.getImage(), 0, 420, this);
    }

    private void drawFruit(Graphics g) {
        Fruit f = FruitManager.getFruit();
        if(f != null){
            g.drawImage(f.getImage(), f.getX(), f.getY(), this);
            if(CoordManager.checkCollision(pacman,f)){
                f.setDead(true);
                this.pacman.addPoints(f.getPoints());
                SoundPlayer.playEffect(EAT_FRUIT);
            }
        }
    }

    private void drawPoints(){
        pointsLabel.setText("Points: "+this.pacman.getPoints());
        if(this.pacman.getPoints() >= 10000 * this.lifeCounter){
            this.lives++;
            this.livesIcons.add(ImageFactory.createImage(Image.LIFE));
            this.lifeCounter++;
        }
    }

    private void drawPortals(Graphics g) {
        Portal bluePortal = CoordManager.maze.getBluePortal();
        Portal redPortal = CoordManager.maze.getRedPortal();
        g.drawImage(bluePortal.getImage(), bluePortal.getX(), bluePortal.getY(), this);
        g.drawImage(redPortal.getImage(), redPortal.getX(), redPortal.getY(), this);
        //System.out.println("Le coordinate del rosso sono: "+bluePortal.getOther().getX()+" e "+bluePortal.getOther().getY());
        //System.out.println("Le coordinate del blu sono: "+redPortal.getOther().getX()+" e "+redPortal.getOther().getY());
        if(System.currentTimeMillis() >= (this.portalTime + 400) && !this.pacman.isDead()){
            if(CoordManager.checkCollision(pacman,bluePortal)){
                teleport(pacman,bluePortal);
            }else if(CoordManager.checkCollision(pacman,redPortal)){
                teleport(pacman,redPortal);
            }else{
                for(Ghost ghost : this.ghosts) {
                    if (CoordManager.checkCollision(ghost, bluePortal)) {
                        teleport(ghost, bluePortal);
                    } else if (CoordManager.checkCollision(ghost, redPortal)) {
                        teleport(ghost, redPortal);
                    }
                }
            }
        }
    }

    private void teleport(Sprite a, Portal p){
        a.setX(p.getOther().getX());
        a.setY(p.getOther().getY());
        if(p.getColor().equals("BLUE")){
            SoundPlayer.playEffect(BLUE_PORTAL_SOUND);
        }else{
            SoundPlayer.playEffect(RED_PORTAL_SOUND);
        }
        this.portalTime = System.currentTimeMillis();
    }

    private void drawPowerPills(Graphics g) {
        for(int i = 0; i< CoordManager.maze.getPowerPillsNum(); i++){
            PowerPill pp = CoordManager.maze.getPowerPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(CoordManager.checkCollision(pacman,pp)){
                if(!pp.isDead()){
                    CoordManager.maze.removeAlivePowerPill();
                    this.pacman.addPoints(pp.getPoints());
                    this.consecutiveGhosts = 0;
                    for(Ghost ghost : this.ghosts) {
                        if (ghost.getState() != EATEN) {
                            ghost.becomeFrightened();
                            System.out.println("Passo a frightened");
                        }
                    }
                }
                pp.setDead(true);
            } else if(!pp.isDead()){
                g.drawImage(pp.getImage(), pp.getX(), pp.getY(), this);
            }
        }
    }

    private void drawPills(Graphics g) {
        for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
            Pill p = CoordManager.maze.getPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(CoordManager.checkCollision(pacman,p)){
                if(!p.isDead()){
                    CoordManager.maze.removeAlivePill();
                    this.pacman.addPoints(p.getPoints());
                    if(munch){
                        SoundPlayer.playEffect(MUNCH_1);
                        munch = false;
                    } else {
                        SoundPlayer.playEffect(MUNCH_2);
                        munch = true;
                    }
                }
                p.setDead(true);
            } else if(!p.isDead()){
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }
        }
    }

    private void drawPacman(Graphics g) {
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
        if(pacmanDead){
            if(!this.pacman.isDead()){
                pacmanDead = false;
                this.lives--;
                livesIcons.remove(0);
                if (this.lives == 0) {
                    makeGameOver();
                }else{
                    restartLevel();
                }
            }
        }
    }

    private void drawGhosts(Graphics g) {
        for(Ghost ghost : this.ghosts){
            if(!this.pacman.isDead()) {
                g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), this);
                if (CoordManager.checkCollision(pacman, ghost)) {
                    switch (ghost.getState()) {
                        case CHASE:
                        case SCATTER:
                            // fermare tutti i suoni
                            SoundPlayer.stopAll();
                            SoundPlayer.playEffect(DEATH);
                            // rendiamo fantasmi invisibili
                            // animazione + suono morte
                            this.pacman.setDead(true);
                            this.pacmanDead = true;
                            break;
                        case FRIGHTENED:
                            ghost.becomeEaten();
                            SoundPlayer.playEffect(EAT_GHOST);
                            this.pacman.addPoints(ghost.getPoints() * (2 ^ this.consecutiveGhosts));
                            this.consecutiveGhosts++;
                            break;
                    }
                }
            } else {
                g.drawImage(null, ghost.getX(), ghost.getY(), this);
            }
        }
    }

    public void makeGameOver(){
        levelLabel.setText("");
        this.gameOverLabel.setText("Game Over!");
        this.gameOverLabel.paintImmediately(this.gameOverLabel.getVisibleRect());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restartApplication();
    }

    public void restartApplication() {
        int highScore = this.frame.writeHighScore(this.pacman.getPoints());
        this.highScoreLabel.setText("High Score: "+highScore);
        this.gameEventListener = null;
        this.timer.stop();
        this.pacman.getTimer().stop();
        for(Ghost ghost : this.ghosts) {
            ghost.getTimer().stop();
        }
        this.pacman.setDead(true);
        this.inGame = false;
        System.gc();
        SoundPlayer.stopAll();
        frame.initializeGameMenu();
    }

    public void doOneLoop() {
        this.requestFocus();
        update();
        repaint();
    }

    private void update() {
        if(this.pacmanStart && this.inGame){
            boolean frightened = false;
            boolean eaten = false;
            this.pacman.move();
            for(Ghost ghost : this.ghosts) {
                ghost.move();
                if(ghost.getState() == FRIGHTENED){
                    frightened = true;
                }
                if(ghost.getState() == EATEN){
                    eaten = true;
                }
            }
            if(!this.pacman.isDead()){
                SoundPlayer.playBackgroundMusic(frightened,eaten);
            }
            if(CoordManager.maze.getAlivePills() == 0){
                SoundPlayer.stopAll();
                endGame();
            }
        }else{
            if(System.currentTimeMillis() >= (this.startTime + 1*4000)) { //multiply by 1000 to get milliseconds
                SoundPlayer.removeMusic(DEATH);
                SoundPlayer.removeMusic(GAME_START);
                this.pacmanStart=true;
                readyLabel.setText("");
                levelLabel.setText("Level: "+level);
                for(Ghost ghost : this.ghosts) {
                    ghost.getTimer().start();
                }
                FruitManager.setGameStart();
            }
        }
    }

    private void endGame(){
        System.out.println("fine livello");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.level++;
        int highScore = this.frame.writeHighScore(this.pacman.getPoints());
        this.highScoreLabel.setText("High Score: "+highScore);
        FruitManager.chooseFruit(this.level);
        CoordManager.populateMaze();
        for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
            CoordManager.maze.getPill(i).setDead(false);
        }
        restartLevel();
    }

    public void keyPressed(KeyEvent e) {
        if(inGame){
            this.pacman.keyPressed(e);
            int keyPressed = e.getKeyCode();
            if(keyPressed == VK_ENTER){
                if(timer.isRunning() && this.pacmanStart){
                    pauseGame();
                } else {
                    resumeGame();
                }
            }
        }
    }

    private void pauseGame() {
        SoundPlayer.stopAll();
        timer.stop();
        for(Ghost ghost : this.ghosts) {
            ghost.getTimer().stop();
            ghost.pause();
        }
        frame.showPauseMenu();
    }

    public void resumeGame(){
        timer.start();
        for(Ghost ghost : this.ghosts) {
            ghost.resume();
            ghost.getTimer().start();
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
