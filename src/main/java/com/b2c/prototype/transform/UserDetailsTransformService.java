package com.b2c.prototype.transform;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.user.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsTransformService implements IUserDetailsTransformService {

    @Override
    public UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto) {
        return null;
    }

    @Override
    public UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto) {
        return null;
    }

    @Override
    public ResponseUserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails) {
        return null;
    }
}
