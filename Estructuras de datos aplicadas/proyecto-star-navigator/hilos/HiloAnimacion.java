package hilos;

/**
 * Hilo de animación que mueve la nave visualmente entre planetas.
 * 
 * Este hilo interpola la posición de la nave desde un punto A hasta un punto B
 * en el mapa, creando una animación de viaje. Usa coordenadas (x, y) y actualiza
 * la posición cada 30ms (~33 FPS).
 * 
 * INSTRUCCIONES: Implementa el método run() y animarViaje().
 * 
 * Conceptos clave:
 * - El hilo se DUERME entre frames (Thread.sleep) para controlar la velocidad
 * - Usa SwingUtilities.invokeLater() para pedir repintado del panel
 * - La interpolación lineal mueve la nave suavemente: pos = inicio + (fin - inicio) * progreso
 */
public class HiloAnimacion extends Thread {

    private volatile boolean activo;
    private volatile boolean animando;

    // Posición actual de la nave (se actualiza durante la animación)
    private double naveX;
    private double naveY;

    // Destino de la animación
    private double destinoX;
    private double destinoY;
    private double origenX;
    private double origenY;

    private AnimacionListener listener;

    public interface AnimacionListener {
        void onFrameAnimacion(double x, double y);
        void onAnimacionTerminada();
    }

    public HiloAnimacion() {
        this.activo = true;
        this.animando = false;
        setDaemon(true);
        setName("Hilo-Animacion");
    }

    public void setListener(AnimacionListener listener) {
        this.listener = listener;
    }

    /**
     * Inicia una animación de viaje desde (origenX, origenY) hasta (destinoX, destinoY).
     * La nave se mueve suavemente durante ~1 segundo.
     */
    public void animarViaje(double fromX, double fromY, double toX, double toY) {
        this.origenX = fromX;
        this.origenY = fromY;
        this.destinoX = toX;
        this.destinoY = toY;
        this.naveX = fromX;
        this.naveY = fromY;
        this.animando = true;
    }

    /**
     * TODO: Implementar el ciclo de animación.
     * 
     * Pseudocódigo:
     * mientras activo:
     *     si animando:
     *         totalFrames = 30 (para ~1 segundo a 30ms por frame)
     *         para i desde 0 hasta totalFrames:
     *             si no activo, salir
     *             progreso = i / (double) totalFrames
     *             
     *             // Interpolación lineal
     *             naveX = origenX + (destinoX - origenX) * progreso
     *             naveY = origenY + (destinoY - origenY) * progreso
     *             
     *             // Notificar al listener para que repinte
     *             si listener != null:
     *                 listener.onFrameAnimacion(naveX, naveY)
     *             
     *             Thread.sleep(30)  // ~33 FPS
     *         
     *         // Animación terminada
     *         naveX = destinoX
     *         naveY = destinoY
     *         animando = false
     *         si listener != null:
     *             listener.onAnimacionTerminada()
     *     sino:
     *         Thread.sleep(50)  // esperar hasta que haya algo que animar
     */
    @Override
    public void run() {
        // TODO: Implementar el ciclo de animación del hilo
        // Este hilo corre indefinidamente. Cuando 'animando' es true,
        // interpola la posición de la nave y notifica al listener en cada frame.
        // Cuando 'animando' es false, simplemente espera (sleep).
    }

    public boolean isAnimando() {
        return animando;
    }

    public double getNaveX() { return naveX; }
    public double getNaveY() { return naveY; }

    public void setPosicion(double x, double y) {
        this.naveX = x;
        this.naveY = y;
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}
