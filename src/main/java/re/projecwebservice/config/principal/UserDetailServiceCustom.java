package re.projecwebservice.config.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import re.projecwebservice.entity.Users;
import re.projecwebservice.respository.UserRespository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceCustom implements UserDetailsService {
    private final UserRespository userRespository;


    // logic tải thông tin người dùng dựa trên user name
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       User user = userRespository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        Users user = userRespository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        // biến user thành UserDetail thì security mới có thể hiểu được
        // vì role la string nên cần biến đổi nó sang authority

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            // biến đổi rồi thêm vào llist
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            UserDetailCostum userDetailCostum = UserDetailCostum.builder()
                    .username(username)
                    .password(user.getPasswordHash())
                    .authorities(grantedAuthorities)
                    .build();
            return userDetailCostum;

    }
}
