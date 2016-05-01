/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.deltaspike.data.api.criteria.Criteria;
import smedim.config.Application;
import smedim.entidade.Cliente;
import smedim.entidade.Cliente_;
import smedim.entidade.Medico;
import smedim.entidade.Usuario;
import smedim.repository.ClienteRepository;
import smedim.rn.MedicoService;
import smedim.rn.UsuarioRN;
import smedim.rn.relatorio.ExcelExporter;
import smedim.util.BeanUtil;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class UsuarioBean implements Serializable {

    @Inject
    private ExcelExporter excelExporter;
    @Inject
    private Logger log;
    @Inject
    private UsuarioRN rn;
    @Inject
    private MedicoService rnM;
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private List<Medico> medicos;
    private boolean remember;
    private boolean medico;
    private boolean recepecao;
    private boolean adm;

    public void salvar() {
        if (rn.salvar(usuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O usuário foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o usuário.");
        }
    }
    
    public void deletar() {
        if (rn.deletar(usuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O usuário foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o usuário.");
        }
    }
    
    public List<Usuario> getUsuarios() {
        usuarios = rn.obterTodos();
        return usuarios;
    }
    
    public String autenticar() {
        usuario = rn.autenticar(usuario);
        if (usuario != null) {
            BeanUtil.colocarNaSessao("usuarioLogado", usuario);
            if (remember && Application.REMEMBER_ME) {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                boolean containsRemember = false;
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("remember_smedim"))
                        containsRemember = true;
                }
                if (!containsRemember && BeanUtil.getUsuarioLogado() != null) {
                    response.addCookie(new Cookie("remember_smedim", BeanUtil.getUsuarioLogado().getLogin()));
                } else {
                    log.warning("O cookie não foi adicionado");
                }
            }
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO,
                    "Seja Bem Vindo " + usuario.getNome() + "!");

            return "/privado/index.xhtml";
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_ERROR, "Erro! Login ou Senha inválidos.");
            return null;
        }
    }

    public String deslogar() {
        BeanUtil.removerDaSessao("usuarioLogado");
        if (Application.REMEMBER_ME) {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Cookie cookieRemember = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("remember_smedim"))
                    cookieRemember = cookie;
            }
            if (cookieRemember != null) {
                cookieRemember.setMaxAge(0);
                response.addCookie(cookieRemember);
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    /*
    * Method test for class ExcelExporter
     */
    public void exporter() {
        Map<String, String> header = new HashMap<String, String>() {
            {
                put("nome", "Nome");
                put("idade", "Idade");
            }
        };
        ArrayList<Map<String, String>> dataset = new ArrayList<Map<String, String>>() {
            {
                add(new HashMap<String, String>() { {
                    put("nome", "Mikael Lima");
                    put("idade", "21");
                } });
                add(new HashMap<String, String>() { {
                    put("nome", "Guilherme Lima");
                    put("idade", "1");
                } });
                add(new HashMap<String, String>() { {
                    put("nome", "Yan Lima");
                    put("idade", "18");
                } });
                add(new HashMap<String, String>() { {
                    put("nome", "Simeia Lima");
                    put("idade", "46");
                } });
                add(new HashMap<String, String>() { {
                    put("nome", "Miguel Lima");
                    put("idade", "51");
                } });
                add(new HashMap<String, String>() { {
                    put("nome", "Kezya Lima");
                    put("idade", "24");
                } });
            }
        };

        excelExporter.exporter(header, dataset, "teste.xlsx");
    }

    public boolean isMedico() {
        medico = BeanUtil.isMedico();
        return medico;
    }

    public boolean isRecepecao() {
        recepecao = BeanUtil.isRecepcao();
        return recepecao;
    }

    public boolean isAdm() {
        adm = BeanUtil.isAdm();
        return adm;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Medico> getMedicos() {
        if (medicos == null) {
            medicos = rnM.obterTodos();
        }
        return medicos;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
