/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import smedim.config.Application;
import smedim.dao.UsuarioDAO;
import smedim.entidade.Usuario;
import smedim.util.BeanUtil;

import java.io.IOException;

/**
 *
 * @author Mikael Lima
 */
public class AutenticacaoLogin implements PhaseListener {

    private static final String RESTRITION_PATTERN = "/privado/";
    private String lastView;

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext context = pe.getFacesContext();
        String viewId = context.getViewRoot().getViewId();
        lastView = viewId;
        boolean urlProtegida = validar(RESTRITION_PATTERN, viewId);
        Usuario usuario = BeanUtil.getUsuarioLogado();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie cookieRemember = null;
        if (request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("remember_smedim"))
                    cookieRemember = cookie;
            }
        }
        if (cookieRemember != null && usuario == null && Application.REMEMBER_ME) {
            UsuarioDAO usuarioDAO = BeanProvider.getContextualReference(UsuarioDAO.class, false);
            usuario = usuarioDAO.obterPorLogin(cookieRemember.getValue());
            BeanUtil.colocarNaSessao("usuarioLogado", usuario);
            try {
                if ("/publico/login.xhtml".equals(viewId)) {
                    BeanUtil.navegar(context, "index");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(viewId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            BeanUtil.navegar(context, "index");
        } else if (urlProtegida && usuario == null) {
            BeanUtil.navegar(context, "login");
        } else if (BeanUtil.isRecepcao() && recepcao(viewId)) {
            BeanUtil.navegar(context, "index");
        } else if (BeanUtil.isMedico() && medico(viewId)) {
            BeanUtil.navegar(context, "index");
        }
        if (usuario != null) {
            BeanUtil.navegar(context, "index");
        }
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public boolean recepcao(String path) {
        boolean url = validar("/cadastro/", path)
                || validar("/medico/", path)
                || validar("/faturamento/", path);
        return url;
    }

    public boolean medico(String path) {
        boolean url = validar("/cadastro/", path)
                || validar("/expediente/", path)
                || validar("/faturamento/", path);
        return url;
    }

    public static boolean validar(String urlProtegida, String path) {
        return path.contains(urlProtegida);
    }
}
