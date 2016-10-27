  1、 <optional>true</optional> maven 用法  
	
	一方法：可以中断依赖传递，在<dependency>中加<optional>true</optional>，
	
	声明只给当前项目使用，其他项目依赖当前项目时，
	
	这个jar不会传递给其他项目。
	
	二方法：使用 <exclusions>排除