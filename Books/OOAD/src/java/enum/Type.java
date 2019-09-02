public enum Type {

    ACOUSTIC, ELECTIC;

    @Override
    public String toString() {
        switch (this) {
            case ACOUSTIC: return "acoustic";
            case ELECTIC: return "electric";
            default: return "";
        }
    }
}
