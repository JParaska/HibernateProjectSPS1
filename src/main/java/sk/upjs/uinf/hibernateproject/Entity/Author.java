/*
 */
package sk.upjs.uinf.hibernateproject.Entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Jan
 */
@Entity
@Table(name = "authors")
public class Author implements Serializable{
    
    @Id //tato premenna je identifikator
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identifikator si databaza generuje sama
    @Column(name = "id") //mapovanie premennej k stlpcu
    private int id;
    
    @Column(name = "author_name") //mapovanie premennej k stlpcu
    private String author_name;
        
    //obojsmerny vztah, ktory zabezpecuje spojovacia tabulka book_authors
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors",
            joinColumns = { @JoinColumn(name = "id_author") }, 
            inverseJoinColumns = { @JoinColumn(name = "id_book") })
    private Set<Book2> books;
    
    public Author () {}

    public Author(String author_name, Set<Book2> books) {
        this.author_name = author_name;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Set<Book2> getBooks() {
        return books;
    }

    public void setBooks(Set<Book2> books) {
        this.books = books;
    }
    
    
}
