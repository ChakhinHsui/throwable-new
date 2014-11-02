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

/*user=查询用户 根据用户id查询用户*/
/*user_queryUserById*/
select * from seava_throwable.t_user where id = @id

/*user=查询用户  根据id查询用户部分信息*/
/*user_queryPartUserInfoById*/
select username, password, rights, email, nickname, phone, qq, score, user_state from seava_throwable.t_user where id = @id

/*user=查询用户  根据用户名查询用户部分信息*/
/*user_queryPartUserInfoByUserName*/
select username, password, rights, email, nickname, phone, qq, score, user_state from seava_throwable.t_user where username = @username


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

/*question=根据时间段查询问题*/
/*question_queryQuestionByTime*/
select * from seava_throwable.t_question where create_time > @minTime and create_time < @maxTime


/*question=根据时间段查询问题*/
/*question_queryQuestionByTimePage*/
select * from seava_throwable.t_question where create_time > @minTime and create_time < @maxTime limit @page, @count





/*Kind*/

/*kind=插入kind*/
/*kind_insertOneKind*/
insert into seava_throwable.t_question_kind(kind_name, kind_parent_id) values(@kind_name, @kind_parent_id)

/*kind=根据kind_name查询kind*/
/*kind_queryKindByKindName*/
select * from seava_throwable.t_question_kind where kind_name = @kind_name

/*kind=查询所有kind*/
/*kind_queryAllKind*/
select * from seava_throwable.t_question_kind

/*kind=根据分类id查询分类名*/
/*kind_queryKindNameByKindId*/
select kind_name from seava_throwable.t_question_kind where id = @id

/*kind=根据kind_id查询kind*/
/*kind_queryKindByKindId*/
select * from seava_throwable.t_question_kind where id = @id




/*answer*/

/*answer=查询答案 根据问题id查询答案*/
/*answer_queryAnswerByQuestionId*/
select * from seava_throwable.t_answer where question_id = @question_id

/*answer=查询答案 根据用户id查询答案*/
/*answer_queryAnswerByUserId*/
select * from seava_throwable.t_answer where user_id = @user_id

/*answer=查询答案 根据答案id查询答案*/
/*answer_queryAnswerByAnswerId*/
select * from seava_throwable.t_answer where id = @id

/*answer=插入答案*/
/*answer_insertAnswer*/
insert into seava_throwable.t_answer(answer_abstract, answer_description, correct_type, agrees, viewers, answer_time, question_id, user_id) values(@answer_abstract, @answer_description, @correct_type, @agrees, @viewers, @answer_time, @question_id, @user_id)
