package throwable.server.api;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import throwable.server.service.AnswerService;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("/answerApi")
@IocBean
public class AnswerApi {

	@Inject
	private AnswerService answerService;
}
