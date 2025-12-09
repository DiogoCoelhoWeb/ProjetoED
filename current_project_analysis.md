# Análise Atual do Projeto: Labirinto da Glória

## Sumário

Após uma revisão detalhada do código e da estrutura do projeto, foi confirmado que as funcionalidades críticas anteriormente identificadas continuam por implementar. O `project_analysis_summary.md` anterior estava amplamente correto, e esta nova análise reflete o estado atual com base nos ficheiros presentes.

## 🔴 Funcionalidades Críticas em Falta (Prioridade Máxima)

Estas são as funcionalidades essenciais para que o jogo cumpra os requisitos do enunciado.

### 1. Carregamento de Mapas (JSON)

*   **Estado Atual:** Inexistente. A classe `MapManager.java` gere uma lista de objetos `Map` em memória, mas não há um mecanismo para carregar a estrutura completa de um mapa a partir de um ficheiro JSON. Apenas o `EnigmaLoader.java` existe, mas foca-se nos eventos de enigma.
*   **O que falta:**
    *   Criar uma classe dedicada (ex: `MapLoader.java` em `src/files`).
    *   Implementar a lógica para ler um ficheiro JSON que contenha a definição completa das salas, as suas conexões (arestas do grafo) e os eventos associados (tanto de sala quanto de aresta).
    *   Instanciar corretamente os objetos `Map`, `MapLocations` e o `NetworkGraph` com base nos dados do JSON.
*   **Impacto:** Sem esta funcionalidade, não é possível criar ou carregar mapas dinamicamente, limitando o jogo a mapas "hardcoded".

### 2. Integração do Modo Automático (Bots)

*   **Estado Atual:** Inexistente. Embora a classe `Bot.java` exista e herde de `Player` com alguma lógica de decisão (`chooseMove`), o `GameLoop.java` não a diferencia de um jogador humano. O método `playTurn` do `GameLoop` utiliza `reader.readLine()` para obter input, o que impede os bots de agirem automaticamente.
*   **O que falta:**
    *   Modificar o método `playTurn` no `GameLoop.java`.
    *   Adicionar uma verificação para determinar se o `currentPlayer` é uma instância de `Bot`.
    *   Se for um `Bot`, chamar `currentPlayer.chooseMove()` para que o bot decida a sua próxima ação, em vez de esperar por input do utilizador.
    *   Se for um `Player` (humano), manter a lógica de `reader.readLine()`.
*   **Impacto:** O jogo atualmente só pode ser jogado manualmente, não cumprindo o requisito de "Modo Automático".

### 3. Eventos de Troca de Posição (Swap)

*   **Estado Atual:** Inexistente. Não há classes de evento específicas em `src/events` para este fim (ex: `SwapEvent`), nem métodos na classe `Player.java` que permitam definir diretamente a `currentLocation` de um jogador de forma forçada, sem passar pelas validações de movimento do `moveTo`.
*   **O que falta:**
    *   Adicionar um método `public void setCurrentLocation(MapLocations newLocation)` na classe `Player` (ou semelhante) para permitir a alteração direta da posição do jogador, ignorando as verificações de movimento normal.
    *   Criar uma nova classe de evento (ex: `SwapEvent.java`) em `src/events` que implemente a lógica para:
        *   Trocar a posição de dois jogadores específicos.
        *   Trocar a posição de todos os jogadores aleatoriamente.
*   **Impacto:** Não é possível implementar eventos dinâmicos de manipulação de posição dos jogadores no jogo.

### 4. Relatórios de Jogo (JSON)

*   **Estado Atual:** Inexistente. Não há lógica implementada para capturar ou armazenar o histórico de eventos e movimentos durante uma partida, nem para gerar um ficheiro de relatório ao seu término.
*   **O que falta:**
    *   Implementar um mecanismo para registar os eventos chave do jogo (quem se moveu para onde, que eventos ocorreram, etc.) ao longo da partida.
    *   Criar uma funcionalidade (possivelmente no `GameLoop` ou numa nova classe de `ReportGenerator`) para serializar esse registo para um ficheiro JSON no final da partida.
*   **Impacto:** O requisito de auditoria do jogo através de relatórios JSON não é cumprido.

## 🟡 Funcionalidades Secundárias / Dependentes

Estas funcionalidades são importantes mas a sua implementação depende, em grande parte, das funcionalidades críticas listadas acima.

### 5. Editor de Mapas (`MapCreationMenu.java`)

*   **Estado Atual:** Esqueleto vazio. A classe `MapCreationMenu.java` apenas apresenta opções no menu, mas os métodos (`createRoom`, `linkRooms`, etc.) não contêm qualquer lógica de implementação.
*   **O que falta:**
    *   Implementar a lógica para permitir que o utilizador crie salas, as conecte, e configure os seus eventos via interface de consola.
    *   Integrar esta funcionalidade com um futuro `MapLoader` e `MapSaver` (para JSON) para que os mapas criados possam ser guardados e carregados.
*   **Impacto:** O requisito de um editor de mapas funcional não é cumprido.

### 6. SaveGame (Guardar/Carregar Estado do Jogo)

*   **Estado Atual:** Inexistente. Não há classes (`GameSaver.java`, `GameLoader.java`) ou lógica para guardar e carregar o progresso do jogo.
*   **O que falta:**
    *   Implementar classes para serializar o estado completo do jogo (posição dos jogadores, turnos, mapa atual, etc.) para JSON.
    *   Implementar a lógica para ler esse JSON e restaurar o estado do jogo.
*   **Impacto:** Essencialmente dependente da implementação do **Carregamento de Mapas (JSON)** para poder reconstruir o ambiente de jogo onde o save foi feito.

---

## Plano de Ação Recomendado

Para garantir um progresso eficiente e desanuviar os bloqueios, sugiro a seguinte ordem de prioridade:

1.  **Integração do Modo Automático (Bots):** É uma alteração relativamente contida no `GameLoop` que te permitirá testar o jogo com bots, facilitando a depuração e o desenvolvimento de outras funcionalidades.
2.  **Carregamento de Mapas (JSON):** Esta é uma base crucial. Uma vez implementada, desbloqueará o desenvolvimento do "Editor de Mapas" e do "SaveGame".
3.  **Eventos de Troca de Posição (Swap):** Pode ser implementada após a `Player.java` ter um `setter` para a localização.
4.  **Relatórios de Jogo (JSON):** Pode ser desenvolvida em paralelo ou como uma das últimas funcionalidades, uma vez que depende da estabilidade do fluxo de jogo.

Qual destas funcionalidades críticas preferes abordar primeiro?
