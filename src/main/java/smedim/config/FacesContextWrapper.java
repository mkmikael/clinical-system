package smedim.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by Simeia Lima on 30/04/2016.
 */
@ApplicationScoped
public class FacesContextWrapper {

    public FacesContextWrapper() {
    }

    public void download(byte[] bytes, String fileName, ContentType contentType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType(contentType.mime);
        if (fileName.matches(".*\\." + contentType.sufix + "$"))
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName);
        else
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + "." + contentType.sufix);
        externalContext.setResponseContentLength(bytes.length);
        try {
            externalContext.getResponseOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        facesContext.responseComplete();
    }

    public enum ContentType {
        XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
        PDF("application/pdf", "pdf");

        String mime;
        String sufix;

        ContentType(String mime, String sufix) {
            this.mime = mime;
            this.sufix = sufix;
        }
    }
}
