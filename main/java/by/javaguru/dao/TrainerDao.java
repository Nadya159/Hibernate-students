package by.javaguru.dao;

import by.javaguru.entity.Student;
import by.javaguru.entity.Trainer;
import by.javaguru.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TrainerDao implements Dao<Integer, Trainer>{
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final TrainerDao INSTANCE = new TrainerDao();
    @Override
    public boolean save(Trainer trainer) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.persist(trainer);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean update(Trainer trainer) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.merge(trainer);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.delete(session.get(Trainer.class, id));
            transaction.commit();
            return true;
        }
    }

    @Override
    public List<Trainer> findAll() {
        try (var session = sessionFactory.getCurrentSession();) {
            var transaction = session.beginTransaction();
            return session.createQuery("from Trainer", Trainer.class).list();
        }
    }

    @Override
    public Optional<Trainer> findById(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, id);
            return Optional.ofNullable(trainer);
        }
    }

    public static TrainerDao getInstance() {
        return INSTANCE;
    }

    private TrainerDao() {
    }
}
