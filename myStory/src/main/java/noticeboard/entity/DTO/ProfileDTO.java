package noticeboard.entity.DTO;

public class ProfileDTO {
	private String idStatus;
	private inputProfileData inputProfileData;
	
	public class inputProfileData {
		private String userId;
		private String email;
		private String phone;
		private Integer option1;
		private Integer option2;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Integer getOption1() {
			return option1;
		}
		public void setOption1(Integer option1) {
			this.option1 = option1;
		}
		public Integer getOption2() {
			return option2;
		}
		public void setOption2(Integer option2) {
			this.option2 = option2;
		}
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public inputProfileData getInputProfileData() {
		return inputProfileData;
	}

	public void setInputProfileData(inputProfileData inputProfileData) {
		this.inputProfileData = inputProfileData;
	}

	
	
} 
