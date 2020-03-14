package kg.bekhub.school.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class UrlUtil {

    public static String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if(enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }

        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (Exception e) {
            log.info(e.toString());
        }
        return pathSegment;
    }
}
