package hilos;

import modelo.ObjetoEspacial;

/**
 * Hilo que genera eventos aleatorios en el espacio.
 * Eventos posibles: asteroides (daño), cofres (objetos), señales (puntos), nebulosas (combustible).
 * 
 * INSTRUCCIONES: Implementa el método run() marcado con TODO.
 * Este hilo debe:
 * 1. Esperar entre 5 y 12 segundos (aleatorio)
 * 2. Generar un evento aleatorio
 * 3. Notificar al listener con el tipo, descripción y dato
 * 4. Repetir mientras 'activo' sea true
 */
public class HiloEventos extends Thread {

    private volatile boolean activo;
    private EventoListener listener;

    public interface EventoListener {
        void onEvento(String tipo, String descripcion, Object dato);
    }

    public HiloEventos() {
        this.activo = true;
        setDaemon(true);
        setName("Hilo-Eventos");
    }

    public void setListener(EventoListener listener) {
        this.listener = listener;
    }

    /**
     * TODO: Implementar el ciclo del hilo.
     * 
     * Pseudocódigo:
     * mientras activo:
     *     espera = 5000 + random(0-6999) milisegundos
     *     dormir(espera)
     *     si no activo, salir
     *     generarEventoAleatorio()
     * 
     * generarEventoAleatorio():
     *     random = Math.random()
     *     si random < 0.3 → evento "asteroide" con daño 5-19
     *         listener.onEvento("asteroide", "¡Lluvia de asteroides! Daño: X", daño)
     *     si random < 0.6 → evento "cofre" con objeto aleatorio
     *         listener.onEvento("cofre", "Cofre flotante: nombre", objeto)
     *     si random < 0.8 → evento "señal" con 25 puntos
     *         listener.onEvento("señal", "Señal de auxilio. +25 pts", 25)
     *     sino → evento "nebulosa" con combustible 10-19
     *         listener.onEvento("nebulosa", "Nebulosa energética. +X", combustible)
     */
    @Override
    public void run() {
        // TODO: Implementar el ciclo de generación de eventos
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}
