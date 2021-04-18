https://dzone.com/articles/creating-external-dsls-using

演示怎么使用antlr来构建DSL解析器，编译方法
1. 配置IDEA的IDEA插件。
2. Graph.g4, 右键->Configure ANTLR，配置由g4文件产生的
java文件和包名。
3. Graph.g4, 右键->generate parser。生成java文件，现在可以
在自己的java文件中引用他们。
4. 实现MyGraphBaseListener，主要是实现规约的语义。
