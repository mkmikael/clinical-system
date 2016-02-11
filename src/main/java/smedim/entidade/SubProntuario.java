/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bpmlab
 */
@Entity
@Table(name = "sub_prontuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubProntuario.findAll", query = "SELECT s FROM SubProntuario s"),
    @NamedQuery(name = "SubProntuario.findById", query = "SELECT s FROM SubProntuario s WHERE s.id = :id"),
    @NamedQuery(name = "SubProntuario.findByQueixaPrincipal", query = "SELECT s FROM SubProntuario s WHERE s.queixaPrincipal = :queixaPrincipal"),
    @NamedQuery(name = "SubProntuario.findByHistoricoAtual", query = "SELECT s FROM SubProntuario s WHERE s.historicoAtual = :historicoAtual"),
    @NamedQuery(name = "SubProntuario.findByExameClinico", query = "SELECT s FROM SubProntuario s WHERE s.exameClinico = :exameClinico"),
    @NamedQuery(name = "SubProntuario.findByImpressaoDiagnostica", query = "SELECT s FROM SubProntuario s WHERE s.impressaoDiagnostica = :impressaoDiagnostica"),
    @NamedQuery(name = "SubProntuario.findByCondutaTerapeutica", query = "SELECT s FROM SubProntuario s WHERE s.condutaTerapeutica = :condutaTerapeutica"),
    @NamedQuery(name = "SubProntuario.findByEvolucao", query = "SELECT s FROM SubProntuario s WHERE s.evolucao = :evolucao"),
    @NamedQuery(name = "SubProntuario.findByAtendimento", query = "SELECT s FROM SubProntuario s WHERE s.atendimento = :atendimento")})
public class SubProntuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "queixa_principal")
    private String queixaPrincipal;
    @Basic(optional = false)
    @Column(name = "historico_atual")
    private String historicoAtual;
    @Basic(optional = false)
    @Column(name = "exame_clinico")
    private String exameClinico;
    @Basic(optional = false)
    @Column(name = "impressao_diagnostica")
    private String impressaoDiagnostica;
    @Basic(optional = false)
    @Column(name = "conduta_terapeutica")
    private String condutaTerapeutica;
    @Basic(optional = false)
    @Column(name = "evolucao")
    private String evolucao;
    @Basic(optional = false)
    @Column(name = "atendimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date atendimento;
    @JoinColumn(name = "prontuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prontuario prontuario;

    public SubProntuario() {
    }

    public SubProntuario(Integer id) {
        this.id = id;
    }

    public SubProntuario(Integer id, String queixaPrincipal, String historicoAtual, String exameClinico, String impressaoDiagnostica, String condutaTerapeutica, String evolucao, Date atendimento) {
        this.id = id;
        this.queixaPrincipal = queixaPrincipal;
        this.historicoAtual = historicoAtual;
        this.exameClinico = exameClinico;
        this.impressaoDiagnostica = impressaoDiagnostica;
        this.condutaTerapeutica = condutaTerapeutica;
        this.evolucao = evolucao;
        this.atendimento = atendimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getHistoricoAtual() {
        return historicoAtual;
    }

    public void setHistoricoAtual(String historicoAtual) {
        this.historicoAtual = historicoAtual;
    }

    public String getExameClinico() {
        return exameClinico;
    }

    public void setExameClinico(String exameClinico) {
        this.exameClinico = exameClinico;
    }

    public String getImpressaoDiagnostica() {
        return impressaoDiagnostica;
    }

    public void setImpressaoDiagnostica(String impressaoDiagnostica) {
        this.impressaoDiagnostica = impressaoDiagnostica;
    }

    public String getCondutaTerapeutica() {
        return condutaTerapeutica;
    }

    public void setCondutaTerapeutica(String condutaTerapeutica) {
        this.condutaTerapeutica = condutaTerapeutica;
    }

    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }

    public Date getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Date atendimento) {
        this.atendimento = atendimento;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
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
        if (!(object instanceof SubProntuario)) {
            return false;
        }
        SubProntuario other = (SubProntuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "smedim.entidade.SubProntuario[ id=" + id + " ]";
    }
    
}
