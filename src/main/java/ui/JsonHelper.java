package ui;

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


public class JsonHelper {

    private final int LEADERBOARDSIZE = 100;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final File file;
    private JsonObject[] array = new JsonObject[LEADERBOARDSIZE];


    public JsonHelper(String username, int numberOfMoves, long seconds) {
        file=new File(determineFilePath());
        JsonObject newRecord=new JsonObject(username,numberOfMoves,seconds);
        initializeArray();
        ArrayList<JsonObject> jsonObjects = new ArrayList<>(Arrays.asList(array));
        Comparator<JsonObject> jsonObjectComparator= Comparator.comparing(JsonObject::getNumberOfMoves).thenComparing(JsonObject::getSeconds).thenComparing(JsonObject::getUsername);
        List<JsonObject> orderedJsonObjects=checkRecords(jsonObjects,jsonObjectComparator,newRecord);
        array = orderedJsonObjects.toArray(array);

    }


    public void initializeArray() {
        if (file.exists()) {
            try {
                array = objectMapper.readValue(file, JsonObject[].class);
            } catch (Exception e) {
                Logger.debug("Cannot read values from the specified JSON file");
            }
        } else {
            Arrays.fill(array, new JsonObject("default", Integer.MAX_VALUE, Long.MAX_VALUE));
        }
    }

    private String determineFilePath(){
        System.out.println("EEEEEEEEE:"+System.getProperty("user.dir"));
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

    public void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(objectMapper.writeValueAsString(array));
        } catch (IOException e) {
            Logger.debug("Saving failed. Cannot write to specified file.");
        }
    }

    private static class JsonObject {
        private String username;

        private int numberOfMoves;

        private long seconds;

        JsonObject(){

        }

        JsonObject(String username, int numberOfMoves, long seconds) {
            this.username = username;
            this.numberOfMoves = numberOfMoves;
            this.seconds = seconds;
        }

        public String getUsername() {
            return username;
        }

        public int getNumberOfMoves() {
            return numberOfMoves;
        }

        public long getSeconds() {
            return seconds;
        }

    }
}
