package ie.tus.eng.car.etag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class handleRequests{
	

	public ResponseEntity<String> handleRequest(String currentContent, String ifNoneMatchHeader) {
	    String etag = "\"" + Integer.toHexString(currentContent.hashCode()) + "\"";
	    if (matchesIfNoneMatch(etag, ifNoneMatchHeader)) {
	        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
	    }
	    return ResponseEntity.ok().eTag(etag).body(currentContent);
	}

	private boolean matchesIfNoneMatch(String etag, String header) {
	    if (header == null || header.isBlank()) return false;
	    for (String raw : header.split(",")) {
	        String candidate = raw.trim();
	        if ("*".equals(candidate)) return true;       // any current representation
	        if (candidate.startsWith("W/")) candidate = candidate.substring(2).trim(); // weak validators match
	        if (candidate.equals(etag)) return true;      // quoted strong match
	    }
	    return false;
	}
}
