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

/*user=更新用户登陆失败(异常用户)的日志信息*/
/*user_updateUserTimeInfoException*/
update seava_throwable.t_user set user_state = @user_state, last_mark_time = @last_mark_time, last_mark_ip = @last_mark_ip where id = @id

/*user=查询用户账号状态*/
/*user_queryUserState*/
select user_state from seava_throwable.t_user where id = @id

/*user=更新用户账号状态*/
/*user_updateUserState*/
update seava_throwable.t_user set user_state = @user_state where id = @id




/*Question*/

/*question=插入问题*/
/*question_insertOneQuestion*/
insert into seava_throwable.t_question(question_name, question_description, question_type, viewers, agrees, degrees, answers, focuses, collections, solved, create_time, kind_id, user_id) values(@question_name, @question_description, @question_type, @viewers, @agrees, @degrees, @answers, @focuses, @collections, @solved, @create_time, @kind_id, @user_id)

/*question=添加问题描述*/
/*question_addQuestionDesc*/
update seava_throwable.t_question set question_description = @question_description where id = @id

/*question=公开问题*/
/*question_publicQuestion*/
update seava_throwable.t_question set question_type = @question_type where id = @id 

/*question=修改访问数*/
/*question_updateViewers*/
update seava_throwable.t_question set viewers = viewers + @viewers where id = @id 

/*question=修改赞同数*/
/*question_updateAgrees*/
update seava_throwable.t_question set agrees = agrees + @agrees where id = @id 

/*question=根据id查询问题*/
/*question_queryOneQuestionInfo*/
select a.id, a.question_name, a.question_description, a.viewers, a.agrees, a.create_time, b.username from seava_throwable.t_question as a inner join seava_throwable.t_user as b on a.user_id = b.id where a.id = @id 

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

/*question=根据questionType查询所有问题*/
/*question_queryAllQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type order by a.id desc 

/*question=根据userid查询问题的部分信息*/
/*question_queryPartQByUserId*/
select id, question_name, question_type, viewers, agrees, degrees, answers, focuses, collections, solved, create_time from seava_throwable.t_question where user_id = @user_id

/*question=根据questionType查询所有最热(访问数最多)问题*/
/*question_queryAllHotQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type order by a.viewers desc, a.id desc

/*question=根据questionType查询所有关注最多的问题*/
/*question_queryAllMostFocusQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.focuses, a.solved, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type and a.solved = 0 order by a.focuses desc, a.id desc

/*question=根据questionType查询10条最新的回答最多的问题*/
/*question_queryNewMostAnswerQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.focuses, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type order by a.answers desc, a.id desc limit 10

/*question=增加问题的回答数*/
/*question_addQuestionAnswers*/
update seava_throwable.t_question set answers = answers + @counts where id = @id

/*question=agree问题*/
/*question_agreeQuestion*/
update seava_throwable.t_question set agrees = agrees + @agrees where id = @id

/*question=disagree问题*/
/*question_disagreeQuestion*/
update seava_throwable.t_question set disagrees = disagrees + @disagrees where id = @id

/*question=增加访问数*/
/*question_addViewers*/
update seava_throwable.t_question set viewers = viewers + @viewers where id = @id


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
select a.id, a.answer_description, a.correct_type, a.agrees, a.viewers, a.answer_time, b.username from seava_throwable.t_answer as a inner join seava_throwable.t_user as b on a.user_id = b.id  where question_id = @question_id order by a.correct_type, a.agrees desc, a.viewers desc

/*answer=查询答案 根据用户id查询答案*/
/*answer_queryAnswerByUserId*/
select * from seava_throwable.t_answer where user_id = @user_id

/*answer=查询答案 根据答案id查询答案*/
/*answer_queryAnswerByAnswerId*/
select * from seava_throwable.t_answer where id = @id

/*answer=插入答案*/
/*answer_insertAnswer*/
insert into seava_throwable.t_answer(answer_abstract, answer_description, correct_type, agrees, disagrees, viewers, answer_time, question_id, user_id) values(@answer_abstract, @answer_description, @correct_type, @agrees, @disagrees, @viewers, @answer_time, @question_id, @user_id)

/*answer=查询用户的回答*/
/*answer_queryAnswerQuestionInfoByUserId*/
select a.id, a.answer_abstract, a.correct_type, a.agrees, a.viewers, a.answer_time, a.question_id, b.question_name from seava_throwable.t_answer as a inner join seava_throwable.t_question as b on a.question_id = b.id where a.user_id = @user_id
