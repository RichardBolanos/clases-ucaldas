package modelo;

/**
 * Representa un planeta (nodo del grafo galáctico).
 * (Archivo completo - no requiere modificaciones)
 */
public class Planeta {

    private String nombre;
    private String tipo; // "comercial", "hostil", "recursos", "misterioso"
    private int x; // coordenada para dibujo en la GUI
    private int y;
    private boolean explorado;

    public Planeta(String nombre, String tipo, int x, int y) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.explorado = false;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isExplorado() { return explorado; }
    public void setExplorado(boolean explorado) { this.explorado = explorado; }

    @Override
    public String toString() {
        return nombre + " [" + tipo + "]";
    }
}
