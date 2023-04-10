import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Imagen4 extends JLabel implements Runnable, KeyListener {

    private String url1, url2;
    private ImageIcon icon;
    JLabel base;
    private int y = 14, posX = 10;
    private boolean runStatus = false, right = false, up = false, changeImg = false;

    public Imagen4(String url1, String url2) {
        this.url1 = url1;
        this.url2 = url2;
        icon = new ImageIcon(this.getClass().getResource(url1));
        setIcon(icon);
    }

    public void run() {
        runStatus = true;

        while (true) {
            if (interseccion()) {
                gravedad(0,50);
                if (right){ moveImagen(5, 50); }
                if (right && up) {
					saltote(40);
				}
				if (up) {
					saltito(20);
				}
            }else{
                gravedad(5, 10);
            }
        } // end while
    }// end run

    private void moveImagen(int power, int time){
        if (changeImg){
            icon = new ImageIcon(this.getClass().getResource(url1));
            changeImg = false;
        }else{
            icon = new ImageIcon(this.getClass().getResource(url2));
            changeImg = true;
        }
        posX += power;
        setIcon(icon);
        setBounds(posX, y, 42, 42);
        try{ Thread.sleep(time); }catch(Exception ex){}
    }

    private void gravedad(int presion, int time) {
            y += presion;
            setBounds(getX(), y, 42, 42);
            try {
                Thread.sleep(time);
            } catch (Exception ex) {
            }
    }// end gravedad

    public boolean interseccion() {
        Area aBase = new Area(base.getBounds());
        Area aMario = new Area(this.getBounds());

        return aBase.intersects(aMario.getBounds2D());
    }

    private void saltito(int time) {
        //Implementar gravedad
		for (int y = 14; y >= 0; y--) {
			setBounds(getX(), y, 42, 42);
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
		for (int y = getY(); y <= 14; y++) {
			setBounds(getX(), y, 42, 42);
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
	}

	private void saltote(int time) {
		for (int y = 14; y >= 0; y--) {
			setBounds(posX += 5, y, 42, 42);
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
		for (int y = getY(); y <= 14; y++) {
			setBounds(posX += 5, y, 42, 42);
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
	}

    public void keyTyped(KeyEvent e) {
    }// end keyTyped

    public void keyPressed(KeyEvent e) {
        if (runStatus){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) { right = true; }
            if (e.getKeyCode() == KeyEvent.VK_UP) { up = true; }
        }
    }// end keyPressed

    public void keyReleased(KeyEvent e) {
        if (runStatus){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) { right = false; }
            if (e.getKeyCode() == KeyEvent.VK_UP) { up = false; }
        }
    }// end keyReleased
}
