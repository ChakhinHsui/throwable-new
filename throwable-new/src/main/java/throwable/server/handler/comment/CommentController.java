package throwable.server.handler.comment;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.bean.Comment;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年3月22日
 */
@At("comment")
@IocBean
public class CommentController {

	@Inject
	private CommentServer commentServer;
	
	/**
	 * 添加留言
	 * @param comment
	 * @return
	 */
	@At("/addComment")
	public int addComment(@Param("..") Comment comment) {
		if(comment.belongId < 1 || (comment.belongType != 0 && comment.belongType != 1) || comment.toUserId < 1 || comment.fromUserId < 1 
				|| StringTool.isEmpty(comment.toUserName) || StringTool.isEmpty(comment.fromUserName) || StringTool.isEmpty(comment.comment)) {
			BackTool.errorInfo("100001", "参数错误");
		}
		return commentServer.addComment(comment);
	}
	
	/**
	 * 根据评论id查询评论
	 * @param id
	 * @return
	 */
	@At("/queryById")
	public Comment queryCommentById(int id) {
		if(id < 1) {
			BackTool.errorInfo("100001", "参数错误");
		}
		return commentServer.queryById(id);
	}
	
	/**
	 * 根据所属id和类型查询所有评论
	 * @param belongId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@At("/queryByBelongId")
	public List<Map> queryCommentByBelongIdType(int belongId, int type) {
		if(belongId < 1 || (type != 0 && type != 1)) {
			BackTool.errorInfo("100001", "参数错误");
		}
		return commentServer.queryCommentByBelongIdType(belongId, type);
	}
	
	/**
	 * 根据留言用户查询评论
	 * @param fromUserId
	 * @return
	 */
	@At("/queryByFrom")
	public List<Comment> queryCommentByFromUser(int fromUserId) {
		if(fromUserId < 1) {
			BackTool.errorInfo("100001", "参数错误");
		}
		return commentServer.queryCommentByFromUser(fromUserId);
	}
	
	/**
	 * 根据被留言用户查询评论
	 * @param toUserId
	 * @return
	 */
	@At("/queryByTo")
	public List<Comment> queryCommentByToUser(int toUserId) {
		if(toUserId < 1) {
			BackTool.errorInfo("100001", "参数错误");
		}
		return commentServer.queryCommentByToUser(toUserId);
	}
}
