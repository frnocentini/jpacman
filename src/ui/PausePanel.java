package ui;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static sound.Sound.*;

public class PausePanel extends JPanel {

    private GameMainFrame gameMainFrame;

    public PausePanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setLayout(null);
        SoundPlayer.playEffect(PAUSE_SOUND);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(ImageFactory.createImage(Image.PAUSE).getImage(), 0,0,null);
        doDrawing();
    }

    private void doDrawing() {
        drawLayout();
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLayout() {
        JButton resumeButton = new JButton("Resume Game");
        resumeButton.setBackground(Color.YELLOW);
        resumeButton.setForeground(Color.RED);
        resumeButton.setFocusPainted(false);
        resumeButton.setBorder(new LineBorder(Color.RED));
        resumeButton.setFont(new Font("Tahoma", Font.BOLD, 18));
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
            muteMusicButton  = new JButton("Enable Music");
        } else {
            muteMusicButton  = new JButton("Mute Music");
        }
        muteMusicButton.setBackground(Color.YELLOW);
        muteMusicButton.setForeground(Color.RED);
        muteMusicButton.setFocusPainted(false);
        muteMusicButton.setBorder(new LineBorder(Color.RED));
        muteMusicButton.setBounds(90,165,90,60);
        muteMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playEffect(CREDIT);
                if(!SoundPlayer.isMuteMusic()){
                    SoundPlayer.setMuteMusic(true);
                    muteMusicButton.setText("Enable Music");
                } else {
                    SoundPlayer.setMuteMusic(false);
                    muteMusicButton.setText("Mute Music");
                }
            }
        });
        add(muteMusicButton);
        JButton muteEffectsButton;
        if(SoundPlayer.isMuteEffects()){
            muteEffectsButton  = new JButton("Enable Effects");
        } else {
            muteEffectsButton  = new JButton("Mute Effects");
        }
        muteEffectsButton.setBackground(Color.YELLOW);
        muteEffectsButton.setForeground(Color.RED);
        muteEffectsButton.setFocusPainted(false);
        muteEffectsButton.setBorder(new LineBorder(Color.RED));
        muteEffectsButton.setBounds(200,165,90,60);
        muteEffectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!SoundPlayer.isMuteEffects()){
                    SoundPlayer.setMuteEffects(true);
                    muteEffectsButton.setText("Enable Effects");
                } else {
                    SoundPlayer.setMuteEffects(false);
                    SoundPlayer.playEffect(CREDIT);
                    muteEffectsButton.setText("Mute Effects");
                }
            }
        });
        add(muteEffectsButton);
        JButton quitButton = new JButton("Quit Game");
        quitButton.setBackground(Color.YELLOW);
        quitButton.setForeground(Color.RED);
        quitButton.setFocusPainted(false);
        quitButton.setBorder(new LineBorder(Color.RED));
        quitButton.setFont(new Font("Tahoma", Font.BOLD, 18));
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
}
