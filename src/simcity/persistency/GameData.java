package simcity.persistency;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;
import simcity.model.GameEngine;
import simcity.model.Map;

public class GameData implements Serializable {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("map")
    private Map map;

    @JsonProperty("funds")
    private int funds;

    @JsonProperty("tax")
    private int taxRate;

    public GameData() {

    }

    public GameData(GameEngine engine) {
        this.date = engine.getDate();
        this.map = engine.getMap();
        this.funds = engine.getFunds();
        this.taxRate = engine.getRate();

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

}
