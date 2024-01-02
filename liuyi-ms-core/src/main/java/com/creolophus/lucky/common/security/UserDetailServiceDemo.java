package com.creolophus.lucky.common.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author magicnana
 * @date 2019/5/27 下午5:27
 */
public class UserDetailServiceDemo implements UserDetailsService {

  public static final Map<String, UserDetails> map = new HashMap();

  static {
    List<GrantedAuthority> list1 = new ArrayList<>();
    list1.add(new SimpleGrantedAuthority("ROLE_ole1"));
    list1.add(new SimpleGrantedAuthority("ROLE_role2"));

    List<GrantedAuthority> list2 = new ArrayList<>();
    list2.add(new SimpleGrantedAuthority("ROLE_role3"));
    list2.add(new SimpleGrantedAuthority("ROLE_role4"));

    String code = new BCryptPasswordEncoder().encode("1");

    User user1 = new User("张无忌", code, list1);
    User user2 = new User("赵敏", code, list1);

    User user3 = new User("白景琦", code, list2);
    User user4 = new User("黄春", code, list2);

    map.put(user1.getUsername(), user1);
    map.put(user2.getUsername(), user2);
    map.put(user3.getUsername(), user3);
    map.put(user4.getUsername(), user4);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<GrantedAuthority> list2 = new ArrayList<>();
    list2.add(new SimpleGrantedAuthority("ROLE_role3"));
    list2.add(new SimpleGrantedAuthority("ROLE_role4"));
    String code = new BCryptPasswordEncoder().encode("1");
    return new User(username, code, list2);
  }
}
