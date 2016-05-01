package smedim.rn.relatorio;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import smedim.config.FacesContextWrapper;
import smedim.config.FacesContextWrapper.ContentType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Simeia Lima on 30/04/2016.
 */
@ApplicationScoped
public class ExcelExporter implements Serializable {

    @Inject
    private FacesContextWrapper facesContextWrapper;

    public byte[] exporter(Map<String, String> header, List<Map<String, String>> dataset) {
        if (header == null)
            throw new IllegalArgumentException("param 'header' is required");
        if (dataset == null)
            throw new IllegalArgumentException("param 'dataset' is required");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        List<String> headerLabels = new ArrayList<>(header.values());
        List<String> headerKeys = new ArrayList<>(header.keySet());

        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < headerLabels.size(); i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(headerLabels.get(i));
        }

        for (int i = 1; i <= dataset.size(); i++) {
            Row row = sheet.createRow(i);
            Map<String, String> map = dataset.get(i - 1);
            for (int j = 0; j < headerKeys.size(); j++) {
                Cell cell = row.createCell(j);
                String key = headerKeys.get(j);
                cell.setCellValue(map.get(key));
            }
        }

        for (int i = 0; i < headerKeys.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    public void exporter(Map<String, String> header, List<Map<String, String>> dataset, String fileName) {
        byte[] bytes = exporter(header, dataset);
        facesContextWrapper.download(bytes, fileName, ContentType.XLSX);
    }

}
