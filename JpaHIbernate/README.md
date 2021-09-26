# JPA Hibernate in depth with Junit5 Test cases

**Let see methods of** ``EntityManager`` for performing various crud operations in ``CourseRepository.java`` <br>


```java
	@Repository
	@Transactional
	public class CourseRepository {
		
		@Autowired
		EntityManager em;
		
		/*Find by id*/
		public Course findById(Long id) {
			return em.find(Course.class, id);
		}
		
		/*Delete by id method*/
		public void delete(Long id) {
			
			em.remove(findById(id));
		}
		
		//For Insert or Update operations
		public Course save(Course c) {
			
			if(c.getId()==null) {
				em.persist(c);    //Insert 
			}
			else em.merge(c);    // Update
			return c;
		}
		
	}
```

### Importance of ``@Transactional`` annotation in Repository. <br>

```java
	@Repository
	@Transactional
	public class CourseRepository {
	....
		public void playWithEntityManager() {
			Course entity = new Course("WebServices");
			em.persist(entity);
			entity.setName("SpringBoot WeServices");
		}
	....
	}
```

Output <br>

```log
Hibernate: call next value for hibernate_sequence
Hibernate: insert into course (name, id) values (?, ?)
Hibernate: update course set name=? where id=?
```
As you can see after executing insert query for ``em.persist(entity)``, update is executed. 
This is beacause ``@Transactional`` ensures that there is atomic execution of operation that is either all or non.

***

## Some Imp Methods of ``EntityManager``



``flush()`` saves the changes till this point to database <br>

```java
	public void playWithEntityManager() {
		Course entity = new Course("WebServices");
		em.persist(entity);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
		             So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		
		entity.setName("SpringBoot WeServices");
		em.flush();
		
	}
```

``detach(Object)`` If you want to stop tracking the changes of an object as a part of transaction <br>

```java
	public void playWithEntityManager() {
		Course entity = new Course("WebServices");
		em.persist(entity);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
		             So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		
		entity.setName("SpringBoot WeServices");
		em.flush();
		
		Course course2 = new Course("Dragon ball z ");
		em.persist(course2);
		em.flush();
		
		em.detach(course2); // stop tracking changes to course2 as part of transaction
		course2.setName("One piece is the best anime ever");
		em.flush();
		
	}
```
Console Output

```log
Hibernate: call next value for hibernate_sequence
Hibernate: insert into course (name, id) values (?, ?)
Hibernate: update course set name=? where id=?
Hibernate: call next value for hibernate_sequence
Hibernate: insert into course (name, id) values (?, ?)
```
H2-console : As you can see ``One piece is the best anime ever`` is not updated <br>

<img src="src/main/resources/static/images/h2-console-detach.PNG" width="500" height="400"> <br>

``clear()`` It is similar to ``detach(Object)`` , but once it is called it will everything that is being tracked by ``EntityManager`` .

```java

	public void playWithEntityManager() {
		Course entity = new Course("WebServices");
		em.persist(entity);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
		             So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		Course course2 = new Course("Dragon ball z ");
		em.persist(course2);
		em.flush();
		
		em.clear(); // stops tracking changes to course2 and entity both
		course2.setName("One piece is the best anime ever");
		em.flush();
		
		entity.setName("SpringBoot WeServices");
		em.flush();
		
	}

```
Console Output <br>

```log
Hibernate: call next value for hibernate_sequence
Hibernate: insert into course (name, id) values (?, ?)
Hibernate: call next value for hibernate_sequence
Hibernate: insert into course (name, id) values (?, ?)
```
H2-console: As you can see after ``clear()`` changes to ``entity`` and ``course2`` are not updated.

<img src="src/main/resources/static/images/h2-console-clear.PNG" width="500" height="400"> <br>

``refresh(Object)`` this method makes the object synchronize with the db , that is ``entity`` and ``course2`` will have the same value 
what is stored in the db before ``clear()`` or ``detach()`` is called. See the below code.

```java
	public void playWithEntityManager() {
		Course course1 = new Course("WebServices");
		em.persist(course1);
		Course course2 = new Course("Dragon ball z ");
		em.persist(course2);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
        So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		course2.setName("One piece is the best anime ever");
		course1.setName("SpringBoot WeServices");
		
		em.refresh(course1); // refresh() updates the respective object to have the same values as the db, which is returned and hibernate select query is executed  
		em.flush();
		
		
	}
```
Console Output 

```log
Hibernate: insert into course (name, id) values (?, ?) //1st flush()
Hibernate: insert into course (name, id) values (?, ?) //2nd flush()
Hibernate: select course0_.id as id1_0_0_, course0_.name as name2_0_0_ from course course0_ where course0_.id=? //refresh() of course1
Hibernate: update course set name=? where id=? // update name of course 2
```
***

h2-console: As you can see ``course2``(id=2) is updated but ``course1``(id=1) is not updated

<img src="src/main/resources/static/images/flush.PNG" width="500" height="400"> <br>

***
### For Unit test cases you can see files in ``src/test/java`` folder <br>

``@DirtiesContext`` in Junit Test classes <br>

```java
	@Test
	@DirtiesContext // after Execution of this test the state will be reverted to prior state i.e deleted data will be
	//restored so that other dependent test cases are not affected.
	 
	void deleteCourse(){
		try {
			repository.delete(10002L);
			assertNull(repository.findById(10002L));
		} catch (Exception e) {
			assertFalse(true);
		}
	}
```
**JPQL Unit test cases** for findAll() courses from db. <br>

```java
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourseRepositoryJpqlTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager manager;

	/*Generic Type*/
	@Test
	void JpqlFindAll_basic() {
		Query createQuery = manager.createQuery("select c from Course c");
		List resultList = createQuery.getResultList();
		logger.info("Result list is -> {}",resultList);
	}
	
	/*Typed query*/
	@Test
	void JpqlFindAll_Typed() {
		TypedQuery<Course> createQuery = manager.createQuery("select c from Course c",Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list is -> {}",resultList);
	}
	
	/* where clause*/
	@Test
	void JpqlFindAll_where() {
		TypedQuery<Course> createQuery = manager.createQuery("select c from Course c where name like '%100%' ",Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list with where clause is -> {}",resultList);
	}
	
	/*NamedQuery*/
	@Test
	void JpqlFindAll_NamedQuery() {
		TypedQuery<Course> createQuery = manager.createNamedQuery("find_all_courses",Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list with NamedQuery is -> {}",resultList);
	}
}

```

### Important Annotation provided by JPA and Hibernate

____

1.``@Table`` <br>
----
Defines name of the table <br>
Example ``@Table(name="CourseDetails")`` , so instead of ``course``, ``course_details`` table is created.

2.``@Column`` <br>
----
Defines the name of property in bean as column name in table
Example 

```java
@Coumn(name="fullname") // name will be 'fullname' in course table for property 'name'
private String name; 
/*To specify nullability of a column*/
@Column(name="fullname" nullable=false)
private String name;
```
3.``@UpdateTimestamp`` and ``@CreationTimestamp``
----
It is Hibernate custom annotation which is not specified by JPA
``@UpdateTimestamp`` For storing last updated date time of a row.
``@CreationTimestamp`` For storing created date time of a row.

```java
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	@CreationTimestamp
	private LocalDateTime createdDate;
```

4.``@NamedQuery``
---
We have already seen How we can use ``@NamedQuery`` In ``Courses.java`` 

```java
@Entity
@NamedQuery(name = "find_all_courses", query = "select c from Course c")
public class Course {
....
}
```
5.``@NamedQueries``
---
For using multiple Named Query we can use it.
``@NamedQuery`` is specified inside ``@NamedQueries`` in value array

```java
	@NamedQueries(value = {
		@NamedQuery(name="find_all_courses",query = "select c from Course c"),
		@NamedQuery(name = "find_all_cources_with_where_clause",query = "select c from Course c where name like '%100%'")
		public class Course{
		...
		}
})
```
____

### Native Queries

**These are nothing but standard SQL Queries**

_List of test cases for native queries in: _ `` NativeQueriesTest.java``

1.Basic Query
---
```java
@Test
	void native_query_basic() {
		Query q  = manager.createNativeQuery("select * from course",Course.class);
		List resultList = q.getResultList();
		logger.info("select * from course : -> {}",resultList);
	}
```
2.Parameterized Query
---
```java
@Test
	void native_query_with_parameter() {
		Query q  = manager.createNativeQuery("select * from course where id =? ",Course.class);
		q.setParameter(1, 10001L);
		List resultList = q.getResultList();
		logger.info("select * from course where id = 10001L : -> {}",resultList);
	}
```
3.Named Parameterized query
---

```java
@Test
	void native_query_with_named_parameter() {
		Query q  = manager.createNativeQuery("select * from course where id =:id ",Course.class);
		q.setParameter("id", 10001L);
		List resultList = q.getResultList();
		logger.info("select * from course where id = 10001L : -> {}",resultList);
	}
```
4.Native Query for mass update
---
Sometimes It is not efficient to use JPQL e.g for updating all the rows of the table. <br>
If you were to do that with ``JPQL`` you will fetch each row and update them one by one which is not efficient.
Hence, you will have to use Native Query

```java
	@Test
	@Transactional // as update is being performed else it will throws TransactionRequiredException
	void native_query_mass_update() {
		Query q  = manager.createNativeQuery("update course set updated_date =sysdate()",Course.class);
		int noOfROwsUpdated = q.executeUpdate();
		logger.info("No of rows updated -> {}",noOfROwsUpdated);
	}
	
```		

