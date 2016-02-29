package smedim.bean;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import smedim.entidade.Cliente;
import smedim.rn.ClienteService;

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

    @Override
    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Cliente> list = clienteService.list(first, pageSize);
        if(getRowCount() <= 0){
            setRowCount(clienteService.count().intValue());
        }
        setPageSize(pageSize);
        return list;
    }

}
