package ui;

import logic.MenuLogic;
import image.ImageList;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static sound.Sound.*;

public class MenuPanel extends JPanel {

    private GameMainFrame gameMainFrame; // Riferimento al nostro JFrame
    private MenuLogic logic; // Riferimento al controller relaivo a questo pannello

    public MenuPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setLayout(null);
        SoundPlayer.playMusic(STARTUP);
        this.logic = new MenuLogic();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Disegnamo l'immagine del menù
        g.drawImage(ImageFactory.createImage(ImageList.MENU).getImage(), 0,0,null);
        doDrawing();
    }

    public void doDrawing() {
        // Creiamo i bottoni
        drawLayout();
        // Metodo che sincronizza tutte le componenti grafiche
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawLayout() {
        // Popoliamo l'Array di Stringhe
        String[] mazeStrings = logic.populateMazeStrings();
        // Creiamo con questo array la ComboBox
        JComboBox mazeList = new JComboBox(mazeStrings);
        mazeList.setSelectedIndex(0);
        mazeList.setBorder(new LineBorder(Color.RED));
        mazeList.setBackground(Color.YELLOW);
        mazeList.setBounds(240,190,50,40);
        add(mazeList);
        JLabel mazesLabel = new JLabel("Maze");
        mazesLabel.setBounds(240,155,50,40);
        mazesLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        mazesLabel.setForeground(Color.RED);
        add(mazesLabel);
        // Creiamo il bottone per avviare il gioco
        JButton startButton = new JButton("Start Game");
        startButton.setBackground(Color.YELLOW);
        startButton.setForeground(Color.RED);
        startButton.setFocusPainted(false);
        startButton.setBorder(new LineBorder(Color.RED));
        startButton.setFont(new Font("PF Arma Five", Font.BOLD, 20));
        startButton.setBounds(90,170,140,60);
        // Aggiungiamo le azioni da eseguire alla sua pressione
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ferma la musica, fa sentire credit e richiama il metodo per creare la schermata di gioco
                SoundPlayer.stopMusic(STARTUP);
                SoundPlayer.playEffect(CREDIT);
                //
                gameMainFrame.initializeLayout(mazeList.getSelectedIndex()+1);
            }
        });
        add(startButton);
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
        muteMusicButton.setBounds(90,245,90,60);
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
        muteEffectsButton.setBounds(200,245,90,60);
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
    }
}
