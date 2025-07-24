package controller.pos;

import model.entity.CartDish;
import model.entity.Dish;
import model.entity.Employee;
import model.enums.CrudAction;
import view.components.modal.ClientInfoModalController;
import view.components.modal.CustomModal;
import view.components.modal.EditDishModalController;
import view.listeners.ModalListeners;
import view.pos.OrderConfirmationPanel;
import view.pos.OrderPanel;
import view.pos.PointOfSaleFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CartPanelController extends AbstractController implements ActionListener, ModalListeners {

    private DefaultTableModel model;
    private final OrderPanel orderPanel;

    public CartPanelController(PointOfSaleFrame pointOfSaleFrm) {
        super(pointOfSaleFrm);
        this.orderPanel = pointOfSaleFrm.orderPanel;
    }

    @Override
    protected void init() {
        listeners();
        model = (DefaultTableModel) orderPanel.tableDishes.getModel();
    }

    private void listeners() {
        orderPanel.btnNext.addActionListener(this);

        // Mouse listener when user double-clicks on any dish table row
        orderPanel.tableDishes.addMouseListener(new MouseAdapter() {
            private int startX;

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
            }

            // Swipe left to delete dish
            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getX();
                int selectedRow = orderPanel.tableDishes.getSelectedRow();

                if (startX - endX > 100 && selectedRow != -1) {
                    CartDish cartDish = getSelectedDish(selectedRow);
                    orderInterface.removeDish(cartDish);
                    refreshCart();
                    updateNextButtonState();
                }
            }

            // Double click to edit dish
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int selectedRow = orderPanel.tableDishes.getSelectedRow();
                    if (selectedRow != -1) {
                        CustomModal modal = new CustomModal();
                        EditDishModalController editDishModalController = new EditDishModalController(modal, pointOfSaleFrm, CartPanelController.this);
                        CartDish cartDish = getSelectedDish(selectedRow);
                        editDishModalController.setOrderDish(cartDish);
                        editDishModalController.showModal();
                        orderPanel.tableDishes.clearSelection();
                    }
                }
            }
        });
    }


    /*
    * Main method to add a dish to the cart
    * @param cartDish: the dish to be added to the cart
    */
    public void addDishToCart(CartDish cartDish) {
        if (!isDishAlreadyInCart(cartDish.getDish().getId())) {
            orderInterface.addDish(cartDish);
            refreshCart();
            updateNextButtonState();
        }
    }

    // Method to check if a dish is already in the cart
    private boolean isDishAlreadyInCart(String dishId) {
        List<CartDish> cartDishes = orderInterface.getDishes();
        for (CartDish cartDish : cartDishes) {
            if (cartDish.getDish().getId().equals(dishId)) {
                return true; // Dish is already in cart
            }
        }
        return false; // Dish is not in cart
    }

    protected void updateNextButtonState() {
        boolean hasProductsInCart = !orderInterface.getDishes().isEmpty();
        orderPanel.btnNext.setEnabled(hasProductsInCart);
    }

    // Method to refresh the dishes table
    private void refreshCart() {
        model.setRowCount(0);
        List<CartDish> cartDishes = orderInterface.getDishes();
        for (CartDish cartDish : cartDishes) {
            model.addRow(new Object[]{cartDish.getQuantity(), cartDish.getDish().getName()});
        }
    }

    // Method to get the selected dish from the dishes table
    private CartDish getSelectedDish(int selectedRow) {
        List<CartDish> cartDishes = orderInterface.getDishes();
        if(selectedRow >= 0 && selectedRow < cartDishes.size()) {
            return cartDishes.get(selectedRow);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderPanel.btnNext) {
            CustomModal modal = new CustomModal();
            ClientInfoModalController clientInfoModalController = new ClientInfoModalController(modal, pointOfSaleFrm, CartPanelController.this);
            clientInfoModalController.showModal();
        }
    }

    @Override
    public void onEditDishModalClose() {
        refreshCart();
    }

    @Override
    public void onClientInfoModalClose() {
        OrderConfirmationPanel orderConfirmationPanel = pointOfSaleFrm.orderConfirmationPanel;
        ConfirmationPanelController confirmationPanelController = new ConfirmationPanelController(pointOfSaleFrm, orderConfirmationPanel);
        changePanel(orderConfirmationPanel, confirmationPanelController);
        changeHeaderPanel("CONFIRMACIÓN DE ORDEN");
    }

    @Override
    public void onDishActionModalClose(Dish dish, CrudAction crudAction) {

    }

    @Override
    public void onEmployeeActionModalClose(Employee employee, CrudAction crudAction) {

    }
}
