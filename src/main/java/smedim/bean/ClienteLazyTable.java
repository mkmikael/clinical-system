package smedim.bean;

import org.apache.deltaspike.data.api.criteria.Criteria;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import smedim.entidade.Cliente;
import smedim.entidade.Cliente_;
import smedim.rn.ClienteService;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * Created by Simeia Lima on 28/02/2016.
 */
public class ClienteLazyTable extends LazyDataModel<Cliente> {

    private ClienteService clienteService;

    public ClienteLazyTable(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Object getRowKey(Cliente object) {
        return object.getId();
    }

    @Override
    public Cliente getRowData(String rowKey) {
        Integer key = new Integer(rowKey);
        return clienteService.findById(key);
    }

//    @Override
//    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//        List<Cliente> list = clienteService.list(filters, first, pageSize);
//        if (getRowCount() <= 0)
//            setRowCount(clienteService.count(filters).intValue());
//        setPageSize(pageSize);
//        return list;
//    }

    @Override
    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (getRowCount() <= 0) {
            Criteria<Cliente, Cliente> criteria = clienteService.createCriteria(filters);
            Criteria<Cliente, Long> select = criteria.select(Long.class, clienteService.getClienteRepository().count(Cliente_.id));
            setRowCount(select.getSingleResult().intValue());
        }
        setPageSize(pageSize);
        return clienteService.createCriteria(filters).createQuery()
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

}
