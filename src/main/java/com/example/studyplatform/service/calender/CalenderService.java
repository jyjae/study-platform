package com.example.studyplatform.service.calender;

import com.example.studyplatform.domain.attend.Attend;
import com.example.studyplatform.domain.attend.AttendRepository;
import com.example.studyplatform.domain.calender.Calender;
import com.example.studyplatform.domain.calender.CalenderRepository;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.calender.CalenderResponse;
import com.example.studyplatform.dto.calender.PostCalenderRequest;
import com.example.studyplatform.dto.calender.PutCalenderRequest;
import com.example.studyplatform.dto.calender.SimpleCalenderResponse;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.CalenderNotFoundException;
import com.example.studyplatform.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalenderService {

    private final CalenderRepository calenderRepository;
    private final StudyRepository studyRepository;
    private final AttendRepository attendRepository;

    public List<SimpleCalenderResponse> list(Long studyId){
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        List<Calender> calenders = calenderRepository.findByStudy_Id(study.getId());

        return calenders.stream().map(calender -> calender.simpleResult()).collect(Collectors.toList());
    }

    public CalenderResponse get(Long calenderId){
        Calender calender = calenderRepository.findById(calenderId).orElseThrow(CalenderNotFoundException::new);

        // 참석자 ID 가져오기
        List<Attend> attends = attendRepository.findByCalenderId(calender.getId());
        Set<Long> userIds = attends.stream().map(i -> i.getUserId()).collect(Collectors.toSet());

        return calender.result(userIds);
    }

    @Transactional
    public CalenderResponse create(Long studyId, User user, PostCalenderRequest req) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        // 1.일정 생성
        Calender calender = Calender.builder()
                            .calenderTitle(req.getCalenderTitle())
                            .calenderContents(req.getCalenderContents())
                            .study(study)
                            .userId(user.getId())
                            .startDate(req.getStartDate())
                            .endDate(req.getEndDate())
                            .startTime(req.getStartTime())
                            .endTime(req.getEndTime())
                            .isAlarm(req.isAlarm())
                            .isOnline(req.isOnline())
                            .build();

        Calender savedCalender = calenderRepository.save(calender);

        // 2.참석자 테이블 생성
        if (!req.getAttends().isEmpty()) {
            for (Long userId : req.getAttends()) {
                Attend attend = Attend.builder().calenderId(savedCalender.getId()).userId(userId).build();
                attendRepository.save(attend);
            }
        }

        return savedCalender.result(req.getAttends());
    }

    @Transactional
    public CalenderResponse update(Long calenderId, User user, PutCalenderRequest req) {
        //1. 일정 조회
        Calender calender = calenderRepository.findById(calenderId).orElseThrow(CalenderNotFoundException::new);

        //2. 일정 아이디로 참석자 조회
        List<Attend> attends = attendRepository.findByCalenderId(calenderId);

        //3-1. 기존 참석자 모두 삭제 후 다시 추가
        if (!attends.isEmpty()) {
            attendRepository.deleteAll(attends);

            for (Long userId : req.getAttends()) {
                Attend attend = Attend.builder().calenderId(calender.getId()).userId(userId).build();
                attendRepository.save(attend);
            }
        //3-2. 없을 경우 새로 추가
        } else {
            for (Long userId : req.getAttends()) {
                Attend attend = Attend.builder().calenderId(calender.getId()).userId(userId).build();
                attendRepository.save(attend);
            }
        }
        return calender.update(req);
    }

    @Transactional
    public Response delete(Long calenderId) {
        Calender calender = calenderRepository.findById(calenderId).orElseThrow(CalenderNotFoundException::new);
        calender.inActive();
        return Response.success();
    }
}
