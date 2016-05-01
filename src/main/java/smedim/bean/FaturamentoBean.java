package smedim.bean;

import smedim.entidade.Medico;
import smedim.repository.FaturamentoRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Simeia Lima on 05/03/2016.
 */
@Named
@ConversationScoped
public class FaturamentoBean implements Serializable {

    @Inject
    private FaturamentoRepository faturamentoRepository;
    private Medico medico;
    private FaturamentoLazyTable faturamentoLazyTable;

    @PostConstruct
    public void init() {
    }

    public FaturamentoLazyTable getFaturamentoLazyTable() {
        System.out.println("Medico: " + medico);
        faturamentoLazyTable = new FaturamentoLazyTable(faturamentoRepository, medico);
        return faturamentoLazyTable;
    }

    public void setFaturamentoLazyTable(FaturamentoLazyTable faturamentoLazyTable) {
        this.faturamentoLazyTable = faturamentoLazyTable;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
