package com.example.exameninterfaces18122023;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
    private TableView<Coche> tblParking;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colMatricula;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colModelo;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colFechaEntrada;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colFechaSalida;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colCliente;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colTarifa;
    @javafx.fxml.FXML
    private TableColumn<Coche,String> colCoste;
    @javafx.fxml.FXML
    private Label lblFooter;
    @javafx.fxml.FXML
    private Label lblCoste;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbModelo.setEditable(true);

        cbModelo.getItems().addAll("Nii-san", "Pug-DEMOND", "Pgeot");
        cbCliente.getItems().addAll("Victor Fernandez", "Lucas Gomez", "Manuel Burguete");

        ToggleGroup tarifaGroup = new ToggleGroup();
        rbStandard.setToggleGroup(tarifaGroup);
        rbOferta.setToggleGroup(tarifaGroup);
        rbLargaDuracion.setToggleGroup(tarifaGroup);

        tblParking.getItems().add(new Coche("ABC223", "Toyota Corolla", "Juan Pérez", "Oferta", LocalDate.of(2023, 4, 27), LocalDate.of(2023, 5, 10), 78));
        tblParking.getItems().add(new Coche("XYZ789", "Honda Civic", "Laura García", "Oferta", LocalDate.of(2023, 4, 25), LocalDate.of(2023, 5, 10), 90));
        tblParking.getItems().add(new Coche("DEF456", "Ford Focus", "Carlos López", "Oferta", LocalDate.of(2023, 4, 24), LocalDate.of(2023, 5, 8), 84));
        tblParking.getItems().add(new Coche("GHI012", "Volkswagen Golf", "Ana Martín", "Standard", LocalDate.of(2023, 4, 22), LocalDate.of(2023, 5, 6), 112));
        tblParking.getItems().add(new Coche("JKL345", "Audi A4", "Roberto Díaz", "Oferta", LocalDate.of(2023, 5, 2), LocalDate.of(2023, 5, 12), 60));


        colMatricula.setCellValueFactory( (fila) -> {
            String matricula = fila.getValue().getMatricula();
            return new SimpleStringProperty( matricula);
        });

        colModelo.setCellValueFactory( (fila) -> {
            String modelo = fila.getValue().getModelo();
            return new SimpleStringProperty( modelo);
        });

        colFechaEntrada.setCellValueFactory( (fila) -> {
            LocalDate entrada = fila.getValue().getFechaEntrada();
            return new SimpleStringProperty(entrada.toString());
        });

        colFechaSalida.setCellValueFactory( (fila) -> {
            LocalDate salida = fila.getValue().getFechaSalida();
            return new SimpleStringProperty(salida.toString());
        });

        colCliente.setCellValueFactory( (fila) -> {
            String cliente = fila.getValue().getCliente();
            return new SimpleStringProperty( cliente);
        });

        colTarifa.setCellValueFactory( (fila) -> {
            String tarifa = fila.getValue().getTarifa();
            return new SimpleStringProperty( tarifa);
        });

        colCoste.setCellValueFactory( (fila) -> {
            Integer coste = fila.getValue().getCosteTotal();
            return new SimpleStringProperty(coste.toString()+" €");
        });

        btnAgregar.setOnAction(this::accionAgregar);
        
        lblFooter.setOnMouseClicked(event -> mostrarAlerta("Víctor Jesús Fernández Noguer 2ºDAM"));

        dpEntrada.setOnAction(event -> {
            this.mostrarCosteLabel();
        });

        dpSalida.setOnAction(event -> {
            this.mostrarCosteLabel();
        });

        tarifaGroup.selectedToggleProperty().addListener(event -> {
            this.mostrarCosteLabel();
        });

    }

    private void accionAgregar(ActionEvent event) {
        if (formularioIncompleto()) {
            mostrarAlerta("Todos los campos son obligatorios.");
        } else {
            String matricula = txtMatricula.getText();

            String modelo;
            String modeloCustom = cbModelo.getEditor().getText();
            if (!modeloCustom.isEmpty()){
                modelo = modeloCustom;
            } else {
               modelo = cbModelo.getSelectionModel().getSelectedItem().toString();
            }

            String cliente = cbCliente.getSelectionModel().getSelectedItem().toString();
            String tarifa = obtenerTarifaSeleccionada();
            LocalDate fechaEntrada = dpEntrada.getValue();
            LocalDate fechaSalida = dpSalida.getValue();


            long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
            int costeTotal = (int) dias * calcularCosteDiario(tarifa);

            tblParking.getItems().add(new Coche(matricula, modelo, cliente, tarifa, fechaEntrada, fechaSalida, costeTotal));

            resetearFormulario();
        }
    }

    private String obtenerTarifaSeleccionada() {
        if (rbStandard.isSelected()) {
            return "Standard";
        } else if (rbOferta.isSelected()) {
            return "Oferta";
        } else if (rbLargaDuracion.isSelected()) {
            return "Larga duración";
        } else {
            return "";
        }
    }

    private int calcularCosteDiario(String tarifa) {
        switch (tarifa) {
            case "Standard":
                return 8;
            case "Oferta":
                return 6;
            case "Larga duración":
                return 2;
            default:
                return 0;
        }
    }

    private boolean formularioIncompleto() {

        Boolean modeloEmpty = cbModelo.getSelectionModel().isEmpty() && cbModelo.getEditor().getText().isEmpty();

        return txtMatricula.getText().trim().isEmpty() ||
                modeloEmpty ||
                cbCliente.getSelectionModel().isEmpty() ||
                dpEntrada.getValue() == null ||
                dpSalida.getValue() == null ||
                (!rbStandard.isSelected() && !rbOferta.isSelected() && !rbLargaDuracion.isSelected());
    }

    private void mostrarCosteLabel() {
        String tarifa = obtenerTarifaSeleccionada();
        LocalDate fechaEntrada = dpEntrada.getValue();
        LocalDate fechaSalida = dpSalida.getValue();

        if (tarifa == null || fechaEntrada == null || fechaSalida == null)  return;

        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        int costeTotal = (int) dias * calcularCosteDiario(tarifa);

        lblCoste.setText(costeTotal+" €");
    }

    private void resetearFormulario() {
        txtMatricula.clear();
        cbModelo.getEditor().clear();
        cbModelo.getSelectionModel().clearSelection();
        cbCliente.getSelectionModel().clearSelection();
        dpEntrada.setValue(null);
        dpSalida.setValue(null);
        rbStandard.setSelected(false);
        rbOferta.setSelected(false);
        rbLargaDuracion.setSelected(false);
        lblCoste.setText("XXX €");

    }

    private void mostrarAlerta(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }


}