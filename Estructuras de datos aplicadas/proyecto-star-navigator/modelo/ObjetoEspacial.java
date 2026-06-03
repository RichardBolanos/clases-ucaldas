package modelo;

/**
 * Representa un objeto que la nave puede recolectar y almacenar en el inventario.
 * (Archivo completo - no requiere modificaciones)
 */
public class ObjetoEspacial {

    private String nombre;
    private String tipo; // "combustible", "arma", "escudo", "material"
    private int valor;

    public ObjetoEspacial(String nombre, String tipo, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getValor() { return valor; }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ", +" + valor + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ObjetoEspacial otro = (ObjetoEspacial) obj;
        return nombre.equals(otro.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
