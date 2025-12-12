package files;

import events.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class EnigmaLoader {

    private static final String ENIGMAS_PATH = "data/choiceEvents/enigmas.json";

    /**
     * Loads enigma events from a JSON file and initializes a ChoiceEventManager instance.
     * The method parses the JSON content, extracts various types of enigma events
     * (quizzes, dead ends, and lever events), and populates the ChoiceEventManager
     * with these events.
     *
     * @return an instance of ChoiceEventManager containing all the loaded enigma events
     */
    public static ChoiceEventManager loadEnigmas() {
        JSONParser parser = new JSONParser();
        ChoiceEventManager manager = new ChoiceEventManager();

        try (FileReader reader = new FileReader(ENIGMAS_PATH)) {

            Object object = parser.parse(reader);
            JSONObject rootObject = (JSONObject) object;

            loadQuizzes(rootObject, manager);

            loadDeadEnds(rootObject, manager);

            loadLeverEvents(rootObject, manager);


        } catch (Exception e) {
            System.err.println("ERRO FATAL durante o carregamento do JSON. Detalhes:");
            e.printStackTrace();
        }
        return manager;
    }


    /**
     * Loads quizzes from the provided JSON object and adds them to the given
     * ChoiceEventManager. Each quiz in the JSON array is parsed into an EnigmaEvent
     * and its choices are added. If the JSON format is incorrect or a required field
     * is missing, the quiz is skipped and an error message is printed.
     *
     * @param rootObject the root JSON object containing the quizzes data under the "quizzes" key
     * @param manager the ChoiceEventManager to which parsed EnigmaEvents will be added
     */
    private static void loadQuizzes(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray quizzesArray = (JSONArray) rootObject.get("quizzes");

        if (quizzesArray != null) {

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

    /**
     * Loads "dead end" events from the provided JSON object into the given ChoiceEventManager.
     * The method processes an array of "dead_end_quotes" entries in the JSON object,
     * each defining an impossible enigma event with its description, response quote,
     * and a set of associated choices. These events are added to the ChoiceEventManager for use in the application.
     *
     * @param rootObject the root JSON object containing the "dead_end_quotes" array
     * @param manager the ChoiceEventManager where the parsed events will be added
     */
    private static void loadDeadEnds(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray deadEndArray = (JSONArray) rootObject.get("dead_end_quotes");

        if (deadEndArray != null) {

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

    /**
     * Loads lever events from the provided JSON object and adds them to the specified ChoiceEventManager.
     * The method extracts lever events represented as JSON objects, processes their attributes such as
     * the question, choices, and the correct choice, and creates LeverEvent instances which are added
     * to the manager. Logs messages indicating the success or failure of the loading process for each event.
     *
     * @param rootObject the JSON object containing the "lever_events" array
     * @param manager the ChoiceEventManager instance where the loaded events will be added
     */
    private static void loadLeverEvents(JSONObject rootObject, ChoiceEventManager manager) {
        JSONArray leverEventsArray = (JSONArray) rootObject.get("lever_events");

        if (leverEventsArray != null) {

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
