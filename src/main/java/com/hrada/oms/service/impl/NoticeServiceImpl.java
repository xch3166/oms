package com.hrada.oms.service.impl;

import com.hrada.oms.dao.model.NoticeRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.model.Notice;
import com.hrada.oms.model.model.Personal;
import com.hrada.oms.service.NoticeService;
import com.hrada.oms.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shin on 2018/12/19.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    MessageUtil messageUtil;

    @Autowired
    PersonalRepository personalRepository;

    @Override
    public void sendMessage(Long id) {
        Notice notice = noticeRepository.findOne(id);
        List<Personal> list = personalRepository.findAll();
        String title = notice.getTitle();
        String content = "<a href='http://sys.hrada.net/notice/"+notice.getId()+"'>"+notice.getTitle()+"</a>";
        for(Personal personal:list){
            String email = personal.getEmail();
            messageUtil.send(email, title, content);
        }
    }
}
