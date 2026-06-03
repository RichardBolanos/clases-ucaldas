package gui;

import estructuras.Grafo;
import modelo.Planeta;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Panel que dibuja el mapa galáctico (grafo) y la nave del jugador.
 * 
 * INSTRUCCIONES PARA MEJORAR:
 * Este panel funciona con el mínimo visual. Tu trabajo es mejorarlo para
 * que parezca un juego de verdad. Busca los TODO marcados como [MEJORA UI].
 * 
 * Ideas de mejora:
 * - Dibujar la nave como un triángulo/cohete en lugar de un círculo amarillo
 * - Agregar animación de partículas al viajar (estela de la nave)
 * - Hacer que las estrellas de fondo parpadeen
 * - Dibujar las rutas con líneas punteadas o con efecto de energía
 * - Agregar un efecto de "pulso" al planeta actual
 * - Mostrar la ruta BFS resaltada cuando se calcula
 * - Agregar asteroides flotantes como decoración animada
 */
public class PanelMapa extends JPanel {

    private Grafo grafo;
    private Map<String, Planeta> planetas;
    private String planetaActual;

    // Posición visual de la nave (para animación)
    private double naveVisualX = -1;
    private double naveVisualY = -1;
    private boolean mostrarNaveAnimada = false;

    public PanelMapa(Grafo grafo, Map<String, Planeta> planetas) {
        this.grafo = grafo;
        this.planetas = planetas;
        setBackground(new Color(10, 10, 30));
        setPreferredSize(new Dimension(700, 600));
    }

    public void setPlanetaActual(String planeta) {
        this.planetaActual = planeta;
        repaint();
    }

    /**
     * Actualiza la posición visual de la nave durante la animación.
     * Este método es llamado por el HiloAnimacion en cada frame.
     */
    public void setPosicionNave(double x, double y) {
        this.naveVisualX = x;
        this.naveVisualY = y;
        this.mostrarNaveAnimada = true;
        repaint();
    }

    public void ocultarNaveAnimada() {
        this.mostrarNaveAnimada = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarEstrellas(g2d);
        dibujarRutas(g2d);
        dibujarPlanetas(g2d);
        dibujarNave(g2d);
    }

    private void dibujarEstrellas(Graphics2D g2d) {
        // [MEJORA UI] TODO: Hacer que las estrellas parpadeen.
        // Idea: usar un Timer o un contador de frames para variar la opacidad
        // de algunas estrellas aleatoriamente.
        g2d.setColor(new Color(200, 200, 255, 80));
        java.util.Random rand = new java.util.Random(42);
        for (int i = 0; i < 80; i++) {
            int x = rand.nextInt(Math.max(1, getWidth()));
            int y = rand.nextInt(Math.max(1, getHeight()));
            int size = rand.nextInt(3) + 1;
            g2d.fillOval(x, y, size, size);
        }
    }

    private void dibujarRutas(Graphics2D g2d) {
        // [MEJORA UI] TODO: Mejorar las rutas.
        // Ideas:
        // - Dibujar con línea punteada (setStroke con DashPattern)
        // - Usar color diferente para rutas ya recorridas vs no exploradas
        // - Agregar efecto de "energía" (gradiente o brillo)
        g2d.setColor(new Color(80, 80, 120));
        g2d.setStroke(new BasicStroke(1.5f));
        for (String nodo : grafo.obtenerNodos()) {
            Planeta p1 = planetas.get(nodo);
            if (p1 == null) continue;
            for (String vecino : grafo.vecinos(nodo)) {
                Planeta p2 = planetas.get(vecino);
                if (p2 == null) continue;
                if (nodo.compareTo(vecino) < 0) {
                    g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                }
            }
        }
    }

    private void dibujarPlanetas(Graphics2D g2d) {
        for (Map.Entry<String, Planeta> entry : planetas.entrySet()) {
            Planeta p = entry.getValue();
            int radio = 18;
            int x = p.getX() - radio;
            int y = p.getY() - radio;

            // Color según tipo
            Color color;
            switch (p.getTipo()) {
                case "comercial": color = new Color(50, 200, 50); break;
                case "hostil": color = new Color(220, 50, 50); break;
                case "recursos": color = new Color(50, 150, 220); break;
                case "misterioso": color = new Color(180, 50, 220); break;
                default: color = Color.GRAY; break;
            }

            // [MEJORA UI] TODO: Agregar efecto de "pulso" al planeta actual.
            // Idea: dibujar un anillo que crece y se desvanece usando un contador
            // que incrementa con cada repaint.

            // Resaltar planeta actual (si la nave no está animándose)
            if (p.getNombre().equals(planetaActual) && !mostrarNaveAnimada) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawOval(x - 4, y - 4, radio * 2 + 8, radio * 2 + 8);
            }

            // Dibujar planeta
            g2d.setColor(color);
            g2d.fillOval(x, y, radio * 2, radio * 2);
            g2d.setColor(color.brighter());
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(x, y, radio * 2, radio * 2);

            // Nombre
            if (p.isExplorado()) {
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(p.getNombre());
                g2d.drawString(p.getNombre(), p.getX() - textWidth / 2, p.getY() + radio + 15);
            } else {
                g2d.setColor(new Color(150, 150, 150));
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
                g2d.drawString("???", p.getX() - 10, p.getY() + radio + 15);
            }
        }
    }

    private void dibujarNave(Graphics2D g2d) {
        if (mostrarNaveAnimada && naveVisualX >= 0) {
            // La nave se está moviendo — dibujarla en su posición interpolada
            // [MEJORA UI] TODO: Dibujar la nave como un triángulo/cohete apuntando
            // hacia la dirección de movimiento en lugar de un simple círculo.
            // Ideas:
            // - Calcular el ángulo: Math.atan2(destinoY - origenY, destinoX - origenX)
            // - Rotar el Graphics2D y dibujar un polígono triangular
            // - Agregar una "estela" de partículas detrás de la nave

            int nx = (int) naveVisualX;
            int ny = (int) naveVisualY;

            // Estela simple (círculos que se desvanecen)
            // [MEJORA UI] TODO: Mejorar la estela con más partículas y colores
            g2d.setColor(new Color(255, 200, 0, 60));
            g2d.fillOval(nx - 8, ny - 8, 16, 16);
            g2d.setColor(new Color(255, 200, 0, 30));
            g2d.fillOval(nx - 12, ny - 12, 24, 24);

            // Nave (círculo amarillo básico)
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(nx - 6, ny - 6, 12, 12);
            g2d.setColor(Color.WHITE);
            g2d.drawOval(nx - 6, ny - 6, 12, 12);
        } else if (planetaActual != null) {
            // La nave está estacionada en un planeta
            Planeta p = planetas.get(planetaActual);
            if (p != null) {
                int nx = p.getX();
                int ny = p.getY() - 25; // arriba del planeta

                // [MEJORA UI] TODO: Mejorar el sprite de la nave estacionada
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(nx - 5, ny - 5, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Monospaced", Font.BOLD, 8));
                g2d.drawString("▲", nx - 4, ny - 6);
            }
        }
    }
}
