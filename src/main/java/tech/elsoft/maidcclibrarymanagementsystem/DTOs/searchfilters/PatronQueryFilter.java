package tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatronQueryFilter extends QueryFilters {

    private String fullName;
    private String email;
    private String username;
    private String streetAddress;
    private String city;
    private String stateOrProvince;
    private String postalCode;
    private String country;

    private boolean activeSubscription;
}
