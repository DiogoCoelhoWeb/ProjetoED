package graph;

import exeptions.ElementNotFoundException;
import lists.ArrayUnorderedList;
import queue.LinkedQueue;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    /**
     * Expands the capacity of the graph when the current storage limit for vertices is reached.
     * Doubles the size of the vertices array and adjacency matrix to accommodate more vertices.
     * Copies existing vertex data to the new vertices array and replicates the current adjacency
     * matrix values into the expanded matrix.
     */
    private void expandCapacity() {
        int newCapacity = vertices.length * 2;
        T[] newVertices = (T[]) (new Object[newCapacity]);
        System.arraycopy(vertices, 0, newVertices, 0, vertices.length);
        vertices = newVertices;

        adjMatrix = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = adjMatrix[j][i];
            }
        }
    }

    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph
     * if necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length)
            expandCapacity();
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    /**
     * Removes a single vertex from the graph along with all its
     * associated edges. The removal is performed by replacing the
     * vertex and its connections in the adjacency matrix and vertex array,
     * maintaining the structure of the graph.
     *
     * @param vertex the vertex to be removed from the graph
     */
    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);

        if (indexIsValid(index)) {
            this.numVertices--;

            this.vertices[index] = this.vertices[this.numVertices];
            this.vertices[this.numVertices] = null;

            for (int i = 0; i < this.numVertices; i++) {
                this.adjMatrix[index][i] = this.adjMatrix[this.numVertices][i];
            }

            for (int i = 0; i < this.numVertices + 1; i++) {
                this.adjMatrix[i][index] = this.adjMatrix[i][this.numVertices];
            }
        }
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Checks if the provided index is valid within the bounds of the current graph's vertices.
     *
     * @param index the index to check for validity
     * @return true if the index is between 0 (inclusive) and the number of vertices (exclusive), false otherwise
     */
    private boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices;
    }

    /**
     * Inserts an edge between two vertices of the graph by their objects.
     * The method retrieves the indices of the vertices in the graph
     * and uses these indices to add the edge in the adjacency representation.
     *
     * @param vertex1 the first vertex to connect
     * @param vertex2 the second vertex to connect
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Retrieves the index of the specified vertex in the graph.
     * The method iterates through the vertices array to find
     * the matching vertex and returns its index. If the vertex
     * is not found, it returns -1.
     *
     * @param vertex the vertex whose index is to be retrieved
     * @return the index of the specified vertex if found; -1 otherwise
     */
    private int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) return i;
        }
        return -1;
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (!indexIsValid(index1) || indexIsValid(index2)) {
            throw new ElementNotFoundException("Vertex not found in graph.");
        }
        adjMatrix[index1][index2] = false;
        adjMatrix[index2][index1] = false;
    }

    /**
     * Returns an iterator that performs a breadth first search
     * traversal starting at the given index.
     *
     * @param startVertex the Vertex to begin the search from
     * @return an iterator that performs a breadth first traversal
     */
    @Override
    public Iterator iteratorBFS(T startVertex) {
        int startIndex = getIndex(startVertex);
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex))
            return resultList.iterator();
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);
            /** Find all vertices adjacent to x that have
             not been visited and queue them up */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        return null;
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        //Dikstra with priority queue
        return null;
    }

    @Override
    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public int size() {
        return this.numVertices;
    }
}