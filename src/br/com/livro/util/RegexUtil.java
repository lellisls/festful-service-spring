package br.com.livro.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

public class RegexUtil {
	private static final Pattern regexAll = Pattern.compile("/carros");
	private static final Pattern regexById = Pattern.compile("/carros/([0-9]*)");
	
	public static Long matchId(String requestUrl) throws ServletException {
		Matcher matcher = regexById.matcher(requestUrl);
		if( matcher.find() && matcher.groupCount() > 0 ) {
			String s = matcher.group(1);
			if( s != null && s.trim().length() > 0 ) {
				Long id = Long.parseLong(s);
				return id;
			}
		}
		return null;
	}

	public static boolean matchAll(String requestUrl) throws ServletException {
		Matcher matcher = regexAll.matcher(requestUrl);
		return matcher.find();
	}
}
