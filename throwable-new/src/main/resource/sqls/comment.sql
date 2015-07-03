/*Comment*/

/*评论-插入一条评论数据*/
/*comment_addComment*/
insert into seava_throwable.t_comment(belongId, belongType, toUserId, toUserName, fromUserId, fromUserName, comment, time)
values(@belongId, @belongType, @toUserId, @toUserName, @fromUserId, @fromUserName, @comment, @time)


/*根据所属id和type查询评论*/
/*comment_queryByBelongIdType*/
select a.id, a.belongId, a.belongType, a.toUserId, a.toUserName, a.fromUserId, a.fromUserName, a.comment, a.time, b.image 
from seava_throwable.t_comment as a left join seava_throwable.t_user_extend as b on a.fromUserId = b.user_id 
where a.belongId = @belongId and a.belongType=@belongType order by a.id

/*根据留言用户id查询评论*/
/*comment_queryByFromUser*/
select * from seava_throwable.t_comment where fromUserId = @fromUserId

/*根据被留言用户id查询评论*/
/*comment_queryByToUser*/
select * from seava_throwable.t_comment where toUserId = @toUserId

/*根据id查询评论*/
/*comment_queryByid*/
select * from seava_throwable.t_comment where id = @id
