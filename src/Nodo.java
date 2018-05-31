
public class Nodo {

    Nodo padre;
    Nodo[] hijos = new Nodo[4];
    int i;
    int j;
    boolean visitado;

    public Nodo(int i, int j) {
        padre = null;
        hijos[0] = null;
        hijos[1] = null;
        hijos[2] = null;
        hijos[3] = null;
        visitado = false;
        this.i = i;
        this.j = j;
    }
}
