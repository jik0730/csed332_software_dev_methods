package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ParseOrthopedicFormAction;

/**
 * ParseOrthopedicFormActionTest
 */
public class ParseOrthopedicFormActionTest {

	/**
	 * test if it parses request well
	 */
	@Test
	public void testParse() throws FileUploadException {
		List<FileItem> items = new ArrayList<>();
		FileItem item1 = mock(FileItem.class);
		when(item1.getFieldName()).thenReturn("date");
		when(item1.getString()).thenReturn("test");
		FileItem item2 = mock(FileItem.class);
		when(item2.getFieldName()).thenReturn("Injured");
		when(item2.getString()).thenReturn("test");
		FileItem item3 = mock(FileItem.class);
		when(item3.getFieldName()).thenReturn("XRay");
		when(item3.getString()).thenReturn(null);
		FileItem item4 = mock(FileItem.class);
		when(item4.getFieldName()).thenReturn("MRI");
		when(item4.getString()).thenReturn(null);
		FileItem item5 = mock(FileItem.class);
		when(item5.getFieldName()).thenReturn("mriReport");
		when(item5.getString()).thenReturn("test");
		FileItem item6 = mock(FileItem.class);
		when(item6.getFieldName()).thenReturn("ICDCode");
		when(item6.getString()).thenReturn("test");
		FileItem item7 = mock(FileItem.class);
		when(item7.getFieldName()).thenReturn("ovID");
		when(item7.getString()).thenReturn("1");
		FileItem item8 = mock(FileItem.class);
		when(item8.getFieldName()).thenReturn("OrderedHCPID");
		when(item8.getString()).thenReturn("1");
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		items.add(item8);
		
		ServletFileUpload upload = mock(ServletFileUpload.class);
		when(upload.parseRequest(any(HttpServletRequest.class))).thenReturn(items);
		ParseOrthopedicFormAction action = new ParseOrthopedicFormAction(upload);
		action.parse(mock(HttpServletRequest.class));
		assertEquals("test", action.getRecordBean().getInjured());
	}

}
