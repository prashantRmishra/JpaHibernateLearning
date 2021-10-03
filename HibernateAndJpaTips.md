## Hibernate and JPA tips

**Hibernate soft Deletes**


Part 1
---
It keeps history of what row has been deleted. As every row is an object mapped to db table.

Lets take example of ``Course.java``

Add field ``private boolean isDeleted`` in it. It will be ``true`` if row is deleted or ``false`` if row is not deleted.

Add ``is_deleted`` as column and ``false`` as value to ``course`` table in ``data.sql`` as well else it will through ``null`` error while inserting.

We will use hibernate specific annotation ``@SQLDelete`` , its not JPA annotation.

```java

@Entity
@NamedQuery(name = "find_all_courses", query = "select c from Course c")
@SQLDelete(sql = "update course set is_deleted =true where id ?")
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	....
	}
```
But Below test will still fetch course with deleted id (as the row is still present, but is_deleted column value is true but getById() doesn't know that)

```java
	@Test
	@DirtiesContext
	void deleteCourse(){
		try {
			repository.delete(10000L);
			assertNull(repository.findById(10000L)); // it will still fetch result
		} catch (Exception e) {
			assertFalse(true);
		}
	}
```
To avoid above we can use ``@where`` clause

```java
@Entity
@NamedQuery(name = "find_all_courses", query = "select c from Course c")
@SQLDelete(sql = "update course set is_deleted =true where id=?")
@Where(clause = "is_deleted=fasle") // so fetch rows if is_deleted=false
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
```
Upon running above test console output is :

```log
Hibernate: update course set is_deleted =true where id=?
2021-10-03 10:23:40.715 TRACE 13416 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [10000]
Hibernate: select course0_.id as id1_0_0_, course0_.created_date as created_2_0_0_, course0_.is_deleted as is_delet3_0_0_, course0_.name as name4_0_0_, course0_.updated_date as updated_5_0_0_ from course course0_ where course0_.id=? and ( course0_.is_deleted=0)
```

Part 2
---
If you are using ``NativeQueries`` then you have to manually add ``where is_deleted =0`` for ``course`` table as ``NativeQueries`` don't recognize ``@SQLDelete`` and ``@Where`` clause.

Example update following test like this in your ``NativeQueriesTest.java``

```java
	@Test
	void native_query_basic() {
		Query q  = manager.createNativeQuery("select * from course where is_deleted = 0",Course.class);
		List resultList = q.getResultList();
		logger.info("select * from course : -> {}",resultList);
	}
	
```

**Important : **
When deleteById(Long id) is called first findById(id) is called as we can see in ``CourseRepository.java``

```java
/*Delete by id method*/
	public void delete(Long id) {
		em.remove(findById(id)); //findById return COurse Obj from table row
	}
```
then ``em.remove(course)`` is called , here hibernate sets ``is_deleted= true`` as we have mentioned in ``@SQLDelete`` annotation.

But attribute value isDeleted is still false, which is a problem. Because hibernate has no way of knowing that isDeleted field is being set as it just appends the query from ``@SQLDelete``.

<img src="src/main/resources/static/images/sqldelete_annotation.png" width="800" heigth="500">

As we can see even after removing the row from db (after setting ``is_deleted=true``), ``isDeleted`` field has ``false`` value, which should be avoided.

Hence to avoid this  we can use ``@PreRemove`` life cycle hook of JPA

Add following to your ``Course.java`` class

```java
	@PreRemove
	void beforeRemove() {
		
		LOGGER.info("isDelete is being set to true");
		this.isDeleted=true;
	}
```
This will set the value of ``isDeleted=true`` ,as ``is_deleted`` will be set to true in db table of ``course``

****

JPA Life Cycle Hooks or Methods
---
``PreRemove``,``postLoad``,``PreUpdate``,``PrePersist``,``PostUpdate``,``PostRemove``,and ``PostPersist``

We have already seen ``preRemove``

____

Using ``@Embedded`` and ``@Embeddable`` 
---

If student has field Address and you want to store the address directly int ostudent table , not in Address table. Then we can use ``@Embeddable`` abd ``@Embedded`` .

create class ``Address.java`` and add annotation ``@Embeddable``

```java

@Embeddable
public class Address {

	private String line1;
	private String line2;
	private String city;
	
	public Address(String line1, String line2, String city) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
	}
	public Address() {}
}

```
create field address in ``Student.java`` class

```java
	@Embedded
	private Address address;
```
	
IF we run the following test method.

```java
/*Inserting Address directly to the student table*/
	@Test
	@Transactional
	void insertAddressIntoStudent() {
		Student student = entityManager.find(Student.class, 20001L);
		student.setAddress(new Address("Room no 202/Patidar complex ", "Kalher pipeline", "Kalher thane west"));
		entityManager.flush(); // storing in db till this point.
	
	}
```
We will get following console output:

 
```log
Hibernate: select student0_.id as id1_5_0_, student0_.city as city2_5_0_, student0_.line1 as line3_5_0_, student0_.line2 as line4_5_0_, student0_.name as name5_5_0_, student0_.passport_id as passport6_5_0_ from student student0_ where student0_.id=?
2021-10-03 13:51:10.732 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [20001]
2021-10-03 13:51:10.763 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicExtractor   : extracted value ([city2_5_0_] : [VARCHAR]) - [null]
2021-10-03 13:51:10.763 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicExtractor   : extracted value ([line3_5_0_] : [VARCHAR]) - [null]
2021-10-03 13:51:10.763 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicExtractor   : extracted value ([line4_5_0_] : [VARCHAR]) - [null]
2021-10-03 13:51:10.764 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicExtractor   : extracted value ([name5_5_0_] : [VARCHAR]) - [Prashant]
2021-10-03 13:51:10.766 TRACE 3696 --- [           main] o.h.type.descriptor.sql.BasicExtractor   : extracted value ([passport6_5_0_] : [BIGINT]) - [30001]
2021-10-03 13:51:10.801 TRACE 3696 --- [           main] org.hibernate.type.CollectionType        : Created collection wrapper: [com.prashant.jpa.hibernate.JpaHIbernate.entity.Student.courses#20001]
2021-10-03 13:51:10.823 TRACE 3696 --- [           main] org.hibernate.type.CollectionType        : Created collection wrapper: [com.prashant.jpa.hibernate.JpaHIbernate.entity.Student.reviews#20001]
Hibernate: update student set city=?, line1=?, line2=?, name=?, passport_id=? where id=?
```
As we can see, update on student table is called with new fields line1, lin2, and city.

If you manually want to add data to table , add them directly from ``data.sql`` table.

```sql
insert into Student(id,name,passport_id,line1,line2,city) values(20001,'Prashant',30001,'line1','line2','sikandarabad');
```

Using ENUM with JPA
---
We have used ``String rating`` in ``Review.java`` class which is not a good practice, we as the values for the ``rating`` field are only ``1,2,3,4,5``. Hence using string is not a good practice.

We can use ENUM for it.

``ReviewRating.java``

```java
package com.prashant.jpa.hibernate.JpaHIbernate.entity;

public enum ReviewRating {
	ONE,TWO,THREE,FOUR,FIVE
}

```
``Review.java``

```java

@Entity
public class Review {
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated
	private ReviewRating rating;
	
	....
	}
```

Update your ``data.sql`` appropreately as well 

Example

```sql
insert into Review(id,rating,description,course_id,student_id) values(40001,4,'Good course',10001,20001);

```
As we can see that the type of the rating is ``Ordinal`` i.e it will be stored as integer in table. Its better to have it as String, integer would be based on index position which is not good.









