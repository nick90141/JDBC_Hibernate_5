package ex_002_insert_and_update;

import ex_002_insert_and_update.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }


    public List<Author> getAuthorList() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);
        cq.select(root);

        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();

        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id);
        session.close();
        return author;
    }

    public Author addAuthor(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();
        return author;
    }

    public void flushAndClear() {
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
    }

    public void commit() {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().commit();
    }

    public void updateAuthorNameById(long id, String newName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author = session.get(Author.class, id);
        if (author != null) {
            author.setName(newName);
            session.update(author);
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        AuthorHelper ah = new AuthorHelper();
        BookHelper bh = new BookHelper();

        for (int i = 1; i <= 200; i++) {
            Author author = new Author();
            author.setName("Author" + i);
            author.setLastName("LastName" + i);
            ah.addAuthor(author);

            if (i % 10 == 0) {
                ah.flushAndClear();
            }
        }

        ah.updateAuthorNameById(1, "NewAuthorName");
        ah.commit();

        bh.updateBookNameById(1, "NewBookName");

    }



}
