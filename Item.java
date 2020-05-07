public class Item
{
    private String descripcion;
    private String identificador;
    private int peso;

    public Item(String nombre, String id, int pesoI) {
        descripcion = nombre;
        identificador = id;
        peso = pesoI;
    }
    
    public void setItem(String descrip, int weight) {
        descripcion = descrip;
        peso = weight;
    }
    
    public String getItem() {
        String description = descripcion + " '" + identificador + "'" + "\n" + "Weight: " + peso;
        return description;
    }
    
    public String getId() {
        return identificador;
    }
    
}
