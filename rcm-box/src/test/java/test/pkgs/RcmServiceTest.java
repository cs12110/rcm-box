package test.pkgs;

import org.junit.Test;

import com.rcm.entity.Nearest;
import com.rcm.service.RcmService;
import com.rcm.util.StdUtil;

public class RcmServiceTest {

	@Test
	public void testname() throws Exception {

		// 1 -> 1218:1.0 , 1230:1.0 , 7:1.0 , 8:1.0 , 804:1.0
		long start = System.currentTimeMillis();
		Nearest[] rcm = RcmService.getRcm("1");
		long end = System.currentTimeMillis();

		System.out.println("Spend time: " + (end - start));

		StdUtil.display("1", rcm);

	}
}
