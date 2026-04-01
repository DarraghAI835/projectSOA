package ie.tus.eng.car.etag;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class EtagGenerator {
	public static String generate(String content) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(content.getBytes(StandardCharsets.UTF_8));
		return "\""+HexFormat.of().formatHex(digest)+"\"";
	}
}
