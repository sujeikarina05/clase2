import com.itextpdf.text.DocumentException;
import model.entity.CartDish;
import model.entity.Client;
import model.entity.Dish;
import model.entity.Order;
import model.utils.ReceiptGenerator;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptGeneratorTest {
    public static void main(String[] args) throws DocumentException, IOException {
        Order order = new Order();
        order.setId("B00011");
        order.setDate(LocalDateTime.now());
        order.setTotalAmount(100.00);
        order.setPaid(true);
        order.setClient(new Client(74847426, "Juan", "Perez"));
        List<CartDish> orderDishes = new ArrayList<>();
        for ( int i=0;i<5;i++){
            orderDishes.add(new CartDish(
                    new Dish("D00010", "Ceviche", 20.00, "C01"),
                    1,
                    null
            ));
        }
        ReceiptGenerator.generateReceipt(order, orderDishes);
        String currentWorkingDir = System.getProperty("user.dir");
        File receiptsFolder = new File(currentWorkingDir, "receipts");
        String pdfFilePath = receiptsFolder.getAbsolutePath() + File.separator + "recibo_B00011.pdf";
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new File(pdfFilePath));
        }
    }

}
