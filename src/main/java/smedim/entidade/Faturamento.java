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
@Table(name = "faturamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faturamento.findAll", query = "SELECT f FROM Faturamento f"),
    @NamedQuery(name = "Faturamento.findById", query = "SELECT f FROM Faturamento f WHERE f.id = :id"),
    @NamedQuery(name = "Faturamento.findByDataDoFaturamento", query = "SELECT f FROM Faturamento f WHERE f.dataDoFaturamento = :dataDoFaturamento"),
    @NamedQuery(name = "Faturamento.findByNumDeAtendimento", query = "SELECT f FROM Faturamento f WHERE f.numDeAtendimento = :numDeAtendimento"),
    @NamedQuery(name = "Faturamento.findByPreco", query = "SELECT f FROM Faturamento f WHERE f.preco = :preco")})
public class Faturamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data_do_faturamento")
    @Temporal(TemporalType.DATE)
    private Date dataDoFaturamento;
    @Basic(optional = false)
    @Column(name = "num_de_atendimento")
    private int numDeAtendimento;
    @Basic(optional = false)
    @Column(name = "preco")
    private double preco;
    @JoinColumn(name = "servico_convenio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ServicoConvenio servicoConvenio;
    @JoinColumn(name = "medico", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Medico medico;

    public Faturamento() {
    }

    public Faturamento(Integer id) {
        this.id = id;
    }

    public Faturamento(Integer id, Date dataDoFaturamento, int numDeAtendimento, double preco) {
        this.id = id;
        this.dataDoFaturamento = dataDoFaturamento;
        this.numDeAtendimento = numDeAtendimento;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDoFaturamento() {
        return dataDoFaturamento;
    }

    public void setDataDoFaturamento(Date dataDoFaturamento) {
        this.dataDoFaturamento = dataDoFaturamento;
    }

    public int getNumDeAtendimento() {
        return numDeAtendimento;
    }

    public void setNumDeAtendimento(int numDeAtendimento) {
        this.numDeAtendimento = numDeAtendimento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ServicoConvenio getServicoConvenio() {
        return servicoConvenio;
    }

    public void setServicoConvenio(ServicoConvenio servicoConvenio) {
        this.servicoConvenio = servicoConvenio;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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
        if (!(object instanceof Faturamento)) {
            return false;
        }
        Faturamento other = (Faturamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "smedim.entidade.Faturamento[ id=" + id + " ]";
    }
    
}
