/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn.relatorio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mikael Lima
 */
public class GerarRelatorio implements Serializable {

    public static String getPath() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.getRealPath("/resources/relatorio/");
    }

    public static void exportarParaPDF(
            String jasper, Map<String, Object> param, List<?> lista) {
        try {
            JRDataSource dataSource = new JREmptyDataSource();
            if (lista != null) {
                dataSource = new JRBeanCollectionDataSource(lista);
            }
            String caminho = getPath() + File.separator + jasper + ".jasper";
            JasperPrint print = JasperFillManager.fillReport(caminho, param, dataSource);
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseContentLength(bytes.length);
            externalContext.getResponseOutputStream().write(bytes);
            facesContext.responseComplete();
        } catch (JRException | IOException ex) {
            Logger.getLogger(GerarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void exportarParaPDF(
            String jasper, Map<String, Object> param, Connection connection) {
        try {
            String caminho = getPath() + File.separator + jasper + ".jasper";
            JasperPrint print = JasperFillManager.fillReport(caminho, param, connection);
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseContentLength(bytes.length);
            externalContext.getResponseOutputStream().write(bytes);
            facesContext.responseComplete();
        } catch (JRException | IOException ex) {
            Logger.getLogger(GerarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
