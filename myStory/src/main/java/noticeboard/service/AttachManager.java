package noticeboard.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import noticeboard.entity.freeboard.FreeAttach;
import noticeboard.entity.freeboard.FreePost;
import noticeboard.repository.PostRepository;

@Service
@Transactional
public class AttachManager {
	
	@Autowired PostRepository postRepos;
	
	public void modifiedUpload(String[] deletedFile , Long postNumber) {
		String file_Path = "C:\\notice\\file\\";
		
		FreePost fp = postRepos.findPostByNumbers(postNumber);
		List<FreeAttach> fileData = fp.getFreeAttach();
		
		for(FreeAttach data : fileData) {
			String FileName = data.getFileName();
			if(Arrays.asList(deletedFile).indexOf(FileName) != -1) {
				String fileExtension = data.getFileName().substring(data.getFileName().lastIndexOf(".") , data.getFileName().length());
				File file = new File(file_Path + data.getChangedFile() + fileExtension);
				if(file != null) file.delete();
				
				postRepos.deleteByFileName(data.getChangedFile());
			}
		}
	}

	public void fileUpload(List<MultipartFile> files , Long postNumber) {
		String file_Path = "C:\\notice\\file\\";
		
		FreePost fp = postRepos.findPostByNumbers(postNumber);

		for(MultipartFile file : files) {
			if (file != null) {
				String uuid = UUID.randomUUID().toString().replaceAll("-" , ""); // 고유 문자열
				String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length()); // 확장자
				
				File saveFile = new File(file_Path+"\\"+uuid + fileExtension);
				FreeAttach fa = FreeAttach.createFreeAttach(file.getOriginalFilename() , uuid , file.getSize());
				
				try {
					file.transferTo(saveFile);
					fp.addFreeAttach(fa);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

}
