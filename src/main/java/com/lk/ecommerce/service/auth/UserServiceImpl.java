package com.lk.ecommerce.service.auth;

import com.lk.ecommerce.dto.core.UserDTO;
import com.lk.ecommerce.entity.Orders;
import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.eums.OrderStatus;
import com.lk.ecommerce.eums.UserRoles;
import com.lk.ecommerce.repo.OrderRepository;
import com.lk.ecommerce.repo.UserRepository;
import com.lk.ecommerce.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final OrderRepository orderRepository;

    public UserServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }



    @Override
    public int saveAdmin(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            //default role is DASH_ADMIN - because profile create against company
            userDTO.setRole(UserRoles.ADMIN.name());
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }
    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            //default role is DASH_ADMIN - because profile create against company
            userDTO.setRole(UserRoles.USER.name());
            User user = userRepository.save(modelMapper.map(userDTO, User.class));

            Orders orders = new Orders();
            orders.setAmount(0L);
            orders.setTotalAmount(0L);
            orders.setDiscount(0L);
            orders.setUser(user);
            orders.setOrderStatus(OrderStatus.PENDING);
            orderRepository.save(orders);
            return VarList.Created;
        }
    }

  //  @PostConstruct
    public  void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRoles.ADMIN);
        if(adminAccount==null){
            User user =new User();
            user.setName("Admin");
            user.setRole(UserRoles.ADMIN);
            user.setEmail("Admin123@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("1234"));
            userRepository.save(user);
        }
    }
}
