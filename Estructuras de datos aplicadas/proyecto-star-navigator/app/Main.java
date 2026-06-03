package app;

import gui.VentanaPrincipal;
import javax.swing.*;

/**
 * Punto de entrada del juego Star Navigator.
 * 
 * Para compilar y ejecutar:
 *   javac -d out app\Main.java modelo\*.java estructuras\*.java hilos\*.java gui\*.java logica\*.java
 *   java -cp out app.Main
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Usar el default
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
