
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Mostrador extends JFrame {

    JComboBox[][] espacios;

    public Mostrador(int tamañoLaberinto) {
        JPanel PanelTodo = new JPanel();
        PanelTodo.setLayout(new BoxLayout(PanelTodo, BoxLayout.PAGE_AXIS));
        JPanel PanelRecorrido = new JPanel(new GridLayout(tamañoLaberinto, tamañoLaberinto));
        JPanel PanelBoton = new JPanel(new GridLayout(0, 1));
        JPanel PanelPrueba = new JPanel(new GridLayout(0, 2));

        String[] valores = {"0", "1"};

        JComboBox A1 = new JComboBox(valores);
        JComboBox A2 = new JComboBox(valores);
        PanelPrueba.add(A1);
        PanelPrueba.add(A2);

        espacios = new JComboBox[tamañoLaberinto][tamañoLaberinto];
        for (int i = 0; i < espacios.length; i++) {
            for (int j = 0; j < espacios[i].length; j++) {
                espacios[i][j] = new JComboBox(valores);
                if (i == 0 && j == 0) {
                    espacios[i][j].setSelectedItem("1");
                }
                if (i == espacios.length - 1 && j == espacios.length - 1) {
                    espacios[i][j].setSelectedItem("1");
                }
                PanelRecorrido.add(espacios[i][j]);
            }
        }

        JButton Boton = new JButton("Intentar");
        Boton.addActionListener((ActionEvent e) -> {
            int matriz[][] = new int[tamañoLaberinto][tamañoLaberinto];
            //int recorrido = 0;
            for (int i = 0; i < espacios.length; i++) {
                for (int k = 0; k < espacios[i].length; k++) {
                    String ome = (String) espacios[i][k].getSelectedItem();
                    matriz[i][k] = Integer.parseInt(ome);
                    //recorrido++;
                }
            }
            try {
                realizarRecorrido(matriz);
            } catch (InterruptedException IE) {
                System.out.println(IE.getMessage());
            }

        });
        PanelBoton.add(Boton);
        PanelTodo.add(PanelRecorrido);
        PanelTodo.add(PanelBoton);
        this.add(PanelTodo);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void realizarRecorrido(int[][] M) throws InterruptedException {
        //La matriz que se va a evaluar necesita que hayan ceros al rededor
        int[][] Matriz = new int[(M.length + 2)][(M.length + 2)];
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                if (i == 0 || j == 0 || i == Matriz.length - 1 || j == Matriz.length - 1) {
                    Matriz[i][j] = 0;
                } else {
                    Matriz[i][j] = M[i - 1][j - 1];
                }
            }
        }
        //Dependiendo de la matriz a evaluar, se crea otra de nodos para poderlos relacionar:
        //Si es 1 se crea un nodo con la posición
        //Si es 0 se deja null
        Nodo[][] Nodos = new Nodo[Matriz.length][Matriz.length];
        for (int i = 0; i < Nodos.length; i++) {
            for (int j = 0; j < Nodos[i].length; j++) {
                if (Matriz[i][j] == 1) {
                    Nodos[i][j] = new Nodo(i, j);
                } else {
                    Nodos[i][j] = null;
                }
            }
        }
        //El nodo de inicio siempre va a ser la posición 1,1 
        Nodo AUX = Nodos[1][1];

        while (true) {
            //Si el nodo que se evalua (AUX) llega al final, se termina el ciclo
            if (AUX.i == Matriz.length - 2 && AUX.j == Matriz.length - 2) {
                espacios[AUX.i - 1][AUX.j - 1].setBackground(Color.GREEN);
                break;
            }
            Nodos[AUX.i][AUX.j].visitado = true;
            espacios[AUX.i - 1][AUX.j - 1].setBackground(Color.GREEN);
            //Condiciones para moverse hacia 4 lados
            //Derecha
            if (Nodos[AUX.i][AUX.j + 1] != null && !Nodos[AUX.i][AUX.j + 1].visitado && Matriz[AUX.i][AUX.j + 1] == 1) {
                System.out.println("Va a la derecha");
                //El nodo actual (AUX) se hace padre del nodo siguiente que cumplió la anterior condición
                Nodos[AUX.i][AUX.j + 1].padre = Nodos[AUX.i][AUX.j];

                //Se busca algún hijo del nodo actual (AUX) que sea nulo para relacionarlo
                for (int i = 0; i < 4; i++) {
                    if (Nodos[AUX.i][AUX.j].hijos[i] == null) {
                        Nodos[AUX.i][AUX.j].hijos[i] = Nodos[AUX.i][AUX.j + 1];
                        AUX = Nodos[AUX.i][AUX.j].hijos[i];
                        break;
                    }
                }
                //Arriba
            } else if (Nodos[AUX.i - 1][AUX.j] != null && !Nodos[AUX.i - 1][AUX.j].visitado && Matriz[AUX.i - 1][AUX.j] == 1) {
                System.out.println("Va a arriba");
                Nodos[AUX.i - 1][AUX.j].padre = Nodos[AUX.i][AUX.j];
                for (int i = 0; i < 4; i++) {
                    if (Nodos[AUX.i][AUX.j].hijos[i] == null) {
                        Nodos[AUX.i][AUX.j].hijos[i] = Nodos[AUX.i - 1][AUX.j];
                        AUX = Nodos[AUX.i][AUX.j].hijos[i];
                        break;
                    }
                }
                //Izquierda
            } else if (Nodos[AUX.i][AUX.j - 1] != null && !Nodos[AUX.i][AUX.j - 1].visitado && Matriz[AUX.i][AUX.j - 1] == 1) {
                System.out.println("Va a la Izquierda");
                Nodos[AUX.i][AUX.j - 1].padre = Nodos[AUX.i][AUX.j];
                for (int i = 0; i < 4; i++) {
                    if (Nodos[AUX.i][AUX.j].hijos[i] == null) {
                        Nodos[AUX.i][AUX.j].hijos[i] = Nodos[AUX.i][AUX.j - 1];
                        AUX = Nodos[AUX.i][AUX.j].hijos[i];
                        break;
                    }
                }
                //Abajo
            } else if (Nodos[AUX.i + 1][AUX.j] != null && !Nodos[AUX.i + 1][AUX.j].visitado && Matriz[AUX.i + 1][AUX.j] == 1) {
                System.out.println("Va a abajo");
                Nodos[AUX.i + 1][AUX.j].padre = Nodos[AUX.i][AUX.j];
                for (int i = 0; i < 4; i++) {
                    if (Nodos[AUX.i][AUX.j].hijos[i] == null) {
                        Nodos[AUX.i][AUX.j].hijos[i] = Nodos[AUX.i + 1][AUX.j];
                        AUX = Nodos[AUX.i][AUX.j].hijos[i];
                        break;
                    }
                }
                //En dado caso de que no se puedan hacer más movimientos
            } else {
                espacios[AUX.i - 1][AUX.j - 1].setBackground(Color.RED);
                //Si el nodo llega hasta el inicio, no se pueden hacer más movimientos
                if (AUX.i == 1 && AUX.j == 1) {
                    System.out.println("No se ha podido continuar el recorrido");
                    break;
                //Se retorna al padre del nodo actual
                } else {
                    System.out.println("Se devuelve a " + AUX.padre.i + " ," + AUX.padre.j);
                    AUX = AUX.padre;
                }
            }
        }
    }
}
