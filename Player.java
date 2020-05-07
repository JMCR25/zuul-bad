import java.util.Stack;
import java.util.ArrayList;
public class Player
{
    private Room currentRoom;
    private Stack<Room> salas;
    private ArrayList<Item> mochila;
    public Player()
    {
        salas = new Stack<Room>();
        mochila = new ArrayList<Item>();
    }

    public void firstLocation(Room sala) { 
        currentRoom = sala;
    }

    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            salas.add(currentRoom);
            currentRoom = nextRoom;
            look();
            System.out.println();
        }
    }

    public void back() {
        if (salas.size() > 0)
            currentRoom = salas.pop();
        else
            System.out.println("No more backs");
    }

    public void takeItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String objeto = command.getSecondWord();

        // Try to leave current room.
        Item it = null;
        ArrayList<Item> objs = currentRoom.getItems();
        int cont = 0;
        int cont2 = 0;
        for (Item obj : objs) {
            if (obj.getId().equals(command.getSecondWord())) {
                it = obj;
                cont2 = cont;
            }
            cont++;    
        }
        if (it == null) {
            System.out.println("That item isn't here!");
            look();
            System.out.println();
        }
        else{
            mochila.add(it);
            objs.remove(cont2);
            System.out.println();
        }
    }
}
