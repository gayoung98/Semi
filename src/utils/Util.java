package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dao.CalanderDAO;

public class Util {
	
	static Calendar startday = Calendar.getInstance();
	static Calendar today = Calendar.getInstance();
	static Calendar d_day = Calendar.getInstance();
	
	public static String getSHA512(String input){

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}
	static public long dDay_to_Today() {
			d_day.set(2021,Calendar.AUGUST, 27);
			long today_mill = today.getTimeInMillis() / (1000*60*60*24);
			long d_day_mill = d_day.getTimeInMillis() / (1000*60*60*24);
			return d_day_mill-today_mill;
		}
		
	static public long dDay_to_Total() {
			startday.set(2021, Calendar.MARCH,11);
			d_day.set(2021,Calendar.AUGUST, 27);
			long total_days = (d_day.getTimeInMillis() - startday.getTimeInMillis()) / (1000*60*60*24);
			return (long)(100-(dDay_to_Today()/(float)total_days)*100);
		}
}
