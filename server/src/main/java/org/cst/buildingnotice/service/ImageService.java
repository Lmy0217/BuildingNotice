package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Image;

public interface ImageService {

	public int create(String path, String depict);
	
	public Image getImageById(int id);
}
