package simcity.events;

public class InteractMode {

    private Mode currentMode;
    private BuildItem builtItem;
    private static InteractMode instance = null;

    private InteractMode() {
        this.currentMode = Mode.NORMAL;
        this.builtItem = BuildItem.NOTHING;
    }

    public static InteractMode getInstance() {
        if (instance == null) {
            instance = new InteractMode();
        }

        return instance;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public BuildItem getCurrentBuildItem() {
        return builtItem;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    public void setCurrentBuildItem(BuildItem builditem) {
        this.builtItem = builditem;
    }
}
