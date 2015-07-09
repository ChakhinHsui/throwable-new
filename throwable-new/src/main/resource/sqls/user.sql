/*User*/

/*user=查询用户 根据用户名查询用户*/
/*user_queryUserByUserName*/
select * from seava_throwable.t_user where username = @username

/*user=查询用户 根据邮箱查询用户*/
/*user_queryUserByEmail*/
select * from seava_throwable.t_user where email = @email

/*user=查询用户 根据用户id查询用户*/
/*user_queryUserById*/
select * from seava_throwable.t_user where id = @id

/*user=查询用户  根据id查询用户部分信息*/
/*user_queryPartUserInfoById*/
select id, username, rights, email, nickname, phone, qq, score, user_state from seava_throwable.t_user where id = @id

/*user=查询用户  根据用户名查询用户部分信息*/
/*user_queryPartUserInfoByUserName*/
select id, username, password, rights, email, nickname, phone, qq, score, user_state from seava_throwable.t_user where username = @username

/*user=查询所有用户*/
/*user_queryAllUser*/
select * from seava_throwable.t_user

/*user=插入某个用户*/
/*user_insertOneUser*/
insert into seava_throwable.t_user(username, password, rights, email, nickname, phone, qq, score, user_state, create_time, last_update_time, last_active_time, last_active_area, last_active_ip, last_forgetpassword_time, last_mark_time, last_mark_ip) values(@username, @password, @rights, @email, @nickname, @phone, @qq, @score, @user_state, @create_time, @last_update_time, @last_active_time, @last_active_area, @last_active_ip, @last_forgetpassword_time, @last_mark_time, @last_mark_ip)

/*user=更新用户信息*/
/*user_updateUserInfo*/
update seava_throwable.t_user set nickname = @nickname, phone = @phone, qq = @qq, last_update_time = @last_update_time where id = @id

/*user=更新用户登陆成功的日志信息*/
/*user_updateUserTimeInfoSuccess*/
update seava_throwable.t_user set last_active_time = @last_active_time, last_active_area = @last_active_area, last_active_ip = @last_active_ip where id = @id

/*user=更新用户登陆失败(忘记密码)的日志信息*/
/*user_updateUserTimeInfoForget*/
update seava_throwable.t_user set user_state = @user_state, last_forgetpassword_time = @last_forgetpassword_time where id = @id
/*用户相关的sql文件*/

/*user=更新用户登陆失败(异常用户)的日志信息*/
/*user_updateUserTimeInfoException*/
update seava_throwable.t_user set user_state = @user_state, last_mark_time = @last_mark_time, last_mark_ip = @last_mark_ip where id = @id

/*user=查询用户账号状态*/
/*user_queryUserState*/
select user_state from seava_throwable.t_user where id = @id

/*user=更新用户账号状态*/
/*user_updateUserState*/
update seava_throwable.t_user set user_state = @user_state where id = @id

/*用户扩展信息添加*/
/*user_extend_addInfo*/
insert into seava_throwable.t_user_extend (user_id, live_address, now_job, graduate_school, motto, interest, goodAt, image)
values(@user_id, @live_address, @now_job, @graduate_school, @motto, @interest, @goodAt, @image)

/*用户扩展信息修改*/
/*user_extend_alterInfo*/
update seava_throwable.t_user_extend 
set live_address = @live_address,
now_job = @now_job,
graduate_school = @graduate_school,
motto = @motto,
interest = @interest,
image = @image,
goodAt = @goodAt where user_id = @user_id

/*用户扩展信息查询*/
/*user_extend_queryInfo*/
select * from seava_throwable.t_user_extend where user_id = @user_id

/*查询用户统计表中的信息*/
/*user_statistics_queryInfo*/
select * from seava_throwable.t_user_statistics where user_id = @userId

/*用户统计表插入用户统计相关信息*/
/*user_statistics_insertInfo*/
insert into seava_throwable.t_user_statistics(user_id, asks, answers, agrees, collections, focuses, disagrees)
values(@user_id, @asks, @answers, @agrees, @collections, @focuses, @disagrees)

/*用户统计表中更新统计信息*/
/*user_statistics_updateInfo*/
update seava_throwable.t_user_statistics set asks = asks + @asks, answers = answers + @answers,
agrees = agrees + @agrees, collections = collections + @collections, focuses = focuses + @focuses, disagrees = disagrees + @disagrees
where user_id = @userId


/*用户通知-插入通知内容*/
/*notice_insertNotice*/
insert into seava_throwable.t_notice(noticeText, noticeUrl, readOrNo, type, uuid, userId, time)
values(@noticeText, @noticeUrl, @readOrNo, @type, @uuid, @userId, @time)

/*用户通知-更新通知内容 标记为已读*/
/*notice_updateReadOrNo*/
update seava_throwable.t_notice set readOrNo = @readOrNo where uuid = @uuid

/*用户通知-查询用户未查看的通知*/
/*notice_queryReadOrNo*/
select * from seava_throwable.t_notice where userId = @userId and readOrNo=@readOrNo

/*用户-插入用户赞同反对问题答案的记录  type 1问题  2答案     agreeType 1赞同  2反对*/
/*user_insertAgreeDisAgree*/
insert into seava_throwable.t_user_agree_disagree(user_id, q_a_id, type, time, agreeType) values(@userId, @q_a_id, @type, @time, @agreeType)

/*用户-根据用户id，agreeid以及type查询记录*/
/*user_queryAgreeDisagreeRecord*/
select * from seava_throwable.t_user_agree_disagree where user_id=@userId and q_a_id = @q_a_id and type = @type

/*用户 -查询用户的所有记录*/
/*user_queryUserAgreeDisAgreeRecords*/
select * from seava_throwable.t_user_agree_disagree where user_id=@userId and type = @type

/*用户赞同-查询问题 答案的所有赞同不赞同用户*/
/*user_queryAQAgreeDisAgreeRecords*/
select * from seava_throwable.t_user_agree_disagree where q_a_id=@q_a_id and type = @type

/*用户赞同-查询用户和某些问题 答案 的赞同记录*/
/*user_queryUserAQAgreeDisAgreeRecords*/
select * from seava_throwable.t_user_agree_disagree $condition and user_id = @userId and type = @type

