/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.GameContext;

/**
 *
 * @author WRO00541
 */
class MainFrame extends JFrame {

    public MainFrame(GameContext context) {
        super("Antos Game");
        setSize(AntosProperties.APP_WIDTH, AntosProperties.APP_HEIGHT);
        setLocation(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - AntosProperties.APP_WIDTH) / 2,
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - AntosProperties.APP_HEIGHT) / 2);
        setResizable(false);
        setUndecorated(true);
        getContentPane().setLayout(null);
        GamePanel gamePanel = new GamePanel(context);
        getContentPane().add(gamePanel);
        // getContentPane().add(new MenuPanel(ctx));
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(gamePanel);
        setVisible(true);
        // BufferStrategy bufferStrategy = getBufferStrategy();
        // System.out.println("Buffer strategy " + bufferStrategy.getCapabilities().isPageFlipping());
    }

//    @Override
//    public void paint(Graphics g) {
//        // Paint this component and all subcomponents with buffer strategy graphic
//        g = getBufferStrategy().getDrawGraphics();
//        super.paint(g); //To change body of generated methods, choose Tools | Templates.
//        g.dispose();
//        getBufferStrategy().show();
//        Toolkit.getDefaultToolkit().sync();
//    }
}
