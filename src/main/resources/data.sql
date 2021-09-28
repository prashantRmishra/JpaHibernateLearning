--Course
insert into Course(id,name,created_date,updated_date) values(10001,'JPA in 100 steps',sysdate(),sysdate());
insert into Course(id,name,created_date,updated_date) values(10002,'Perseverance',sysdate(),sysdate());
insert into Course(id,name,created_date,updated_date) values(10003,'is the key',sysdate(),sysdate());
insert into Course(id,name,created_date,updated_date) values(10004,'to success',sysdate(),sysdate());

--Passport
insert into Passport(id,number) values(30001,'E1234');
insert into Passport(id,number) values(30002,'E1235');
insert into passport(id,number) values(30003,'E1236');

--Student
insert into Student(id,name,passport_id) values(20001,'Prashant',30001);
insert into Student(id,name,passport_id) values(20002,'Sandeep',30002);
insert into Student(id,name,passport_id) values(20003,'Ajay',30003);

--Review
insert into Review(id,rating,description,course_id) values(40001,'****','Good course',10001);
insert into Review(id,rating,description,course_id) values(40004,'****','Good course',10001);
insert into Review(id,rating,description,course_id) values(40002,'*','Shitty course',10002);
insert into Review(id,rating,description,course_id) values(40003,'**','Not sure what to say about this course',10003);

--student_course
insert into student_course(student_id,course_id) values(20001,10001);
insert into student_course(student_id,course_id) values(20002,10001);
insert into student_course(student_id,course_id) values(20003,10001);
insert into student_course(student_id,course_id) values(20001,10002);
insert into student_course(student_id,course_id) values(20002,10003);
insert into student_course(student_id,course_id) values(20003,10003);


