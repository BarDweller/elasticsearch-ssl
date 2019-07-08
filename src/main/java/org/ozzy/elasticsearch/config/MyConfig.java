package org.ozzy.elasticsearch.config;

import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.RestClients.ElasticsearchRestClient;
import org.springframework.http.HttpHeaders;


@Configuration
public class MyConfig {

    final String hostandport;
    final String cert;
    final String username;
    final String password;

    //Using @Value via constructor as best practice to enable config to be final.
    public MyConfig( @Value("${demo.elasticsearch.hostandport}") final String hostandport,
                     @Value("${demo.elasticsearch.certificate}") final String cert,
                     @Value("${demo.elasticsearch.username}") final String username,
                     @Value("${demo.elasticsearch.password}") final String password){
        this.hostandport = hostandport;
        this.cert = cert;
        this.username = username;
        this.password = password;
    }

    @Bean
    ElasticsearchRestClient client(@Autowired ClientConfiguration c) {
        //Return regular RestClient using bean built config. 
        return RestClients.create(c);
    }

    @Bean
    ClientConfiguration config() throws Exception {
        //Create SSL Context that trusts 'cert'
        SSLContext ctx = SSLContext.getInstance("TLS");
        Base64TrustingTrustManager tm = new Base64TrustingTrustManager(cert);
        ctx.init(null, new TrustManager[] { tm }, null);

        //Define basic auth header for username/password.
        HttpHeaders headers = new HttpHeaders();         
        headers.setBasicAuth(username,password);

        //Build client configuration using SSL & Basic Auth.
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(hostandport)
            .usingSsl(ctx)
            .withDefaultHeaders(headers)
            .withConnectTimeout(Duration.ofSeconds(5)) 
            .withSocketTimeout(Duration.ofSeconds(3)) 
            .build();

        return clientConfiguration;
    }    
}