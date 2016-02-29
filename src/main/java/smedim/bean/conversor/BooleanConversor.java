/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean.conversor;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author Mikael Lima
 */
@Named(value = "booleanConversor")
@ApplicationScoped
public class BooleanConversor implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.isEmpty()) {
            return null;
        } else {
            switch (string) {
                case "Sim":
                    return Boolean.TRUE;
                case "Não":
                    return Boolean.FALSE;
                default:
                    return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return "";
        } else {
            Boolean bool = (Boolean) o;
            if (bool) {
                return "Sim";
            } else {
                return "Não";
            }
        }
    }

}
