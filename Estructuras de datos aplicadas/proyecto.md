# Proyecto Final: Star Navigator — Juego Espacial

## Descripción General

**Star Navigator** es un juego espacial donde el jugador pilota una nave a través de un mapa galáctico (grafo), recolectando recursos, combatiendo enemigos y completando misiones. El juego utiliza interfaz gráfica con Java Swing y maneja múltiples sistemas concurrentes mediante hilos.

**Tiempo estimado de desarrollo:** 10 horas

---

## Estructuras de datos utilizadas

| Estructura | Uso en el juego |
|---|---|
| **Lista enlazada** | Inventario de la nave (objetos recolectados) |
| **Pila** | Sistema de navegación con retroceso (undo de saltos entre planetas) |
| **Cola** | Cola de misiones y cola de enemigos por oleadas |
| **Árbol binario de búsqueda** | Ranking de puntuaciones de jugadores |
| **Grafo** | Mapa galáctico (planetas conectados por rutas espaciales) |

---

## Hilos utilizados

| Hilo | Función |
|---|---|
| **Hilo principal (EDT)** | Interfaz gráfica Swing |
| **Hilo de generación de enemigos** | Genera oleadas de enemigos cada cierto tiempo y los encola |
| **Hilo de eventos aleatorios** | Genera eventos espaciales (asteroides, cofres, señales de auxilio) |
| **Hilo de combate** | Procesa la cola de combate por turnos en segundo plano |

---

## Mecánicas del juego

### 1. Mapa Galáctico (Grafo)
- El mapa es un grafo de 8-12 planetas conectados por rutas espaciales
- Cada planeta tiene un tipo: comercial, hostil, recursos, misterioso
- El jugador navega entre planetas usando BFS para ver la ruta más corta
- Se puede explorar usando DFS para descubrir todos los planetas alcanzables

### 2. Navegación con Retroceso (Pila)
- Cada salto entre planetas se apila en el historial de navegación
- El jugador puede "retroceder" (undo) a planetas anteriores
- Útil para escapar de zonas peligrosas

### 3. Inventario (Lista Enlazada)
- El jugador recolecta objetos: combustible, armas, escudos, materiales
- Se implementa como lista enlazada con operaciones: agregar, eliminar, buscar
- Capacidad limitada (máximo 10 objetos)

### 4. Cola de Misiones y Enemigos (Cola)
- Las misiones se procesan en orden FIFO
- Los enemigos aparecen en oleadas y se encolan para combate
- Cola de prioridad para misiones urgentes

### 5. Ranking (Árbol BST)
- Al terminar el juego, la puntuación se inserta en un BST
- Se puede ver el ranking ordenado (recorrido inorden)
- Buscar si una puntuación ya existe

---

## Interfaz Gráfica (Swing)

La ventana principal tiene:

```
┌──────────────────────────────────────────────────────┐
│  ⭐ STAR NAVIGATOR                          [Puntos] │
├──────────────────────────────────────────────────────┤
│                    │                                  │
│   MAPA GALÁCTICO   │   PANEL DE INFORMACIÓN          │
│   (Grafo visual)   │   - Planeta actual              │
│                    │   - HP de la nave               │
│   ○───○───○        │   - Combustible                 │
│   │       │        │   - Inventario (lista)          │
│   ○───○   ○        │   - Misión actual               │
│       │   │        │                                  │
│       ○───○        │                                  │
│                    │                                  │
├──────────────────────────────────────────────────────┤
│  [Viajar] [Retroceder] [Inventario] [Misiones]       │
│  [Explorar DFS] [Ruta corta BFS] [Ranking]           │
└──────────────────────────────────────────────────────┘
```

---

## Arquitectura del código

```
proyecto-star-navigator/
├── app/
│   └── Main.java                # Punto de entrada
├── modelo/
│   ├── Nave.java                # Estado del jugador
│   ├── Planeta.java             # Nodo del grafo
│   ├── Mision.java              # Datos de una misión
│   ├── Enemigo.java             # Datos de un enemigo
│   └── ObjetoEspacial.java      # Item del inventario
├── estructuras/
│   ├── ListaEnlazada.java       # Inventario
│   ├── Pila.java                # Historial de navegación
│   ├── Cola.java                # Cola de misiones/enemigos
│   ├── ArbolBST.java            # Ranking de puntuaciones
│   ├── Grafo.java               # Mapa galáctico
│   └── Nodo.java                # Nodo genérico
├── hilos/
│   ├── HiloEnemigos.java        # Generador de oleadas
│   ├── HiloEventos.java         # Eventos aleatorios
│   └── HiloCombate.java         # Procesador de combate
├── gui/
│   ├── VentanaPrincipal.java    # JFrame principal
│   ├── PanelMapa.java           # Dibuja el grafo
│   └── PanelInfo.java           # Muestra información
└── logica/
    └── MotorJuego.java          # Lógica central del juego
```

---

## Requisitos de entrega

### Mínimos (para aprobar)
1. ✅ Mapa galáctico funcional con al menos 8 planetas (Grafo)
2. ✅ Navegación entre planetas con historial y retroceso (Pila)
3. ✅ Inventario funcional con agregar/eliminar/buscar (Lista enlazada)
4. ✅ Sistema de misiones en cola (Cola)
5. ✅ Ranking de puntuaciones (Árbol BST)
6. ✅ Al menos 2 hilos ejecutándose concurrentemente
7. ✅ Interfaz gráfica con Swing

### Adicionales (para nota máxima)
- Animación de la nave viajando entre planetas (HiloAnimacion)
- Panel de combate visual con proyectiles animados (PanelCombate)
- Nave dibujada como cohete real (polígono detallado)
- Estrellas parpadeantes en el fondo
- Efecto de sacudida cuando la nave recibe daño
- Rutas BFS resaltadas visualmente
- Efectos de partículas en la estela de la nave

---

## Criterios de evaluación

| Criterio | Peso |
|---|---|
| Uso correcto de las 5 estructuras de datos | 35% |
| Funcionalidad de hilos (concurrencia real) | 20% |
| Interfaz gráfica funcional | 20% |
| Calidad del código y organización | 15% |
| Creatividad y jugabilidad | 10% |

---

## Distribución sugerida del tiempo (10 horas)

| Hora | Tarea |
|---|---|
| 1-2 | Implementar estructuras de datos: Pila, Cola, ListaEnlazada |
| 3-4 | Implementar ArbolBST y Grafo (BFS y DFS) |
| 5-6 | Implementar lógica de la Nave y MotorJuego |
| 7-8 | Implementar los 3 hilos (enemigos, eventos, combate) |
| 9 | Probar todo integrado, corregir bugs |
| 10 | Pruebas finales, ajustes y mejoras opcionales |

---

## ¿Qué se entrega como base?

La carpeta `proyecto-star-navigator/` contiene un **esqueleto funcional**. El código compila y la GUI se muestra, pero las funcionalidades principales están marcadas con `// TODO` para que tú las implementes.

### Archivos que DEBES completar (marcados con TODO):

| Archivo | Qué implementar |
|---|---|
| `estructuras/ListaEnlazada.java` | agregar, eliminar, obtener, contiene, toArray |
| `estructuras/Pila.java` | push, pop, peek |
| `estructuras/Cola.java` | enqueue, dequeue, peek |
| `estructuras/ArbolBST.java` | insertar, buscar, obtenerRanking (inorden), altura |
| `estructuras/Grafo.java` | caminoMasCorto (BFS), explorarDFS (DFS recursivo) |
| `modelo/Nave.java` | viajarA (usa Pila), retroceder (usa Pila), agregarObjeto/usarObjeto (usa Lista) |
| `hilos/HiloEnemigos.java` | método run() — ciclo de generación de oleadas |
| `hilos/HiloEventos.java` | método run() — ciclo de eventos aleatorios |
| `hilos/HiloCombate.java` | método procesarCombate() — combate por turnos |
| `hilos/HiloAnimacion.java` | método run() — mover la nave visualmente con interpolación |
| `gui/PanelCombate.java` | animarDisparo() — proyectil viajando entre naves |
| `gui/PanelMapa.java` | [MEJORA UI] mejorar sprites, estrellas, rutas, estela |
| `logica/MotorJuego.java` | viajarA, retroceder, calcularRuta, explorarDesdeActual, verificarMision, inicializarMisiones, inicializarRankingBase + integrar animación |

### Archivos que NO necesitas modificar:

| Archivo | Razón |
|---|---|
| `app/Main.java` | Solo lanza la GUI |
| `estructuras/Nodo.java` | Nodo genérico completo |
| `modelo/Planeta.java` | Solo datos |
| `modelo/Enemigo.java` | Solo datos |
| `modelo/Mision.java` | Solo datos |
| `modelo/ObjetoEspacial.java` | Solo datos |
| `gui/VentanaPrincipal.java` | GUI base, conecta todo |
| `gui/PanelInfo.java` | Muestra la información |

---

## Orden sugerido de implementación

1. **Primero las estructuras** (el juego no funciona sin ellas):
   - Pila → Cola → ListaEnlazada → ArbolBST → Grafo (BFS/DFS)
2. **Luego la lógica de la nave y motor de juego:**
   - viajarA, retroceder, inventario, misiones
3. **Luego los hilos de lógica:**
   - HiloEnemigos → HiloEventos → HiloCombate
4. **Luego la animación y UI:**
   - HiloAnimacion (mover nave visualmente)
   - PanelCombate (animación de disparos)
   - Mejoras visuales en PanelMapa (sprites, partículas, efectos)

---

## Instrucciones para compilar y ejecutar

```bash
cd proyecto-star-navigator
javac -d out app\Main.java modelo\*.java estructuras\*.java hilos\*.java gui\*.java logica\*.java
java -cp out app.Main
```

O desde un IDE, ejecutar la clase `app/Main.java`.

**El proyecto compila desde el primer momento.** A medida que implementes los TODOs, las funcionalidades irán activándose en la GUI.

---

## Notas importantes

- **No usar** `java.util.ArrayList`, `java.util.LinkedList`, `java.util.Stack`, `java.util.Queue` para las estructuras principales. El objetivo es usar las implementaciones propias del curso.
- **Sí se puede usar** `java.util.HashMap`/`HashSet`/`ArrayList`/`LinkedList` **solo** dentro de `Grafo.java` para la representación interna y algoritmos BFS/DFS.
- Los métodos de `Cola.java` deben ser `synchronized` porque los hilos acceden concurrentemente.
- La GUI ya maneja `SwingUtilities.invokeLater()` correctamente — no necesitas preocuparte por eso.
- Cada TODO tiene pseudocódigo o instrucciones detalladas de lo que debes hacer.
