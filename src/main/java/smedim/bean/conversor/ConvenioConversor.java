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
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Convenio;
import smedim.rn.ConvenioRN;

/**
 *
 * @author Mikael Lima
 */
@Named(value = "convenioConversor")
@ApplicationScoped
public class ConvenioConversor implements Converter {

    @Inject
    private ConvenioRN rn;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.isEmpty()) {
            return null;
        } else {
            return rn.obterPorId(new Integer(string));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return "";
        } else {
            Integer id = ((Convenio) o).getId();
            if (id == null) {
                return "";
            } else {
                return id.toString();
            }
        }
    }

}
