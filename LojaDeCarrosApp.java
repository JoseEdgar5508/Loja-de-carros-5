package projeto.bd.poo.meu;

import javax.swing.SwingUtilities;

//ARQUIVO PRINCIPAL
public class LojaDeCarrosApp {
    public static void main(String[] args) {
        // Iniciando a interface gráfica
        SwingUtilities.invokeLater(LojaDeCarrosGUI::new);
    }
}
