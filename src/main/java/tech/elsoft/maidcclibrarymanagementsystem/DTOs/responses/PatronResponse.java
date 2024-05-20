package tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatronResponse implements Serializable {
    private Long id;

    private String fullName;
    private String email;
    private String streetAddress;
    private String city;
    private String stateOrProvince;
    private String postalCode;
    private String country;
    private boolean activeSubscription;
}
