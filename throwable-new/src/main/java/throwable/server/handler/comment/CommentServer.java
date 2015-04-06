package throwable.server.handler.comment;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.bean.Comment;
import throwable.server.service.CommentService;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年3月22日
 */
@IocBean
public class CommentServer {

	@Inject
	private CommentService commentService;
	
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	public int addComment(Comment comment) {
		comment.time = System.currentTimeMillis();
		return commentService.addComment(comment);
	}
	
	/**
	 * 根据评论id查询评论
	 * @param id
	 * @return
	 */
	public Comment queryById(int id) {
		return commentService.queryCommentById(id);
	}
	
	/**
	 * 根据留言用户查询其所有评论
	 * @param fromUserId  留言用户id
	 * @return
	 */
	public List<Comment> queryCommentByFromUser(int fromUserId) {
		return commentService.queryCommentByFromUser(fromUserId);
	}
	
	/**
	 * 根据被留言用户查询其所有评论
	 * @param fromUserId  被留言用户id
	 * @return
	 */
	public List<Comment> queryCommentByToUser(int toUserId) {
		return commentService.queryCommentByToUser(toUserId);
	}
	
	/**
	 * 根据所属id和所属类型查询所有评论
	 * @param belongId   所属id
	 * @param type       类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryCommentByBelongIdType(int belongId, int type) {
		return commentService.queryCommentByBelongIdType(belongId, type);
	}
}
