package by.javaguru;

import by.javaguru.dao.TrainerDao;
import by.javaguru.entity.Course;
import by.javaguru.entity.Trainer;
import by.javaguru.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainerDaoTest {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static TrainerDao trainerDao = TrainerDao.getInstance();

    private static Trainer trainer = new TrainerDaoTest().buildTrainer();

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
        assertTrue(trainerDao.save(trainer));
    }

    @Test
    void testUpdate() {
        assertTrue(trainerDao.update(trainer));
    }

    @Test
    void testDelete() {
        assertTrue(trainerDao.delete(trainer.getId()));
    }

    public Trainer buildTrainer() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        Course course1 = session.get(Course.class, 1);
        Course course2 = session.get(Course.class, 2);
        Course course3 = session.get(Course.class, 3);

        var trainer = Trainer.builder()
                .name("Test123")
                .courses(List.of(course1, course2, course3))
                .build();
        return trainer;
    }
}
