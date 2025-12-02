package graph;

import events.BuffDebuffEvent;
import events.Event;
import events.GoBackEvent;
import events.LoseTurnEvent;
import queue.PriorityQueue;


public class NetworkGraph<T> extends Graph<T> implements NetworkADT<T> {

    protected Event[][] weights;

    /**
     * Constructs an empty NetworkGraph with a default capacity.
     * <p>
     * Initializes the graph's adjacency matrix and weights array. The weights
     * array is represented as a two-dimensional array of Event objects, with
     * dimensions defined by a default capacity. The NetworkGraph is intended
     * to manage edges with weights represented by Event instances and includes
     * methods for edge manipulation, risk assessment, and pathfinding.
     * <p>
     * This constructor calls the parent Graph constructor to initialize common
     * graph structures such as the adjacency matrix and vertex list, then
     * sets up the weights array specific to the NetworkGraph implementation.
     */
    public NetworkGraph() {
        super();
        weights = new Event[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    /**
     * Expands the capacity of the graph by increasing the size of the weights array.
     * This method overrides the base expandCapacity method to ensure the weights array
     * is updated to accommodate the new capacity of the vertices and adjacency matrix.
     * <p>
     * Creates a new two-dimensional Event array with dimensions matching the new capacity.
     * Copies existing weight values from the old array to the corresponding positions
     * in the new array. Updates the class-level weights array to reference the newly
     * expanded array.
     */
    @Override
    protected void expandCapacity() {
        super.expandCapacity();

        Event[][] newWeights = new Event[vertices.length][vertices.length];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                newWeights[i][j] = weights[i][j];
            }
        }
        weights = newWeights;
    }

    /**
     * Adds an edge between two vertices in the graph with a specified event.
     * The edge is represented in the adjacency matrix and the weights array,
     * provided the event is of type BuffDebuffEvent. Throws an exception if
     * the event is not a valid BuffDebuffEvent. Updates both directions of
     * the edge in an undirected graph.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @param event   the BuffDebuffEvent representing the weight of the edge
     *                and any associated effects
     * @throws IllegalArgumentException if the provided event is not an instance
     *                                  of BuffDebuffEvent
     */
    @Override
    public void addEdge(T vertex1, T vertex2, Event event) {
        super.addEdge(vertex1, vertex2); // Updates adjMatrix (connectivity)

        if (!(event instanceof BuffDebuffEvent)) {
            throw new IllegalArgumentException("Events for corridor must be of type BuffDebuffEvent");
        }

        BuffDebuffEvent buffDebuffEvent = (BuffDebuffEvent) event;

        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            weights[index1][index2] = buffDebuffEvent;
            weights[index2][index1] = buffDebuffEvent;
        }
    }

    /**
     * Retrieves the weight of the edge connecting two specified vertices in the graph.
     * The weight is represented by an Event object stored in a two-dimensional array,
     * where its position is determined by the indices of the vertices.
     *
     * @param vertex1 the starting vertex of the edge
     * @param vertex2 the ending vertex of the edge
     * @return the Event object representing the weight of the edge between the two vertices,
     * or null if no edge exists
     */
    @Override
    public Event getEdgeWeight(T vertex1, T vertex2) {
        return weights[getIndex(vertex1)][getIndex(vertex2)];
    }


    /**
     * Evaluates the risk level associated with a given BuffDebuffEvent.
     * The risk is determined based on the type of the event:
     * - Returns 2 if the event is null.
     * - Returns 3 if the event is an instance of LoseTurnEvent.
     * - Returns 4 if the event is an instance of GoBackEvent.
     * - Returns 1 for all other event types.
     *
     * @param event the BuffDebuffEvent being evaluated; can be null or any subclass of BuffDebuffEvent
     * @return an integer representing the calculated risk level for the given event:
     * - 2 for null events,
     * - 3 for LoseTurnEvent,
     * - 4 for GoBackEvent,
     * - 1 for all other cases
     */
    private int calculateRisk(BuffDebuffEvent event) {
        if (event == null) {
            return 2;
        } else if (event instanceof LoseTurnEvent) {
            return 3;
        } else if (event instanceof GoBackEvent) {
            return 4;
        } else {
            return 1;
        }
    }


    /**
     * Computes the weight of the shortest path between two specified vertices in the graph.
     * The method uses a priority queue to implement a variation of Dijkstra's algorithm,
     * taking into account weights represented by BuffDebuffEvent instances to calculate
     * risk-based edge costs.
     *
     * @param vertex1 the starting vertex of the path
     * @param vertex2 the target vertex of the path
     * @return the weight of the shortest path between the specified vertices, or Double.MAX_VALUE
     * if no valid path exists
     * @throws IllegalArgumentException if one or both of the specified vertices are not found in the graph
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int start = getIndex(vertex1);
        int end = getIndex(vertex2);

        if (!indexIsValid(start) || !indexIsValid(end)) {
            throw new IllegalArgumentException("One or both vertices not found in graph.");
        }

        int[] dist = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        // Initialize distances
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        dist[start] = 0;

        PriorityQueue<T> pq = new PriorityQueue<>();
        pq.addElement(vertex1, 0);

        while (!pq.isEmpty()) {

            T currentVertex = pq.removeNext();
            int currentIndex = getIndex(currentVertex);

            if (visited[currentIndex]) continue;
            visited[currentIndex] = true;

            // Stop early if we reached the target
            if (currentIndex == end) {
                return dist[end];
            }

            // Check neighbors
            for (int neighborIndex = 0; neighborIndex < numVertices; neighborIndex++) {

                if (adjMatrix[currentIndex][neighborIndex]) {

                    BuffDebuffEvent event = (BuffDebuffEvent) weights[currentIndex][neighborIndex];
                    int risk = calculateRisk(event);

                    // Relaxation step
                    if (dist[currentIndex] + risk < dist[neighborIndex]) {
                        dist[neighborIndex] = dist[currentIndex] + risk;
                        pq.addElement(vertices[neighborIndex], dist[neighborIndex]);
                    }
                }
            }
        }

        return Double.MAX_VALUE;
    }

}
