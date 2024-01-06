package by.javaguru.dao;

import by.javaguru.entity.Course;
import by.javaguru.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CourseDao implements Dao<Integer, Course> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final CourseDao INSTANCE = new CourseDao();

    @Override
    public boolean save(Course course) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean update(Course course) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.merge(course);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.delete(session.get(Course.class, id));
            transaction.commit();
            return true;
        }
    }

    @Override
    public List<Course> findAll() {
        try (var session = sessionFactory.getCurrentSession();) {
            return session.createQuery("from Course", Course.class).list();
        }
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            Course course = session.get(Course.class, id);
            return Optional.ofNullable(course);
        }
    }

    public static CourseDao getInstance() {
        return INSTANCE;
    }

    private CourseDao() {
    }
}
