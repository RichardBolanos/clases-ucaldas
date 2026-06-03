package estructuras;

/**
 * Nodo genérico para lista enlazada y pila enlazada.
 * 
 * TODO: Este archivo está completo. Úsalo como base para tus estructuras.
 */
public class Nodo<T> {

    public T valor;
    public Nodo<T> siguiente;

    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
