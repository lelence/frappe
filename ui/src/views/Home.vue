<template>
  <el-row type="flex">
    <el-col :span="6">
      <div class="grid-content bg-purple"></div>
    </el-col>
    <el-col :span="6">
      <div class="grid-content bg-purple-light"></div>
    </el-col>
    <el-col :span="6">
      <div class="grid-content bg-purple"></div>
    </el-col>
  </el-row>
</template>
<script>
import Vue from "vue";
import { Row, Col } from "element-ui";

Vue.use(Row).use(Col);

export default {
  name: "home"
};
</script>
