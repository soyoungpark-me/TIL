import door.DogDoor;
import door.Remote;

public class DogDoorTester {

    public static void main(String[] args) {
        DogDoor door = new DogDoor();
        Remote remote = new Remote(door);

        System.out.println();
        remote.pressButton();

        System.out.println("Fido has gone outside...");
        System.out.println("\nFido's all done...");

        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {}

        System.out.println("\n...but he's stuck outside!");
        System.out.println("\nFido starts barking...");
        System.out.println("\n...so Gina grapb the remote control.");
        remote.pressButton();
        System.out.println("\nFido's back inside...");
    }

}
