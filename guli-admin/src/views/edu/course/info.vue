<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          @change="subjectLevelOneChanged"
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
        >
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/file/upload'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover" />
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />
        元
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import courseApi from "@/api/edu/course";
import teacherApi from "@/api/edu/teacher";
import subjectApi from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";

const defaultForm = {
  id: "",
  title: "",
  subjectId: "",
  subjectParentId: "",
  teacherId: "",
  lessonNum: 0,
  description: "",
  cover: "/static/10.jpg",
  price: 0,
};

export default {
  components: { Tinymce },
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseInfo: {
        id: "",
        title: "",
        subjectId: "",
        subjectParentId: "",
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover: "/static/10.jpg",
        price: 0,
      },
      teacherList: [],
      subjectNestedList: [], //一级分类列表
      subSubjectList: [], //二级分类列表
    };
  },
  watch: {
    $route(to, from) {
      console.log("watch $route");
      this.init();
    },
  },
  created() {
    console.log("info created");
    this.init();
  },
  methods: {
    fetchCourseInfoById(id) {
      courseApi.getCourseInfoById(id).then((response) => {
        this.courseInfo = response.data.item;
        // 初始化分类列表
        subjectApi.getNestedTreeList().then((responseSubject) => {
          this.subjectNestedList = responseSubject.data.items;
          for (let i = 0; i < this.subjectNestedList.length; i++) {
            if (
              this.subjectNestedList[i].id === this.courseInfo.subjectParentId
            ) {
              this.subSubjectList = this.subjectNestedList[i].children;
            }
          }
        });
        // 获取讲师列表
        this.initTeacherList();
      });
    },
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.courseInfo.id = id;
        this.fetchCourseInfoById(id);
      } else {
        // 获取讲师列表
        this.initTeacherList();
        this.initSubjectList();
      }
    },
    subjectLevelOneChanged(value) {
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === value) {
          this.subSubjectList = this.subjectNestedList[i].children;
          this.courseInfo.subjectId = "";
        }
      }
    },
    initSubjectList() {
      subjectApi.getNestedTreeList().then((response) => {
        this.subjectNestedList = response.data.items;
      });
    },
    initTeacherList() {
      teacherApi.getList().then((response) => {
        this.teacherList = response.data.items;
      });
    },
    next() {
      console.log("next");
      this.saveBtnDisabled = true;
      if (!this.courseInfo.id) {
        this.saveData();
      } else {
        this.updateData();
      }
    },
    updateData() {
      this.saveBtnDisabled = true;
      courseApi.updateCourseInfoById(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        this.$router.push({
          path: "/edu/course/chapter/" + this.courseInfo.id,
        });
      });
    },
    // 保存
    saveData() {
      courseApi
        .saveCourseInfo(this.courseInfo)
        .then((response) => {
          this.$message({
            type: "success",
            message: "保存成功!",
          });
          this.$router.push({
            path: "/edu/course/chapter/" + response.data.courseId,
          });
        })
        .catch((response) => {
          this.$message({
            type: "error",
            message: response.message,
          });
        });
    },
    handleAvatarSuccess(res, file) {
      console.log(res); // 上传响应
      console.log(URL.createObjectURL(file.raw)); // base64编码
      this.courseInfo.cover = res.data.url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
  },
};
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>