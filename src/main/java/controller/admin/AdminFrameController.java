package controller.admin;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.login.LoginController;
import net.miginfocom.swing.MigLayout;
import view.admin.*;
import view.components.admin.ButtonMenu;
import view.login.Login;

import javax.swing.*;
import java.awt.*;

public class AdminFrameController extends AdminAbstractController{

    public AdminFrameController(AdminIntranetFrame adminIntranetFrm) {
        super(adminIntranetFrm);
    }

    @Override
    public void init() {
        // Menu options layout
        adminIntranetFrm.navBarPanel.setLayout(new MigLayout("wrap, fillx, inset 2", "[fill]", "[]4[]"));

        // Admin icon
        adminIntranetFrm.adminAvatar.setIcon(new FlatSVGIcon("icons/admin.svg", 130, 130));

        // Menu options
        setMenuOption("Dashboard", 0);
        setMenuOption("Analíticas", 1);
        setMenuOption("Menú (Carta)",2);
        setMenuOption("Empleados",3);
        setMenuOption("Cerrar sesión", 4);

        // Default menu option
        setMenuOptionSelected((ButtonMenu) adminIntranetFrm.navBarPanel.getComponent(0));
        changeMenuOption(0);
    }

    // Method to set menu options
    private void setMenuOption(String text, int menu_index){
        ButtonMenu buttonMenu = new ButtonMenu();
        buttonMenu.setText(text);
        adminIntranetFrm.navBarPanel.add(buttonMenu);
        buttonMenu.addActionListener(e -> {
            setMenuOptionSelected(buttonMenu);
            changeMenuOption(menu_index);
        });
    }

    // Method to set selected menu option
    private void setMenuOptionSelected(ButtonMenu buttonMenu){
        for(Component comp : adminIntranetFrm.navBarPanel.getComponents()){
            if(comp instanceof ButtonMenu){
                ((ButtonMenu) comp).setSelected(false);
            }
        }
        buttonMenu.setSelected(true);
    }

    // Method to change menu option
    private void changeMenuOption(int menu_index){
        switch (menu_index) {
            case 0 -> {
                DashboardPanel dashboardPanel = new DashboardPanel();
                showMenuPanel(dashboardPanel, new DashboardPanelController(adminIntranetFrm, dashboardPanel));
            }
            case 1 -> {
                AnalyticsPanel analyticsPanel = new AnalyticsPanel();
                showMenuPanel(analyticsPanel, new AnalyticsPanelController(adminIntranetFrm, analyticsPanel));
            }
            case 2 -> {
                MenuPanel menuPanel = new MenuPanel();
                showMenuPanel(menuPanel, new MenuPanelController(adminIntranetFrm, menuPanel));
            }
            case 3 -> {
                EmployeesPanel employeesPanel = new EmployeesPanel();
                showMenuPanel(employeesPanel, new EmployeesPanelController(adminIntranetFrm, employeesPanel));
            }
            case 4 -> {
                int option = JOptionPane.showConfirmDialog(
                        adminIntranetFrm,
                        "¿Está seguro que desea cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if(option == JOptionPane.YES_OPTION){
                    adminIntranetFrm.dispose();
                    Login login = new Login();
                    LoginController loginController = new LoginController(login);
                    loginController.init();
                    login.setVisible(true);
                }
            }
            // Add more cases here
        }
    }

    // Method to show menu panel
    private void showMenuPanel(Component component, AdminAbstractController controller){
        if (adminIntranetFrm.mainPanel.getComponentCount() > 0) {
            Component lastPanel = adminIntranetFrm.mainPanel.getComponent(0);
            adminIntranetFrm.mainPanel.remove(lastPanel);
        }

        adminIntranetFrm.mainPanel.add(component);
        adminIntranetFrm.mainPanel.revalidate();
        adminIntranetFrm.mainPanel.repaint();
        controller.init();
    }
}
