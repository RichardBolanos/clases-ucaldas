package gui;

import modelo.Nave;
import modelo.Mision;
import estructuras.Cola;

import javax.swing.*;
import java.awt.*;

/**
 * Panel lateral que muestra información de la nave y el estado del juego.
 * (Archivo completo - no requiere modificaciones)
 */
public class PanelInfo extends JPanel {

    private JLabel lblNombre;
    private JProgressBar barraVida;
    private JLabel lblCombustible;
    private JLabel lblAtaque;
    private JLabel lblEscudo;
    private JLabel lblPuntuacion;
    private JLabel lblPlaneta;
    private JTextArea txtInventario;
    private JLabel lblMision;
    private JLabel lblEnemigos;

    public PanelInfo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(20, 20, 40));
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        lblNombre = crearLabel("Nave: ---", 14, Color.CYAN);
        add(lblNombre);
        add(Box.createVerticalStrut(10));

        lblPlaneta = crearLabel("Planeta: ---", 12, Color.WHITE);
        add(lblPlaneta);
        add(Box.createVerticalStrut(10));

        add(crearLabel("HP:", 11, Color.GREEN));
        barraVida = new JProgressBar(0, 100);
        barraVida.setValue(100);
        barraVida.setStringPainted(true);
        barraVida.setForeground(new Color(50, 200, 50));
        barraVida.setBackground(new Color(40, 40, 60));
        barraVida.setMaximumSize(new Dimension(230, 20));
        add(barraVida);
        add(Box.createVerticalStrut(8));

        lblCombustible = crearLabel("Combustible: 50", 11, new Color(255, 200, 50));
        add(lblCombustible);
        lblAtaque = crearLabel("Ataque: 20", 11, new Color(255, 100, 100));
        add(lblAtaque);
        lblEscudo = crearLabel("Escudo: 5", 11, new Color(100, 150, 255));
        add(lblEscudo);
        lblPuntuacion = crearLabel("Puntuación: 0", 12, Color.YELLOW);
        add(lblPuntuacion);
        add(Box.createVerticalStrut(15));

        add(crearLabel("── Inventario ──", 11, Color.GRAY));
        txtInventario = new JTextArea(6, 20);
        txtInventario.setEditable(false);
        txtInventario.setBackground(new Color(30, 30, 50));
        txtInventario.setForeground(Color.WHITE);
        txtInventario.setFont(new Font("Monospaced", Font.PLAIN, 10));
        JScrollPane scrollInv = new JScrollPane(txtInventario);
        scrollInv.setMaximumSize(new Dimension(230, 100));
        add(scrollInv);
        add(Box.createVerticalStrut(10));

        add(crearLabel("── Misión Actual ──", 11, Color.GRAY));
        lblMision = crearLabel("Sin misión", 10, new Color(200, 200, 100));
        add(lblMision);
        add(Box.createVerticalStrut(10));

        lblEnemigos = crearLabel("Enemigos en cola: 0", 10, new Color(255, 100, 100));
        add(lblEnemigos);
    }

    private JLabel crearLabel(String texto, int size, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Monospaced", Font.BOLD, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    public void actualizar(Nave nave, Cola<Mision> misiones, int enemigosEnCola) {
        lblNombre.setText("Nave: " + nave.getNombre());
        lblPlaneta.setText("Planeta: " + nave.getPlanetaActual());

        barraVida.setMaximum(nave.getVidaMaxima());
        barraVida.setValue(nave.getVida());
        barraVida.setString(nave.getVida() + "/" + nave.getVidaMaxima());

        if (nave.getVida() > 60) {
            barraVida.setForeground(new Color(50, 200, 50));
        } else if (nave.getVida() > 30) {
            barraVida.setForeground(new Color(255, 200, 0));
        } else {
            barraVida.setForeground(new Color(220, 50, 50));
        }

        lblCombustible.setText("Combustible: " + nave.getCombustible());
        lblAtaque.setText("Ataque: " + nave.getAtaque());
        lblEscudo.setText("Escudo: " + nave.getEscudo());
        lblPuntuacion.setText("Puntuación: " + nave.getPuntuacion());

        StringBuilder inv = new StringBuilder();
        String[] items = nave.getInventario().toArray();
        if (items.length == 0) {
            inv.append("(vacío)");
        } else {
            for (String item : items) {
                inv.append("• ").append(item).append("\n");
            }
        }
        txtInventario.setText(inv.toString());

        if (!misiones.estaVacia()) {
            lblMision.setText(misiones.peek().toString());
        } else {
            lblMision.setText("¡Todas completadas!");
        }

        lblEnemigos.setText("Enemigos en cola: " + enemigosEnCola);
    }
}
