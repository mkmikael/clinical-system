package smedim.bean;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import smedim.entidade.Cliente;
import smedim.entidade.Faturamento;
import smedim.entidade.Medico;
import smedim.repository.FaturamentoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Simeia Lima on 05/03/2016.
 */
public class FaturamentoLazyTable extends LazyDataModel<Faturamento> {

    private FaturamentoRepository repository;
    private Medico medico;

    public FaturamentoLazyTable(FaturamentoRepository repository) {
        this.repository = repository;
    }

    public FaturamentoLazyTable(FaturamentoRepository repository, Medico medico) {
        this.repository = repository;
        this.medico = medico;
    }

    @Override
    public List<Faturamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (medico == null)
            return Collections.EMPTY_LIST;

        List<Faturamento> list = repository.findByMedico(medico, first, pageSize);
        System.out.println(list);
        if (getRowCount() <= 0) {
            int count = repository.countByMedico(medico).intValue();
            setRowCount(count);
            System.out.println(count);
        }
        setPageSize(pageSize);
        return list;
    }

    @Override
    public Object getRowKey(Faturamento object) {
        return object.getId();
    }

    @Override
    public Faturamento getRowData(String rowKey) {
        Integer id = new Integer(rowKey);
        return repository.findBy(id);
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
