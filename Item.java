public class Item
{
    private String descripcion;
    private int peso;

    public Item(String nombre, int pesoI) {
        descripcion = nombre;
        peso = pesoI;
    }
    
    public void setItem(String descrip, int weight) {
        descripcion = descrip;
        peso = weight;
    }
    
    public String getItem() {
        String description = "\n" + "You found a: " + descripcion + "\n" + "Weight: " + peso;
        return description;
    }
    
}
