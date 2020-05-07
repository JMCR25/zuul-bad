public class Item
{
    private String descripcion;
    private String identificador;
    private int peso;
    private boolean sePuedeCoger;

    public Item(String nombre, String id, int pesoI, boolean disponibilidad) {
        descripcion = nombre;
        identificador = id;
        peso = pesoI;
        sePuedeCoger = disponibilidad;
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

    public boolean getDisp() {
        return sePuedeCoger;
    }

    public int getWeight() {
        return peso;
    }

}
