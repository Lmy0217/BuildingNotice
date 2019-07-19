package org.cst.buildingnotice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cst.buildingnotice.dao.ImageMapper;
import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageMapper imageMapper;

	public Integer create(String path, String depict) {
		Image image = new Image();
		image.setPath(path);
		image.setDepict(depict);
		imageMapper.insertSelective(image);
		return image.getId();
	}

	public Image getImageById(int id) {
		return imageMapper.selectByPrimaryKey(id);
	}

	public List<Image> getImagesByIdList(List<Integer> ids) {
		if (ids == null) return null;
		if (ids.isEmpty()) return new ArrayList<Image>();
		return imageMapper.selectByIdList(ids);
	}
}
