public enum Wood {

    ALDER, SITKA;

    @Override
    public String toString() {
        switch (this) {
            case ALDER: return "alder";
            case SITKA: return "sitka";
            default: return "";
        }
    }
}
