package com.liuliu.demo.springretry.service;

import org.springframework.remoting.RemoteAccessException;

public class XmlRetryService {

	public void xmlRetryService(String arg01) throws Exception {
		System.out.println("xmlRetryService do something...");
		throw new RemoteAccessException("RemoteAccessException....");
	}

}
