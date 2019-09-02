import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

    private List guitars;

    public Inventory() {
        this.guitars = new LinkedList();
    }

    public void addGuitar(String serialNumber, double price, Builder builder, String model, Type type, Wood backWood, Wood topWood) {
        Guitar guitar = new Guitar(serialNumber, price, builder, model, type, backWood, topWood);

        this.guitars.add(guitar);
    }

    public List search(GuitarSpec searchSpec) {
        List matchingGuitars = new ArrayList();

        for (Iterator i = this.guitars.iterator(); i.hasNext();) {
            Guitar guitar = (Guitar) i.next();

            if (searchGuitar.getBuilder() != guitar.getBuilder()) {
                continue;
            }

            String model = searchGuitar.getModel();
            if ((model != null) && (!model.equals("")) && (!model.equals(guitar.getModel()))) {
                continue;
            }

            if (searchGuitar.getType() != guitar.getType()) {
                continue;
            }

            if (searchGuitar.getBackWood() != guitar.getBackWood()) {
                continue;
            }

            if (searchGuitar.getTopWood() != guitar.getTopWood()) {
                continue;
            }

            matchingGuitars.add(guitar);
        }

        return matchingGuitars;
    }

}
