package viewers.utils;

public enum ProgressBarType {
    INFO("blue-bar"),
    ELIXIR_INFO("violet-bar"),
    GOLD_INFO("gold-bar"),
    HIT_POINTS("green-bar"),
    REMAINED_TIME("green-bar");

    private String id;

    ProgressBarType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
