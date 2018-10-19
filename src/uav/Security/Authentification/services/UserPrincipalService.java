package uav.Security.Authentification.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uav.Security.Authentification.DataModel.UserPrincipal;
import uav.Security.Authentification.UserInfo;
import uav.Security.Authentification.Repository.UserInfoRepository;

@Service("userPrincipalService")
@Transactional
public class UserPrincipalService implements UserDetailsService {
    static final Logger logger = LoggerFactory.getLogger(UserPrincipalService.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByEmail(ssoId);
        logger.info("User : {}", userInfo);
        if(userInfo == null) {
            logger.info("User haven't registered yet");
            throw new UsernameNotFoundException(ssoId);
        }
        return new UserPrincipal(userInfo);
    }
}
