/*answer*/
/*回答相关的sql*/

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
