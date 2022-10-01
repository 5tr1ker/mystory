//package noticeboard.preferences;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import noticeboard.controller.PostController;
//import noticeboard.entity.freeboard.FreeCommit;
//import noticeboard.repository.CommitRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class) // Spring이 제공하는 어노테이션을 사용
//@ContextConfiguration(locations = {"classpath:webAppConfig.xml" , "classpath:controllerConf.xml"}) // 스프링 설정 정보
//@Transactional // 테스트가 끝나면 강제로 롤백한다. ( 테스트 한정 ) 
//public class TestNoticeboard {
//
//	@PersistenceContext EntityManager em;
//	@Autowired CommitRepository rs;
//	@Autowired PostController pc;
//	@Test
//	public void 무작위POST10000개생성() {
//		List<FreeCommit> ps = rs.getNotifice("tester");
//		System.out.println(ps.size());
//	}
//}
