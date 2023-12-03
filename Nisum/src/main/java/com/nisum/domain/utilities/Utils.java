package com.nisum.domain.utilities;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Utils {

    public String formatDate(Date fecha, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha);
    }

    public boolean validarExpresionRegular(String texto, String expresionRegular) {
        try {
            Pattern pattern = Pattern.compile(expresionRegular);
            Matcher matcher = pattern.matcher(texto);
            return matcher.matches();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;

        }

    }

}
