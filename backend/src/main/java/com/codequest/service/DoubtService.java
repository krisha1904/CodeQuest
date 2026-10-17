package com.codequest.service;

import com.codequest.dto.SupportDto;
import com.codequest.entity.Doubt;
import com.codequest.entity.DoubtReply;
import com.codequest.entity.Lesson;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.DoubtRepository;
import com.codequest.repository.LessonRepository;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoubtService {

    @Autowired
    private DoubtRepository doubtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Doubt createDoubt(SupportDto.CreateDoubtDto dto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Lesson lesson = null;
        if (dto.getLessonId() != null) {
            lesson = lessonRepository.findById(dto.getLessonId()).orElse(null);
        }

        Doubt doubt = new Doubt(user, lesson, dto.getTopic(), dto.getTitle(), dto.getDescription());
        return doubtRepository.save(doubt);
    }

    @Transactional
    public Doubt replyToDoubt(Long doubtId, String replyText, String username, boolean isAdmin) {
        Doubt doubt = doubtRepository.findById(doubtId)
                .orElseThrow(() -> new ResourceNotFoundException("Doubt ticket not found"));
        User author = userRepository.findByUsername(username).orElseThrow();

        DoubtReply reply = new DoubtReply(doubt, author, replyText, isAdmin);
        doubt.getReplies().add(reply);

        if (isAdmin) {
            doubt.setStatus("ANSWERED");
        }
        return doubtRepository.save(doubt);
    }

    @Transactional(readOnly = true)
    public List<Doubt> getDoubtsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return doubtRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    @Transactional(readOnly = true)
    public List<Doubt> getAllDoubts() {
        return doubtRepository.findAllByOrderByCreatedAtDesc();
    }
}
