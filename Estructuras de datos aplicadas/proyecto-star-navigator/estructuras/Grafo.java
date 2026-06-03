package estructuras;

import java.util.*;

/**
 * Grafo implementado con lista de adyacencia para el mapa galáctico.
 * 
 * INSTRUCCIONES: Implementa los métodos de BFS y DFS marcados con TODO.
 * Los métodos básicos (agregarNodo, agregarArista, vecinos) están implementados.
 * 
 * NOTA: Se permite usar HashMap, HashSet, ArrayList y LinkedList de java.util
 * SOLO para la estructura interna del grafo y los algoritmos BFS/DFS.
 */
public class Grafo {

    private Map<String, List<String>> adyacencia;

    public Grafo() {
        adyacencia = new HashMap<>();
    }

    public void agregarNodo(String nodo) {
        adyacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    public void agregarArista(String origen, String destino) {
        agregarNodo(origen);
        agregarNodo(destino);
        if (!adyacencia.get(origen).contains(destino)) {
            adyacencia.get(origen).add(destino);
        }
        if (!adyacencia.get(destino).contains(origen)) {
            adyacencia.get(destino).add(origen);
        }
    }

    public List<String> vecinos(String nodo) {
        return adyacencia.getOrDefault(nodo, new ArrayList<>());
    }

    public Set<String> obtenerNodos() {
        return adyacencia.keySet();
    }

    public boolean hayArista(String origen, String destino) {
        List<String> vec = adyacencia.get(origen);
        return vec != null && vec.contains(destino);
    }

    public int cantidadNodos() {
        return adyacencia.size();
    }

    /**
     * BFS: Encuentra el camino más corto entre dos planetas.
     * Retorna la lista de planetas en orden del camino.
     * Si no hay camino, retorna lista vacía.
     * 
     * TODO: Implementar usando una cola (java.util.LinkedList como Queue).
     * Algoritmo:
     * 1. Crear un Map<String,String> para guardar el padre de cada nodo
     * 2. Crear un Set<String> de visitados
     * 3. Encolar el nodo inicio, marcarlo como visitado
     * 4. Mientras la cola no esté vacía:
     *    - Desencolar un nodo
     *    - Si es el destino, reconstruir el camino usando el mapa de padres
     *    - Si no, encolar todos sus vecinos no visitados
     * 5. Si la cola se vacía sin encontrar destino, retornar lista vacía
     */
    public List<String> caminoMasCorto(String inicio, String destino) {
        // TODO: Implementar BFS
        return new ArrayList<>();
    }

    /**
     * DFS: Descubre todos los planetas alcanzables desde un punto.
     * Retorna la lista de planetas en orden de descubrimiento.
     * 
     * TODO: Implementar usando recursividad.
     * Algoritmo:
     * 1. Crear un Set<String> de visitados y una List<String> para el orden
     * 2. Llamar al método recursivo dfsRecursivo
     * 3. En dfsRecursivo: marcar nodo como visitado, agregarlo a la lista,
     *    y llamar recursivamente para cada vecino no visitado
     */
    public List<String> explorarDFS(String inicio) {
        // TODO: Implementar DFS recursivo
        List<String> orden = new ArrayList<>();
        orden.add(inicio); // placeholder
        return orden;
    }
}
