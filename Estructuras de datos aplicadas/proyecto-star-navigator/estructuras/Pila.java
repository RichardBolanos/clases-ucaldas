package estructuras;

/**
 * Pila genérica implementada con lista enlazada.
 * Usada para el historial de navegación (undo de saltos entre planetas).
 * 
 * INSTRUCCIONES: Implementa todos los métodos marcados con TODO.
 * Recuerda: LIFO (Last In, First Out). El tope es el primer nodo.
 */
public class Pila<T> {

    private Nodo<T> tope;
    private int tamaño;

    public Pila() {
        tope = null;
        tamaño = 0;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * TODO: Implementar
     */
    public void push(T valor) {
        // TODO: Crear un nuevo nodo, enlazarlo al tope actual,
        // y actualizar el tope.
    }

    /**
     * Quita y retorna el elemento del tope.
     * @throws RuntimeException si la pila está vacía
     * TODO: Implementar
     */
    public T pop() {
        // TODO: Guardar el valor del tope, mover el tope al siguiente,
        // decrementar tamaño y retornar el valor.
        // Lanzar RuntimeException si está vacía.
        throw new RuntimeException("Pila vacía");
    }

    /**
     * Retorna el elemento del tope sin quitarlo.
     * @throws RuntimeException si la pila está vacía
     * TODO: Implementar
     */
    public T peek() {
        // TODO: Retornar el valor del tope sin modificar la pila.
        throw new RuntimeException("Pila vacía");
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamaño() {
        return tamaño;
    }
}
