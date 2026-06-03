package modelo;

/**
 * Representa un enemigo espacial que aparece en oleadas.
 * (Archivo completo - no requiere modificaciones)
 */
public class Enemigo {

    private String nombre;
    private int vida;
    private int ataque;
    private int recompensa;

    public Enemigo(String nombre, int vida, int ataque, int recompensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.recompensa = recompensa;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    public int getRecompensa() { return recompensa; }

    public void recibirDaño(int daño) {
        vida -= daño;
        if (vida < 0) vida = 0;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    @Override
    public String toString() {
        return nombre + " [HP:" + vida + " ATK:" + ataque + "]";
    }
}
