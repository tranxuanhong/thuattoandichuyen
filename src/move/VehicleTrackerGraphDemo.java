/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package move;

/**
 *
 * @author Tran Xuan Hong - N15DCPT032
 */

/*press arrow key to move, press space key to shoot*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/******* game graphics ********/
public class VehicleTrackerGraphDemo {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(10, 10, 400, 400);
        mainFrame.setLayout(new BorderLayout());
        final JPanel paintPanel = new GameBoard();
        mainFrame.add(paintPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}

class GameBoard extends JPanel implements KeyListener {
    private final int HEIGHT = 300;
    private final int WIDTH = 400;
    private final int TANKWIDTH = 30;
    private final int TANKHEIGHT = 10;
    private final int MOVESPEED = 2;
    private final int TARGETX = 100;
    private final int TARGETY = 200;
    private int dx = WIDTH / 2;
    private int dy = HEIGHT;
    private boolean enmeyAlive = true;
    private Rectangle2D yourtank = new Rectangle2D.Double(dx, dy, TANKWIDTH,
            TANKHEIGHT);
    private Rectangle2D laserBeam = new Rectangle2D.Double(-10, -10, 0,
            0);

    public GameBoard() {
        this.addKeyListener(this);
        this.setBackground(Color.white);
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        cleanDead();
        super.paintComponent(grphcs);
        Graphics2D gr = (Graphics2D) grphcs;
        gr.draw(yourtank);
        gr.draw(laserBeam);
        if (enmeyAlive)
            drawTank(grphcs, TARGETX, TARGETY);
    }

    private void cleanDead() {
        if (overlaps(TARGETX, TARGETY, TANKWIDTH, TANKHEIGHT, laserBeam)) {
            enmeyAlive = false;
        }
    }

    private boolean overlaps(int x, int y, int width, int height, Rectangle2D r) {
        return x < r.getX() + r.getWidth() && x + width > r.getX()
                && y < r.getY() + r.getHeight() && y + height > r.getY();
    }
    
    private void drawTank(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.draw3DRect(x, y, TANKWIDTH, TANKHEIGHT, true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());
        shoot();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        moveRec(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        laserBeam.setRect(-10, -10, 0, 0);  //hide it
        repaint();
    }

    public void shoot() {
        laserBeam.setRect(dx + TANKWIDTH/2, 0, 2, dy);
    }

    public void moveRec(KeyEvent evt) {
        switch (evt.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            dx -= MOVESPEED;
            yourtank.setRect(dx, dy, TANKWIDTH, TANKHEIGHT);
            break;
        case KeyEvent.VK_RIGHT:
            dx += MOVESPEED;
            yourtank.setRect(dx, dy, TANKWIDTH, TANKHEIGHT);
            break;
        case KeyEvent.VK_UP:
            dy -= MOVESPEED;
            yourtank.setRect(dx, dy, TANKWIDTH, TANKHEIGHT);
            break;
        case KeyEvent.VK_DOWN:
            if (dy < HEIGHT)
                dy += MOVESPEED;
            yourtank.setRect(dx, dy, TANKWIDTH, TANKHEIGHT);
            break;
        }
    }

}
