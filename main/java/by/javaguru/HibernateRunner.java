package by.javaguru;

import by.javaguru.dao.StudentDao;
import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
import by.javaguru.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j

public class HibernateRunner {
    public static void main(String[] args) {
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        session.beginTransaction();
        Course javaEnterprise = session.get(Course.class, 2);
        StudentDao studentDao = StudentDao.getInstance();
        System.out.println(studentDao.findByCourse(javaEnterprise));
        //log.warn("User object: {} ", studentDao);
        sessionFactory.close();
    }
}
