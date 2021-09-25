# JPA Hibernate in depth with Junit5 Test cases
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