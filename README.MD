# GoodAt AOP 注解库 公用库 

>  goodAt == good@  ==  good annotation

## 可自行选取部分代码 拷贝至项目


## 引入

###  项目根目录 build.gradle


```
buildscript{
    ...
    dependencies{
        ...
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
    }
}

```

###  模块build.gradle 

```
apply plugin: 'android-aspectjx'

```

```
aspectjx {
    include 'com.模块名'
    include 'goodat.weaving'
}
```



```
dependencies{
   ...
   implementation 'com.luckyaf:goodat:0.3.0' 
}
```











