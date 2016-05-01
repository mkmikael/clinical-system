package smedim.rn;

import smedim.entidade.Faturamento;
import smedim.rn.relatorio.ExcelExporter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Simeia Lima on 01/05/2016.
 */
@ApplicationScoped
public class FaturamentoService implements Serializable {

    @Inject
    private ExcelExporter excelExporter;
    @Inject
    private EntityManager entityManager;

    public void relatorioByDataAndIsParticular(Date data, boolean isParticular) {
        String operation = isParticular ? "=" : "<>";
        String jpql = "select f from Faturamento f where f.servicoConvenio.convenio.nome " + operation + " 'Particular' " +
                "and f.dataDoFaturamento = :dataDoFaturamento";
        List<Faturamento> result = entityManager.createQuery(jpql)
                .setParameter("dataDoFaturamento", data, TemporalType.DATE)
                .getResultList();
        Map<String, String> header = new LinkedHashMap<String, String>() {
            {
                put("convenio", "Convênio");
                put("servico", "Serviço");
                put("preco", "Preço");
                put("numero", "Núm. Atendimentos");
                put("subtotal", "Sub-Total");
            }
        };
        List<Map<String, String>> dataset = new ArrayList<>();
        for (final Faturamento f : result) {
            Map<String, String> entry = new HashMap<>();
            if (f.getServicoConvenio() != null) {
                entry.put("convenio", f.getServicoConvenio().getConvenio().getNome());
                entry.put("servico", f.getServicoConvenio().getServico().getNome());
            }
            DecimalFormat df = new DecimalFormat("#,##0.00");
            entry.put("preco", df.format(f.getPreco()));
            entry.put("numero", f.getNumDeAtendimento() + "");
            double subtotal = f.getNumDeAtendimento() * f.getPreco();
            entry.put("subtotal", df.format(subtotal));
            dataset.add(entry);
        }
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String convenioOuParticular = isParticular ? "particular" : "convenios";
        excelExporter.exporter(header, dataset, "faturamento_" + convenioOuParticular + "_" + currentDate);
    }
}
