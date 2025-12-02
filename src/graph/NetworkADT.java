package graph;

import events.Event;

public interface NetworkADT<T> extends GraphADT<T> {
    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param event   the event associated with the edge
     */
    void addEdge(T vertex1, T vertex2, Event event);

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    double shortestPathWeight(T vertex1, T vertex2);

    /**
     * Retrieves the weight (associated event) of the edge connecting the specified vertices in the network.
     *
     * @param vertex1 the first vertex connected by the edge
     * @param vertex2 the second vertex connected by the edge
     * @return the Event associated with the edge between the given vertices, or null if the edge does not exist
     */
    Event getEdgeWeight(T vertex1, T vertex2);
}
