package ca.uqam.inf6150.ete2021.gr010.flight;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRLINE")
public final class Airline
        implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "AIRLINENO")
    private long m_id;

    @NonNull
    @Column(name = "NAME")
    private String m_name;
}
