
import javax.swing.JOptionPane;


public class main {

    public static void main(String[] args) {
        int tamaño = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz"));
        Mostrador M = new Mostrador(tamaño);
        M.setVisible(true);
    }
}
