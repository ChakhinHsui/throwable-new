/*Comment*/

/*评论-插入一条评论数据*/
/*comment_addComment*/
insert into seava_throwable.t_comment(belongId, belongType, toUserId, toUserName, fromUserId, fromUserName, comment, time)
values(@belongId, @belongType, @toUserId, @toUserName, @fromUserId, @fromUserName, @comment, @time)


/*根据所属id和type查询评论*/
/*comment_queryByBelongIdType*/
select * from seava_throwable.t_comment where belongId = @belongId, belongType=@belongType

/*根据留言用户id查询评论*/
/*comment_queryByFromUser*/
select * from seava_throwable.t_comment where fromUserId = @fromUserId

/*根据被留言用户id查询评论*/
/*comment_queryByToUser*/
select * from seava_throwable.t_comment where toUserId = @toUserId

/*根据id查询评论*/
/*comment_queryByid*/
select * from seava_throwable.t_comment where id = @id
