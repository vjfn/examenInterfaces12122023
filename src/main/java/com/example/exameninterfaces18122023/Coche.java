package com.example.exameninterfaces18122023;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Coche {
    private String matricula;
    private String modelo;
    private String cliente;
    private String tarifa;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer costeTotal;


}
