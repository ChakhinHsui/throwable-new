package throwable.server.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import throwable.server.bean.Comment;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年3月22日
 */
public class CommentService extends BaseService {

	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	public int addComment(Comment comment) {
		Sql sql = dao.sqls().create("comment_addComment");
		sql.params().set("belongId", comment.belongId);
		sql.params().set("belongType", comment.belongType);
		sql.params().set("toUserId", comment.toUserId);
		sql.params().set("toUserName", comment.toUserName);
		sql.params().set("fromUserId", comment.fromUserId);
		sql.params().set("fromUserName", comment.fromUserName);
		sql.params().set("comment", comment.comment);
		sql.params().set("time", comment.time);
		dao.execute(sql);
		return sql.getUpdateCount();
	}
	
	/**
	 * 根据所属id和所属分类查询评论
	 * @param belongId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryCommentByBelongIdType(int belongId, int type) {
		Sql sql = dao.sqls().create("comment_queryByBelongIdType");
		sql.params().set("belongId", belongId);
		sql.params().set("belongType", type);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 根据留言用户id查询评论
	 * @param userId  来源用户id
	 * @return
	 */
	public List<Comment> queryCommentByFromUser(int userId) {
		Sql sql = dao.sqls().create("comment_queryByFromUser");
		sql.params().set("fromUserId", userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Comment.class));
		dao.execute(sql);
		return sql.getList(Comment.class);
	}
	
	/**
	 * 根据被留言用户id查询评论
	 * @param userId
	 * @return
	 */
	public List<Comment> queryCommentByToUser(int userId) {
		Sql sql = dao.sqls().create("comment_queryByToUser");
		sql.params().set("toUserId", userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Comment.class));
		dao.execute(sql);
		return sql.getList(Comment.class);
	}
	
	/**
	 * 根据id查询评论
	 * @param id  
	 * @return
	 */
	public Comment queryCommentById(int id) {
		Sql sql = dao.sqls().create("comment_queryByid");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entity());
		sql.setEntity(dao.getEntity(Comment.class));
		dao.execute(sql);
		return sql.getObject(Comment.class);
	}
}
