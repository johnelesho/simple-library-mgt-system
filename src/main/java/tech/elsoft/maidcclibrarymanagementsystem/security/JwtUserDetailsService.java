package tech.elsoft.maidcclibrarymanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.LibrarianRepository;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

   final LibrarianRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Librarian librarian = repository.findByEmailOrUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException("User not found with username: " + username));

        return librarian;
        }
    }

