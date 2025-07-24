package controller.pos;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.login.LoginController;
import view.login.Login;
import view.pos.PointOfSaleFrame;

import java.awt.*;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class PosFrameController extends AbstractController implements ActionListener {

    public PosFrameController(PointOfSaleFrame pointOfSaleFrm) {
        super(pointOfSaleFrm);
    }

    @Override
    public void init() {
        frameConfig();
        showTime();
    }

    private void showTime() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String fecha = now.format(dateFormatter);
                String hora = now.format(timeFormatter);
                pointOfSaleFrm.posHeader.lblTime.setText(fecha + " " + hora);
                pointOfSaleFrm.posSecondaryHeader.lblTime.setText(fecha + " " + hora);
            }
        }, 0, 1000);
    }

    private void frameConfig() {
        pointOfSaleFrm.getContentPane().setBackground(Color.BLACK);
        pointOfSaleFrm.posHeader.btnLogout.setIcon(new FlatSVGIcon("icons/logout.svg", 40, 40));
        // Initial panel: OrderPanel and CartPanel
        CartPanelController cartPanelController = new CartPanelController(pointOfSaleFrm);
        cartPanelController.init();
        OrderPanelController orderPanelController = new OrderPanelController(pointOfSaleFrm, cartPanelController);
        orderPanelController.init();

        // Listeners
        pointOfSaleFrm.posHeader.btnLogout.addActionListener(this);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getSource() == pointOfSaleFrm.posHeader.btnLogout){
            int dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro que desea cerrar sesión?",
                    "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if(dialogResult == JOptionPane.YES_OPTION){
                pointOfSaleFrm.dispose();
                Login login = new Login();
                LoginController loginController = new LoginController(login);
                loginController.init();
                login.setVisible(true);
            }
        }
    }

}
