package com.example.exameninterfaces18122023;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingController implements Initializable{
    @javafx.fxml.FXML
    private Label lblHeader;
    @javafx.fxml.FXML
    private TextField txtMatricula;
    @javafx.fxml.FXML
    private ComboBox cbModelo;
    @javafx.fxml.FXML
    private ComboBox cbCliente;
    @javafx.fxml.FXML
    private RadioButton rbStandard;
    @javafx.fxml.FXML
    private RadioButton rbOferta;
    @javafx.fxml.FXML
    private RadioButton rbLargaDuracion;
    @javafx.fxml.FXML
    private DatePicker dpEntrada;
    @javafx.fxml.FXML
    private DatePicker dpSalida;
    @javafx.fxml.FXML
    private Button btnAgregar;
    @javafx.fxml.FXML
    private Button btnSalir;
    @javafx.fxml.FXML
    private TableView tblParking;
    @javafx.fxml.FXML
    private TableColumn colMatricula;
    @javafx.fxml.FXML
    private TableColumn colModelo;
    @javafx.fxml.FXML
    private TableColumn colFechaEntrada;
    @javafx.fxml.FXML
    private TableColumn colFechaSalida;
    @javafx.fxml.FXML
    private TableColumn colCliente;
    @javafx.fxml.FXML
    private TableColumn colTarifa;
    @javafx.fxml.FXML
    private TableColumn colCoste;
    @javafx.fxml.FXML
    private Label lblFooter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbModelo.getItems().addAll("Nii-san", "Pug-DEMOND", "Pgeot");
        cbCliente.getItems().addAll("1,Victor,vic@mail.com", "2,Victor,vic@mail.com", "3,Victor,vic@mail.com");
        
        btnAgregar.setOnAction(this::accionAgregar);
        
        lblFooter.setOnMouseClicked(event -> mostrarAlerta("Víctor Jesús Fernández Noguer 2ºDAM"));
    }

    private void accionAgregar(ActionEvent event) {
        if (formularioIncompleto()) {
            mostrarAlerta("Todos los campos son obligatorios.");
        } else {
            resetearFormulario();
        }
    }

    private boolean formularioIncompleto() {
        return txtMatricula.getText().trim().isEmpty() ||
                cbModelo.getSelectionModel().isEmpty() ||
                cbCliente.getSelectionModel().isEmpty() ||
                dpEntrada.getValue() == null ||
                dpSalida.getValue() == null ||
                (!rbStandard.isSelected() && !rbOferta.isSelected() && !rbLargaDuracion.isSelected());
    }

    private void resetearFormulario() {
        txtMatricula.clear();
        cbModelo.getSelectionModel().clearSelection();
        cbCliente.getSelectionModel().clearSelection();
        dpEntrada.setValue(null);
        dpSalida.setValue(null);
        rbStandard.setSelected(false);
        rbOferta.setSelected(false);
        rbLargaDuracion.setSelected(false);
    }

    private void mostrarAlerta(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}