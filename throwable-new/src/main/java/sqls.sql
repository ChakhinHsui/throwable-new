/*所有数据库SQL配置文件*/

/*基础=查询数据库时间*/
/*base_queryNowTime*/
select getdate()

/*user=查询用户 根据用户名查询用户*/
/*user_queryUserByUserName*/
select * from seava_throwable.t_user where username = @username

/*user=查询用户 根据邮箱查询用户*/
/*user_queryUserByEmail*/
select * from seava_throwable.t_user where email = @email

/*user=查询所有用户*/
/*user_queryAllUser*/
select * from seava_throwable.t_user

/*user=插入某个用户*/
/*user_insertOneUser*/
insert into seava_throwable.t_user(username, password, rights, email, nickname, phone, qq, score, user_state, create_time, last_update_time, last_active_time, last_active_area, last_active_ip, last_forgetpassword_time, last_mark_time, last_mark_ip) values(@username, @password, @rights, @email, @nickname, @phone, @qq, @score, @user_state, @create_time, @last_update_time, @last_active_time, @last_active_area, @last_active_ip, @last_forgetpassword_time, @last_mark_time, @last_mark_ip)