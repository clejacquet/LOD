package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.mediaselector.model.RDFModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Clement on 06/05/2015.
 */
@Entity
@Table(name = "user")
public class User implements RDFModel {
    @Id
    @GeneratedValue
    private long id;

    @Size(min=3, max = 30)
    @Column(nullable = false,unique = true)
    private String login;

    @Size(min=4)
    @Column(nullable = false)
    private String password;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {

    }
}
