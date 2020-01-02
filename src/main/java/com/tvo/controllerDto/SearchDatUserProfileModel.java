package com.tvo.controllerDto;

import java.util.Date;

import lombok.Data;

@Data
public class SearchDatUserProfileModel {
	
	private String usrid;
	private String cifgrp;
	private String idno;
	
	private Date fromDate;
	private Date toDate;
}
