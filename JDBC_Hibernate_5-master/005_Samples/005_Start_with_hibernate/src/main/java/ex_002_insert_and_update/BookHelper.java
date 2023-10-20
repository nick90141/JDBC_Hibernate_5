package ex_002_insert_and_update;

import ex_002_insert_and_update.entity.Author;
import ex_002_insert_and_update.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class BookHelper {

    private SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Book addBook(String bookName, String authorName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Author author = getAuthorByName(authorName);

        if (author == null) {
            author = new Author();
            author.setName(authorName);
            session.save(author);
        }

        Book book = new Book();
        book.setName(bookName);
        book.setAuthorId(author.getId());
        session.save(book);

        session.getTransaction().commit();
        session.close();

        return book;
    }

    public List<Book> getBooksByAuthorName(String authorName) {
        Session session = sessionFactory.openSession();

        String hql = "FROM Book B WHERE B.author.name = :authorName";
        Query query = session.createQuery(hql).setParameter("authorName", authorName);
        List<Book> books = query.getResultList();

        session.close();

        return books;
    }




    private Author getAuthorByName(String authorName) {
        Session session = sessionFactory.openSession();

        String hql = "FROM Author A WHERE A.name = :authorName";
        Author author = (Author) session.createQuery(hql)
                .setParameter("authorName", authorName)
                .uniqueResult();

        session.close();

        return author;
    }
    public void updateBookNameById(long bookId, String newBookName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Book book = session.get(Book.class, bookId);
        if (book != null) {
            book.setName(newBookName);
            session.update(book);
        }
        session.getTransaction().commit();
        session.close();
    }

}
