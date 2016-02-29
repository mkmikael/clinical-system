/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.util;

import java.io.Serializable;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import smedim.entidade.Usuario;

/**
 *
 * @author Mikael Lima
 */
public class BeanUtil implements Serializable {


    public static void mensagem(Severity severity, String summary, String content) {
        FacesMessage fm = new FacesMessage(
                severity, summary, content);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void mensagem(Severity severity, String summary) {
        mensagem(severity, summary, "");
    }

    public static void mensagemFatal(String summary, String content) {
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_FATAL, summary, content);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void mensagemInfo(String summary, String content) {
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_INFO, summary, content);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void mensagemError(String summary, String content) {
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, summary, content);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void mensagemWarn(String summary, String content) {
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_WARN, summary, content);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void colocarNaSessao(String key, Object obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> mapa = context.getExternalContext().getSessionMap();
        mapa.put(key, obj);
    }

    public static Object lerDaSessao(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> mapa = context.getExternalContext().getSessionMap();
        return mapa.get(key);
    }

    public static void removerDaSessao(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> mapa = context.getExternalContext().getSessionMap();
        mapa.remove(key);
    }

    public static Usuario getUsuarioLogado() {
        Object o = BeanUtil.lerDaSessao("usuarioLogado");
        Usuario u = null;
        if (o != null && o instanceof Usuario)
            u = (Usuario) BeanUtil.lerDaSessao("usuarioLogado");
        return u;
    }

    public static boolean isRecepcao() {
        Object obj = lerDaSessao("usuarioLogado");
        if (obj == null) {
            return false;
        } else {
            Usuario u = (Usuario) lerDaSessao("usuarioLogado");
            return u.getPerfil() == 'R';
        }
    }

    public static boolean isAdm() {
        Object obj = lerDaSessao("usuarioLogado");
        if (obj == null) {
            return false;
        } else {
            Usuario u = (Usuario) lerDaSessao("usuarioLogado");
            return u.getPerfil() == 'A';
        }
    }

    public static boolean isMedico() {
        Object obj = lerDaSessao("usuarioLogado");
        if (obj == null) {
            return false;
        } else {
            Usuario u = (Usuario) lerDaSessao("usuarioLogado");
            return u.getPerfil() == 'M';
        }
    }

    public static void navegar(FacesContext context, String outcome) {
        NavigationHandler navigation = context.getApplication()
                .getNavigationHandler();
        navigation.handleNavigation(context, null, outcome);
    }
}
