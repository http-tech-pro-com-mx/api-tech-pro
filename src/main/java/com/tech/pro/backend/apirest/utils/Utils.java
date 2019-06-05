package com.tech.pro.backend.apirest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public String getFormatDate(Date date) {
		SimpleDateFormat formateador = new SimpleDateFormat("EEEE MMMM d");
		return formateador.format(date);
	}
	
}
