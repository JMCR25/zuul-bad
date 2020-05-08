import java.util.Stack;
import java.util.ArrayList;
public class Player
{
    private Room currentRoom;
    private Stack<Room> salas;
    private ArrayList<Item> mochila;
    private int pesoMax;
    public Player(int fuerza)
    {
        salas = new Stack<Room>();
        mochila = new ArrayList<Item>();
        pesoMax = fuerza;
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
        cont = pesoMax - it.getWeight();
        if (it == null) {
            System.out.println("That item isn't here!");
            look();
            System.out.println();
        }
        else{
            if ((cont >= 0) && it.getDisp() == true){
                mochila.add(it);
                objs.remove(cont2);
                pesoMax -= it.getWeight();
                System.out.println("You have: " + pesoMax + " kg(s) free");
                System.out.println();
            }
            else 
                System.out.println("This item can't be carried!");
        }
    }

    public void showItems() {
        String objetos = "You have in inventory: ";
        int peso = 0;
        for (Item inventario : mochila) {
            objetos += "\n" + inventario.getItem();
            peso += inventario.getWeight();
        }
        System.out.println(objetos + "\n" + "The total weight is: " + peso);
    }

    public void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String objeto = command.getSecondWord();

        // Try to leave current room.
        Item it = null;
        int cont = 0;
        int cont2 = 0;
        for (Item obj : mochila) {
            if (obj.getId().equals(command.getSecondWord())) {
                it = obj;
                cont2 = cont;
            }
            cont++;    
        }
        if (it == null) {
            System.out.println("You don't have that item!");
        }
        else {
            currentRoom.addPremadeItem(it);
            pesoMax += it.getWeight();
            mochila.remove(cont2);
            System.out.println("You have: " + pesoMax + " kg(s) free");
            System.out.println();
        }
    }

    public void drinkPotion(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drink what?");
            return;
        }
        String objeto = command.getSecondWord();
        boolean drinked = false;
        int cont = 0;
        int pesoAumento = pesoMax;
        for (Item obj : mochila){
            if (obj.getId().equals("potion")) {
                drinked = true;
            }
            pesoAumento += obj.getWeight();
            if (drinked == false){
                cont++; 
            }
        }
        if (drinked = false) {
            System.out.println("You don't have that item!");
        }
        else {
            pesoMax += pesoAumento + mochila.get(cont).getWeight();
            mochila.remove(cont);
            System.out.println("You have: " + pesoMax + " kg(s) free");
            System.out.println();
        }
    }
}

