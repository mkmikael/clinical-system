/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean.conversor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author Mikael Lima
 */
@Named(value = "dataTempoConversor")
@ApplicationScoped
public class DataTempoConversor implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.isEmpty()) {
            return null;
        } else {
            try {
                SimpleDateFormat ft = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                return ft.parse(string);
            } catch (ParseException ex) {
                Logger.getLogger(DataTempoConversor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return "";
        } else {
            Date date = (Date) o;
            SimpleDateFormat ft = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            return ft.format(date);
        }
    }

}
