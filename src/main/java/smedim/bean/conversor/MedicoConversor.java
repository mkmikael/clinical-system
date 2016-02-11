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
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.dao.GenericDAO;
import smedim.dao.MedicoDAO;
import smedim.entidade.Medico;

/**
 *
 * @author Simeia Lima
 */
@Named(value = "medicoConversor")
@ApplicationScoped
public class MedicoConversor implements Converter {

    @Inject
    private MedicoDAO dao;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.isEmpty()) {
            return null;
        } else {
            return dao.obterPorId(Medico.class, new Integer(string));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return "";
        } else {
            Integer id = ((Medico) o).getId();
            if (id == null) {
                return "";
            } else {
                return id.toString();
            }
        }
    }
    
}
