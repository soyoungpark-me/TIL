import java.util.Iterator;
import java.util.List;

public class FindGuitarTester {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        initializeInventory(inventory);

        GuitarSpec whatErinLikes = new GuitarSpec("Stratocastor", "12", Builder.FENDER, Type.ELECTIC, Wood.ALDER, Wood.ALDER);
        List matchingGuitars = inventory.search(whatErinLikes);

        if (!matchingGuitars.isEmpty()) {
            System.out.println("Erin, you might like these guitars : ");
            for (Iterator i = matchingGuitars.iterator(); i.hasNext();) {
                Guitar guitar = (Guitar) i.next();

                System.out.println("Erin, you might like this " +
                        guitar.getSpec().getBackWood() + " " +
                        guitar.getSpec().getModel() + " " +
                        guitar.getSpec().getType() + " guitar: \n " +
                        guitar.getSpec().getBackWood() + " back and sides, \n " +
                        guitar.getSpec().getTopWood() + " top.\n You can have it for only $" +
                        guitar.getPrice() + "!");
            }
        } else {
            System.out.println("Sorry, Erin, we have nothing for you");
        }

    }

    private static void initializeInventory(Inventory inventory) {
        inventory.addGuitar("V95693", 1499.95, "12", "Stratocastor", Builder.FENDER, Type.ELECTIC, Wood.ALDER, Wood.ALDER);
        inventory.addGuitar("V9512", 1549.95, "12", "Stratocastor", Builder.FENDER, Type.ELECTIC, Wood.ALDER, Wood.ALDER);
    }
}
