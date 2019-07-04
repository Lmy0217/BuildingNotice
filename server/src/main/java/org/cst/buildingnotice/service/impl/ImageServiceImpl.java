package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.ImageMapper;
import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageMapper imageMapper;

	public int save(String path, String depict) {
		Image image = new Image();
		image.setPath(path);
		image.setDepict(depict);
		imageMapper.insertSelective(image);
		return image.getId();
	}

	public Image getImageById(Integer id) {
		return imageMapper.selectByPrimaryKey(id);
	}

}
