/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bpmlab
 */
@Entity
@Table(name = "prontuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prontuario.findAll", query = "SELECT p FROM Prontuario p"),
    @NamedQuery(name = "Prontuario.findById", query = "SELECT p FROM Prontuario p WHERE p.id = :id"),
    @NamedQuery(name = "Prontuario.findByAntecedentesPessoais", query = "SELECT p FROM Prontuario p WHERE p.antecedentesPessoais = :antecedentesPessoais"),
    @NamedQuery(name = "Prontuario.findByAntecedentesFamiliares", query = "SELECT p FROM Prontuario p WHERE p.antecedentesFamiliares = :antecedentesFamiliares")})
public class Prontuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "antecedentes_pessoais")
    private String antecedentesPessoais;
    @Basic(optional = false)
    @Column(name = "antecedentes_familiares")
    private String antecedentesFamiliares;
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prontuario")
    private List<SubProntuario> subProntuarioList;

    public Prontuario() {
    }

    public Prontuario(Integer id) {
        this.id = id;
    }

    public Prontuario(Integer id, String antecedentesPessoais, String antecedentesFamiliares) {
        this.id = id;
        this.antecedentesPessoais = antecedentesPessoais;
        this.antecedentesFamiliares = antecedentesFamiliares;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAntecedentesPessoais() {
        return antecedentesPessoais;
    }

    public void setAntecedentesPessoais(String antecedentesPessoais) {
        this.antecedentesPessoais = antecedentesPessoais;
    }

    public String getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }

    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @XmlTransient
    public List<SubProntuario> getSubProntuarioList() {
        return subProntuarioList;
    }

    public void setSubProntuarioList(List<SubProntuario> subProntuarioList) {
        this.subProntuarioList = subProntuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prontuario)) {
            return false;
        }
        Prontuario other = (Prontuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "smedim.entidade.Prontuario[ id=" + id + " ]";
    }
    
}
