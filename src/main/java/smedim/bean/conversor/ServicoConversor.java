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

import smedim.entidade.Servico;
import smedim.rn.ServicoRN;

/**
 *
 * @author Mikael Lima
 */
@Named(value = "servicoConversor")
@ApplicationScoped
public class ServicoConversor implements Converter {

    @Inject
    private ServicoRN rn;

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
            Integer id = ((Servico) o).getId();
            if (id == null) {
                return "";
            } else {
                return id.toString();
            }
        }
    }

}
