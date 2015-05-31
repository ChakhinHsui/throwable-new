/*Question  问题相关的sql语句*/

/*question=插入问题*/
/*question_insertOneQuestion*/
insert into seava_throwable.t_question(question_name, question_description, question_type, viewers, agrees, degrees, answers, focuses, collections, solved, create_time, kind_id, user_id) 
values(@question_name, @question_description, @question_type, @viewers, @agrees, @degrees, @answers, @focuses, @collections, @solved, @create_time, @kind_id, @user_id)

/*question=添加问题描述*/
/*question_addQuestionDesc*/
update seava_throwable.t_question set question_description = @question_description where id = @id

/*question=公开问题*/
/*question_publicQuestion*/
update seava_throwable.t_question set question_type = @question_type where id = @id 

/*根据问题信息返回问题的id*/
/*question_queryQId*/
select id from seava_throwable.t_question
where question_type = @question_type
and question_name = @question_name
and question_description = @question_description
and create_time = @create_time
and user_id = @user_id
and kind_id = @kind_id order by id desc


/*question=修改访问数*/
/*question_updateViewers*/
update seava_throwable.t_question set viewers = viewers + @viewers where id = @id 

/*question=修改赞同数*/
/*question_updateAgrees*/
update seava_throwable.t_question set agrees = agrees + @agrees where id = @id 

/*question=根据id查询问题*/
/*question_queryOneQuestionInfo*/
select a.id, a.question_name, a.question_description, a.viewers, a.agrees, a.degrees, a.solved, a.user_id, a.kind_id, a.focuses, a.collections, a.create_time, b.username, c.image, d.asks, d.answers
from seava_throwable.t_question as a inner join seava_throwable.t_user as b on a.user_id = b.id 
left join seava_throwable.t_user_extend as c on c.user_id = a.user_id
left join seava_throwable.t_user_statistics as d on d.user_id = a.user_id where a.id = @id 

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
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.create_time, b.username
from seava_throwable.t_question as a inner join seava_throwable.t_user as b on a.user_id = b.id
where a.question_type = @question_type order by a.id desc

/*question=根据questionType分页查询查询所有问题*/
/*question_queryAllQuestionByTypePage*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.create_time, b.username
from seava_throwable.t_question as a inner join seava_throwable.t_user as b on a.user_id = b.id
where a.question_type = @question_type order by a.id desc limit @page, @count

/*question=根据userid查询问题的部分信息*/
/*question_queryPartQByUserId*/
select id, question_name, question_type, viewers, agrees, degrees, answers, focuses, collections, solved, create_time from seava_throwable.t_question where user_id = @user_id

/*question=根据questionType查询所有最热(访问数最多)问题*/
/*question_queryAllHotQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type order by a.viewers desc, a.id desc limit @page, @count

/*question=根据questionType查询所有关注最多的问题*/
/*question_queryAllMostFocusQuestionByType*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.focuses, a.solved, a.create_time, b.username from seava_throwable.t_question as a left join seava_throwable.t_user as b on a.user_id = b.id  where a.question_type = @question_type and a.solved = 0 order by a.focuses desc, a.id desc limit @page, @count

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
update seava_throwable.t_question set degrees = degrees + @disagrees where id = @id

/*question=增加访问数*/
/*question_addViewers*/
update seava_throwable.t_question set viewers = viewers + @viewers where id = @id

/*question=查询用户问题数*/
/*question_getUserQuestionNumber*/
select count(*) from seava_throwable.t_question where user_id = @userId

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

/*question-根据label查询问题*/
/*question_queryQuestionByLabelId*/
select a.id as questionId, a.question_name as questionName, a.question_type as questionType, a.answers as answers, a.focuses as focuses from seava_throwable.t_question as a inner join seava_throwable.t_label_question as b where b.label_id = @labelId and b.question_id = a.id

/*question-查询问题的总记录数 用于分页*/
/*question_queryAllQuestionNumber*/
select count(*) from seava_throwable.t_question where question_type = 1

/*question-查询热门问题的总记录数 用于分页*/
/*question_queryAllMostFocusQuestionNumber*/
select count(*) from seava_throwable.t_question where question_type = 1 and solved = 0


/*question-问题已解决*/
/*question_solveQuestion*/
update seava_throwable.t_question set solved = @solved where id = @id

/*question-查询相似的问题*/
/*question_querySameQuestions*/
select a.id, a.question_name, a.viewers, a.agrees, a.degrees, a.answers, a.focuses, a.create_time, b.username 
from seava_throwable.t_question as a 
left join seava_throwable.t_user as b 
on a.user_id = b.id  
where a.question_type = @question_type 
and kind_id = @kindId 
order by a.answers desc, a.id desc limit 10
