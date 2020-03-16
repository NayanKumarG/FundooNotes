/**
 * @author Nayan Kumar G
 * purpose : Configuration for elastic search
 * date    :15-03-2020
 */
package com.bridgelabz.fundoonotes.configuration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {
		
	@Bean
	public RestHighLevelClient client()
	{
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http")));
		return client;
	}
	
}
