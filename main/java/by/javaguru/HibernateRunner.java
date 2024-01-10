package by.javaguru;
import by.javaguru.dao.CourseDao;
import by.javaguru.dao.StudentDao;
import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
import by.javaguru.entity.StudentProfile;
import by.javaguru.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j

public class HibernateRunner {
    public static void main(String[] args) {
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();

        //Студенты, которые учаться на курсе "Java Enterprise" - 1-й способ
        Course javaEnterprise = session.get(Course.class, 2);
        System.out.println("Students:");
        for (Student s : javaEnterprise.getStudents()) {
            System.out.println(s.getName());
        }

        //Студенты, которые учаться на курсе "Java Enterprise" - 2-й способ
        var query1 = session.createQuery("from Student s where s.course = :course", Student.class)
                .setParameter("course", javaEnterprise)
                .list();
        System.out.println("Students:" + query1);

       //Удалить всех студентов на курсе "Java Enterprise" с оценкой ниже 6 - 1-й способ (без связанной student_profile)
        var query2 = session.createMutationQuery("delete Student s where s.course = :course and " +
                                                 "s.studentProfile.assessment < :value")
                .setParameter("course", javaEnterprise)
                .setParameter("value", 6.0)
                .executeUpdate();
        System.out.println("Count of deleted students: " + query2);

        //Удалить всех студентов на курсе "Java Enterprise" с оценкой ниже 6 - 2-й способ (со связанной student_profile)
        for (Student s : javaEnterprise.getStudents()) {
            session.remove(s);
        }
        session.flush();

        //Добавить студента к курсе "Java Enterprise"
        StudentDao studentDao = StudentDao.getInstance();
        studentDao.save(Student.builder()
                .name("Александр Иванов")
                .course(javaEnterprise)
                .studentProfile(StudentProfile.builder()
                        .assessment(6.0)
                        .build())
                .build());

        //Удалить курс "Java Enterprise" -1-й способ
        session.remove(javaEnterprise);
        session.flush();

        //Удалить курс "Java Enterprise" -2-й способ
        CourseDao courseDao = CourseDao.getInstance();
        courseDao.delete(2);
        session.flush();

        log.warn("Course object: {} ", courseDao);
        session.clear();
        transaction.commit();
        sessionFactory.close();
    }
}
