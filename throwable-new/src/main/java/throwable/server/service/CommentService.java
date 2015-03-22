package throwable.server.service;

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
}
