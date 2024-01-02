package org.gerken.spanish.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.channels.ReadableByteChannel;

import org.eclipse.jetty.util.resource.Resource;

public class SpanishPath extends Resource {

	private String path;
	private Boolean exists = null;
	
	private SpanishPath() {

	}
	
	public SpanishPath(String path) {
		this.path = path;
	}

	@Override
	public Resource addPath(String path) throws IOException, MalformedURLException {
		return new SpanishPath(path);
	}

	@Override
	public void close() {

	}

	@Override
	public boolean delete() throws SecurityException {
		return false;
	}

	@Override
	public boolean exists() {
		if (exists!=null) {
			return exists;
		}
		exists = false;
		try {
			InputStream is = getInputStream();
			exists = (is != null);
			is.close();
			return exists;
		} catch (Throwable e) {

		}
		return exists;
	}

	@Override
	public File getFile() throws IOException {
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return SpanishPath.class.getResourceAsStream(path);
	}

	@Override
	public String getName() {
		return path;
	}

	@Override
	public ReadableByteChannel getReadableByteChannel() throws IOException {
		return null;
	}

	@Override
	public boolean isContainedIn(Resource arg0) throws MalformedURLException {
		return false;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public long lastModified() {
		return 0;
	}

	@Override
	public long length() {
		try {
			InputStream is = getInputStream();
			long result = (long) is.available();
			is.close();
			return result;
		} catch (IOException e) {

		}
		return 0;
	}

	@Override
	public String[] list() {
		return new String[0];
	}

	@Override
	public boolean renameTo(Resource arg0) throws SecurityException {
		return false;
	}

	@Override
	public URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

}
