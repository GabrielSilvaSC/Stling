package br.com.stling.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageBundle {
	static Locale aLocale = new Locale("en", "US");

	public static String getString(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle",aLocale);
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", aLocale);
		try {
			return MessageFormat.format(bundle.getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
