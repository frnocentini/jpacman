package ui;

import callbacks.PauseEventListener;
import constants.Constants;
import image.Image;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import static java.awt.event.KeyEvent.VK_ENTER;
import static sound.Sound.*;

public class PausePanel extends JPanel {

    private PauseEventListener pauseEventListener;
    private GameMainFrame gameMainFrame;

    public PausePanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setLayout(null);
        SoundPlayer.playEffect(PAUSE_SOUND);
        this.pauseEventListener = new PauseEventListener(this);
        addKeyListener(this.pauseEventListener);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(ImageFactory.createImage(Image.PAUSE).getImage(), 0,0,null);
        doDrawing();
    }

    private void doDrawing() {
        drawLayout();
        this.requestFocus();
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLayout() {
        JButton resumeButton = new JButton("Resume Game");
        resumeButton.setBackground(Color.YELLOW);
        resumeButton.setForeground(Color.RED);
        resumeButton.setFocusPainted(false);
        resumeButton.setBorder(new LineBorder(Color.RED));
        resumeButton.setFont(new Font("PF Arma Five", Font.BOLD, 20));
        resumeButton.setBounds(90,90,200,60);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playEffect(CREDIT);
                gameMainFrame.resumeGame();
            }
        });
        add(resumeButton);
        JButton muteMusicButton;
        if(SoundPlayer.isMuteMusic()){
            muteMusicButton  = new JButton("<html>Enable<br>Music</html>");
        } else {
            muteMusicButton  = new JButton("<html>Mute<br>Music</html>");
        }
        muteMusicButton.setBackground(Color.YELLOW);
        muteMusicButton.setForeground(Color.RED);
        muteMusicButton.setFocusPainted(false);
        muteMusicButton.setFont(new Font("PF Arma Five", Font.PLAIN, 14));
        muteMusicButton.setBorder(new LineBorder(Color.RED));
        muteMusicButton.setBounds(90,165,90,60);
        muteMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playEffect(CREDIT);
                if(!SoundPlayer.isMuteMusic()){
                    SoundPlayer.stopMusic(STARTUP);
                    SoundPlayer.removeMusic(STARTUP);
                    SoundPlayer.setMuteMusic(true);
                    muteMusicButton.setText("<html>Enable<br>Music</html>");
                } else {
                    SoundPlayer.setMuteMusic(false);
                    SoundPlayer.playMusic(STARTUP);
                    muteMusicButton.setText("<html>Mute<br>Music</html>");
                }
            }
        });
        add(muteMusicButton);
        JButton muteEffectsButton;
        if(SoundPlayer.isMuteEffects()){
            muteEffectsButton  = new JButton("<html>Enable<br>Effects</html>");
        } else {
            muteEffectsButton  = new JButton("<html>Mute<br>Effects</html>");
        }
        muteEffectsButton.setBackground(Color.YELLOW);
        muteEffectsButton.setForeground(Color.RED);
        muteEffectsButton.setFocusPainted(false);
        muteEffectsButton.setBorder(new LineBorder(Color.RED));
        muteEffectsButton.setFont(new Font("PF Arma Five", Font.PLAIN, 14));
        muteEffectsButton.setBounds(200,165,90,60);
        muteEffectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!SoundPlayer.isMuteEffects()){
                    SoundPlayer.stopAll();
                    SoundPlayer.setMuteEffects(true);
                    muteEffectsButton.setText("<html>Enable<br>Effects</html>");
                } else {
                    SoundPlayer.setMuteEffects(false);
                    SoundPlayer.playEffect(CREDIT);
                    muteEffectsButton.setText("<html>Mute<br>Effects</html>");
                }
            }
        });
        add(muteEffectsButton);
        JButton quitButton = new JButton("Quit Game");
        quitButton.setBackground(Color.YELLOW);
        quitButton.setForeground(Color.RED);
        quitButton.setFocusPainted(false);
        quitButton.setBorder(new LineBorder(Color.RED));
        quitButton.setFont(new Font("PF Arma Five", Font.BOLD, 20));
        quitButton.setBounds(90,240,200,60);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playEffect(CREDIT);
                gameMainFrame.quitGame();
            }
        });
        add(quitButton);
    }

    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if(keyPressed == VK_ENTER){
            gameMainFrame.resumeGame();
        }
    }
}
