package com.chen.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.entity.EduSubject;
import com.chen.eduservice.entity.excel.ExcelSubjectData;
import com.chen.eduservice.entity.subject.OneSubject;
import com.chen.eduservice.entity.subject.TwoSubject;
import com.chen.eduservice.listener.SubjectExcelListener;
import com.chen.eduservice.mapper.EduSubjectMapper;
import com.chen.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.servicebase.execption.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file) {
        try{
            //获取文件输入流
            InputStream inputStream = file.getInputStream();

            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(this)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }

    }

    @Override
    public List<OneSubject> nestedList() {

        ArrayList<OneSubject> subjectNestedVoArrayList = new ArrayList<>();

        //查询一级分类
        QueryWrapper<EduSubject> oneWarrper = new QueryWrapper<>();
        oneWarrper.eq("parent_id",0);
        List<EduSubject> oneEduSubjects = baseMapper.selectList(oneWarrper);

        //查询二级分类
        QueryWrapper<EduSubject> twoWarrper = new QueryWrapper<>();
        oneWarrper.ne("parent_id",0);
        List<EduSubject> twoEduSubjects = baseMapper.selectList(twoWarrper);

        //添加一级分类
        for (EduSubject oneEduSubject : oneEduSubjects) {

            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(oneEduSubject, oneSubject);
            subjectNestedVoArrayList.add(oneSubject);
            //添加二级分类
            for (EduSubject twoEduSubject : twoEduSubjects) {
                if(twoEduSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject,twoSubject);
                    oneSubject.getChildren().add(twoSubject);
                }
            }
        }

        return subjectNestedVoArrayList;
    }
}
