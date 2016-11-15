/*
 */
package sk.upjs.uinf.hibernateproject;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sk.upjs.uinf.hibernateproject.Entity.Author;
import sk.upjs.uinf.hibernateproject.Entity.Book2;

/**
 * Hlavna trieda pre spustenie celeho projektu
 * @author Jan
 */
public class Spustac {
    
    public static void main(String[] args) {
        SessionFactory factory;
        try {            
            factory = new Configuration().configure().buildSessionFactory();
            
            //v starsej verzii bolo potrebne pridat aj anotovane classy, teraz staci pridat ich odkaz na koniec suboru hibernate.xfg.xml
            //->factory = new Configuration().configure().addAnnotatedClass(Book2.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }
        
        //Vytvorenie triedy ManageBook
        ManageBook mb = new ManageBook(factory);     
        
        //Vytvorenie knihy bez autora vytvori len zaznam v tabulky knihy
        //Rovnako by tomu bolo aj pri vytvoreni autora bez knih
        Book2 b2 = new Book2();
        
        b2.setTitle("kniha bez autora");
        b2.setPrice(10);
        b2.setQty(0);
        
        int bookId = mb.addBook(b2);       
        System.out.println("New Book id = " + bookId);
        System.out.println("#########################");
      
        //Vytvorenie objektu autor
        Author author = new Author();
        //Zmente prosím na svoje meno
        String mojeMeno = "Ján";
        author.setAuthor_name(mojeMeno);
        
        //Mnozina autorov, ktoru nastavime obom kniham
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        
        //Vytvorenie dvoch objektov triedy Book2
        Book2 kniha1 = new Book2("kniha1" + " " + mojeMeno, authors, 10, 10);
        Book2 kniha2 = new Book2("kniha2" + " " + mojeMeno, authors, 5, 5);
        
        //Mnozina knih, ktoru nastavime autorovi
        Set<Book2> books = new HashSet<>();
        books.add(kniha1);
        books.add(kniha2);
        
        //Vlozenie objektu autor do databazy
        //Automaticky sa vlozia aj knihy a preja sa pomocou spojovacej tabulky
        author.setBooks(books);
        
        int newId = mb.addAuthorWithBooks(author);
        System.out.println("New author id: " + newId);
        System.out.println("#########################");
        
        //vypisanie vsetkych knih z databazy
        System.out.println("##########################");
        mb.listBooks();
        System.out.println("##########################");
    }
    
}
