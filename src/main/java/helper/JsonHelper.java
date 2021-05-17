package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.tinylog.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Helper class for easier serializing and deserializing for JSON.
 */
public class JsonHelper {

    /**
     * Value representing the maximum number of records stored.
     */
    public static final int LEADERBOARD_SIZE = 100;

    /**
     * The default username, which is used in auto-generated records.
     */
    public static final String DEFAULT_USERNAME ="*";

    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final File file=new File(determineFilePath());
    private JsonObject[] array = new JsonObject[LEADERBOARD_SIZE];


    /**
     * Creates a {@code JsonHelper} object and also calls the {@code JsonObject} constructor with the given parameters.
     * Creates and ordered array of the records.
     *
     * @param username {@code String} representation of the user's name
     * @param numberOfMoves the number of moves taken to reach the goal
     * @param seconds the time taken in seconds to reach the goal
     */
    public JsonHelper(String username, int numberOfMoves, long seconds) {
        JsonObject newRecord=new JsonObject(username,numberOfMoves,seconds);
        initializeArray();
        ArrayList<JsonObject> jsonObjects = new ArrayList<>(Arrays.asList(array));
        Comparator<JsonObject> jsonObjectComparator= Comparator.comparing(JsonObject::getNumberOfMoves).thenComparing(JsonObject::getSeconds).thenComparing(JsonObject::getUsername);
        List<JsonObject> orderedJsonObjects=checkRecords(jsonObjects,jsonObjectComparator,newRecord);
        array = orderedJsonObjects.toArray(array);

    }


    private void initializeArray() {
        if (file.exists()) {
            array=load();
        } else {
            Arrays.fill(array, new JsonObject("*", Integer.MAX_VALUE, Long.MAX_VALUE));
        }
    }

    private static String determineFilePath(){
        String UserHome = System.getProperty("user.home");
        String path=null;
        try {
            path=Files.createDirectories(Paths.get(UserHome+File.separator+".BallPuzzle")).toString()+File.separator+"leaderboardSave.json";
        }catch (IOException e){
            Logger.debug("The program doesn't have the needed permissions to write in the home directory.");
        }
        return path;
    }

    private List<JsonObject> checkRecords(List<JsonObject> jsonObjects, Comparator<JsonObject> jsonObjectComparator,JsonObject newRecord){
        jsonObjects.sort(jsonObjectComparator);
        int index = -1;
        for (JsonObject jsonObject : jsonObjects) {
            if(jsonObjectComparator.compare(jsonObject,newRecord)>0){
                index = jsonObjects.indexOf(jsonObject);
                break;
            }
        }
        if (index > -1) {
            jsonObjects.add(index, newRecord);
            jsonObjects.remove(jsonObjects.size() - 1);
        }
        return jsonObjects;
    }

    /**
     * Writes the currently used record array to the save file.
     */
    public void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(objectMapper.writeValueAsString(array));
        } catch (IOException e) {
            Logger.debug("Saving failed. Cannot write to specified file.");
        }
    }

    /**
     * {@return the save file's content in a {@code JsonObject} array}
     */
    public static JsonObject[] load(){
        try {
            return objectMapper.readValue(file, JsonObject[].class);
        } catch (Exception e) {
            Logger.debug("Cannot read values from the specified JSON file");
            return null;
        }
    }

    /**
     * Deletes the current save file.
     */
    public static void deleteSave(){
        file.delete();
    }

    /**
     * {@return a {@code File} object, which corresponds to the save file}
     */
    public static File getFile() {
        return file;
    }

    /**
     * Helper class representing an object with all the attributes, which are need to be saved.
     */
    public static class JsonObject {
        private String username;

        private int numberOfMoves;

        private long seconds;

        /**
         * No-args constructor for the {@code JsonObject} class.
         */
        JsonObject(){

        }

        /**
         * Creates a {@code JsonObject} with the given parameters.
         *
         * @param username {@code String} representation of the user's name
         * @param numberOfMoves the number of moves taken to reach the goal
         * @param seconds the time taken in seconds to reach the goal
         */
        JsonObject(String username, int numberOfMoves, long seconds) {
            this.username = username;
            this.numberOfMoves = numberOfMoves;
            this.seconds = seconds;
        }

        /**
         * {@return the username}
         */
        public String getUsername() {
            return username;
        }

        /**
         * {@return the number of moves}
         */
        public int getNumberOfMoves() {
            return numberOfMoves;
        }

        /**
         * {@return the time represented in seconds}
         */
        public long getSeconds() {
            return seconds;
        }

    }
}
