package gui;

import logica.MotorJuego;
import modelo.*;
import hilos.*;
import estructuras.ArbolBST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Ventana principal del juego Star Navigator.
 * 
 * INSTRUCCIONES: La ventana base funciona. Los TODOs marcados con [MEJORA UI]
 * son mejoras opcionales para hacer que se vea como un juego de verdad.
 * Los TODOs de integración de animación SÍ deben implementarse.
 */
public class VentanaPrincipal extends JFrame {

    private MotorJuego motor;
    private PanelMapa panelMapa;
    private PanelInfo panelInfo;
    private PanelCombate panelCombate;
    private JTextArea txtLog;
    private JComboBox<String> comboDestinos;
    private JButton btnViajar, btnRetroceder, btnCombatir;
    private JButton btnBFS, btnDFS, btnRanking, btnTerminar;
    private Timer timerActualizacion;

    public VentanaPrincipal() {
        String nombre = JOptionPane.showInputDialog(this,
            "Ingresa el nombre de tu nave:", "Star Navigator", JOptionPane.PLAIN_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Explorador";
        }

        motor = new MotorJuego(nombre.trim());
        initUI();
        motor.iniciarHilos();
        iniciarListeners();
        iniciarTimerActualizacion();
        log("Bienvenido, Capitán. Tu nave '" + nombre.trim() + "' está lista.");
        log("Estás en Terra Nova. Explora la galaxia, completa misiones y sobrevive.");
        actualizarUI();
    }

    private void initUI() {
        setTitle("Star Navigator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(15, 15, 35));

        panelMapa = new PanelMapa(motor.getMapaGalactico(), motor.getPlanetas());
        panelMapa.setPlanetaActual(motor.getNave().getPlanetaActual());
        add(panelMapa, BorderLayout.CENTER);

        panelInfo = new PanelInfo();
        add(panelInfo, BorderLayout.EAST);

        JPanel panelControles = crearPanelControles();
        add(panelControles, BorderLayout.SOUTH);

        setSize(1000, 750);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        filaBotones.setBackground(new Color(20, 20, 40));

        comboDestinos = new JComboBox<>();
        comboDestinos.setPreferredSize(new Dimension(150, 25));
        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setForeground(Color.WHITE);
        filaBotones.add(lblDestino);
        filaBotones.add(comboDestinos);

        btnViajar = crearBoton("Viajar", new Color(50, 150, 50));
        btnRetroceder = crearBoton("Retroceder", new Color(200, 150, 50));
        btnCombatir = crearBoton("Combatir", new Color(200, 50, 50));
        btnBFS = crearBoton("Ruta Corta (BFS)", new Color(50, 100, 200));
        btnDFS = crearBoton("Explorar (DFS)", new Color(100, 50, 200));
        btnRanking = crearBoton("Ranking", new Color(200, 200, 50));
        btnTerminar = crearBoton("Terminar Juego", new Color(150, 50, 50));

        filaBotones.add(btnViajar);
        filaBotones.add(btnRetroceder);
        filaBotones.add(btnCombatir);
        filaBotones.add(btnBFS);
        filaBotones.add(btnDFS);
        filaBotones.add(btnRanking);
        filaBotones.add(btnTerminar);

        panel.add(filaBotones, BorderLayout.NORTH);

        txtLog = new JTextArea(5, 60);
        txtLog.setEditable(false);
        txtLog.setBackground(new Color(10, 10, 20));
        txtLog.setForeground(new Color(0, 255, 100));
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 80)),
            "Log de Eventos", 0, 0, null, Color.GRAY));
        panel.add(scrollLog, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btn.setMargin(new Insets(3, 8, 3, 8));
        return btn;
    }

    private void iniciarListeners() {
        btnViajar.addActionListener(e -> accionViajar());
        btnRetroceder.addActionListener(e -> accionRetroceder());
        btnCombatir.addActionListener(e -> accionCombatir());
        btnBFS.addActionListener(e -> accionBFS());
        btnDFS.addActionListener(e -> accionDFS());
        btnRanking.addActionListener(e -> accionRanking());
        btnTerminar.addActionListener(e -> accionTerminar());

        // Listener del hilo de enemigos
        if (motor.getHiloEnemigos() != null) {
            motor.getHiloEnemigos().setListener((oleada, cantidad) -> {
                SwingUtilities.invokeLater(() -> {
                    log("!! Oleada " + oleada + ": " + cantidad + " enemigos detectados!");
                    actualizarUI();
                });
            });
        }

        // Listener del hilo de eventos
        if (motor.getHiloEventos() != null) {
            motor.getHiloEventos().setListener((tipo, descripcion, dato) -> {
                SwingUtilities.invokeLater(() -> {
                    procesarEvento(tipo, descripcion, dato);
                    actualizarUI();
                });
            });
        }

        // Listener del hilo de combate
        if (motor.getHiloCombate() != null) {
            motor.getHiloCombate().setListener(new HiloCombate.CombateListener() {
                @Override
                public void onTurnoCombate(String mensaje) {
                    SwingUtilities.invokeLater(() -> log(mensaje));
                }

                @Override
                public void onCombateTerminado(boolean victoria, int recompensa) {
                    SwingUtilities.invokeLater(() -> {
                        if (victoria) {
                            log("Victoria! Recompensa: " + recompensa + " pts");
                        } else {
                            log("Tu nave ha sido destruida... Fin del juego.");
                            accionTerminar();
                        }
                        actualizarUI();
                    });
                }
            });
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                motor.detenerHilos();
            }
        });
    }

    private void procesarEvento(String tipo, String descripcion, Object dato) {
        switch (tipo) {
            case "asteroide":
                int daño = (int) dato;
                motor.getNave().recibirDaño(daño);
                log("[ASTEROIDE] " + descripcion);
                if (!motor.getNave().estaVivo()) {
                    log("Tu nave fue destruida por asteroides...");
                    accionTerminar();
                }
                break;
            case "cofre":
                ObjetoEspacial objeto = (ObjetoEspacial) dato;
                if (motor.getNave().agregarObjeto(objeto)) {
                    log("[COFRE] " + descripcion + " -> Inventario");
                } else {
                    log("[COFRE] " + descripcion + " -> Inventario lleno!");
                }
                break;
            case "señal":
                int puntos = (int) dato;
                motor.getNave().sumarPuntuacion(puntos);
                log("[SEÑAL] " + descripcion);
                break;
            case "nebulosa":
                int combustible = (int) dato;
                motor.getNave().agregarCombustible(combustible);
                log("[NEBULOSA] " + descripcion);
                break;
        }
    }

    private void accionViajar() {
        String destino = (String) comboDestinos.getSelectedItem();
        if (destino == null) return;
        String resultado = motor.viajarA(destino);
        log(resultado);
        panelMapa.setPlanetaActual(motor.getNave().getPlanetaActual());
        actualizarUI();
    }

    private void accionRetroceder() {
        String resultado = motor.retroceder();
        log(resultado);
        panelMapa.setPlanetaActual(motor.getNave().getPlanetaActual());
        actualizarUI();
    }

    private void accionCombatir() {
        if (motor.getColaEnemigos().estaVacia()) {
            log("No hay enemigos en la cola para combatir.");
            return;
        }
        log("Iniciando combate...");
        motor.combatir();
    }

    private void accionBFS() {
        String destino = (String) comboDestinos.getSelectedItem();
        if (destino == null) return;
        String resultado = motor.calcularRuta(destino);
        log(resultado);
    }

    private void accionDFS() {
        String resultado = motor.explorarDesdeActual();
        log(resultado);
        List<String> explorados = motor.getMapaGalactico().explorarDFS(motor.getNave().getPlanetaActual());
        for (String p : explorados) {
            Planeta planeta = motor.getPlanetas().get(p);
            if (planeta != null) planeta.setExplorado(true);
        }
        panelMapa.repaint();
    }

    private void accionRanking() {
        ArbolBST ranking = motor.getRanking();
        String rankingStr = ranking.obtenerRanking();
        if (rankingStr.isEmpty()) {
            rankingStr = "(Ranking vacío - implementa ArbolBST)";
        }
        JOptionPane.showMessageDialog(this,
            "=== RANKING ===\n" + rankingStr,
            "Ranking de Puntuaciones (BST Inorden)", JOptionPane.INFORMATION_MESSAGE);
    }

    private void accionTerminar() {
        motor.terminarJuego();
        if (timerActualizacion != null) timerActualizacion.stop();
        String ranking = motor.getRanking().obtenerRanking();
        JOptionPane.showMessageDialog(this,
            "Juego terminado!\nPuntuacion final: " + motor.getNave().getPuntuacion() + "\n\n" +
            "=== RANKING FINAL ===\n" + ranking,
            "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        btnViajar.setEnabled(false);
        btnRetroceder.setEnabled(false);
        btnCombatir.setEnabled(false);
    }

    private void actualizarUI() {
        comboDestinos.removeAllItems();
        for (String destino : motor.getDestinosDisponibles()) {
            comboDestinos.addItem(destino);
        }
        panelInfo.actualizar(motor.getNave(), motor.getColaMisiones(),
            motor.getColaEnemigos().tamaño());
        panelMapa.repaint();
    }

    private void iniciarTimerActualizacion() {
        timerActualizacion = new Timer(2000, e -> actualizarUI());
        timerActualizacion.start();
    }

    private void log(String mensaje) {
        txtLog.append("> " + mensaje + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
}
