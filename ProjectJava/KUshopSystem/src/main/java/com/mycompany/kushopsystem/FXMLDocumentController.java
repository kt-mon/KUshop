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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField fp_username;

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<String> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPass;

    @FXML
    private PasswordField np_newPass;

    @FXML
    private AnchorPane np_newPassForm;

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

    private String resetUser;

    @FXML
    private void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        np_newPassForm.setVisible(false);
    }

    @FXML
    private void proceedForgot() {
        String u = fp_username.getText().trim();
        String q = fp_question.getValue();
        String a = fp_answer.getText().trim();

        if (u.isEmpty() || q == null || a.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "กรุณากรอกข้อมูลให้ครบ");
            return;
        }

        String[] user = UserStore.find(u);
        if (user != null && user[2].equals(q) && user[3].equalsIgnoreCase(a)) {
            resetUser = u;
            fp_questionForm.setVisible(false);
            np_newPassForm.setVisible(true);
        } else {
            showAlert(Alert.AlertType.ERROR, "ข้อมูลไม่ถูกต้อง");
        }
    }

    @FXML
    private void changePass() {
        String p1 = np_newPass.getText();
        String p2 = np_confirmPass.getText();

        if (p1.isEmpty() || p2.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "กรุณากรอกรหัสผ่านให้ครบ");
            return;
        }
        if (p1.contains("|")) {
            showAlert(Alert.AlertType.ERROR, "ห้ามใช้เครื่องหมาย | ในรหัสผ่าน");
            return;
        }
        if (!p1.equals(p2)) {
            showAlert(Alert.AlertType.ERROR, "รหัสผ่านไม่ตรงกัน");
            return;
        }

        try {
            UserStore.updatePassword(resetUser, p1);
            showAlert(Alert.AlertType.INFORMATION, "เปลี่ยนรหัสผ่านสำเร็จ");
            backToLogin();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "บันทึกข้อมูลไม่สำเร็จ");
        }
    }

    @FXML
    private void backToLogin() {
        fp_questionForm.setVisible(false);
        np_newPassForm.setVisible(false);
        si_loginForm.setVisible(true);

        fp_username.clear();
        fp_answer.clear();
        fp_question.getSelectionModel().clearSelection();
        np_newPass.clear();
        np_confirmPass.clear();
        resetUser = null;
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });

            slider.play();
        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainForm.fxml"));
                Parent root = loader.load();

                mainFormController main = loader.getController();
                main.setUsername(u);

                Stage stage = (Stage) si_loginBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.sizeToScene();
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "เปิดหน้าหลักไม่สำเร็จ");
            }
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
        fp_question.getItems().addAll(questionList);

        fp_questionForm.setVisible(false);
        np_newPassForm.setVisible(false);
    }
}
