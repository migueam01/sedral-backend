package com.uce.sedral.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejoFechas {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

    public static String convertirFechaAString(LocalDateTime fecha) {
        return fecha.format(formatter);
    }

    public static LocalDateTime convertirFechaADateTime(String fechaString) {
        return LocalDateTime.parse(fechaString, formatter);
    }
}
