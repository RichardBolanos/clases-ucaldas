package estructuras;

/**
 * Lista enlazada genérica para el inventario de la nave.
 * 
 * INSTRUCCIONES: Implementa todos los métodos marcados con TODO.
 * Usa la clase Nodo<T> como base.
 */
public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     * TODO: Implementar
     */
    public void agregar(T valor) {
        // TODO: Crear un nuevo nodo y agregarlo al final de la lista
        // Si la lista está vacía, el nuevo nodo es la cabeza.
        // Si no, recorrer hasta el último nodo y enlazar el nuevo.
    }

    /**
     * Elimina la primera ocurrencia del valor en la lista.
     * @return true si se eliminó, false si no se encontró
     * TODO: Implementar
     */
    public boolean eliminar(T valor) {
        // TODO: Buscar el nodo con el valor dado y eliminarlo.
        // Caso especial: si es la cabeza.
        // Caso general: buscar el nodo anterior al que se quiere eliminar.
        return false;
    }

    /**
     * Obtiene el elemento en la posición indicada.
     * @return el valor o null si el índice es inválido
     * TODO: Implementar
     */
    public T obtener(int indice) {
        // TODO: Recorrer la lista hasta la posición 'indice' y retornar el valor.
        return null;
    }

    /**
     * Verifica si un valor existe en la lista.
     * TODO: Implementar
     */
    public boolean contiene(T valor) {
        // TODO: Recorrer la lista buscando el valor. Usar equals() para comparar.
        return false;
    }

    public int tamaño() {
        return tamaño;
    }

    public boolean estaVacia() {
        return tamaño == 0;
    }

    /**
     * Convierte la lista a un arreglo de Strings (para mostrar en la GUI).
     * TODO: Implementar
     */
    public String[] toArray() {
        // TODO: Crear un arreglo de tamaño 'tamaño', recorrer la lista
        // y llenar el arreglo con toString() de cada valor.
        return new String[0];
    }
}
