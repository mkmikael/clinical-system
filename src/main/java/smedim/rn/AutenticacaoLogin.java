/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import smedim.util.BeanUtil;

/**
 *
 * @author Mikael Lima
 */
public class AutenticacaoLogin implements PhaseListener {

    private static final String RESTRITION_PATTERN = "/privado/";

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext context = pe.getFacesContext();
        String viewId = context.getViewRoot().getViewId();

        boolean urlProtegida = validar(RESTRITION_PATTERN, viewId);
        Object usuario = BeanUtil.lerDaSessao("usuarioLogado");

        if (urlProtegida && usuario == null) {
            BeanUtil.navegar(context, "login");
        } else if (BeanUtil.isRecepcao() && recepcao(viewId)) {
            BeanUtil.navegar(context, "index");
        } else if (BeanUtil.isMedico() && medico(viewId)) {
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
