package guitar.enumeration;

public enum Builder {

    FENDER, MARTIN;

    @Override
    public String toString() {
        switch (this) {
            case FENDER: return "fender";
            case MARTIN: return "martin";
            default: return "";
        }
    }
}
