package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Image;

public interface ImageService {

	public int save(String path, String depict);
	
	public Image getImageById(Integer id);
}
