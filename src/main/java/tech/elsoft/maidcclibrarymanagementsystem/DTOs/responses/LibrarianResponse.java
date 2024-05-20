package tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.LibraryUserType;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LibrarianResponse implements Serializable {
    private Long id;


    private String fullName;


    private String email;


    private String username;



    private LibraryUserType libraryUserType;


}
