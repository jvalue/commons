package org.jvalue.commons.utils;


import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpServiceCheck {

	private static final int DEFAULT_RETRIES = 50;
	private static final int DEFAULT_SLEEP_TIME_BETWEEN_RETRIES = 3000;

	public static boolean check(String url) {
		return doCheck(url, DEFAULT_RETRIES, DEFAULT_SLEEP_TIME_BETWEEN_RETRIES);
	}


	public static boolean check(String url, int retries, int sleepTimeBetweenRetries) {
		return doCheck(url, retries, sleepTimeBetweenRetries);
	}


	private static boolean doCheck(String url, int retries, int sleepTimeBetweenRetries) {
		int retryCounter = retries;

		do {
			if (isReachable(url)) {
				Log.info("[success] Service check for URL " + url);
				return true;
			} else {
				String msg = String.format("[%d/%d] Retry service check for URL %s", retryCounter, retries, url);
				Log.info(msg);
				doSleep(sleepTimeBetweenRetries);
			}
			retryCounter--;
		} while (retryCounter > 0);

		Log.error("[failure] Service check for URL  " + url);
		return false;
	}


	private static boolean isReachable(String url) {
		try {
			URL serviceUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
			connection.connect();

			int responseCode = connection.getResponseCode();
			return isOk(responseCode);
		} catch (Exception e) {
		}

		return false;
	}


	private static void doSleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			Log.error(e.getMessage());
		}
	}


	private static boolean isOk(int statusCode) {
		return statusCode == 200;
	}

}
