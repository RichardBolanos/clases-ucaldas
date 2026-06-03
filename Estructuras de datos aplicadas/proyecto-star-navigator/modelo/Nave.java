package modelo;

import estructuras.ListaEnlazada;
import estructuras.Pila;

/**
 * Representa la nave del jugador con su estado, inventario y navegación.
 * 
 * INSTRUCCIONES: Implementa los métodos marcados con TODO.
 * Esta clase usa la Pila (historial de navegación) y la ListaEnlazada (inventario).
 */
public class Nave {

    private String nombre;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int escudo;
    private int combustible;
    private int puntuacion;
    private String planetaActual;
    private ListaEnlazada<ObjetoEspacial> inventario;
    private Pila<String> historialNavegacion;
    private static final int MAX_INVENTARIO = 10;

    public Nave(String nombre) {
        this.nombre = nombre;
        this.vida = 100;
        this.vidaMaxima = 100;
        this.ataque = 20;
        this.escudo = 5;
        this.combustible = 50;
        this.puntuacion = 0;
        this.inventario = new ListaEnlazada<>();
        this.historialNavegacion = new Pila<>();
    }

    /**
     * Viaja a un nuevo planeta. Apila el planeta actual en el historial (Pila)
     * y actualiza el planeta actual. Consume 10 de combustible.
     * @return true si pudo viajar, false si no hay combustible suficiente
     * 
     * TODO: Implementar usando la Pila historialNavegacion
     */
    public boolean viajarA(String planeta) {
        // TODO:
        // 1. Verificar que combustible >= 10, si no retornar false
        // 2. Hacer push del planetaActual en historialNavegacion
        // 3. Actualizar planetaActual al nuevo planeta
        // 4. Restar 10 de combustible
        // 5. Retornar true
        return false;
    }

    /**
     * Retrocede al planeta anterior (pop del historial).
     * Consume 5 de combustible.
     * @return el nombre del planeta al que retrocedió, o null si no hay historial
     * 
     * TODO: Implementar usando la Pila historialNavegacion
     */
    public String retroceder() {
        // TODO:
        // 1. Verificar que la pila no esté vacía, si lo está retornar null
        // 2. Hacer pop del historialNavegacion
        // 3. Actualizar planetaActual con el valor obtenido
        // 4. Restar 5 de combustible
        // 5. Retornar el planetaActual
        return null;
    }

    /**
     * Agrega un objeto al inventario (ListaEnlazada).
     * Máximo 10 objetos.
     * @return true si se agregó, false si el inventario está lleno
     * 
     * TODO: Implementar usando la ListaEnlazada inventario
     */
    public boolean agregarObjeto(ObjetoEspacial objeto) {
        // TODO:
        // 1. Verificar que inventario.tamaño() < MAX_INVENTARIO
        // 2. Si hay espacio, agregar el objeto y retornar true
        // 3. Si no, retornar false
        return false;
    }

    /**
     * Usa un objeto del inventario, aplicando su efecto y eliminándolo.
     * Efectos según tipo: combustible (suma combustible), arma (suma ataque),
     * escudo (suma escudo), material (suma puntuación).
     * @return true si se usó, false si no se encontró
     * 
     * TODO: Implementar usando la ListaEnlazada inventario
     */
    public boolean usarObjeto(ObjetoEspacial objeto) {
        // TODO:
        // 1. Verificar que el inventario contiene el objeto
        // 2. Según objeto.getTipo(), aplicar el efecto:
        //    - "combustible" → combustible += objeto.getValor()
        //    - "arma" → ataque += objeto.getValor()
        //    - "escudo" → escudo += objeto.getValor()
        //    - "material" → puntuacion += objeto.getValor()
        // 3. Eliminar el objeto del inventario
        // 4. Retornar true (o false si no existía)
        return false;
    }

    // --- Métodos de combate (completos) ---

    public void recibirDaño(int daño) {
        int dañoReal = Math.max(1, daño - escudo);
        vida -= dañoReal;
        if (vida < 0) vida = 0;
    }

    public void curar(int cantidad) {
        vida = Math.min(vidaMaxima, vida + cantidad);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // --- Getters (completos) ---

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getAtaque() { return ataque; }
    public int getEscudo() { return escudo; }
    public int getCombustible() { return combustible; }
    public int getPuntuacion() { return puntuacion; }
    public String getPlanetaActual() { return planetaActual; }
    public ListaEnlazada<ObjetoEspacial> getInventario() { return inventario; }
    public Pila<String> getHistorialNavegacion() { return historialNavegacion; }

    public void setPlanetaActual(String planeta) { this.planetaActual = planeta; }
    public void sumarPuntuacion(int puntos) { this.puntuacion += puntos; }
    public void agregarCombustible(int cantidad) { this.combustible += cantidad; }
}
