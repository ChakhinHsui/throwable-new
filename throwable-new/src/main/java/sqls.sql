/*所有数据库SQL配置文件*/

/*基础=查询数据库时间*/
/*base_queryNowTime*/
select getdate()


/*User*/

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


/*Question*/

/*question=插入问题*/
/*question_insertOneQuestion*/
insert into seava_throwable.t_question(question_name, question_description, question_type, viewers, agrees, create_time, kind_id, user_id) values(@question_name, @question_description, @question_type, @viewers, @agrees, @create_time, @kind_id, @user_id)

/*question=根据id查询问题*/
/*question_queryQuestionByQuestionId*/
select * from seava_throwable.t_question where id = @id 

/*question=根据kind_id查询问题*/
/*question_queryQuestionByKindId*/
select * from seava_throwable.t_question where kind_id = @kind_id order by id desc

/*question=根据user_id查询问题*/
/*question_queryQuestionByUserId*/
select * from seava_throwable.t_question where user_id = @user_id order by id desc

/*question=根据kind_id分页查询问题*/
/*question=queryQuestionByKindIdPage*/
select * from seava_throwable.t_question where kind_id = @kind_id order by id desc limit @page, @count

/*question=根据user_id分页查询问题*/
/*question_queryQuestionByUserIdPage*/
select * from seava_throwable.t_question where user_id = @user_id order by id desc limit @page, @count

/*Kind*/

/*kind=插入kind*/
/*kind_insertOneKind*/
insert into seava_throwable.t_question_kind(kind_name, kind_parent_id) values(@kind_name, @kind_parent_id)

/*kind=根据kind_name查询kind*/
/*kind_queryKindByKindName*/
select * from seava_throwable.t_question_kind where kind_name = @kind_name

/*kind=根据kind_id查询kind*/
/*kind_queryKindByKindId*/
select * from seava_throwable.t_question_kind where id = @id
