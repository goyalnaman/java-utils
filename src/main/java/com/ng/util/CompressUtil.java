package com.ng.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressUtil {
	
	public static byte[] compress(String data) throws Exception {
		if(data == null || data.isEmpty()) {
			return null;
		}
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bao);
		gzip.write(data.getBytes("UTF-8"));
		gzip.close();
		return bao.toByteArray();
	}
	
	public static String decompress(final byte[] compressed) throws Exception {
		StringBuilder output = new StringBuilder();
		if(compressed == null || compressed.length == 0) {
			return null;
		}
		if(isCompressed(compressed)) {
			GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(compressed));
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip, "UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				output.append(line);
			}
		} else {
			output.append(new String(compressed));
		}
		
		return output.toString();
	}

	public static boolean isCompressed(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}
	
}
