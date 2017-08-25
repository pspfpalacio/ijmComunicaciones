package managedBean.reporte;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@SessionScoped
public class BeanReporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void generar(Map parametros, List lista, String reporte, String printeo){
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/" + reporte + ".jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					lista);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", printeo + ";filename="
					+ reporte + ".pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parametros, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
