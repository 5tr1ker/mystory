package noticeboard.entity.DTO;

public class PostdataDTO {
	private String idStatus; // 사용자
	private String[] labelData; // 태그 데이터
	private postOption postOption;	// 글 옵션
	private postContent postContent; // 글 내용
	private String[] deletedFileList; // 삭제된 파일
	
	public class postOption {
		private boolean blockComm;
		private boolean privates;
		
		public boolean isBlockComm() {
			return blockComm;
		}
		public void setBlockComm(boolean blockComm) {
			this.blockComm = blockComm;
		}
		public boolean isPrivates() {
			return privates;
		}
		public void setPrivates(boolean privates) {
			this.privates = privates;
		}
		public postOption() {}
		public postOption(boolean blockComm, boolean privates) {
			this.blockComm = blockComm;
			this.privates = privates;
		}
	}
	
	public class postContent {
		private String title;
		private String content;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public postContent() {}
		public postContent(String title, String content) {
			this.title = title;
			this.content = content;
		}
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String[] getLabelData() {
		return labelData;
	}

	public void setLabelData(String[] labelData) {
		this.labelData = labelData;
	}

	public postOption getPostOption() {
		return postOption;
	}

	public void setPostOption(postOption postOption) {
		this.postOption = postOption;
	}

	public postContent getPostContent() {
		return postContent;
	}

	public void setPostContent(postContent postContent) {
		this.postContent = postContent;
	}

	public String[] getDeletedFileList() {
		return deletedFileList;
	}

	public void setDeletedFileList(String[] deletedFileList) {
		this.deletedFileList = deletedFileList;
	}
	
	
	
}
