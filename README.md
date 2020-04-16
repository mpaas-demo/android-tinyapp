# mPaaS TinyApp Demo

## 介绍

本代码用于指导如何集成mPaaS小程序组件，详细文档请参考[官网](https://help.aliyun.com/document_detail/67444.html?spm=a2c4g.11186623.6.1098.5667597ekEzGsA)。


## 编译

本代码出于发布便捷性考虑，将inside和aar集成方式合并在一个工程中，您可通过修改配置来编译不同的目标类型。

### Inside编译
1. gradle.properties里mPaasBuildType设置为inside

### AAR编译
1. gradle.properties里mPaasBuildType设置为aar

使用命令行编译, ./gradlew clean installDebug
