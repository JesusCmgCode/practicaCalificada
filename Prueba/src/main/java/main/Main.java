package main;

import com_2310976_controller.GastoController;
import com_2310976_view.GastoUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GastoController controller = new GastoController();
            GastoUI ui = new GastoUI(controller);
            ui.setVisible(true);
        });
    }
}
