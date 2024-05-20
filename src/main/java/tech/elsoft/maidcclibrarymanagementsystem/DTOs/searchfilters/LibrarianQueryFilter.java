package tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters;

import lombok.*;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.LibraryUserType;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibrarianQueryFilter extends QueryFilters {

    private String username;
    private String email;
    private String fullName;
    private LibraryUserType libraryUserType;
}
