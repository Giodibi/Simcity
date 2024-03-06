package simcity.persistency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class Persistency {

    private GameData gd;

    public Persistency() {
        File directory = new File("data/Saves");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveGame(int slotNum) {

        File saveFile = new File("data/Saves/save" + slotNum + ".json");
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
            objectMapper.registerModule(new JavaTimeModule());

            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            objectMapper.writeValue(new FileWriter(saveFile), gd);
        } catch (IOException ex) {
            Logger.getLogger(Persistency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean loadGame(int slotNum) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (!(new File("data/Saves/save" + slotNum + ".json").exists())) {
            return false;
        } else {
            try {
                gd = objectMapper.readValue(Paths.get("data/Saves/save" + slotNum + ".json").toFile(), GameData.class);
            } catch (IOException ex) {
                Logger.getLogger(Persistency.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;

    }

    public void setGameData(GameData data) {
        this.gd = data;
    }

    public GameData getGameData() {
        return this.gd;
    }
}
