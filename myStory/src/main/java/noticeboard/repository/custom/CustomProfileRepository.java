package noticeboard.repository.custom;

import java.util.List;

public interface CustomProfileRepository {

	List<Long> getPostView(String idinfo);

}
