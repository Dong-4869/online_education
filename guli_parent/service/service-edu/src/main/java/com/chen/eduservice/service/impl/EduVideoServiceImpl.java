package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.R;
import com.chen.eduservice.client.VodClient;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.mapper.EduVideoMapper;
import com.chen.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        //根据课程id查询所有视频列表
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);
        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }
        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.removeVideoList(videoSourceIdList);
        }

        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper2);
        return null != count && count > 0;
    }

    @Override
    public R removeVideoById(String id) {
        //查询云端视频id
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeVideo(videoSourceId);
            if(r.getCode() == 20001){
                return r;
            }
        }
        Integer result = baseMapper.deleteById(id);
        if (null != result && result > 0){
            return R.ok();
        }
        return R.error();
    }


}
