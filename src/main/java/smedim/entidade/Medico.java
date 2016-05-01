/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.Dependent;
import javax.persistence.*;

/**
 *
 * @author Simeia Lima
 */
@Dependent
@Entity
@NamedQueries({
    @NamedQuery(name = "Medico.findAll", query = "SELECT c FROM Medico c"),
    @NamedQuery(name = "Medico.findById", query = "SELECT c FROM Medico c WHERE c.id = :id"),
    @NamedQuery(name = "Medico.findByNome", query = "SELECT c FROM Medico c WHERE c.nome = :nome")})
public class Medico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
    private List<Cliente> clientes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
    private List<Faturamento> faturamentos;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Convenio> convenios;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Servico> servicos;
    @Transient // TODO: Não ultilizado
    private List<ServicoConvenio> servicoConvenios;

    public Medico() {
    }

    public Medico(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Faturamento> getFaturamentos() {
        return faturamentos;
    }

    public void setFaturamentos(List<Faturamento> faturamentos) {
        this.faturamentos = faturamentos;
    }

    public List<ServicoConvenio> getServicoConvenios() {
        if (servicoConvenios == null) {
            servicoConvenios = new ArrayList<>();
        }
        return servicoConvenios;
    }

    public void setServicoConvenios(List<ServicoConvenio> servicoConvenios) {
        this.servicoConvenios = servicoConvenios;
    }

    public List<Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Convenio> convenios) {
        this.convenios = convenios;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Medico other = (Medico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() { return nome; }
}
