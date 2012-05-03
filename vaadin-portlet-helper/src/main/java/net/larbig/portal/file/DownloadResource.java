package net.larbig.portal.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.StreamResource;

@SuppressWarnings("serial")
public class DownloadResource extends StreamResource {

	private final String filename;

	public DownloadResource(File fileToDownload, Application application) throws FileNotFoundException {
		super(new FileStreamResource(fileToDownload), fileToDownload.getName(), application);

		this.filename = fileToDownload.getName();
	}

	public DownloadStream getStream() {

		DownloadStream stream = null;

		if (filename.endsWith(".pdf"))
			stream = new DownloadStream(getStreamSource().getStream(), "application/pdf", filename);
		else
			stream = new DownloadStream(getStreamSource().getStream(), "application/zip", filename);
		stream.setParameter("Content-Disposition", "attachment;filename=" + filename);
		return stream;
	}

	private static class FileStreamResource implements StreamResource.StreamSource, Serializable {

		private final InputStream inputStream;

		public FileStreamResource(File fileToDownload) throws FileNotFoundException {
			inputStream = new MyFileInputStream(fileToDownload);
		}

		public InputStream getStream() {
			return inputStream;
		}

		public class MyFileInputStream extends FileInputStream implements Serializable {

			public MyFileInputStream(File fileToDownload) throws FileNotFoundException {
				super(fileToDownload);
			}

		}

	}
}