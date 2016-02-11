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
@Table(name = "servico_convenio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicoConvenio.findAll", query = "SELECT s FROM ServicoConvenio s"),
    @NamedQuery(name = "ServicoConvenio.findById", query = "SELECT s FROM ServicoConvenio s WHERE s.id = :id"),
    @NamedQuery(name = "ServicoConvenio.findByPreco", query = "SELECT s FROM ServicoConvenio s WHERE s.preco = :preco")})
public class ServicoConvenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "preco")
    private double preco;
    @JoinColumn(name = "convenio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Convenio convenio;
    @JoinColumn(name = "servico", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servico servico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoConvenio")
    private List<Faturamento> faturamentoList;

    public ServicoConvenio() {
    }

    public ServicoConvenio(Integer id) {
        this.id = id;
    }

    public ServicoConvenio(Integer id, double preco) {
        this.id = id;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @XmlTransient
    public List<Faturamento> getFaturamentoList() {
        return faturamentoList;
    }

    public void setFaturamentoList(List<Faturamento> faturamentoList) {
        this.faturamentoList = faturamentoList;
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
        if (!(object instanceof ServicoConvenio)) {
            return false;
        }
        ServicoConvenio other = (ServicoConvenio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "smedim.entidade.ServicoConvenio[ id=" + id + " ]";
    }
    
}
