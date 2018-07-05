package org.automation.atfs.configuration;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangqiang on 2018/6/22
 */
@Configuration
public class RestTemplateConfig {

    private static final  int READ_TIME_OUT = 20000;
    private static final  int CONNECTION_TIME_OUT = 15000;

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpClient httpClient = HttpClientBuilder.create()
        .setRedirectStrategy(new EnhancedRedirectStrategy()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setHttpClient(httpClient);
        requestFactory.setReadTimeout(READ_TIME_OUT);
        requestFactory.setConnectTimeout(CONNECTION_TIME_OUT);

//        CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClientBuilder()
//                .setRedirectStrategy(new EnhancedRedirectStrategy())
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        requestFactory.setReadTimeout(READ_TIME_OUT);
//        requestFactory.setConnectTimeout(CONNECTION_TIME_OUT);

        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(requestFactory);

        return bufferingClientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        //todo customize settings, e.g timeout

        RestTemplate template = new RestTemplate(clientHttpRequestFactory);
//        template.getInterceptors().add(new RequestInterceptor());
        return template;
    }


    private static class EnhancedRedirectStrategy extends LaxRedirectStrategy {

        @Override
        public boolean isRedirected(org.apache.http.HttpRequest request, HttpResponse response,
                                    HttpContext context) throws ProtocolException {

//            System.out.println(response);

            // If redirect intercept intermediate response.
            if (super.isRedirected( request, response, context)){

                // int statusCode  = response.getStatusLine().getStatusCode();
                String redirectURL = response.getFirstHeader("Location").getValue();
                System.out.println("redirectURL: " + redirectURL);
                return true;
            }
            return false;
        }
    }


}

