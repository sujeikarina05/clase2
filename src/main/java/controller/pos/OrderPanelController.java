package controller.pos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import view.components.pos.DishPanel;
import view.components.pos.Loading;
import model.dao.impl.DishDAOImpl;
import model.enums.Category;
import model.entity.Dish;
import model.entity.CartDish;
import view.pos.OrderPanel;
import view.pos.PointOfSaleFrame;

public class OrderPanelController extends AbstractController implements ActionListener {

    private final OrderPanel orderPanel;
    private DishDAOImpl dishDAO;
    private final Map<Category, List<Dish>> productCache = new HashMap<>();
    private final CartPanelController cartPanelController;

    public OrderPanelController(PointOfSaleFrame pointOfSaleFrame, CartPanelController cartPanelController) {
        super(pointOfSaleFrame);
        this.orderPanel = pointOfSaleFrm.orderPanel;
        this.cartPanelController = cartPanelController;
    }

    @Override
    protected void init() {
        panelConfiguration();
        listeners();
        dishDAO = new DishDAOImpl();
    }

    private void panelConfiguration() {
        // Set category buttons icons
        int height = 125;
        int width = orderPanel.btnCarta.getWidth() - 40;
        FlatSVGIcon menuIcon = new FlatSVGIcon("icons/menu.svg", width, height);
        FlatSVGIcon snacksIcon = new FlatSVGIcon("icons/snacks.svg", width, height);
        FlatSVGIcon hotDrinksIcon = new FlatSVGIcon("icons/hot-drinks.svg", width, height);
        FlatSVGIcon coldDrinksIcon = new FlatSVGIcon("icons/cold-drinks.svg", width, height);
        FlatSVGIcon beerIcon = new FlatSVGIcon("icons/beer.svg", width, height);
        FlatSVGIcon drinksIcon = new FlatSVGIcon("icons/drinks.svg", width, height);
        FlatSVGIcon dessertsIcon = new FlatSVGIcon("icons/desserts.svg", width, height);
        orderPanel.btnCarta.setIcon(menuIcon);
        orderPanel.btnAntojitos.setIcon(snacksIcon);
        orderPanel.btnHotDrinks.setIcon(hotDrinksIcon);
        orderPanel.btnColdDrinks.setIcon(coldDrinksIcon);
        orderPanel.btnBeer.setIcon(beerIcon);
        orderPanel.btnDrinks.setIcon(drinksIcon);
        orderPanel.btnDesserts.setIcon(dessertsIcon);

        // Remove all borders from dishes table
        orderPanel.scrollPaneDishes.setViewportBorder(null);
        orderPanel.scrollPaneDishes.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(null);
        orderPanel.tableDishes.getTableHeader().setDefaultRenderer(renderer);
        orderPanel.tableDishes.getTableHeader().setPreferredSize(new Dimension(0, 0));

        // Align dishes table text
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(JLabel.LEFT);
        orderPanel.tableDishes.getColumnModel().getColumn(0).setCellRenderer(defaultTableCellRenderer);
        orderPanel.tableDishes.getColumnModel().getColumn(1).setCellRenderer(defaultTableCellRenderer);

        // Products panel layout
        orderPanel.productsPanel.setLayout(new GridLayout(0, 4, 12, 12));
        int ORDER_PANEL_PADDING = 14;
        orderPanel.productsPanel.setBorder(BorderFactory.createEmptyBorder(ORDER_PANEL_PADDING, ORDER_PANEL_PADDING, ORDER_PANEL_PADDING, ORDER_PANEL_PADDING));
    }

    private void listeners() {
        // Buttons
        orderPanel.btnCarta.addActionListener(this);
        orderPanel.btnAntojitos.addActionListener(this);
        orderPanel.btnHotDrinks.addActionListener(this);
        orderPanel.btnColdDrinks.addActionListener(this);
        orderPanel.btnBeer.addActionListener(this);
        orderPanel.btnDrinks.addActionListener(this);
        orderPanel.btnDesserts.addActionListener(this);
    }

    // Function to handle category buttons
    private void handleCategoryButton(Category category) {
        // Verify if the category dishes are already cached
        if (productCache.containsKey(category)) {
            List<Dish> cachedProducts = productCache.get(category);
            loadProducts(cachedProducts);
        } else {
            // Add loading panel to products panel while dishes are being fetched
            orderPanel.productsPanel.removeAll();
            Loading loadingPanel = new Loading();
            orderPanel.productsPanel.setLayout(new GridBagLayout());
            orderPanel.productsPanel.add(loadingPanel);
            orderPanel.productsPanel.revalidate();
            orderPanel.productsPanel.repaint();

            // Background thread to fetch dishes
            SwingWorker<List<Dish>, Void> worker = new SwingWorker<List<Dish>, Void>() {
                // Function to fetch dishes in background
                @Override
                protected List<Dish> doInBackground() throws Exception {
                    return dishDAO.getDishesByCategory(category.getId());
                }

                // Function to load dishes into the dishes panel when the background thread finishes
                @Override
                protected void done() {
                    try {
                        List<Dish> dishList = get();
                        productCache.put(category, dishList);
                        loadProducts(dishList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        // Remove loading panel from products panel
                        orderPanel.productsPanel.remove(loadingPanel);
                        orderPanel.productsPanel.revalidate();
                        orderPanel.productsPanel.repaint();
                        orderPanel.productsPanel.setLayout(new GridLayout(0, 4, 12, 12));
                    }
                }
            };
            worker.execute();
        }
    }

    // Function to load dishes into the dishes panel
    private void loadProducts(List<Dish> dishes) {
        orderPanel.productsPanel.removeAll();
        dishes.forEach((dish) -> {
            // Create dish panel
            DishPanel dishPanel = new DishPanel();
            dishPanel.lblName.setText(
                    "<html><body><p style='text-align:center; padding: 0 6px;'>" + dish.getName() + "</p></body></html>"
            );
            orderPanel.productsPanel.add(dishPanel);
            orderPanel.productsPanel.revalidate();
            orderPanel.productsPanel.repaint();

            // Action listeners to increase and reduce quantity
            dishPanel.btnIncrease.addActionListener((ActionEvent e1) -> {
                int quantity = Integer.parseInt(dishPanel.txtQuantity.getText());
                dishPanel.txtQuantity.setText(String.valueOf(quantity + 1));
            });

            dishPanel.btnReduce.addActionListener((ActionEvent e1) -> {
                int quantity = Integer.parseInt(dishPanel.txtQuantity.getText());
                if (quantity > 1) {
                    dishPanel.txtQuantity.setText(String.valueOf(quantity - 1));
                }
            });

            // Action listener to add a dish to the order
            dishPanel.btnAdd.addActionListener((ActionEvent e1) -> {
                int quantity = Integer.parseInt(dishPanel.txtQuantity.getText());
                CartDish cartDish = new CartDish(dish, quantity, null);

                // Add dish to cart panel
                cartPanelController.addDishToCart(cartDish);

                // Disable dish panel buttons
//                dishPanel.btnIncrease.setEnabled(false);
//                dishPanel.btnReduce.setEnabled(false);
//                dishPanel.btnAdd.setEnabled(false);
            });
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderPanel.btnCarta) {
            handleCategoryButton(Category.CARTA);
        }
        if (e.getSource() == orderPanel.btnAntojitos) {
            handleCategoryButton(Category.ANTOJITOS);
        }
        if (e.getSource() == orderPanel.btnHotDrinks) {
            handleCategoryButton(Category.BEBIDAS_CALIENTES);
        }
        if (e.getSource() == orderPanel.btnColdDrinks) {
            handleCategoryButton(Category.BEBIDAS_FRIAS);
        }
        if (e.getSource() == orderPanel.btnBeer) {
            handleCategoryButton(Category.TRAGOS);
        }
        if (e.getSource() == orderPanel.btnDrinks) {
            handleCategoryButton(Category.CERVEZAS);
        }
        if (e.getSource() == orderPanel.btnDesserts) {
            handleCategoryButton(Category.POSTRES);
        }
    }

}
