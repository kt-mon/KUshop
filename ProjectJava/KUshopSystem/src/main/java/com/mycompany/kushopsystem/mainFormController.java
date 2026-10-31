/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kushopsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class mainFormController implements Initializable{
    
    @FXML
    private Button catalog_btn;

    @FXML
    private Button customer_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<?, ?> inventory_col_Date;

    @FXML
    private TableColumn<?, ?> inventory_col_Price;

    @FXML
    private TableColumn<?, ?> inventory_col_ProductID;

    @FXML
    private TableColumn<?, ?> inventory_col_ProductName;

    @FXML
    private TableColumn<?, ?> inventory_col_Status;

    @FXML
    private TableColumn<?, ?> inventory_col_Stock;

    @FXML
    private TableColumn<?, ?> inventory_col_Type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<?> inventory_tableView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label username;
    
    @FXML
    public void inventory_addBtn(ActionEvent event) {

    }

    @FXML
    public void inventory_clearBtn(ActionEvent event) {

    }

    @FXML
    public void inventory_deleteBtn(ActionEvent event) {

}

    @FXML
    public void inventory_updateBtn(ActionEvent event) {

    }

    
    public void setUsername(String name) {
    username.setText(name);
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
