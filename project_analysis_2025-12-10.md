# Análise do Projeto: Labirinto da Glória

**Data:** 10 de Dezembro de 2025
**Estado:** Em Desenvolvimento

## 1. Visão Geral do Estado Atual

O projeto apresenta uma base sólida para a lógica de jogo (`GameLoop`, `Player`, `Bot`) e para a estrutura de dados do mapa (`NetworkGraph`, `MapLocations`). No entanto, funcionalidades cruciais de persistência de dados (carregamento de mapas e guardar jogo) e ferramentas de edição estão em falta ou incompletas, impedindo que o jogo funcione conforme os requisitos do enunciado.

### ✅ O que está implementado e funcional:
*   **Motor de Jogo (`GameLoop`):** O ciclo principal do jogo está funcional. Suporta turnos, movimento de jogadores, e verificação de condições de vitória (encontrar o Tesouro).
*   **Integração de Bots (`Bot`):** Ao contrário de análises anteriores, os **Bots estão implementados e integrados**. A classe `Bot` possui lógica de decisão (`chooseMove`) com 20% de chance de procurar o caminho mais curto para o tesouro e lógica de exploração aleatória. O `GameLoop` executa corretamente os turnos dos bots automaticamente.
*   **Estrutura de Grafos (`graph`):** A implementação do grafo (`NetworkGraph`) parece robusta e suporta as operações necessárias de navegação e cálculo de caminhos (BFS, Dijkstra/Shortest Path).
*   **Gestão de Eventos (`events`):** Existem várias classes de eventos (`ChoiceEvent`, `EnigmaEvent`, `LeverEvent`, `ImpossibleEnigma`) e um carregador parcial (`EnigmaLoader`) para carregar perguntas de um JSON.
*   **Salvar Mapa (`MapSave`):** Existe lógica para exportar a estrutura do mapa (salas e corredores) para JSON.

### ❌ O que está em falta (Crítico):
1.  **Carregamento de Mapas (JSON):** Embora exista código para *salvar* mapas (`MapSave`), **não existe código para carregar mapas completos** a partir do JSON especificado no enunciado. O `EnigmaLoader` apenas carrega perguntas, não a topologia (salas e conexões) do mapa. Sem isto, o jogo depende de mapas criados manualmente no código (hardcoded).
2.  **Editor de Mapas (`MapCreationMenu`):** A classe existe mas é apenas um "esqueleto". Os métodos `createRoom`, `linkRooms`, etc., estão vazios. Não é possível criar mapas via menu.
3.  **Relatórios de Jogo (JSON):** O enunciado exige um ficheiro JSON com o relatório final do jogo (vencedor, turnos, histórico de posições). O `GameLoop` atualmente apenas imprime mensagens na consola e não gera este ficheiro.
4.  **Save/Load de Jogo (Estado da Partida):** Existe o `MapSave` (estrutura do labirinto), mas falta o **SaveGame** (estado atual da partida: onde estão os jogadores, quantos turnos faltam, etc.). O enunciado especifica um JSON de "Save File" diferente do "Map File".
5.  **Eventos de Troca (Swap):** Não existem eventos implementados para trocar jogadores de posição, nem métodos na classe `Player` para forçar uma mudança de posição sem validar o movimento.

---

## 2. To-Do List (Priorizada)

Esta lista organiza as tarefas por ordem de dependência e importância.

### Prioridade Alta (Bloqueantes)
- [ ] **Criar `MapLoader.java` (`src/files/`):**
    - Implementar leitura do JSON de mapa (ver formato no PDF).
    - Criar instâncias de `MapLocations` (Salas) baseadas no JSON.
    - Criar as conexões (Corredores) no `NetworkGraph`.
    - Associar os eventos corretos às salas/corredores.
- [ ] **Implementar Editor de Mapas (`src/menus/MapCreationMenu.java`):**
    - Implementar lógica de `createRoom` (pedir nome, tipo, evento).
    - Implementar lógica de `linkRooms` (criar aresta no grafo).
    - Integrar com `MapSave` para exportar o mapa criado.

### Prioridade Média (Requisitos de Avaliação)
- [ ] **Implementar Relatórios de Jogo (`src/game/GameLoop.java` ou nova classe):**
    - Criar estrutura para armazenar histórico de jogadas em memória.
    - No final do jogo (`checkWinCondition`), gerar o JSON conforme o enunciado ("Save File" / Relatório) com `finished: true`, `winner`, `path_history`, etc.
- [ ] **Implementar Save/Load da Partida (`src/files/GameSaver.java`, `src/files/GameLoader.java`):**
    - Permitir salvar o estado atual a meio do jogo (jogadores, posições, turnos).
    - Permitir retomar um jogo a partir desse ficheiro.

### Prioridade Baixa (Funcionalidades Extra)
- [ ] **Adicionar `SwapEvent` (`src/events/`):**
    - Criar evento que troca a posição de dois jogadores.
    - Adicionar método `forceLocation(MapLocations loc)` na classe `Player` para suportar isto.
- [ ] **Melhorar IA do Bot:**
    - Fazer o bot "aprender" com caminhos sem saída (evitar repetir erros).

---

## 3. Sugestões de Melhoria Técnica

1.  **Tratamento de Exceções JSON:** O `EnigmaLoader` imprime a stack trace completa em caso de erro. Seria melhor apresentar mensagens de erro amigáveis ao utilizador se o ficheiro estiver corrompido.
2.  **Separação UI/Lógica:** O `GameLoop` mistura muita lógica de jogo com `System.out.println`. Mover as mensagens de interface para uma classe `GameUI` ou `View` tornaria o código mais limpo e facilitaria a tradução ou mudança de interface no futuro.
3.  **Refatorização de IDs:** O `Player` usa um contador estático para IDs. Ao carregar um jogo salvo, é preciso garantir que este contador é atualizado para não gerar IDs duplicados para novos jogadores.
