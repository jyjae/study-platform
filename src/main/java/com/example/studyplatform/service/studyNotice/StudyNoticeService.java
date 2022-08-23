package com.example.studyplatform.service.studyNotice;

import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.studyNotice.StudyNoticeRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.alarm.AlarmRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyNotice.PostStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.PutStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.StudyNoticeResponse;
import com.example.studyplatform.exception.StudyNotFoundException;
import com.example.studyplatform.exception.StudyNoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StudyNoticeService {

    private final StudyRepository studyRepository;
    private final StudyNoticeRepository studyNoticeRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final AlarmRepository alarmRepository;

    public List<StudyNoticeResponse> list(Long studyId) {
        List<StudyNotice> studyNotices = studyNoticeRepository.findAllByStudyId(studyId);

        return studyNotices.stream().map(i -> i.result()).collect(Collectors.toList());
    }

    @Transactional
    public StudyNoticeResponse create(Long studyId, User user, PostStudyNoticeRequest req) {

        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        StudyNotice studyNotice = studyNoticeRepository.save(req.toEntity(study, user));

        return studyNotice.result();
    }

    @Transactional
    public void sendStudyNoticeAlarm(Long studyId, User user) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        String topic = channelTopic.getTopic();
        String title = user.getNickname() + "님이 " + study.getStudyName() + "에 공지를 남겼습니다.";

        study.getStudyUsers().forEach(u -> {
            Alarm alarm = alarmRepository.save(Alarm.builder()
                    .title(title)
                    .url("studyURL")
                    .user(u.getUser())
                    .build());

            redisTemplate.convertAndSend(topic, AlarmRequest.toDto(alarm, u.getUser().getId()));
        });
    }

    @Transactional
    public StudyNoticeResponse update(Long studyNoticeId, PutStudyNoticeRequest req, User user) {
        StudyNotice studyNotice = getStudyNoticeByIdAndUser(studyNoticeId, user);
        return studyNotice.update(req);
    }

    @Transactional
    public Response delete(Long studyNoticeId, User user) {
        StudyNotice studyNotice = getStudyNoticeByIdAndUser(studyNoticeId, user);
        studyNoticeRepository.delete(studyNotice);
        return Response.success();
    }

    private StudyNotice getStudyNoticeByIdAndUser(Long studyNoticeId, User user) {
        return studyNoticeRepository.findByIdAndUserId(studyNoticeId, user.getId())
                .orElseThrow(StudyNoticeNotFoundException::new);
    }

}
