package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Image;

public interface ImageService {

	public int create(String path, String depict);
	
	public Image getImageById(int id);
	
	public List<Image> getImagesByIdList(List<Integer> ids);
}
