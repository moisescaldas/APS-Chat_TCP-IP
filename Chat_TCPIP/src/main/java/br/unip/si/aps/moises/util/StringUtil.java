package br.unip.si.aps.moises.util;

/**
 * Usar metodos da classe String do Java 11 kkkkkkk 
 */

public class StringUtil {
	private StringUtil() {}
	
	public static String rightStrip(String str) {
		String lastChar = str.length() == 0 ? "" : str.substring(str.length()-1);
		if (lastChar.equals("\n") || lastChar.equals("\t") || lastChar.equals(" ")) {
			return rightStrip(str.substring(0, str.length()-1));
		}
		return str;
	}
}
