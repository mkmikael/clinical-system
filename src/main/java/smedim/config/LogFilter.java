package smedim.config;

import smedim.entidade.Usuario;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Simeia Lima on 10/02/2016.
 */
public class LogFilter implements PhaseListener {
    private static Logger log = Logger.getLogger("LogFilter");
    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        HttpServletRequest request = (HttpServletRequest) phaseEvent.getFacesContext().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String view = phaseEvent.getFacesContext().getViewRoot().getViewId();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for (String name : parameterMap.keySet()) {
            params.put( name, Arrays.toString( parameterMap.get(name) ) );
        }
        Object user = request.getSession().getAttribute("usuarioLogado");
        String username = "";
        if (user != null instanceof Usuario) {
            username = ((Usuario)user).getLogin();
        }

        log.info(String.format("ip: %s, user: %s, view: %s, params: %s", ipAddress, username, view, params));
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
