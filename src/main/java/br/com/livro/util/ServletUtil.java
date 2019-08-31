package br.com.livro.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
	public static void writeXML(HttpServletResponse resp, String xml ) throws IOException {
		if( xml != null ) {
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/xml;charset=utf-8");
			resp.setCharacterEncoding("utf-8");

			writer.write(xml);
			writer.close();
		}
	}
	
	public static void writeJSON(HttpServletResponse resp, String json ) throws IOException {
		if( json != null ) {
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			writer.write(json);
			writer.close();
		}
	}
}
