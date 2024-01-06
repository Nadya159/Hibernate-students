package by.javaguru.dao;

import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
import by.javaguru.util.HibernateUtil;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class StudentDao implements Dao<Integer, Student> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final StudentDao INSTANCE = new StudentDao();

    @Override
    public boolean save(Student student) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean update(Student student) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.delete(session.get(Student.class, id));
            transaction.commit();
            return true;
        }
    }

    @Override
    public List<Student> findAll() {
        try (var session = sessionFactory.getCurrentSession();) {
            var transaction = session.beginTransaction();
            return session.createQuery("from Student", Student.class).list();
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        }
    }

    @SneakyThrows
    public List<Student> findByCourse(Course courseRequired) {
        var session = sessionFactory.getCurrentSession();
        var transaction = session.beginTransaction();
        return session.createQuery("from Student s where s.course = :courseRequired", Student.class).list();
    }


    public static StudentDao getInstance() {
        return INSTANCE;
    }

    private StudentDao() {
    }
}
