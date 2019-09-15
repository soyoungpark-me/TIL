package guitar;

import guitar.enumeration.Builder;
import guitar.enumeration.Type;
import guitar.enumeration.Wood;

public class GuitarSpec {

    private String model, numStrings;
    private Builder builder;
    private Type type;
    private Wood backWood, topWood;

    public GuitarSpec(String model, String numStrings, Builder builder, Type type, Wood backWood, Wood topWood) {
        this.model = model;
        this.numStrings = numStrings;
        this.builder = builder;
        this.type = type;
        this.backWood = backWood;
        this.topWood = topWood;
    }

    public boolean match(GuitarSpec otherSpec) {
        if (builder != otherSpec.getBuilder()) {
            return false;
        }

        if ((model != null) && (!model.equals("")) && (!model.equals(otherSpec.getModel()))) {
            return false;
        }

        if (numStrings != otherSpec.numStrings) {
            return false;
        }

        if (type != otherSpec.getType()) {
            return false;
        }

        if (backWood != otherSpec.getBackWood()) {
            return false;
        }

        if (topWood != otherSpec.getTopWood()) {
            return false;
        }

        return true;
    }

    public Builder getBuilder() {
        return builder;
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumStrings() {
        return numStrings;
    }

    public void setNumStrings(String numStrings) {
        this.numStrings = numStrings;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Wood getBackWood() {
        return backWood;
    }

    public void setBackWood(Wood backWood) {
        this.backWood = backWood;
    }

    public Wood getTopWood() {
        return topWood;
    }

    public void setTopWood(Wood topWood) {
        this.topWood = topWood;
    }

}
