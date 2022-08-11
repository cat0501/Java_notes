

# 编写SQL查询语句







查询重复

查询平均分不及格

查询每门课成绩不低于80

查询学生总成绩

查询总成绩最高

查询 某个值第N高 的记录



<hr>



### 1、查询student表中重名的学生，结果包含id和name，按name,id升序

```sql
select id,name
from student
where name in (
select name from student group by name having(count(*) > 1)
) order by name;
```



> 我们经常需要查询某一列重复的行，一般通过group by(有重复的列)然后取count>1的值。 关系型数据库有他的局限性， 有些看似简单的查询写出来的sql很复杂，而且效率也会很低。



<hr>



### 2、在student_course表中查询平均分不及格的学生，列出学生id和平均分

```sql
select sid,avg(score) as avg_score
from student_course
group by sid having(avg_score<60);
```



> group by和having是最常考的。 where子句中不能用聚集函数作为条件表达式，但是having短语可以，where和having的区别在于对用对象不同，where作用于记录，having作用于组。



### 3、在student_course表中查询每门课成绩都不低于80的学生id

```sql
select distinct sid
from student_course
where sid not in (
select sid from student_course
where score < 80);
```

> 用到反向思想，其实就是数理逻辑中的∀x:P和¬∃x:¬P是等价的。



### 4、查询每个学生的总成绩，结果列出学生姓名和总成绩 如果使用下面的sql会过滤掉没有成绩的人

```sql
select name,sum(score) total
from student,student_course
where student.id=student_course.sid
group by sid;
```

>更保险的做法应该是使用 左外连接



```sql
select name,sum(score)
from student left join student_course
on student.id=student_course.sid
group by sid;
```





### 5、总成绩最高的学生，结果列出学生id和总成绩 下面的sql效率很低，因为要重复计算所有的总成绩。

```sql
SELECT sid ,sum(score) as sum_score
FROM student_course group by sid having sum_score>=all
(select sum(score) from student_course group by sid);
```



> 因为order by中可以使用聚集函数，最简单的方法是：



```sql
select sid,sum(score) as sum_score
from student_course group by sid
order by sum_score desc limit 2;
```

> 同理可以查总成绩的前三名。







### 6、在student_course表查询课程1成绩第2高的学生，如果第2高的不止一个则列出所有的学生

这是个查询 第N大数 的问题。 我们先查出第2高的成绩：

```sql
select min(score) from student_course where cid = 1 group by score order by score desc limit 2;
```



> 使用这种方式是错的，因为作用的先后顺序是group by->min->order by->limit，mysql提供了limit offset,size这种方式来取第N大的值，因此正确的做法是：

```sql
select score from student_course where cid = 1 group by score order by score desc limit 1,1;
```



然后再取出该成绩对应的学生：

```sql
select * from student_course
where cid=1 and score = (
select score from student_course where cid = 1 group by score order by score desc limit 1,1
);
```



类似的，可以查询 某个值第N高 的记录。

































































