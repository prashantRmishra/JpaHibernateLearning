--Course
insert into Course(id,name,created_date,updated_date,is_deleted) values(10001,'JPA in 100 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10002,'Perseverance in 20 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10003,'Angular in 10 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10004,'Springboot in 90 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10000,'React in 60 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10005,'Mahabharat in lifetime',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10006,'.Net in 50 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10007,'Javascript in 70 steps',sysdate(),sysdate(),false);
insert into Course(id,name,created_date,updated_date,is_deleted) values(10008,'Full stack dweveloper in 100 steps',sysdate(),sysdate(),false);

--Passport
insert into Passport(id,number) values(30001,'E14574');
insert into Passport(id,number) values(30002,'E125335');
insert into passport(id,number) values(30003,'E12378');
insert into passport(id,number) values(30004,'E1234566');
insert into passport(id,number) values(30005,'E12756');

--Student
insert into Student(id,name,passport_id,line1,line2,city) values(20001,'Prashant',30001,'line1','line2','sikandarabad');
insert into Student(id,name,passport_id,line1,line2,city) values(20002,'Sandeep',30002,'line1','line2','sikandarabad');
insert into Student(id,name,passport_id,line1,line2,city) values(20003,'Ajay',30003,'line1','line2','sikandarabad');
insert into Student(id,name,passport_id,line1,line2,city) values(20004,'Chad',30004,'line1','line2','sikandarabad');
insert into Student(id,name,passport_id,line1,line2,city) values(20005,'Manish',30005,'line1','line2','sikandarabad');

--Review
insert into Review(id,rating,description,course_id,student_id) values(40001,'4','Good course',10001,20001);
insert into Review(id,rating,description,course_id,student_id) values(40004,'4','Good course',10001,20001);
insert into Review(id,rating,description,course_id,student_id) values(40002,'1','Shitty course',10002,20003);
insert into Review(id,rating,description,course_id,student_id) values(40003,'6','Not sure what to say about this course',10003,20001);

--student_course
insert into student_course(student_id,course_id) values(20001,10001);
insert into student_course(student_id,course_id) values(20002,10001);
insert into student_course(student_id,course_id) values(20003,10001);
insert into student_course(student_id,course_id) values(20001,10002);
insert into student_course(student_id,course_id) values(20002,10003);
insert into student_course(student_id,course_id) values(20003,10003);


