package graph;

import exeptions.ElementNotFoundException;
import lists.ArrayUnorderedList;
import queue.LinkedQueue;
import stack.LinkedStack;

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
    protected void expandCapacity() {
        int newCapacity = vertices.length * 2;
        T[] newVertices = (T[]) (new Object[newCapacity]);
        System.arraycopy(vertices, 0, newVertices, 0, vertices.length);
        vertices = newVertices;

        boolean[][] newAdjMatrix = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                newAdjMatrix[i][j] = adjMatrix[i][j];
            }
        }
        adjMatrix = newAdjMatrix;
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
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Internal method to insert an edge between two vertices of the graph by their indices.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdgeByIndex(int index1, int index2) {
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
    protected boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices;
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
    protected int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null && vertices[i].equals(vertex)) return i;
        }
        return -1;
    }

    /**
     * Removes an edge between two vertices in the graph. If either vertex is not present
     * in the graph, an ElementNotFoundException is thrown. The adjacency matrix is updated
     * to reflect the removal of the edge in both directions.
     *
     * @param vertex1 the first vertex from which the edge originates
     * @param vertex2 the second vertex to which the edge is connected
     * @throws ElementNotFoundException if either of the vertices is not found in the graph
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (!indexIsValid(index1) || !indexIsValid(index2)) {
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
            resultList.addToRear(vertices[x]);
            /** Find all vertices adjacent to x that have
             not been visited and queue them up */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth-first search (DFS) traversal of the graph,
     * starting from the specified vertex. The traversal explores as far as possible along
     * each branch before backtracking.
     *
     * @param startVertex the vertex to begin the depth-first search from
     * @return an iterator that performs a depth-first traversal of the graph starting from
     *         the specified vertex; returns an empty iterator if the vertex is invalid
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        int startIndex = getIndex(startVertex);
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /** Find a vertex adjacent to x that has not been visited
             and push it on the stack */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a traversal to find the shortest path
     * from the specified starting vertex to the target vertex in the graph.
     * If no path exists or the vertices are invalid, an empty iterator is returned.
     *
     * @param startVertex the starting vertex of the path
     * @param targetVertex the target vertex of the path
     * @return an iterator over the shortest path from startVertex to targetVertex,
     *         or an empty iterator if no path exists or the vertices are invalid
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        int[] predecessor = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
            predecessor[i] = -1;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            int x = traversalQueue.dequeue();

            if (x == targetIndex) { // Found the target
                // Reconstruct path
                int current = targetIndex;
                LinkedStack<T> pathStack = new LinkedStack<>();
                while (current != -1) {
                    pathStack.push(vertices[current]);
                    current = predecessor[current];
                }
                while(!pathStack.isEmpty()){
                    resultList.addToRear(pathStack.pop());
                }
                return resultList.iterator();
            }

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    visited[i] = true;
                    predecessor[i] = x;
                    traversalQueue.enqueue(i);
                }
            }
        }

        return resultList.iterator(); // Path not found
    }


    /**
     * Determines if there is an edge between the start vertex and the target vertex
     * in the graph by checking the adjacency matrix.
     *
     * @param startVertex the starting vertex
     * @param targetVertex the target vertex
     * @return true if there is an edge between the provided vertices, false otherwise
     */
    public boolean veifyToVertex(T startVertex, T targetVertex){
        return this.adjMatrix[getIndex(startVertex)][getIndex(targetVertex)];
    }


    /**
     * Retrieves all the neighboring vertices directly connected to the specified vertex.
     * It iterates through the adjacency matrix to identify connections and collects
     * the corresponding vertices.
     *
     * @param vertex the vertex whose neighbors are to be retrieved
     * @return a list of neighboring vertices connected to the specified vertex
     */
    public ArrayUnorderedList<T> getNeighbors(T vertex){
        ArrayUnorderedList<T> neighbors = new ArrayUnorderedList<>();
        for(int i = 0; i < numVertices; i++){
            if(adjMatrix[getIndex(vertex)][i]){
                neighbors.addToRear(vertices[i]);
            }
        }
        return neighbors;
    }

    /**
     * Determines if the graph is empty. A graph is considered empty if it has no vertices.
     *
     * @return true if there are no vertices in the graph; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    /**
     * Determines if the graph is connected. A graph is considered connected if
     * all vertices are reachable from any starting vertex. This method performs
     * a breadth-first search (BFS) traversal to count the number of vertices
     * that can be reached from the first vertex and compares it with the total
     * number of vertices in the graph.
     *
     * @return true if all vertices are connected; false otherwise
     */
    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorBFS(this.vertices[0]);
        int counter = 0;

        while (it.hasNext()) {
            it.next();
            counter++;
        }

        return (counter == this.numVertices);
    }

    /**
     * Returns the number of vertices currently present in the graph.
     *
     * @return the integer number of vertices in the graph
     */
    @Override
    public int size() {
        return this.numVertices;
    }

    /**
     * Retrieves the array of vertices in the graph.
     *
     * @return an array containing the vertices of the graph
     */
    public T[] getVertices() {
        return vertices;
    }

    /**
     * Retrieves the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    public int getNumVertices() {
        return numVertices;
    }
}
