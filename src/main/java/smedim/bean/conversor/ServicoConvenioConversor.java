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

import smedim.entidade.ServicoConvenio;
import smedim.rn.ServicoConvenioRN;

/**
 *
 * @author JULIENA NOBRE SOARES
 */
@Named(value = "servicoConvenioConversor")
@ApplicationScoped
public class ServicoConvenioConversor implements Converter {

    @Inject
    private ServicoConvenioRN rn;
    
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
            Integer id = ((ServicoConvenio) o).getId();
            if (id == null) {
                return "";
            } else {
                return id.toString();
            }
        }
    }
}
