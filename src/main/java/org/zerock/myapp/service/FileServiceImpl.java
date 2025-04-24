package org.zerock.myapp.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.UpFile;
import org.zerock.myapp.persistence.FileRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j


@Service
public class FileServiceImpl implements FileService {
	@Autowired FileRepository dao;


    @Value("${file.path}")
    private String filePath; 
    

    @Override
    public Boolean save(MultipartFile file, Employee emloyee) {
	  	log.debug("🔥🔥🔥 FileServiceImpl.save() 진입함!");
	
        // 디렉토리 확인 & 생성
        File dir = new File(filePath);
        
        if (!dir.exists()) {
            dir.mkdirs();
        } // if
    
        String originalName = file.getOriginalFilename();
//        String extension = originalName.substring(originalName.lastIndexOf('.') + 1);
        String extension = "png";
        String uuidFileName = UUID.randomUUID().toString() + '.' + extension;

        UpFile upfile = new UpFile();
        
        upfile.setOriginal(originalName);
        upfile.setUuid(uuidFileName);
        upfile.setPath(filePath);
        upfile.setEnabled(true);
        upfile.setEmployee(emloyee);
        
        log.info("filePath: {}", filePath);
        
        try {
	        this.dao.save(upfile);
	        
	        // 저장할 파일 경로 생성
	        File dest = new File(filePath+"/"+emloyee.getEmpno()+"."+extension);

	        // 파일이 이미 존재하면 삭제
	        if (dest.exists()) {
	            boolean deleted = dest.delete();
	            if (!deleted) {
	                log.warn("기존 파일 삭제 실패: {}",dest.getAbsolutePath());
	            } // if
	        } // if
	        
        	file.transferTo(dest);	
        	return true;
        } catch (IOException e) {
        	log.info(e.getMessage());
        	return false;
        } // try-catch
    } // save
   

    @Override
    public Resource getFile(String fileName) {
    	String dir = System.getProperty("user.dir") + "/" + filePath + "/" + fileName;
    	Path path = Paths.get(filePath).resolve(fileName);
		try {
			Resource resource = new UrlResource(path.toUri());
			return resource;
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		return null;
    }
	
}//end class
