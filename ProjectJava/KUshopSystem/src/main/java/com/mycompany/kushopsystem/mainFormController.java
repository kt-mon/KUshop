package com.mycompany.kushopsystem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class mainFormController implements Initializable {

    @FXML
    private Button catalog_btn;
    @FXML
    private Button customer_btn;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button inventory_btn;
    @FXML
    private Button logout_btn;
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private AnchorPane inventory_form;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private Label username;

    //dash
    @FXML
    private Label dash_customers;
    @FXML
    private Label dash_todayIncome;
    @FXML
    private Label dash_totalIncome;
    @FXML
    private Label dash_sold;
    @FXML
    private AreaChart<String, Number> dash_chartIncome;
    @FXML
    private AreaChart<String, Number> dash_chartCustomer;

    //invento
    @FXML
    private TableView<Product> inventory_tableView;
    @FXML
    private TableColumn<Product, String> inventory_col_ProductID;
    @FXML
    private TableColumn<Product, String> inventory_col_ProductName;
    @FXML
    private TableColumn<Product, String> inventory_col_Type;
    @FXML
    private TableColumn<Product, Integer> inventory_col_Stock;
    @FXML
    private TableColumn<Product, Double> inventory_col_Price;
    @FXML
    private TableColumn<Product, String> inventory_col_Status;
    @FXML
    private TableColumn<Product, String> inventory_col_Date;

    @FXML
    private TextField inventory_productID;
    @FXML
    private TextField inventory_productName;
    @FXML
    private ComboBox<String> inventory_type;
    @FXML
    private TextField inventory_stock;
    @FXML
    private TextField inventory_price;
    @FXML
    private ComboBox<String> inventory_status;
    @FXML
    private ImageView inventory_imageView;
    @FXML
    private Button inventory_importBtn;

    // caatalog
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private TableView<CartItem> menu_tableView;
    @FXML
    private TableColumn<CartItem, String> menu_col_productName;
    @FXML
    private TableColumn<CartItem, Integer> menu_col_quantity;
    @FXML
    private TableColumn<CartItem, Double> menu_col_price;
    @FXML
    private Label menu_total;
    @FXML
    private TextField menu_amount;
    @FXML
    private Label menu_change;
    @FXML
    private Button menu_payBtn;
    @FXML
    private Button menu_removeBtn;

    private final String[] typeList = {"Shirt", "Bottoms", "Ceremony", "Accessories"};
    private final String[] statusList = {"Available", "Unavailable"};

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<CartItem> cart = FXCollections.observableArrayList();
    private String imagePath = "";
    private String currentUser = "";


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inventory_type.getItems().addAll(typeList);
        inventory_status.getItems().addAll(statusList);

        inventory_col_ProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventory_tableView.setItems(products);

        inventory_tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, p) -> {
            if (p != null) {
                fillForm(p);
            }
        });

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        menu_tableView.setItems(cart);

        menu_amount.textProperty().addListener((obs, old, text) -> updateChange());

        menu_gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        menu_gridPane.getColumnConstraints().clear();
        menu_gridPane.setHgap(4);
        menu_gridPane.setVgap(10);
        menu_gridPane.setPadding(new Insets(10, 4, 10, 4));
        menu_scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        dash_chartIncome.setAnimated(false);
        dash_chartCustomer.setAnimated(false);

        products.setAll(ProductStore.load());
        showPage(dashboard_form);
        updateDashboard();
    }

    public void setUsername(String name) {
        username.setText(name);
        currentUser = name;
    }

    // SIGNOUT

    @FXML
    public void switchPage(ActionEvent event) {
        Object source = event.getSource();
        if (source == dashboard_btn) {
            showPage(dashboard_form);
            updateDashboard();
        } else if (source == inventory_btn) {
            showPage(inventory_form);
        } else if (source == catalog_btn) {
            showPage(menu_form);
            refreshMenu();
        }
    }

    private void showPage(AnchorPane page) {
        dashboard_form.setVisible(page == dashboard_form);
        inventory_form.setVisible(page == inventory_form);
        menu_form.setVisible(page == menu_form);

        String active = "-fx-text-fill: #64ff27;";
        dashboard_btn.setStyle(page == dashboard_form ? active : "");
        inventory_btn.setStyle(page == inventory_form ? active : "");
        catalog_btn.setStyle(page == menu_form ? active : "");
    }

    @FXML
    public void logout() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/mycompany/kushopsystem/FXMLDocument.fxml"));
            Stage stage = (Stage) logout_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "กลับหน้า Login ไม่สำเร็จ");
        }
    }

    //CATALOG  :)

    private void refreshMenu() {
        URL cardUrl = getClass().getResource("/fxml/cardProduct.fxml");
        if (cardUrl == null) {
            cardUrl = getClass().getResource("/com/mycompany/kushopsystem/cardProduct.fxml");
        }
        if (cardUrl == null) {
            showAlert(Alert.AlertType.ERROR, "ไม่พบไฟล์ cardProduct.fxml");
            return;
        }

        menu_gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        for (Product p : products) {
            if (!"Available".equals(p.getStatus())) {
                continue;
            }
            try {
                FXMLLoader loader = new FXMLLoader(cardUrl);
                AnchorPane card = loader.load();
                cardProductController controller = loader.getController();
                controller.setData(p, this);

                menu_gridPane.add(card, column, row);
                column++;
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToCart(Product p, int qty) {
        int cartIndex = -1;
        int already = 0;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProductId().equals(p.getProductId())) {
                cartIndex = i;
                already = cart.get(i).getQuantity();
            }
        }

        int newQty = already + qty;
        if (newQty > p.getStock()) {
            showAlert(Alert.AlertType.ERROR, "สินค้าไม่พอ (ในสต็อก " + p.getStock()
                    + " ชิ้น อยู่ในตะกร้าแล้ว " + already + " ชิ้น)");
            return;
        }

        CartItem item = new CartItem(p, newQty);
        if (cartIndex >= 0) {
            cart.set(cartIndex, item);
        } else {
            cart.add(item);
        }
        updateTotal();
    }

    @FXML
    public void menuRemove() {
        CartItem selected = menu_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "กรุณาเลือกรายการในตะกร้าก่อน");
            return;
        }
        cart.remove(selected);
        updateTotal();
    }

    @FXML
    public void menuPay() {
        if (cart.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "ยังไม่มีสินค้าในตะกร้า");
            return;
        }

        double total = cartTotal();
        double amount;
        try {
            amount = Double.parseDouble(menu_amount.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "กรุณาใส่จำนวนเงินที่รับมา");
            return;
        }
        if (amount < total) {
            showAlert(Alert.AlertType.ERROR, "จำนวนเงินไม่พอ");
            return;
        }

        for (CartItem item : cart) {
            int idx = findIndex(item.getProductId());
            if (idx < 0 || products.get(idx).getStock() < item.getQuantity()) {
                showAlert(Alert.AlertType.ERROR,
                        "สินค้า " + item.getProductName() + " ในสต็อกไม่พอ");
                return;
            }
        }

        try {
            for (CartItem item : cart) {
                int idx = findIndex(item.getProductId());
                Product old = products.get(idx);
                SaleStore.add(currentUser, old, item.getQuantity());
                products.set(idx, new Product(old.getProductId(), old.getProductName(),
                        old.getType(), old.getStock() - item.getQuantity(), old.getPrice(),
                        old.getStatus(), old.getDate(), old.getImage()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "บันทึกการขายไม่สำเร็จ");
            return;
        }
        if (!save()) {
            return;
        }
         cart.clear();
        menu_amount.clear();
        updateTotal();
        refreshMenu();
        updateDashboard();
        showAlert(Alert.AlertType.INFORMATION,
                "ชำระเงินสำเร็จ เงินทอน " + String.format("%.2f", amount - total));
    }

    private double cartTotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getPrice();
        }
        return total;
    }

    private void updateTotal() {
        menu_total.setText(String.format("%.2f", cartTotal()));
        updateChange();
    }

    private void updateChange() {
        double change = 0;
        try {
            double amount = Double.parseDouble(menu_amount.getText().trim());
            change = Math.max(0, amount - cartTotal());
        } catch (NumberFormatException e) {
        }
        menu_change.setText(String.format("%.2f", change));
    }

    private void updateDashboard() {
        String today = LocalDate.now().toString();
        int sold = 0;
        double todayIncome = 0;
        double totalIncome = 0;
        Map<String, Double> incomeByDate = new TreeMap<>();
        Map<String, Set<String>> buyersByDate = new TreeMap<>();

        for (String[] s : SaleStore.load()) {
            int qty;
            double total;
            try {
                qty = Integer.parseInt(s[4]);
                total = Double.parseDouble(s[5]);
            } catch (NumberFormatException e) {
                continue;
            }
            sold += qty;
            totalIncome += total;
            if (s[0].equals(today)) {
                todayIncome += total;
            }
            incomeByDate.merge(s[0], total, Double::sum);
            buyersByDate.computeIfAbsent(s[0], k -> new HashSet<>()).add(s[1]);
        }

        dash_customers.setText(String.valueOf(UserStore.count()));
        dash_todayIncome.setText(String.format("%.2f", todayIncome));
        dash_totalIncome.setText(String.format("%.2f", totalIncome));
        dash_sold.setText(String.valueOf(sold));

        Map<String, Integer> customersByDate = new TreeMap<>();
        for (Map.Entry<String, Set<String>> e : buyersByDate.entrySet()) {
            customersByDate.put(e.getKey(), e.getValue().size());
        }

        fillChart(dash_chartIncome, "Income", incomeByDate);
        fillChart(dash_chartCustomer, "Customers", customersByDate);
    }

    private void fillChart(AreaChart<String, Number> chart, String name,
            Map<String, ? extends Number> data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        int skip = Math.max(0, data.size() - 7);
        int i = 0;
        for (Map.Entry<String, ? extends Number> e : data.entrySet()) {
            if (i++ >= skip) {
                series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
            }
        }
        chart.getData().clear();
        chart.getData().add(series);
    }

    //Inven

    @FXML
    public void inventory_addBtn(ActionEvent event) {
        Product p = readForm(LocalDate.now().toString());
        if (p == null) {
            return;
        }
        if (findIndex(p.getProductId()) >= 0) {
            showAlert(Alert.AlertType.ERROR, "ID สินค้านี้ถูกใช้แล้ว");
            return;
        }
        products.add(p);
        if (save()) {
            showAlert(Alert.AlertType.INFORMATION, "เพิ่มสินค้าสำเร็จ");
            inventory_clearBtn(null);
        }
    }

    @FXML
    public void inventory_updateBtn(ActionEvent event) {
        String id = inventory_productID.getText().trim();
        int index = findIndex(id);
        if (index < 0) {
            showAlert(Alert.AlertType.ERROR, "ไม่พบสินค้า ID นี้ (ID แก้ไขไม่ได้ ให้เลือกแถวในตารางก่อน)");
            return;
        }
        Product p = readForm(products.get(index).getDate());
        if (p == null) {
            return;
        }
        products.set(index, p);
        if (save()) {
            showAlert(Alert.AlertType.INFORMATION, "แก้ไขสินค้าสำเร็จ");
            inventory_clearBtn(null);
        }
    }

    @FXML
    public void inventory_deleteBtn(ActionEvent event) {
        Product selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "กรุณาเลือกสินค้าในตารางก่อน");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("ต้องการลบ " + selected.getProductName() + " ใช่หรือไม่?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            products.remove(selected);
            if (save()) {
                inventory_clearBtn(null);
            }
        }
    }

    @FXML
    public void inventory_clearBtn(ActionEvent event) {
        inventory_tableView.getSelectionModel().clearSelection();
        inventory_productID.clear();
        inventory_productName.clear();
        inventory_stock.clear();
        inventory_price.clear();
        inventory_type.getSelectionModel().clearSelection();
        inventory_status.getSelectionModel().clearSelection();
        inventory_imageView.setImage(null);
        imagePath = "";
    }

    @FXML
    public void importImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("เลือกรูปสินค้า");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fc.showOpenDialog(inventory_importBtn.getScene().getWindow());
        if (file != null) {
            imagePath = file.toURI().toString();
            inventory_imageView.setImage(new Image(imagePath, 127, 134, true, true));
        }
    }


    private void fillForm(Product p) {
        inventory_productID.setText(p.getProductId());
        inventory_productName.setText(p.getProductName());
        inventory_type.setValue(p.getType());
        inventory_stock.setText(String.valueOf(p.getStock()));
        inventory_price.setText(String.valueOf(p.getPrice()));
        inventory_status.setValue(p.getStatus());
        imagePath = p.getImage();
        if (imagePath.isEmpty()) {
            inventory_imageView.setImage(null);
        } else {
            inventory_imageView.setImage(new Image(imagePath, 127, 134, true, true));
        }
    }
    
    //arn jark form
    private Product readForm(String date) {
        String id = inventory_productID.getText().trim();
        String name = inventory_productName.getText().trim();
        String type = inventory_type.getValue();
        String status = inventory_status.getValue();
        String stockText = inventory_stock.getText().trim();
        String priceText = inventory_price.getText().trim();

        if (id.isEmpty() || name.isEmpty() || type == null || status == null
                || stockText.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "กรุณากรอกข้อมูลให้ครบ");
            return null;
        }
        if (id.contains("|") || name.contains("|")) {
            showAlert(Alert.AlertType.ERROR, "ห้ามใช้เครื่องหมาย | ในข้อมูล");
            return null;
        }

        int stock;
        double price;
        try {
            stock = Integer.parseInt(stockText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Stock ต้องเป็นจำนวนเต็ม และ Price ต้องเป็นตัวเลข");
            return null;
        }
        if (stock < 0 || price < 0) {
            showAlert(Alert.AlertType.ERROR, "Stock และ Price ต้องไม่ติดลบ");
            return null;
        }
        return new Product(id, name, type, stock, price, status, date, imagePath);
    }

    private int findIndex(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private boolean save() {
        try {
            ProductStore.saveAll(products);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "บันทึกข้อมูลไม่สำเร็จ");
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}