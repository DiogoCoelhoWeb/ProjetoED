package files;

import events.ChoiceEvent;
import events.ChoiceEventManager;
import events.EnigmaEvent;
import events.ImpossibleEnigma;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class EnigmaLoader {

    private static final String ENIGMAS_PATH = "data/choiceEvents/enigmas.json";
    public static ChoiceEventManager loadEnigmas() {
        JSONParser parser = new JSONParser();
        ChoiceEventManager manager = new ChoiceEventManager();

        System.out.println("--- 🧩 Carregamento de Eventos de Escolha Iniciado ---");

        try (FileReader reader = new FileReader(ENIGMAS_PATH)) {

            Object object = parser.parse(reader);
            JSONObject rootObject = (JSONObject) object;

            loadQuizzes(rootObject, manager);

            loadDeadEnds(rootObject, manager);

            loadLeverEvents(rootObject, manager);

            System.out.println("\n--- Carregamento Concluído com Sucesso ---");

        } catch (Exception e) {
            System.err.println("ERRO FATAL durante o carregamento do JSON. Detalhes:");
            e.printStackTrace();
        }
        return manager;
    }


    private static void loadQuizzes(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray quizzesArray = (JSONArray) rootObject.get("quizzes");

        if (quizzesArray != null) {
            System.out.println("\n## Quizzes/Enigmas Normais Carregados (Total: " + quizzesArray.size() + ")");

            for (Object item : quizzesArray) {
                try {
                    JSONObject quiz = (JSONObject) item;
                    String question = (String) quiz.get("question");


                    Long correctChoiceLong = (Long) quiz.get("correct");

                    if (correctChoiceLong == null) {
                        System.out.println("Aviso: Quiz ID " + quiz.get("id") + " ignorado por falta do campo 'correct'.");
                        continue;
                    }

                    int correctChoice = correctChoiceLong.intValue();

                    ChoiceEvent event = new EnigmaEvent(question, correctChoice);

                   JSONArray options = (JSONArray) quiz.get("choices");
                    for (Object obj : options) {
                        String choice = (String) obj;
                        event.addChoice(choice);
                    }
                    manager.addEvent(event);
                } catch (Exception e) {
                    System.err.println("Erro ao processar um item 'quiz'.");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aviso: Array 'quizzes' não encontrado no JSON.");
        }
    }

    private static void loadDeadEnds(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray deadEndArray = (JSONArray) rootObject.get("dead_end_quotes");

        if (deadEndArray != null) {
            System.out.println("\n## Dead Ends/Quotes Impossíveis Carregados (Total: " + deadEndArray.size() + ")");

            for (Object item : deadEndArray) {
                try {
                    JSONObject deadEnd = (JSONObject) item;
                    String question = (String) deadEnd.get("question");
                    String responseQuote = (String) deadEnd.get("response_quote");

                    ChoiceEvent event = new ImpossibleEnigma(question, responseQuote);
                    JSONArray options = (JSONArray) deadEnd.get("choices");
                    for (Object obj : options) {
                        String choice = (String) obj;
                        event.addChoice(choice);
                    }
                    manager.addEvent(event);
                } catch (Exception e) {
                    System.err.println("Erro ao processar um item 'dead_end_quote'.");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aviso: Array 'dead_end_quotes' não encontrado no JSON.");
        }
    }

    private static void loadLeverEvents(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray leverEventsArray = (JSONArray) rootObject.get("lever_events");

        if (leverEventsArray != null) {
            System.out.println("\n## Lever Events loaded (Total: " + leverEventsArray.size() + ")");

            for (Object item : leverEventsArray) {
                try {
                    JSONObject quiz = (JSONObject) item;
                    String question = (String) quiz.get("question");

                    Long correctChoiceLong = (Long) quiz.get("correct");

                    if (correctChoiceLong == null) {
                        System.out.println("Aviso: Lever Event ID " + quiz.get("id") + " ignorado por falta do campo 'correct'.");
                        continue;
                    }

                    int correctChoice = correctChoiceLong.intValue();

                    ChoiceEvent event = new LeverEvent(question, correctChoice);

                 JSONArray options = (JSONArray) quiz.get("choices");
                    for (Object obj : options) {
                        String choice = (String) obj;
                        event.addChoice(choice);
                    }
                    manager.addEvent(event);
                } catch (Exception e) {
                    System.err.println("Erro ao processar um item 'lever_event'.");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aviso: Array 'lever_events' não encontrado no JSON.");
        }
    }
}
