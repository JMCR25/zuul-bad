import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> salas;
    private ArrayList<Item> mochila;
    private int pesoMaximo;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        salas = new Stack<Room>();
        mochila = new ArrayList<Item>();
        pesoMaximo = 16;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room salaPrincipal, sotano, prision, capilla, torreon, salaDelTrono, salaDelTesoro, fuenteDeLaSabiduria, aposentosDelRey, acantilado;
        // create the rooms
        salaPrincipal = new Room("in front of the entrance");
        sotano = new Room("on a dark basement");
        prision = new Room("on the scary prisión");
        capilla = new Room("on the disturbing chapel");
        torreon = new Room("on a decrepit tower");
        salaDelTrono = new Room("on the majestic throne room");
        salaDelTesoro = new Room("on a filled with gold treasure room");
        fuenteDeLaSabiduria  = new Room("the new sage king");
        aposentosDelRey = new Room("in a room which smells weird");
        acantilado = new Room("in a dangerous zone");
        sotano.addItem("a shining sword", "sword", 1);
        capilla.addItem("a carafe of holy water","holy", 5);
        salaDelTesoro.addItem("a Zeus statue", "statue", 100);
        prision.addItem("a rusty shackles", "shackles", 1);
        torreon.addItem("a light armor", "armor", 11);
        salaDelTesoro.addItem("a filled of diamonds chest", "chest", 5);
        // initialise room exits
        salaPrincipal.setExit("east", capilla);
        salaPrincipal.setExit("west", sotano);
        sotano.setExit("south", prision);
        sotano.setExit("east", salaPrincipal);
        prision.setExit("north", sotano);
        capilla.setExit("west", salaPrincipal);
        capilla.setExit("east", torreon);
        capilla.setExit("south", salaDelTrono);
        torreon.setExit("west", capilla);
        torreon.setExit("southeast", acantilado);
        salaDelTrono.setExit("north", capilla);
        salaDelTrono.setExit("south", salaDelTesoro);
        salaDelTrono.setExit("southwest", fuenteDeLaSabiduria);
        salaDelTrono.setExit("southeast", aposentosDelRey);
        salaDelTesoro.setExit("north", salaDelTrono);

        currentRoom = salaPrincipal;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(printLocationInfo());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            if (salas.size() > 0)
                currentRoom = salas.pop();
            else{
                System.out.println("No more backs");
            }
        }
        else if (commandWord.equals("items")) {
            String objetos = "You have in inventory: ";
            for (Item inventario : mochila) {
                objetos += "\n" + inventario.getItem();
            }
            System.out.println(objetos);
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            currentRoom = nextRoom;
            System.out.println(printLocationInfo());
            System.out.println();
            salas.add(currentRoom);
        }
    }

    private String printLocationInfo() {
        return currentRoom.getLongDescription();        
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat() {
        System.out.println( "You have eaten now and you are not hungry any more");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void takeItem(Command command) 
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
            System.out.println(printLocationInfo());
            System.out.println();
        }
        else {
            if ((pesoMaximo - it.getWeight()) <= 0 || pesoMaximo != 0) {
                mochila.add(it);
                pesoMaximo -= it.getWeight();
                objs.remove(cont2);
                System.out.println("You have: " + pesoMaximo + " kg(s) free");
                System.out.println();
            }
            else 
                System.out.println("You have: " + pesoMaximo + " kg(s) free, you can't carry it!");
        }
    }

    private void dropItem(Command command) 
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
            pesoMaximo += it.getWeight();
            mochila.remove(cont2);
            System.out.println("You have: " + pesoMaximo + " kg(s) free");
            System.out.println();
        }
    }
}
