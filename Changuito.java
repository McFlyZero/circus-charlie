import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Changuito implements Runnable, KeyListener {

    private String url4, url5, url6;
    private int posXMono, posYMono = 264, width = 50, height = 50, key = 1, posXInicial = 600;
    public JLabel mono;
    private ImageIcon icon2;
    private boolean bandera = true, pausar = false, stop = false;

    public Changuito(String url4, String url5, String url6, int posXInicial, int posYMono, int width, int height,
            JLabel mono) {
        this.url4 = url4;
        this.url5 = url5;
        this.url6 = url6;
        this.mono = mono;
    }

    public void run() {
        try {
            while (true) {
                moveChanguito();
                Thread.sleep(60);
                // System.out.println("Posicion X: " + mono.getX());
                posXMono = mono.getX() - 10;
                mono.setBounds(posXMono, posYMono, width, height);
                NewMonkey();

                // Para que el hilo espere cuando se pausa el juego
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
            }

        } catch (Exception e) {
            System.out.println("Error en el hilo de Changuito: " + e.getMessage());
        }

    }

    private void moveChanguito() {
        switch (key) {
            case 1:
                icon2 = new ImageIcon(this.getClass().getResource(url4));
                key = 2;
                break;
            case 2:
                icon2 = new ImageIcon(this.getClass().getResource(url5));
                key = 3;
                break;
            case 3:
                icon2 = new ImageIcon(this.getClass().getResource(url6));
                key = 1;
                break;
            default:
                break;
        }
        mono.setIcon(icon2);
    }

    private void NewMonkey() {
        // Mueve el changuito para atrás
        if (mono.getX() < -50) {
            mono.setBounds(posXInicial, posYMono, width, height);
        }
    }

    // Controles del juego y música
    // Pausar
    synchronized public void pausarHilo() {
        pausar = true;
    }// end pausarHilo

    // Reanudar
    synchronized public void reanudarHilo() {
        pausar = false;
        notify();
    }// end reanudarHilo

    // Detener
    synchronized public void stopHilo() {
        stop = true;
        pausar = false;
        notify();
    }// end stopHilo

    public void keyTyped(KeyEvent e) {
    }// end keyTyped

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (bandera) {
                pausarHilo();
                bandera = false;
            } else {
                reanudarHilo();
                bandera = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            stopHilo();
            bandera = true;
        }
    }// end keyPressed

    public void keyReleased(KeyEvent e) {
    }// end keyReleased

}
