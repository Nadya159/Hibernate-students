package by.javaguru;

import by.javaguru.dao.CourseDao;
import by.javaguru.entity.Course;
import by.javaguru.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseDaoTest {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static CourseDao courseDao = CourseDao.getInstance();

    private static Course course = new CourseDaoTest().buildCourse();

    @BeforeAll
    static void setup() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    void setupThis() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearThis() {
        session.getTransaction().commit();
    }

    @AfterAll
    static void tear() {
        sessionFactory.close();
    }

    @Test
    void testSave() {
        assertTrue(courseDao.save(course));
    }

    @Test
    void testUpdate() {
        assertTrue(courseDao.update(course));
    }

    @Test
    void testDelete() {
        assertTrue(courseDao.delete(course.getId()));
    }

    public Course buildCourse() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        var course = Course.builder()
                .name("Test123")
                .build();
        return course;
    }
}
