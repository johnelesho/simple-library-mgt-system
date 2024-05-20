package tech.elsoft.maidcclibrarymanagementsystem.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lms_patron_tbl")
public class Patron extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patron_seq")
    @SequenceGenerator(name = "patron_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    private String fullName;
    private String email;
    private String username;
    private String streetAddress;
    private String city;
    private String stateOrProvince;
    private String postalCode;
    private String country;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activeSubscription= true;


}