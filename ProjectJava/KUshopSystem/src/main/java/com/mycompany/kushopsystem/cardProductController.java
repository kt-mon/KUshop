package com.mycompany.kushopsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController {

    @FXML
    private AnchorPane card_form;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_price;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Spinner<Integer> prod_spinner;
    @FXML
    private Button prod_addBtn;

    private Product product;
    private mainFormController main;

    // เรียกจากหน้าหลักตอนสร้างการ์ดแต่ละใบ
    public void setData(Product product, mainFormController main) {
        this.product = product;
        this.main = main;

        prod_name.setText(product.getProductName());
        prod_price.setText("฿" + String.format("%.2f", product.getPrice()));

        if (!product.getImage().isEmpty()) {
            prod_imageView.setImage(new Image(product.getImage(), 205, 99, true, true));
        }

        if (product.getStock() > 0) {
            // เลือกจำนวนได้ตั้งแต่ 1 ถึงจำนวนที่มีในสต็อก
            prod_spinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getStock(), 1));
        } else {
            prod_spinner.setDisable(true);
            prod_addBtn.setDisable(true);
            prod_addBtn.setText("Out");
        }
    }

    @FXML
    private void addToCart() {
        main.addToCart(product, prod_spinner.getValue());
    }
}