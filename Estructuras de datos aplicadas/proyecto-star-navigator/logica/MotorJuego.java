package logica;

import estructuras.*;
import modelo.*;
import hilos.*;

import java.util.*;

/**
 * Motor central del juego. Conecta las estructuras de datos con la lógica.
 * 
 * INSTRUCCIONES: Implementa los métodos marcados con TODO.
 * Los métodos de inicialización y getters están completos.
 * 
 * También debes integrar el HiloAnimacion para que la nave se mueva
 * visualmente cuando viaja entre planetas.
 */
public class MotorJuego {

    private Nave nave;
    private Grafo mapaGalactico;
    private Map<String, Planeta> planetas;
    private Cola<Mision> colaMisiones;
    private Cola<Enemigo> colaEnemigos;
    private ArbolBST ranking;

    private HiloEnemigos hiloEnemigos;
    private HiloEventos hiloEventos;
    private HiloCombate hiloCombate;
    private HiloAnimacion hiloAnimacion;

    public MotorJuego(String nombreJugador) {
        nave = new Nave(nombreJugador);
        mapaGalactico = new Grafo();
        planetas = new HashMap<>();
        colaMisiones = new Cola<>();
        colaEnemigos = new Cola<>();
        ranking = new ArbolBST();

        inicializarMapa();
        inicializarMisiones();
        inicializarRankingBase();
    }

    // --- Inicialización (completa) ---

    private void inicializarMapa() {
        crearPlaneta("Terra Nova", "comercial", 150, 200);
        crearPlaneta("Nebula X", "recursos", 300, 100);
        crearPlaneta("Orion VII", "hostil", 450, 200);
        crearPlaneta("Zephyr", "misterioso", 300, 300);
        crearPlaneta("Kronos", "hostil", 550, 350);
        crearPlaneta("Arcadia", "comercial", 100, 380);
        crearPlaneta("Helios", "recursos", 200, 450);
        crearPlaneta("Vortex", "misterioso", 450, 450);
        crearPlaneta("Titan", "hostil", 600, 150);
        crearPlaneta("Elysium", "comercial", 350, 550);

        mapaGalactico.agregarArista("Terra Nova", "Nebula X");
        mapaGalactico.agregarArista("Terra Nova", "Zephyr");
        mapaGalactico.agregarArista("Terra Nova", "Arcadia");
        mapaGalactico.agregarArista("Nebula X", "Orion VII");
        mapaGalactico.agregarArista("Nebula X", "Zephyr");
        mapaGalactico.agregarArista("Orion VII", "Titan");
        mapaGalactico.agregarArista("Orion VII", "Kronos");
        mapaGalactico.agregarArista("Zephyr", "Kronos");
        mapaGalactico.agregarArista("Zephyr", "Helios");
        mapaGalactico.agregarArista("Arcadia", "Helios");
        mapaGalactico.agregarArista("Helios", "Vortex");
        mapaGalactico.agregarArista("Helios", "Elysium");
        mapaGalactico.agregarArista("Kronos", "Vortex");
        mapaGalactico.agregarArista("Vortex", "Elysium");
        mapaGalactico.agregarArista("Titan", "Kronos");

        nave.setPlanetaActual("Terra Nova");
        planetas.get("Terra Nova").setExplorado(true);
    }

    private void crearPlaneta(String nombre, String tipo, int x, int y) {
        Planeta p = new Planeta(nombre, tipo, x, y);
        planetas.put(nombre, p);
        mapaGalactico.agregarNodo(nombre);
    }

    private void inicializarMisiones() {
        // TODO: Encolar 5 misiones usando colaMisiones.enqueue()
        // Misión 1: "Recolectar cristales", planeta objetivo "Nebula X", recompensa 30
        // Misión 2: "Eliminar piratas", planeta objetivo "Orion VII", recompensa 50
        // Misión 3: "Explorar anomalía", planeta objetivo "Zephyr", recompensa 40
        // Misión 4: "Rescate en Kronos", planeta objetivo "Kronos", recompensa 60
        // Misión 5: "Comerciar en Elysium", planeta objetivo "Elysium", recompensa 35
    }

    private void inicializarRankingBase() {
        // TODO: Insertar puntuaciones base en el ranking (ArbolBST)
        // Usa ranking.insertar(nombre, puntuacion) para:
        // "Capitán Cosmos" - 500 pts
        // "Nova" - 350 pts
        // "Estelar" - 200 pts
        // "Rookie" - 50 pts
        // "Leyenda" - 800 pts
    }

    // --- Acciones del jugador ---

    /**
     * Viaja a un planeta destino si hay ruta directa y combustible.
     * Marca el planeta como explorado y verifica misiones.
     * 
     * TODO: Implementar
     * 1. Verificar que existe arista entre planetaActual y destino (grafo.hayArista)
     * 2. Verificar que hay combustible suficiente (nave.getCombustible() >= 10)
     * 3. Llamar nave.viajarA(destino)
     * 4. Marcar el planeta como explorado
     * 5. Verificar si se completa la misión actual (verificarMision)
     * 6. Retornar mensaje apropiado
     */
    public String viajarA(String destino) {
        // TODO: Implementar la lógica de viaje
        return "Función viajarA no implementada";
    }

    /**
     * Retrocede al planeta anterior usando la pila de historial.
     * TODO: Implementar
     */
    public String retroceder() {
        // TODO: Llamar nave.retroceder() y retornar mensaje apropiado
        return "Función retroceder no implementada";
    }

    /**
     * Calcula la ruta más corta al destino usando BFS del grafo.
     * TODO: Implementar
     */
    public String calcularRuta(String destino) {
        // TODO: Usar mapaGalactico.caminoMasCorto() y formatear el resultado
        return "Función BFS no implementada";
    }

    /**
     * Explora todos los planetas alcanzables usando DFS del grafo.
     * TODO: Implementar
     */
    public String explorarDesdeActual() {
        // TODO: Usar mapaGalactico.explorarDFS() y formatear el resultado
        return "Función DFS no implementada";
    }

    /**
     * Verifica si al llegar a un planeta se completa la misión actual.
     * La misión actual es la que está al frente de la cola.
     * 
     * TODO: Implementar
     * 1. Si colaMisiones no está vacía
     * 2. Hacer peek() para ver la misión al frente
     * 3. Si el planetaObjetivo de la misión coincide con el planeta actual
     * 4. Hacer dequeue(), marcar completada, sumar recompensa
     */
    private void verificarMision(String planeta) {
        // TODO: Implementar verificación de misiones con la Cola
    }

    // --- Hilos (completo) ---

    public void iniciarHilos() {
        hiloEnemigos = new HiloEnemigos(colaEnemigos);
        hiloEventos = new HiloEventos();
        hiloCombate = new HiloCombate(colaEnemigos, nave);
        hiloAnimacion = new HiloAnimacion();

        hiloEnemigos.start();
        hiloEventos.start();
        hiloCombate.start();
        hiloAnimacion.start();
    }

    public void detenerHilos() {
        if (hiloEnemigos != null) hiloEnemigos.detener();
        if (hiloEventos != null) hiloEventos.detener();
        if (hiloCombate != null) hiloCombate.detener();
        if (hiloAnimacion != null) hiloAnimacion.detener();
    }

    /**
     * TODO: Integrar la animación al viajar.
     * Cuando el jugador viaja de un planeta a otro, debes:
     * 1. Obtener las coordenadas (x, y) del planeta origen y destino
     * 2. Llamar hiloAnimacion.animarViaje(origenX, origenY, destinoX, destinoY)
     * 3. El HiloAnimacion moverá la nave visualmente entre ambos puntos
     * 
     * Para que funcione, necesitas configurar el listener del hiloAnimacion
     * en la VentanaPrincipal para que actualice el PanelMapa en cada frame.
     */
    public HiloAnimacion getHiloAnimacion() { return hiloAnimacion; }

    public void combatir() {
        if (!colaEnemigos.estaVacia()) {
            hiloCombate.iniciarCombate();
        }
    }

    public void terminarJuego() {
        detenerHilos();
        ranking.insertar(nave.getNombre(), nave.getPuntuacion());
    }

    // --- Getters (completos) ---

    public Nave getNave() { return nave; }
    public Grafo getMapaGalactico() { return mapaGalactico; }
    public Map<String, Planeta> getPlanetas() { return planetas; }
    public Cola<Mision> getColaMisiones() { return colaMisiones; }
    public Cola<Enemigo> getColaEnemigos() { return colaEnemigos; }
    public ArbolBST getRanking() { return ranking; }
    public HiloEnemigos getHiloEnemigos() { return hiloEnemigos; }
    public HiloEventos getHiloEventos() { return hiloEventos; }
    public HiloCombate getHiloCombate() { return hiloCombate; }

    public List<String> getDestinosDisponibles() {
        return mapaGalactico.vecinos(nave.getPlanetaActual());
    }
}
