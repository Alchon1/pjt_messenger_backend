package org.zerock.myapp.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.FileDTO;
import org.zerock.myapp.entity.File;

public interface FileService {
	
	
	public void save(MultipartFile file);
	Resource getFile(String fileName);
	
}//end interface