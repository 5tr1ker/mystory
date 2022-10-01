package noticeboard.security.oauth;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import noticeboard.entity.userdata.IdInfo;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
	private String nameAttributeKey;
	private String name;
	
	@Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey ,  String name) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
    }
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)

        return ofGoogle(userNameAttributeName, attributes);
    }
	
	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
	
	public IdInfo toEntity(){
		IdInfo idinfo = new IdInfo();
		idinfo.setId(name);
		idinfo.getRoles().add("ROLE_USER");
        
		return idinfo;
    }
}