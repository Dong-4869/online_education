package com.chen.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.entity.EduSubject;
import com.chen.eduservice.entity.excel.ExcelSubjectData;
import com.chen.eduservice.service.EduSubjectService;
import com.chen.servicebase.execption.GuliException;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
        //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
        //一行一行去读取excle内容
    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new GuliException(20001,"添加失败");
        }
        //添加一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if(existOneSubject == null) {//没有相同的
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }
        //获取一级分类id值
        String pid = existOneSubject.getId();
        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

    //判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
}
