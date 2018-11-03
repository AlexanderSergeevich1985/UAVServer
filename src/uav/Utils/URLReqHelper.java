/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Utils;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class URLReqHelper {
    static public URL getURL(final String scheme, final String host, final String path,
                             final Map<String, String> params) throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path);
        if(params != null) params.forEach((param, value) -> builder.setParameter(param, value));
        return builder.build().toURL();
    }

    public static <T> HttpEntity<T> getHttpEntity(final String authToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        if(authToken != null) headers.add(HttpHeaders.AUTHORIZATION, authToken);
        HttpEntity<T> entity = new HttpEntity<>(headers);
        return entity;
    }

    public static <T> T doGetRequestRest(final URI uri, @Nonnull Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, clazz);
    }

    public static <T> T doPostRequestRest(final URI uri, final Object request, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, request, clazz);
    }

    public static void doPutRequestRest(final URI uri, final Object request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, request);
    }

    public static void doDeleteRequestRest(final URI uri) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri);
    }

    public static <T> ResponseEntity<T> getRequestRestExchange(final URI uri, final HttpMethod method, final HttpEntity<?> request, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri, method, request, clazz);
    }
}
