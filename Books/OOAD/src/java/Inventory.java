import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

    private List guitars;

    public Inventory() {
        this.guitars = new LinkedList();
    }

    public void addGuitar(String serialNumber, double price, String model, Builder builder, Type type, Wood backWood, Wood topWood) {
        Guitar guitar = new Guitar(serialNumber, price, new GuitarSpec(model, builder, type, backWood, topWood));

        this.guitars.add(guitar);
    }

    public List search(GuitarSpec searchSpec) {
        List matchingGuitars = new ArrayList();

        for (Iterator i = this.guitars.iterator(); i.hasNext();) {
            Guitar guitar = (Guitar) i.next();

            if (searchSpec.getBuilder() != guitar.getSpec().getBuilder()) {
                continue;
            }

            String model = searchSpec.getModel();
            if ((model != null) && (!model.equals("")) && (!model.equals(guitar.getSpec().getModel()))) {
                continue;
            }

            if (searchSpec.getType() != guitar.getSpec().getType()) {
                continue;
            }

            if (searchSpec.getBackWood() != guitar.getSpec().getBackWood()) {
                continue;
            }

            if (searchSpec.getTopWood() != guitar.getSpec().getTopWood()) {
                continue;
            }

            matchingGuitars.add(guitar);
        }

        return matchingGuitars;
    }

}
