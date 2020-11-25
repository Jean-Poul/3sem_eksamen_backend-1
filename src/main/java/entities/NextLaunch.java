package entities;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "API_Rocket")
@NamedQueries({
    @NamedQuery(name = "NextLaunch.deleteAllRows", query = "DELETE FROM NextLaunch")})
public class NextLaunch implements Serializable {

    private static final long serialVersionUID = 1L;

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
