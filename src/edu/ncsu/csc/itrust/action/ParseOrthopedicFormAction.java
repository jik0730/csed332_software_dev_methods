package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * ParseOrthopedicFormAction
 */
public class ParseOrthopedicFormAction {
	private OrthopedicOVRecordBean recordBean;
	private OrthopedicDiagnosisBean diagnosisBean;
	private OrderBean orderBean;
	private ServletFileUpload upload;

	/**
	 * ParseOrthopedicFormAction
	 * 
	 * @param upload
	 */
	public ParseOrthopedicFormAction(ServletFileUpload upload) {
		this.recordBean = new OrthopedicOVRecordBean();
		this.diagnosisBean = new OrthopedicDiagnosisBean();
		this.orderBean = new OrderBean();
		this.upload = upload;
	}

	/**
	 * Parse multipart post request and generate bean
	 * 
	 * @param request
	 */
	public void parse(HttpServletRequest request) {
		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			return;
		}

		for (FileItem f : items) {
			switch (f.getFieldName()) {
			case "date":
				recordBean.setVisitDate(f.getString());
				break;
			case "Injured":
				recordBean.setInjured(f.getString());
				break;
			case "XRay":
				recordBean.setXray(fileItemToBytes(f));
				break;
			case "MRI":
				recordBean.setMri(fileItemToBytes(f));
				break;
			case "mriReport":
				recordBean.setMriReport(f.getString());
				break;
			case "ICDCode":
				diagnosisBean.setICDCode(f.getString());
				;
				break;
			case "ovID":
				recordBean.setOid(Integer.valueOf(f.getString()));
				break;
			case "OrderedHCPID":
				orderBean.setOrderedHCPID(Long.valueOf(f.getString()));
			}
		}
	}

	/**
	 * Return diagnoses bean parsed in parse()
	 * 
	 * @return
	 */
	public OrthopedicDiagnosisBean getDiagnosisBean() {
		return diagnosisBean;
	}

	/**
	 * Return record bean parsed in parse()
	 * 
	 * @return
	 */
	public OrthopedicOVRecordBean getRecordBean() {
		return recordBean;
	}
	
	/**
	 * Return order bean pared in parse()
	 * 
	 * @return
	 */
	public OrderBean getOrderBean() {
		return orderBean;
	}

	private byte[] fileItemToBytes(FileItem f) {
		return f.get();
	}

}
