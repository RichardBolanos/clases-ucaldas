package modelo;

/**
 * Representa una misión del juego que se encola para ser completada.
 * (Archivo completo - no requiere modificaciones)
 */
public class Mision {

    private String nombre;
    private String descripcion;
    private String planetaObjetivo;
    private int recompensa;
    private boolean completada;

    public Mision(String nombre, String descripcion, String planetaObjetivo, int recompensa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.planetaObjetivo = planetaObjetivo;
        this.recompensa = recompensa;
        this.completada = false;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getPlanetaObjetivo() { return planetaObjetivo; }
    public int getRecompensa() { return recompensa; }
    public boolean isCompletada() { return completada; }
    public void completar() { this.completada = true; }

    @Override
    public String toString() {
        return nombre + " → " + planetaObjetivo + " (+" + recompensa + " pts)";
    }
}
