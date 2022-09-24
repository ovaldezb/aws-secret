package com.ovaldez.asm.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.ovaldez.asm.control.AwsSecrets;


@Configuration
public class ApplicationConfig {
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	
	private Gson gson = new Gson();
	
	@Bean
	public DataSource dataSource () {
		AwsSecrets secrets = getSecret();
		System.out.println("jdbc:"+secrets.getEngine()+"://"+secrets.getHost()+":"+secrets.getPort()+"/"+secrets.getDbname());
		return DataSourceBuilder
				.create()
				//.driverClassName("")
				.url("jdbc:"+secrets.getEngine()+"://"+secrets.getHost()+":"+secrets.getPort()+"/"+secrets.getDbname())
				.username(secrets.getUsername())
				.password(secrets.getPassword())
				.build();
	}

	private AwsSecrets getSecret() {

	    String secretName = "mysecret-manager";
	    String region = "us-east-2";

	    
	    AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
	                                    .withRegion(region)
	                                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
	                                    .build();
	    
	    String secret;
	    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
	                    .withSecretId(secretName);
	    GetSecretValueResult getSecretValueResult = null;

	    try {
	        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
	    } catch (Exception e) {
	        throw e;
	    } 

	    // Decrypts secret using the associated KMS key.
	    // Depending on whether the secret is a string or binary, one of these fields will be populated.
	    if (getSecretValueResult.getSecretString() != null) {
	        secret = getSecretValueResult.getSecretString();
	        return gson.fromJson(secret, AwsSecrets.class);
	    }
	    
	    return null;
	    
	}
}
