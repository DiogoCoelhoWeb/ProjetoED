# Explicação do Algoritmo do Caminho Mais Curto (`iteratorShortestPath`)

Este documento explica o funcionamento do método `iteratorShortestPath` implementado na classe `Graph.java`.

## Visão Geral

O algoritmo implementado encontra o **caminho mais curto** entre dois vértices num **grafo não ponderado**. "Caminho mais curto" aqui significa o caminho com o menor número de arestas.

Para isso, o algoritmo utiliza uma versão modificada do **Breadth-First Search (BFS)**, ou Travessia em Largura. A razão pela qual o BFS é usado é que ele explora o grafo "camada por camada" a partir do vértice inicial. Isso garante que, na primeira vez que o vértice de destino é alcançado, o caminho percorrido é um dos mais curtos possíveis em termos de número de arestas.

Este método é **diferente do algoritmo de Dijkstra**, que é usado para encontrar o caminho mais curto em grafos *ponderados* (onde cada aresta tem um custo diferente).

## Implementação Passo a Passo

O método pode ser dividido em várias partes principais:

### 1. Inicialização

Antes de começar a busca, o algoritmo prepara todas as estruturas de dados necessárias.

```java
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
```

- **`startIndex`, `targetIndex`**: Converte os vértices de início e de destino para os seus índices internos no array de vértices.
- **`traversalQueue`**: Uma fila (`Queue`) que irá gerir a ordem de visita dos vértices, essencial para o funcionamento do BFS.
- **`resultList`**: Uma lista que irá armazenar o caminho final, se for encontrado.
- **`visited`**: Um array de booleanos para marcar os vértices que já foram visitados e evitar ciclos.
- **`predecessor`**: Este é o array "chave" para reconstruir o caminho. Para cada vértice `i`, `predecessor[i]` irá armazenar o índice do vértice que o antecedeu no caminho mais curto desde a origem. É inicializado com `-1` para todos os vértices.

### 2. Início da Busca

A busca começa no vértice inicial.

```java
    traversalQueue.enqueue(startIndex);
    visited[startIndex] = true;
```

- O vértice inicial (`startIndex`) é adicionado à fila e marcado como visitado.

### 3. Travessia e Busca (O Loop Principal)

O loop principal executa a travessia BFS. Ele continua enquanto houver vértices na fila para serem processados.

```java
    while (!traversalQueue.isEmpty()) {
        int x = traversalQueue.dequeue();

        // ... (ver os próximos passos)
```

- A cada iteração, um vértice `x` é removido da frente da fila.

### 4. Verificação do Destino e Reconstrução do Caminho

Dentro do loop, a primeira coisa a fazer é verificar se o vértice atual `x` é o nosso destino.

```java
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
```

- Se `x` for o `targetIndex`, o caminho mais curto foi encontrado.
- **Reconstrução**: Para obter o caminho, começamos do `targetIndex` e "voltamos para trás" usando o array `predecessor`.
- Uma pilha (`LinkedStack`) é usada para inverter o caminho (já que o obtemos do fim para o início).
- Os vértices são movidos da pilha para a `resultList` para obter a ordem correta (do início para o fim).
- Finalmente, um iterador para a `resultList` contendo o caminho é retornado.

### 5. Explorar Vizinhança

Se o vértice atual `x` não for o destino, o algoritmo explora os seus vizinhos.

```java
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[x][i] && !visited[i]) {
                visited[i] = true;
                predecessor[i] = x;
                traversalQueue.enqueue(i);
            }
        }
    }
```

- O código itera por todos os outros vértices `i` para ver se são vizinhos de `x`.
- Se existir uma aresta (`adjMatrix[x][i]`) e o vizinho `i` ainda não tiver sido visitado:
    - O vizinho `i` é marcado como visitado.
    - O predecessor de `i` é definido como `x` (`predecessor[i] = x`). Este é o passo crucial onde guardamos o "rasto" do caminho.
    - O vizinho `i` é adicionado à fila para ser processado nas próximas iterações.

### 6. Caminho Não Encontrado

Se o loop principal terminar (a fila ficar vazia) e o destino nunca tiver sido alcançado, significa que não existe um caminho entre os vértices de início e de destino.

```java
    return resultList.iterator(); // Path not found
}
```

- Neste caso, o método retorna um iterador para a `resultList`, que permaneceu vazia.
