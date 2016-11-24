package edu.ncsu.csc.itrust.action;

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

public class ParseOrthopedicFormAction {
	
	private DAOFactory factory;
	private long loggedInMID;
	
	private OrthopedicOVRecordBean recordBean;
	private OrthopedicDiagnosisBean diagnosisBean;
	
	public ParseOrthopedicFormAction(DAOFactory factory, long loggedInMID) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
		this.recordBean = new OrthopedicOVRecordBean();
		this.diagnosisBean = new OrthopedicDiagnosisBean();
	}
	
	public void parse(HttpServletRequest request, ServletContext servletContext) {
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch(FileUploadException e) {
			e.printStackTrace();
			return;
		}
		
		
		for (FileItem f : items) {
			switch (f.getFieldName()) {
				case "date": recordBean.setVisitDate(f.getString());
				break;
				case "Injured": recordBean.setInjured(f.getString());
				break;
				case "XRay": recordBean.setXray(fileItemToBytes(f));
				break;
				case "MRI": recordBean.setMri(fileItemToBytes(f));
				break;
				case "mriReport": recordBean.setMriReport(f.getString());
				break;
				case "ICDCode": diagnosisBean.setICDCode(f.getString());;
				break;
				case "ovID": recordBean.setOid(Integer.valueOf(f.getString()));
			}
		}
	}
	
	public OrthopedicDiagnosisBean getDiagnosisBean() {
		return diagnosisBean;
	}
	
	public OrthopedicOVRecordBean getRecordBean() {
		return recordBean;
	}
	
	/** Returns NULL if not available */
	private byte[] fileItemToBytes(FileItem f) {
		return f.get();
	}
	
}
