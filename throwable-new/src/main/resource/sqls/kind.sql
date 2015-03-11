/*Kind*/

/*kind=插入kind*/
/*kind_insertOneKind*/
insert into seava_throwable.t_question_kind(kind_name, kind_parent_id, user_id, time) values(@kind_name, @kind_parent_id, @user_id, @time)

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

/*kind=查询所有分类的部分信息*/
/*kind_queryAllKindPartInfo*/
select id, kind_name from seava_throwable.t_question_kind
