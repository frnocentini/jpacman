package logic;

import loops.GameLoop;
import constants.Constants;
import sound.SoundPlayer;
import sprites.*;
import structure.Maze;
import structure.MazeManager;
import ui.GameMainFrame;
import ui.GamePanel;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_ENTER;
import static sound.Sound.*;
import static sprites.State.EATEN;
import static sprites.State.FRIGHTENED;

public class GameLogic {

    private GameMainFrame frame;                    // Riferimento al nostro JFrame
    private GamePanel gamePanel;                    // Riferimento al nostro JPanel
    private Pacman pacman;                          // Oggetto che rappresenta Pacman
    private Maze maze;
    private ArrayList<Ghost> ghosts;                // ArrayList che contiene i fantasmi
    private boolean inGame;                         // Comunica ad alcuni controlli se in gioco è attivo o meno
    private boolean munch;                          // Gestisce i suoni alternati "waka-waka"
    private boolean pacmanDead;                     // Comunica se Pacman era morto nel ciclo prcedente
                                                    // Tornerà false una volta che Pacman tornerà al suo Spawn Point
    private boolean pacmanStart;                    // Comunica che la partita si è avviata (passati 4 sec.)
    private Timer timer;                            // Timer che scandisce i cicli update-repaint
    private long startTime;                         // Orario in cui è inizata la partita (4 sec. inclusi)
    private long portalTime;                        // Orario dell'ultima volta che si è attivato un portale
    private int level;                              // Livello del gioco
    private int lifeCounter;                        // Prossimo decamigliaio in cui dovrà scattare una vita extra
    private int consecutiveGhosts;                  // Conta quanti fantasmi si sono mangiati dopo una singola PowerPill
    private String readyString;                     // Stringhe che riportano i valori da stampare
    private String gameOverString;                  // nelle label del GamePanel
    private String levelString;
    private String highScoreString;
    private String livesNumString;
    private long lastPillTime;                      // Valore che ci permette di implementare un piccolo ritardo
                                                    // a fine partita

    public GameLogic(GameMainFrame frame, GamePanel gamePanel, int level, int highScore, int lives){
        this.frame = frame;
        this.gamePanel = gamePanel;
        initializeVariables(level,highScore,lives);
    }

    public void initializeVariables(int level, int highScore, int lives) {
        // Richiamiamo il metodo di MazeManager per popolare il labirinto di sprite statici (pillole, portali, frutta)
        this.maze = MazeManager.populateMaze();
        this.lifeCounter = 1;
        this.level = level;
        this.pacman = new Pacman();
        this.pacman.setLives(lives);
        // System.out.println(level);
        this.inGame = true;
        this.pacmanStart = false;
        // Oggetto che farà chiamare doOneLoop() ogni tot millisecondi
        this.timer = new Timer(Constants.GAME_SPEED,new GameLoop(this));
        this.timer.start();
        this.munch = true;
        SoundPlayer.stopAll();
        SoundPlayer.playMusic(GAME_START);
        this.startTime = this.portalTime = System.currentTimeMillis();
        this.maze.setGameStart();
        this.ghosts = new ArrayList<>();
        Pinky pinky = new Pinky(this.pacman);
        Blinky blinky = new Blinky(this.pacman);
        Inky inky = new Inky(this.pacman,blinky);
        Clyde clyde = new Clyde(this.pacman);
        this.ghosts.add(clyde);
        this.ghosts.add(inky);
        this.ghosts.add(pinky);
        this.ghosts.add(blinky);
        this.levelString = new String("");
        this.highScoreString = new String("High Score: "+highScore);
        this.readyString = new String("Ready!");
        this.gameOverString = new String("");
        this.livesNumString = new String("");
        // Sceglie che frutto mostrare in questo livello
        this.maze.chooseFruit(level);
        this.lastPillTime = 0;
    }

    public void restartLevel(){
        this.startTime = System.currentTimeMillis();
        this.portalTime = System.currentTimeMillis();
        this.pacmanStart = false;
        SoundPlayer.stopAll();
        levelString = "";
        readyString= "Ready!";
        //System.out.println(level);
        this.inGame = true;
        this.munch = true;
        this.maze.setGameStart();
        this.pacman.returnToSpawnPoint();
        for(Ghost ghost : this.ghosts) {
            ghost.returnToSpawnPoint();
            ghost.setPausedTime(0);
        }
        System.gc();
        timer.start();
        this.lastPillTime = 0;
    }

    // Viene chimato periodicamente dal Timer con GameLoop
    public void doOneLoop() {
        // Richiede il focus della tastiera su GamePanel
        this.gamePanel.requestFocus();
        update();
        if(inGame){
            this.gamePanel.repaint();
        }else{
            if(timer.isRunning()){
                timer.stop();
            }
        }
    }

    public void update() {
        // Finché non sono passati dall'inizio 4 secondi non entreremo
        if(this.pacmanStart && this.inGame){
            boolean frightened = false;
            boolean eaten = false;
            // Pacman e i fantasmi vengono fatti muovere e cambiare frame di animazione
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
            // Ad ogni decamigliaio di punti Pacman ottiene una vita extra
            getExtraLife();
            // Metodo che controlla tutte le collisioni tra gli sprite
            checkCollision();
            killPacman();
            if(!pacmanDead && this.pacmanStart){
                this.playBackgroundMusic(frightened,eaten);
            }
            if(this.maze.getAlivePills() == 0){
                // Se è rimasto col valore con cui è stato inizializzato
                // lo usiamo per registrare l'istante
                // Dopo 50 millisecondi il gioco può finire
                if(lastPillTime == 0){
                    lastPillTime = System.currentTimeMillis();
                }else if(System.currentTimeMillis() > lastPillTime + 50){
                    SoundPlayer.stopAll();
                    endGame();
                }
            }
        }else if(System.currentTimeMillis() >= (this.startTime + 4*1000)) {
            // Se sono passati 4 secondi, il gioco inizia
            SoundPlayer.removeMusic(DEATH);
            SoundPlayer.removeMusic(GAME_START);
            this.pacmanStart=true;
            readyString = "";
            levelString = "Level: "+level;
            // Viene avviato il timer dei fantasmi e della frutta nel maze
            for(Ghost ghost : this.ghosts) {
                ghost.getTimer().start();
            }
            this.maze.setGameStart();
        }
    }

    public void checkCollision(){
        collisionFruit();
        collisionPortals();
        collisionPowerPills();
        collisionPills();
        collisionGhosts();
    }

    public void collisionFruit() {
        // Se l'oggetto restituito non è null controlla se esiste una collisione con Pacman
        Fruit f = this.maze.getFruit();
        if(f != null){
            if(f.checkCollision(pacman)){
                f.setDead(true);
                this.pacman.addPoints(f.getPoints());
                SoundPlayer.playEffect(EAT_FRUIT);
            }
        }
    }

    public void getExtraLife(){
        if(this.pacman.getPoints() >= 10000 * this.lifeCounter){
            this.pacman.increaseLives();
            this.lifeCounter++;
        }
    }

    public void collisionPortals() {
        // Se un fantasma o Pacman collidono con uno dei portali vengono trasportati all'altro
        Portal bluePortal = this.maze.getBluePortal();
        Portal redPortal = this.maze.getRedPortal();
        // Implementiamo un piccolo ritardo per consentire di uscire dal secondo portale
        if(System.currentTimeMillis() >= (this.portalTime + 400) && !pacmanDead){
            if(bluePortal.checkCollision(pacman)){
                teleport(pacman,bluePortal);
            }else if(redPortal.checkCollision(pacman)){
                teleport(pacman,redPortal);
            }else{
                for(Ghost ghost : this.ghosts) {
                    if (bluePortal.checkCollision(ghost)) {
                        teleport(ghost, bluePortal);
                    } else if (redPortal.checkCollision(ghost)) {
                        teleport(ghost, redPortal);
                    }
                }
            }
        }
    }

    public void teleport(Sprite a, Portal p){
        // Trasportiamo lo sprite che collide con il portale alle cordinate dell'altro
        a.setX(p.getOther().getX());
        a.setY(p.getOther().getY());
        if(p.getColor().equals("BLUE")){
            SoundPlayer.playEffect(BLUE_PORTAL_SOUND);
        }else{
            SoundPlayer.playEffect(RED_PORTAL_SOUND);
        }
        this.portalTime = System.currentTimeMillis();
    }

    public void collisionPowerPills() {
        // Se Pacman collide con una powerpill essa sprisce con setDead(true) e i fantasmi passano allo stato di
        // frightened a meno che non siano nello stato di eaten
        for(int i = 0; i< this.maze.getPowerPillsNum(); i++){
            PowerPill pp = this.maze.getPowerPill(i);
            // Rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(!pp.isDead() && pp.checkCollision(pacman)){
                this.maze.removeAlivePowerPill();
                this.pacman.addPoints(pp.getPoints());
                this.consecutiveGhosts = 0;
                for(Ghost ghost : this.ghosts) {
                    if (ghost.getState() != EATEN) {
                        ghost.becomeFrightened();
                        //System.out.println("Passo a frightened");
                    }
                }
                pp.setDead(true);
            }
        }
    }

    public void collisionPills() {
        // Se Pacman collide con una pill essa sprisce e viene riprodotto uno dei due suoni suoni MUNCH a seconda della
        // booleana munch
        for(int i = 0; i< this.maze.getPillsNum(); i++){
            Pill p = this.maze.getPill(i);
            // Rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(!p.isDead() && p.checkCollision(pacman)){
                this.maze.removeAlivePill();
                this.pacman.addPoints(p.getPoints());
                if(munch){
                    SoundPlayer.playEffect(MUNCH_1);
                    munch = false;
                } else {
                    SoundPlayer.playEffect(MUNCH_2);
                    munch = true;
                }
                p.setDead(true);
            }
        }
    }

    public void killPacman() {
        // Se Pacman era morto nell'ultima rilevazione (lo scorso ciclo)
        if(pacmanDead){
            // Se ora Pacman ha finito l'animazione di morte ed è tornato vivo...
            if(!this.pacman.isDead()){
                // Scaliamo una vita
                this.pacman.decreaseLives();
                // Se le vite sono a 0 Game Over, altrimenti riparte il livello
                if (this.pacman.getLives() == 0) {
                    makeGameOver();
                }else{
                    pacmanDead = false;
                    restartLevel();
                }
            }
        }
    }

    public void collisionGhosts() {
        for(Ghost ghost : this.ghosts){
            // Controllo che, in caso sia morto, è tornato allo spawn
            if(!pacmanDead && ghost.checkCollision(pacman)) {
                switch (ghost.getState()) {
                    // Se è Chase / Scatter allora Pacman inizia l'animazione di morte
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
                    // Se è Frightened allora diventa Eaten
                    case FRIGHTENED:
                        ghost.becomeEaten();
                        SoundPlayer.playEffect(EAT_GHOST);
                        this.pacman.addPoints(ghost.getPoints() * (2 ^ this.consecutiveGhosts));
                        this.consecutiveGhosts++;
                        break;
                }
            }
        }
    }

    public void makeGameOver(){
        levelString = "";
        gameOverString = "Game Over!";
        this.gamePanel.showGameOver();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restartApplication();
    }

    public void restartApplication() {
        this.frame.writeHighScore(this.pacman.getPoints());
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

    // Pacman ha vinto il livello
    public void endGame(){
        //System.out.println("fine livello");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.level++;
        for(Ghost ghost : this.ghosts) {
            ghost.resetGhostLoop(this.level);
        }
        // Scrivo il punteggio massimo
        this.frame.writeHighScore(this.pacman.getPoints());
        // Ripopolo il labirinto
        this.maze = MazeManager.populateMaze();
        this.maze.chooseFruit(this.level);
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

    public void pauseGame() {
        SoundPlayer.stopAll();
        timer.stop();
        for(Ghost ghost : this.ghosts) {
            ghost.getTimer().stop();
            ghost.pause();
        }
        maze.pause();
        frame.showPauseMenu();
    }

    public void resumeGame(){
        timer.start();
        for(Ghost ghost : this.ghosts) {
            ghost.resume();
            ghost.getTimer().start();
        }
        maze.resume();
    }

    public void playBackgroundMusic(boolean frightened, boolean eaten) {
        // A seconda delle condizioni dei fantasmi o delle pillole viene riprodotto un loop
        if(eaten){
            SoundPlayer.loopEffect(EATEN_SOUND);
        } else if (frightened){
            SoundPlayer.loopEffect(FRIGHT_SOUND);
        }else if(this.maze.getAlivePills() > this.maze.getPillsNum() * 4/5){
            SoundPlayer.loopEffect(SIREN_1);
        } else if (this.maze.getAlivePills() > this.maze.getPillsNum() * 3/5) {
            SoundPlayer.loopEffect(SIREN_2);
        } else if (this.maze.getAlivePills() > this.maze.getPillsNum() * 2/5) {
            SoundPlayer.loopEffect(SIREN_3);
        } else if (this.maze.getAlivePills() > this.maze.getPillsNum() / 5) {
            SoundPlayer.loopEffect(SIREN_4);
        } else if (this.maze.getAlivePills() > 0) {
            SoundPlayer.loopEffect(SIREN_5);
        } else if (this.maze.getAlivePills() == 0){
            SoundPlayer.stopAll();
        }
    }

    public String getReadyString() {
        return readyString;
    }

    public String getGameOverString() {
        return gameOverString;
    }

    public String getLevelString() {
        return levelString;
    }

    public String getHighScoreString() {
        return highScoreString;
    }

    public String getLivesNumString() {
        return livesNumString;
    }

    public int getLives() {
        return this.pacman.getLives();
    }

    public int getPoints() {
        return this.pacman.getPoints();
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public Maze getMaze() {
        return maze;
    }
}
