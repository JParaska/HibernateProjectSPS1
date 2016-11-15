/*
 */
package sk.upjs.uinf.hibernateproject.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Jan
 */
@Entity
@Table(name = "books")
public class Book2 implements Serializable {

    @Id //tato premenna je identifikator
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identifikator si databaza generuje sama
    @Column(name = "id") //mapovanie premennej k stlpcu
    private int id;

    @Column(name = "title") //mapovanie premennej k stlpcu
    private String title;

    //@ManyToMany(mappedBy = "books") - tato anotacia je pre jednosmerny vztah, kde vlastnik je author
    //anotacia nizsie je pre obojsmerny vztah, ktory zabezpecuje spojovacia tabulka book_authors
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors",
            joinColumns = {
                @JoinColumn(name = "id_book")},
            inverseJoinColumns = {
                @JoinColumn(name = "id_author")})
    private Set<Author> authors = new HashSet<>();    

    @Column(name = "price") //mapovanie premennej k stlpcu
    private float price;

    @Column(name = "qty") //mapovanie premennej k stlpcu
    private int qty;

    public Book2() {

    }

    public Book2(String title, Set authors, float price, int qty) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (authors.size() > 0) {
            for (Author author : authors) {
                sb.append(author.getAuthor_name()).append(" ");
            }
        } else {
            sb.append("NONE").append(" "); //kniha nema prideleneho autora
        }
        return id + ". " + title + ", By " + sb.toString() + ",price: " + price + ",In store: " + qty;
    }
}
