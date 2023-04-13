import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.*;

public class Personaje extends JLabel implements Runnable, KeyListener {

    private String url1, url2, url3;
    private int y = 234, posX = 5, posXBack = 0, points = 0;
    private ImageIcon icon;
    private Sonido sonido;
    private boolean pausar = false, stop = false, runStatus = false, bandera = true, right = false,
            up = false, changeImg = false, shift = false, down = false;
    public JLabel cuerda, mono, background, score;
    public String url4, url5, url6;
    private ArrayList<String> waving_url = new ArrayList<String>();
    private int widthCharlie = 50, heightCharlie = 80;
    public JButton btnStart;

    public Personaje(String walking1, String walking2, String jump, String wav1, String wav2, String wav3, String wav4,
            String wav5, String monkey1, String monkey2, String monkey3) {
        // Urls de movimiento
        this.url1 = walking1;
        this.url2 = walking2;

        // Urls de salto
        this.url3 = jump;

        // Urls monkey
        this.url4 = monkey1;
        this.url5 = monkey2;
        this.url6 = monkey3;

        // Añadir las urls a la lista
        waving_url.add(wav1);
        waving_url.add(wav2);
        waving_url.add(wav3);
        waving_url.add(wav4);
        waving_url.add(wav5);

        icon = new ImageIcon(this.getClass().getResource(url1));
        setIcon(icon);
    }

    public void setSonido(Sonido sonido) {
        this.sonido = sonido;
    }

    public void run() {
        runStatus = true;
        stop = false;

        while (true) {
            if (!interseccionMono()) {
                score();
                winner();
                if (interseccionCuerda()) {
                    // Gravedad
                    gravedad(0, 40);
                    // Derecha y derecha con shift
                    if (right && shift) {
                        moveImage(15, 10);
                    } else if (right) {
                        moveImage(5, 10);
                    }
                    // Salto caminando y salto en vertical
                    if (right && up) {
                        saltote(1, 3, 5);
                    } else if (up) {
                        saltito(5);
                    } else if (down) {
                        idleanimation();
                    }
                } else {
                    gravedad(1, 10);
                } // end if interseccionCuerda
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                }

                try {
                    synchronized (this) {
                        while (pausar) {
                            wait();
                        }
                        if (stop) {
                            break;
                        }
                    } // end synchronized
                } catch (Exception e) {
                }
            } else {
                pausarHilo();
                JOptionPane.showMessageDialog(null, "SOY MALO ( •᷄∩•᷅ ) ");
                stopHilo();
                System.exit(0);
            } // end if
        } // end for
    }// end run

    private void winner() {
        if (posX > 600) {
            pausarHilo();
            JOptionPane.showMessageDialog(null, "EASY PEASY LEMON SQUEEZY (^-^) \n" + score.getText());
            stopHilo();
            System.exit(0);
        }
    }

    private void score() {
        if (posX > mono.getX()) {
            points++;
            score.setText("Score: " + points);
        }
    }

    private void idleanimation() {
        System.out.println("Idle");
        for (String url : waving_url) {
            try {
                System.out.println("Url: " + url);
                Thread.sleep(80);
                icon = new ImageIcon(this.getClass().getResource(url));
                setIcon(icon);
            } catch (Exception e) {
            }
        }
    }// end idleanimation

    // Pausar
    synchronized public void pausarHilo() {
        pausar = true;
        sonido.pause();
    }// end pausarHilo

    // Reanudar
    synchronized public void reanudarHilo() {
        pausar = false;
        notify();
        sonido.resume();
    }// end reanudarHilo

    // Detener
    synchronized public void stopHilo() {
        stop = true;
        pausar = false;
        notify();
        sonido.stopAlto();
        btnStart.setEnabled(true);
    }// end stopHilo

    // Gravedad
    private void gravedad(int presion, int time) {
        y += presion;
        setBounds(getX(), y, widthCharlie, heightCharlie);
        try {
            Thread.sleep(time);
        } catch (Exception ex) {
        }
    }// end gravedad

    public boolean interseccionCuerda() {
        Area aCuerda = new Area(cuerda.getBounds());
        Area aCharlie = new Area(this.getBounds());
        boolean collideCuerda = false;
        if (aCuerda.intersects(aCharlie.getBounds2D())) {
            collideCuerda = true;
        }
        return collideCuerda;
    }// end interseccionCuerda

    public boolean interseccionMono() {
        Area aMono = new Area(mono.getBounds());
        Area aCharlie = new Area(this.getBounds());
        boolean collideMono = false;

        if (aMono.intersects(aCharlie.getBounds2D())) {
            collideMono = true;
        }
        return collideMono;
    }// end interseccionCuerda

    private void saltito(int time) {
        for (y = 234; y >= 170; y--) {
            try {
                synchronized (this) {
                    while (pausar) {
                        wait();
                    }
                    if (stop) {
                        break;
                    }
                } // end synchronized
            } catch (Exception e) {
            }
            moveImage(1, time);
            setBounds(posX, y, widthCharlie, heightCharlie);
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        } // end for UP
        for (y = getY(); y <= 234; y++) {
            try {
                synchronized (this) {
                    while (pausar) {
                        wait();
                    }
                    if (stop) {
                        break;
                    }
                } // end synchronized
            } catch (Exception e) {
            }
            moveImage(0, time);
            setBounds(posX, y, widthCharlie, heightCharlie);
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        } // end for DOWN
        changeImage(time);
    }

    private void saltote(int powerup, int powerdown, int time) {
        for (y = 234; y >= 170; y -= powerup) {
            try {
                synchronized (this) {
                    while (pausar) {
                        wait();
                    }
                    if (stop) {
                        break;
                    }
                } // end synchronized
            } catch (Exception e) {
            }
            // posX+=power;
            moveImage(powerup + 1, time);
            setBounds(posX, y, widthCharlie, heightCharlie);
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        } // end UP
        for (y = getY(); y <= 234; y += powerdown) {
            try {
                synchronized (this) {
                    while (pausar) {
                        wait();
                    }
                    if (stop) {
                        break;
                    }
                } // end synchronized
            } catch (Exception e) {
            }
            // posX+=power;
            moveImage(powerdown, time);
            setBounds(posX, y, widthCharlie, heightCharlie);
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        } // end DOWN
        changeImage(time);
    }// end saltote

    private void changeImage(int time) {
        if (changeImg) {
            icon = new ImageIcon(this.getClass().getResource(url1));
            // icon2 = new ImageIcon(this.getClass().getResource(url4));
            changeImg = false;
        } else {
            icon = new ImageIcon(this.getClass().getResource(url2));
            // icon2 = new ImageIcon(this.getClass().getResource(url5));
            changeImg = true;
        }
        setIcon(icon);

        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }// end changeImage

    private void moveImage(int power, int time) {
        if (posX >= 120 && right) {
            if (posXBack >= -650) {
                // Posicion del fondo
                posXBack -= power;
                background.setBounds(posXBack, 0, 1250, 500);
                cuerda.setBounds(posXBack, 314, 1250, 7);
                setBounds(posX, y, widthCharlie, heightCharlie);
            } else {
                posX += power;
                setBounds(posX, y, widthCharlie, heightCharlie);
            }
        } else if (posX <= 120 && right) {
            posX += power;
            setBounds(posX, y, widthCharlie, heightCharlie);
        }

        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
        if (up || (y <= 234)) {
            icon = new ImageIcon(this.getClass().getResource(url3));
            setIcon(icon);
        } else {
            changeImage(time);
        }
    }// end moveImage

    public void keyTyped(KeyEvent e) {
    }// end keyTyped

    public void keyPressed(KeyEvent e) {
        if (runStatus) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (bandera) {
                    pausarHilo();
                    bandera = false;
                } else {
                    reanudarHilo();
                    bandera = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                shift = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                stopHilo();
                bandera = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = true;
            }
        }
    }// end keyPressed

    public void keyReleased(KeyEvent e) {
        if (runStatus) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                shift = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = false;
            }
        }
    }// end keyReleased

}
