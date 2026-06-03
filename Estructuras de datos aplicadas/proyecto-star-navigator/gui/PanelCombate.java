package gui;

import modelo.Enemigo;
import modelo.Nave;

import javax.swing.*;
import java.awt.*;

/**
 * Panel visual de combate que muestra la nave vs el enemigo con animaciones.
 * 
 * INSTRUCCIONES: Implementa los métodos marcados con TODO.
 * Este panel se muestra cuando el jugador inicia combate.
 * Debe mostrar:
 * - La nave del jugador a la izquierda
 * - El enemigo a la derecha
 * - Animaciones de disparo (un proyectil que viaja de un lado al otro)
 * - Barras de vida actualizándose en tiempo real
 * 
 * El hilo de combate (HiloCombate) llamará a los métodos de este panel
 * para mostrar cada turno visualmente.
 */
public class PanelCombate extends JPanel {

    private int vidaJugador = 100;
    private int vidaMaxJugador = 100;
    private int vidaEnemigo = 50;
    private int vidaMaxEnemigo = 50;
    private String nombreEnemigo = "Enemigo";

    // Animación de proyectil
    private boolean disparoActivo = false;
    private double proyectilX;
    private double proyectilY;
    private boolean disparoDelJugador = true; // true = jugador ataca, false = enemigo ataca

    // [MEJORA UI] TODO: Agregar variables para:
    // - Efecto de sacudida (shake) cuando un lado recibe daño
    // - Explosiones al destruir al enemigo
    // - Parpadeo del sprite cuando recibe daño

    public PanelCombate() {
        setBackground(new Color(5, 5, 20));
        setPreferredSize(new Dimension(700, 200));
    }

    /**
     * Configura un nuevo combate con los datos del enemigo.
     */
    public void iniciarCombate(Nave nave, Enemigo enemigo) {
        this.vidaJugador = nave.getVida();
        this.vidaMaxJugador = nave.getVidaMaxima();
        this.vidaEnemigo = enemigo.getVida();
        this.vidaMaxEnemigo = enemigo.getVida();
        this.nombreEnemigo = enemigo.getNombre();
        this.disparoActivo = false;
        repaint();
    }

    /**
     * Actualiza las barras de vida después de un turno.
     */
    public void actualizarVidas(int vidaJugador, int vidaEnemigo) {
        this.vidaJugador = vidaJugador;
        this.vidaEnemigo = vidaEnemigo;
        repaint();
    }

    /**
     * TODO: Implementar la animación de disparo.
     * 
     * Este método debe:
     * 1. Activar disparoActivo = true
     * 2. Establecer la posición inicial del proyectil:
     *    - Si esDelJugador: empieza en x=150, y=100 (lado izquierdo)
     *    - Si no: empieza en x=550, y=100 (lado derecho)
     * 3. Crear un hilo anónimo (o usar un Timer) que mueva el proyectil
     *    en pasos de 15px cada 30ms hasta llegar al otro lado
     * 4. En cada paso, llamar repaint() para actualizar la visual
     * 5. Al terminar, disparoActivo = false y repaint()
     * 
     * Ejemplo de hilo anónimo para la animación:
     * new Thread(() -> {
     *     // mover proyectil en un loop
     *     // usar SwingUtilities.invokeLater(() -> repaint()) para cada frame
     * }).start();
     */
    public void animarDisparo(boolean esDelJugador) {
        // TODO: Implementar la animación del proyectil moviéndose
        // de un lado al otro del panel
        this.disparoDelJugador = esDelJugador;
        // Placeholder: solo repinta sin animación
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo de estrellas del combate
        g2d.setColor(new Color(100, 100, 200, 40));
        java.util.Random rand = new java.util.Random(123);
        for (int i = 0; i < 30; i++) {
            g2d.fillOval(rand.nextInt(w), rand.nextInt(h), 2, 2);
        }

        // --- Lado izquierdo: Jugador ---
        dibujarNaveJugador(g2d, 80, h / 2);
        dibujarBarraVida(g2d, 30, h - 40, 150, vidaJugador, vidaMaxJugador, 
                         "Tu Nave", new Color(50, 200, 50));

        // --- Lado derecho: Enemigo ---
        dibujarNaveEnemigo(g2d, w - 80, h / 2);
        dibujarBarraVida(g2d, w - 180, h - 40, 150, vidaEnemigo, vidaMaxEnemigo, 
                         nombreEnemigo, new Color(220, 50, 50));

        // --- VS en el centro ---
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2d.drawString("VS", w / 2 - 15, h / 2 + 7);

        // --- Proyectil ---
        if (disparoActivo) {
            // [MEJORA UI] TODO: Mejorar el proyectil.
            // Ideas: estela de energía, color según tipo de arma, tamaño variable
            g2d.setColor(disparoDelJugador ? Color.CYAN : Color.RED);
            g2d.fillOval((int) proyectilX - 4, (int) proyectilY - 4, 8, 8);
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) proyectilX - 2, (int) proyectilY - 2, 4, 4);
        }
    }

    /**
     * TODO [MEJORA UI]: Mejorar el dibujo de la nave del jugador.
     * Actualmente es un triángulo simple. Hazlo parecer una nave espacial real.
     * Ideas:
     * - Usar un polígono más detallado (5-7 puntos)
     * - Agregar color de motor (naranja/rojo en la parte trasera)
     * - Agregar alas, cabina, etc.
     */
    private void dibujarNaveJugador(Graphics2D g2d, int cx, int cy) {
        // Nave simple (triángulo apuntando a la derecha)
        int[] xPoints = {cx + 30, cx - 20, cx - 20};
        int[] yPoints = {cy, cy - 15, cy + 15};
        g2d.setColor(new Color(100, 200, 255));
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(xPoints, yPoints, 3);

        // Motor (fuego)
        g2d.setColor(new Color(255, 150, 0, 150));
        g2d.fillOval(cx - 30, cy - 5, 12, 10);
    }

    /**
     * TODO [MEJORA UI]: Mejorar el dibujo del enemigo.
     * Ideas:
     * - Distintos sprites según el tipo de enemigo (pirata, dron, acorazado)
     * - Usar colores oscuros/rojos para verse amenazante
     * - Agregar efecto de parpadeo rojo cuando recibe daño
     */
    private void dibujarNaveEnemigo(Graphics2D g2d, int cx, int cy) {
        // Enemigo simple (triángulo apuntando a la izquierda)
        int[] xPoints = {cx - 30, cx + 20, cx + 20};
        int[] yPoints = {cy, cy - 15, cy + 15};
        g2d.setColor(new Color(220, 80, 80));
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(new Color(255, 100, 100));
        g2d.drawPolygon(xPoints, yPoints, 3);
    }

    private void dibujarBarraVida(Graphics2D g2d, int x, int y, int ancho,
                                   int vida, int vidaMax, String nombre, Color color) {
        // Fondo de la barra
        g2d.setColor(new Color(40, 40, 60));
        g2d.fillRect(x, y, ancho, 15);

        // Barra de vida
        int anchoVida = (int) ((double) vida / vidaMax * ancho);
        g2d.setColor(color);
        g2d.fillRect(x, y, anchoVida, 15);

        // Borde
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, ancho, 15);

        // Texto
        g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
        g2d.drawString(nombre + " " + vida + "/" + vidaMax, x + 3, y + 12);
    }

    // Setter para la posición del proyectil (usado por la animación)
    public void setProyectilPos(double x, double y) {
        this.proyectilX = x;
        this.proyectilY = y;
        this.disparoActivo = true;
    }

    public void ocultarProyectil() {
        this.disparoActivo = false;
        repaint();
    }
}
