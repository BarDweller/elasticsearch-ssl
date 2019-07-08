package org.ozzy.elasticsearch.rest;

import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.RestClients.ElasticsearchRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestController {
    
    @Autowired
    ElasticsearchRestClient c;

    @GetMapping("/")
    String test() throws Exception{
        boolean ok = c.rest().ping(RequestOptions.DEFAULT);
        return "Hello "+ok+" "+c.toString();
    }


}