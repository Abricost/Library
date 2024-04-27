package com.library.Library.service;

import com.library.Library.dto.UserDto;
import com.library.Library.mapper.UserMapper;
import com.library.Library.model.User;
import com.library.Library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapstructMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDto> listAll(final Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserDto> result = mapstructMapper.toDTOList(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }
}
