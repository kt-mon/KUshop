package com.mycompany.kushopsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<String> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private Button side_AlreadyHave;

    @FXML
    private TextField su_username;

    private String[] questionList = {
        "What is your favorite Color?",
        "What is your Birthday?",
        "What is your favorite food?"
    };

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
            });

            slider.play();
        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
            });

            slider.play();
        }
    }

    @FXML
    private void login() {
        String u = si_username.getText().trim();
        String p = si_password.getText();

        if (u.isEmpty() || p.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "กรุณากรอกข้อมูลให้ครบ");
            return;
        }

        String[] user = UserStore.find(u);
        if (user != null && user[1].equals(p)) {
            showAlert(Alert.AlertType.INFORMATION, "เข้าสู่ระบบสำเร็จ");
        } else {
            showAlert(Alert.AlertType.ERROR, "Username หรือ Password ไม่ถูกต้อง");
        }
    }

    @FXML
    private void signup() {
        String u = su_username.getText().trim();
        String p = su_password.getText();
        String q = su_question.getValue();
        String a = su_answer.getText().trim();

        if (u.isEmpty() || p.isEmpty() || q == null || a.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "กรุณากรอกข้อมูลให้ครบ");
            return;
        }
        if (u.contains("|") || p.contains("|") || a.contains("|")) {
            showAlert(Alert.AlertType.ERROR, "ห้ามใช้เครื่องหมาย | ในข้อมูล");
            return;
        }
        if (UserStore.find(u) != null) {
            showAlert(Alert.AlertType.ERROR, "Username นี้ถูกใช้แล้ว");
            return;
        }

        try {
            UserStore.add(u, p, q, a);
            showAlert(Alert.AlertType.INFORMATION, "สมัครสมาชิกสำเร็จ");
            su_username.clear();
            su_password.clear();
            su_answer.clear();
            su_question.getSelectionModel().clearSelection();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "บันทึกข้อมูลไม่สำเร็จ");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        su_question.getItems().addAll(questionList);
    }
}