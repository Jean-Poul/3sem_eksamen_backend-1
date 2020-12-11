package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "API_Rocket")
@NamedQueries({
    @NamedQuery(name = "NextLaunch.deleteAllRows", query = "DELETE FROM NextLaunch")})
public class NextLaunch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Fetch_ID")
    private Long id;

    @Column(name = "Fetch_Time")
    private LocalDateTime fetchTime;

    @Column(name = "JSON_STRING", columnDefinition = "TEXT")
    private String data;

    public NextLaunch() {
    }

    public NextLaunch(String data) {
        this.fetchTime = LocalDateTime.now();
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFetchTime() {
        return fetchTime;
    }

    public String getData() {
        return data;
    }
  
}
