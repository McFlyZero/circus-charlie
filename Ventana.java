import javax.swing.*;
import java.awt.event.*;

class Ventana extends JFrame {

    Sonido sonido;

    public Ventana() {
        initValues();
    }

    private void initValues() {
        // Las imagenes del monito
        Personaje charlie = new Personaje("images/charliewalk1.png", "images/charliewalk2.png", "images/jump.png",
                "images/waving1.png", "images/waving2.png", "images/waving3.png", "images/waving4.png",
                "images/waving5.png",
                "images/monkey1.png", "images/monkey2.png", "images/monkey3.png");

        // Fondo como JLabel
        JLabel bg = new JLabel();
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/background.png"));
        bg.setIcon(icon);
        charlie.background = bg;

        // Button Area Background
        JLabel btnArea = new JLabel();
        ImageIcon btnAreaIcon = new ImageIcon(this.getClass().getResource("images/buttonarea.png"));
        btnArea.setIcon(btnAreaIcon);

        // Cuerda como JLabel
        JLabel cuerda = new JLabel();
        ImageIcon cuerdaIcon = new ImageIcon(this.getClass().getResource("images/cuerda.png"));
        cuerda.setIcon(cuerdaIcon);
        charlie.cuerda = cuerda;

        // Mono como JLabel
        JLabel mono = new JLabel();
        ImageIcon monoIcon = new ImageIcon(this.getClass().getResource("images/monkey1.png"));
        mono.setIcon(monoIcon);
        charlie.mono = mono;

        Changuito changuito = new Changuito("images/monkey1.png", "images/monkey2.png", "images/monkey3.png",
         600, 264,50, 50, mono);


        // Score label
        JLabel score = new JLabel("Score: 0");
        charlie.score = score;

        // Definimos los botones
        JButton btnStart = new JButton("Start");
        charlie.btnStart = btnStart;

        // Focusable
        charlie.setFocusable(true);
        btnStart.setFocusable(false);
        bg.setFocusable(false);
        btnArea.setFocusable(false);

        // Charlie
        charlie.setBounds(10, 233, 50, 80);

        // Fondo
        bg.setBounds(0, 0, 1250, 500);

        // Fondo botones
        btnArea.setBounds(0, 500, 600, 100);

        // Boton Start
        btnStart.setBounds(10, 520, 75, 25);

        // Score
        score.setBounds(100, 520, 100, 25);
        score.setFont(new java.awt.Font("Arial", 1, 20));

        // Cuerda
        cuerda.setBounds(0, 314, 1250, 7);

        // Mono
        mono.setBounds(600, 264, 50, 50);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == btnStart) {
                    // Cancioncita
                    sonido = new Sonido("sounds/Circus-Charlie-8Bit.wav"); // Formato de 8 bits
                    Thread t = new Thread(charlie);
                    t.start();
                    charlie.setSonido(sonido);
                    sonido.play();
                    btnStart.setEnabled(false);

                    // Thread de changuitos
                    Thread t2 = new Thread(changuito);
                    t2.start();
                }
            } // end actionPerformed
        };

        btnStart.addActionListener(listener);
        // KeyListener para Charlie
        charlie.addKeyListener(charlie);
        // KeyListener para el mono
        charlie.addKeyListener(changuito);

        add(charlie);
        add(mono);
        add(btnStart);
        add(score);
        add(cuerda);
        add(btnArea);
        add(bg);
        System.out.println(this);

        setTitle("Circus Charlie 2023");
        setSize(600, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}