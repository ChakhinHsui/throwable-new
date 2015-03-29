/*label-增加label*/
/*label_addLabel*/
insert into seava_throwable.t_label(name, type, time) values(@name, @type, @time)

/*label-根据label名查询label*/
/*label_queryLabelByName*/
select * from seava_throwable.t_label where name = @name

/*label-根据label的id查询label*/
/*label_queryLabelById*/
select * from seava_throwable.t_label where id = @id

/*label-增加用户label记录*/
/*label_addUserLabel*/
insert into seava_throwable.t_user_label(user_id, label_id) values(@userId, @labelId)

/*label-增加问题label记录*/
/*label_addQuestionLabel*/
insert into seava_throwable.t_label_question(question_id, label_id) values(@questionId, @labelId)

/*label-根据用户id查询label信息*/
/*label_queryLabelByUserId*/
select a.id as labelId, a.name as name, a.type as labelType from seava_throwable.t_label as a inner join seava_throwable.t_user_label as b where b.user_id = @userId and a.id = b.label_id

/*label-查询所有label*/
/*label_queryAllLabel*/
select * from seava_throwable.t_label

/*label-根据问题id查询label信息*/
/*label_queryLabelByQuestionId*/
select a.id as labelId, a.name as labelName, a.type as labelType from seava_throwable.t_label as a inner join seava_throwable.t_label_question as b where b.question_id = @questionId and a.id = b.label_id

/*label-查询重复数据*/
/*label_queryExists*/
select * from seava_throwable.t_user_label
where user_id = @userId and label_id = @labelId