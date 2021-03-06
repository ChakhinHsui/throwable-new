/*answer*/
/*回答相关的sql*/

/*answer=查询答案 根据问题id查询答案 只显示正确，部分正确和未确认的答案*/
/*answer_queryAnswerByQuestionId*/
select a.id, a.answer_description, a.correct_type, a.agrees, a.disagrees, a.viewers, a.answer_time, a.user_id, b.username, c.image, d.asks, d.answers from seava_throwable.t_answer as a 
inner join seava_throwable.t_user as b on a.user_id = b.id
left join seava_throwable.t_user_extend as c on a.user_id = c.user_id 
left join seava_throwable.t_user_statistics as d on a.user_id = d.user_id 
where a.question_id = @question_id and a.correct_type < 4  order by a.correct_type, a.agrees desc, a.answer_time desc

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

/*answer=查询用户回答的问题个数*/
/*answer_queryUserAnswerNumber*/
select count(*) from seava_throwable.t_answer where user_id = @userId

/*answer=查询问题的正确答案*/
/*answer_queryCorrectAnswer*/
select answer_abstract from seava_throwable.t_answer where question_id = @questionId and correct_type = 1

/*answer=增加答案的赞同数*/
/*answer_agreeAnswer*/
update seava_throwable.t_answer set agrees = agrees + @agrees where id = @id

/*answer=增加答案的反对数*/
/*answer_disagreeAnswer*/
update seava_throwable.t_answer set disagrees = disagrees + @disagrees where id = @id

/*answer=接受答案*/
/*answer_acceptAnswer*/
update seava_throwable.t_answer set correct_type = @correct_type where id = @id
