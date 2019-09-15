package guitar;

import guitar.enumeration.Builder;
import guitar.enumeration.Type;
import guitar.enumeration.Wood;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

    private List guitars;

    public Inventory() {
        this.guitars = new LinkedList();
    }

    public void addGuitar(String serialNumber, double price, String numStrings, String model, Builder builder, Type type, Wood backWood, Wood topWood) {
        Guitar guitar = new Guitar(serialNumber, price, new GuitarSpec(model, numStrings, builder, type, backWood, topWood));

        this.guitars.add(guitar);
    }

    public List search(GuitarSpec searchSpec) {
        List matchingGuitars = new ArrayList();

        for (Iterator i = this.guitars.iterator(); i.hasNext();) {
            Guitar guitar = (Guitar) i.next();

            if (searchSpec.match(guitar.getSpec())) {
                matchingGuitars.add(guitar);
            }
        }

        return matchingGuitars;
    }

}
