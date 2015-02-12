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
select id, username, password, rights, email, nickname, phone, qq, score, user_state from seava_throwable.t_user where id = @id

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

/*user focus question*/
/*用户关注问题*/
/*focus_addFocus*/
insert into seava_throwable.t_user_question_focus(user_id, question_id, create_time) values(@user_id, @question_id, @create_time)

/*用户取消关注问题*/
/*focus_deleteFocus*/
delete from seava_throwable.t_user_question_focus where user_id=@user_id and question_id=@question_id

/*查询用户是否已经关注该问题*/
/*focus_haveFocused*/
select * from seava_throwable.t_user_question_focus where user_id=@user_id and question_id=@question_id

/*查询用户关注的问题*/
/*focus_queryUserFocused*/
select a.id, a.question_name, a.viewers, a.agrees, a.focuses, a.answers, a.solved, a.create_time as question_time, b.create_time as focus_time from seava_throwable.t_question as a inner join seava_throwable.t_user_question_focus as b on a.id = b.question_id where b.user_id = @user_id

/*user collect question*/
/*用户收藏问题*/
/*collection_addCollection*/
insert into seava_throwable.t_user_question_collection(user_id, question_id, create_time, collection_mark) values(@user_id, @question_id, @create_time, @collection_mark)

/*用户取消收藏*/
/*collection_deleteCollection*/
delete from seava_throwable.t_user_question_collection where user_id = @user_id and question_id = @question_id

/*查询用户是否已经收藏过该问题*/
/*collection_haveCollected*/
select * from seava_throwable.t_user_question_collection where user_id = @user_id and question_id = @question_id
