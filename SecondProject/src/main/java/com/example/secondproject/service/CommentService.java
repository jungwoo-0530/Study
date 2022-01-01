package com.example.secondproject.service;

import com.example.secondproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;



}
