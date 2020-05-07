import java.util.Stack;
public class Player
{
    private Room currentRoom;
    private Stack<Room> salas;
    public Player()
    {
        salas = new Stack<Room>();
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
}
