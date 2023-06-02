package com.team.mystory.post.attachment.controller;

import com.team.mystory.post.attachment.service.AttachManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachManager attachManager;

    @RequestMapping(value = "/auth/uploadData/{postNumber}" , method = RequestMethod.POST)
    public void uploadAttachMent(@RequestPart(value = "file") List<MultipartFile> file , @PathVariable("postNumber")Long postNumber) {
        attachManager.fileUpload(file , postNumber);
    }

	/*
	@RequestMapping(value = "/onDownload/{fileName}" , method = RequestMethod.GET)
	public void onDownload(@PathVariable("fileName") String fileName , HttpServletResponse response) throws IOException {
		// String filePath = "C:\\Users\\tjseo\\OneDrive\\바탕 화면\\study\\noticeBoardClient\\upload\\";
		String realName = postRepos.getFileName(fileName);
		String extend = realName.substring(realName.lastIndexOf('.') , realName.length());

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
		response.setContentType("text/plain");

		File downFile = new File(fileName + extend); //파일 다운로드
	    FileInputStream fileIn = new FileInputStream(downFile); //파일 읽어오기
	    ByteStreams.copy(fileIn, response.getOutputStream());
	    response.flushBuffer();
	}

	 */
}
