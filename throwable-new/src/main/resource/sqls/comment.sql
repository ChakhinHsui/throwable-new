/*Comment*/

/*评论-插入一条评论数据*/
/*comment_addComment*/
insert into seava_throwable.t_comment(belongId, belongType, toUserId, toUserName, fromUserId, fromUserName, comment, time)
values(@belongId, @belongType, @toUserId, @toUserName, @fromUserId, @fromUserName, @comment, @time)
