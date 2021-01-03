package com.liuliu.demo.springretry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuliu.demo.springretry.service.RemoteService;


@RestController
public class TestController {
	
	@Autowired
	private RemoteService remoteService;
	
	@RequestMapping("/show")
	public String show(){
		try {
			remoteService.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return "Hello World";		
	}
}