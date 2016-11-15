/*
 */
package sk.upjs.uinf.hibernateproject;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sk.upjs.uinf.hibernateproject.Entity.*;

/**
 *
 * @author Jan
 */
public class ManageBook {
    
    /**
     * objekt SessionFactory nevyhnutny pre vytvaranie sessions - spojeni s databazou
     */
    private final SessionFactory factory;

    /**
     * Constructor
     * @param factory 
     */
    public ManageBook(SessionFactory factory) {
        this.factory = factory;
    }        
    
    /**
     * Metoda, ktora prida autora do databazy
     * Autmoaticky su pridane do zodpovedajucej tabulky aj vsetky knihy, ktore ma ulozene v mnozine Set<Books2> books
     * Automaticky su pridane aj zodpovedauje zaznamy v spojovacej tabulke
     * @param a
     * @return new ID
     */
    public int addAuthorWithBooks(Author a) {
        Session session = factory.openSession();
        Transaction tran = null;
        int id = -1;
        try{
            tran = session.beginTransaction();
            id = (int)session.save(a);
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null) {
                tran.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return id;
    }
    
    /**
     * Metoda na pridanie jednej knih do databazy
     * @param b
     * @return new ID
     */
    public int addBook(Book2 b) {        
        Session session = factory.openSession();
        Transaction tran = null;
        int id = -1;
        try {
            tran = session.beginTransaction();
            
            id = (int)session.save(b);
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null)
                tran.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return id;
    } 
    
    /**
     * Metoda na vypisanie vsetkych knih z databazy
     */
    public void listBooks(){        
        Transaction tran = null;
        
        try (
           Session session = factory.openSession();
        ) {
            tran = session.beginTransaction();
            
            //Hibernate spraví všetko automaticky pomocou anotácií v triedach Book2 a Author
            List<Book2> books = session.createQuery("FROM Book2").list();
            
            for (Book2 book : books) {
                System.out.println(book.toString());
            }
            
            tran.commit();                        
        } catch (HibernateException e) {
            if (tran != null)
                tran.rollback();
            e.printStackTrace();
        }
    }
    
    /**
     * Spraví update počtu kníh v databáze
     * @param id
     * @param qty 
     */
    public void updateBook(int id, int qty){
        Transaction tran = null;
        try (
            Session session = factory.openSession();
        ) {
            tran = session.beginTransaction();
            
            //Podľa daného ID vyhľadáme knihu v databáze
            Book2 b = (Book2)session.get(Book2.class, id);
            //a nastavíme jej novú hodnotu daného atribútu
            b.setQty(qty);
            session.update(b);
            
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null)
                tran.rollback();
            e.printStackTrace(); 
        }
    }
}
