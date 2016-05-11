package br.com.anteros.generator.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.com.anteros.core.utils.StringUtils;

public class AnterosFreeMarkerFileWriter extends FileWriter {

	public AnterosFreeMarkerFileWriter(File file) throws IOException {
		super(file);
	}
	
	@Override
	public void write(String str) throws IOException {
		super.write(str);
	}
	
	@Override
	public void write(char[] cbuf) throws IOException {
		String s = new String(cbuf);
		s = StringUtils.replace(s, "&&{", "${");
		s = StringUtils.replace(s, "}&&", "}");
		super.write(s);
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		super.write(cbuf, off, len);
	}
	@Override
	public void write(int c) throws IOException {
		super.write(c);
	}
	@Override
	public void write(String str, int off, int len) throws IOException {
		super.write(str, off, len);
	}

}
