package com.hht.annotationprocessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 编译时注解处理器
 * 1.定义注解
 * 2.编写注解器
 * 2.1
 * java7以后，可以用SupportedAnnotationTypes和SupportedSourceVersion注解替代重写
 * getSupportedAnnotationTypes()与getSupportedSourceVersion()方法
 * 但考虑兼容性，建议重写方法。
 * 3.注册注解处理器
 * 3.1在main目录下，新建resources资源文件夹
 * 3.2在resources里建META-INF/services文件夹
 * 3.3在services里新建文件名 ：javax.annotation.processing.Processor
 * 内容为注解处理器的全类名，如本例内容应该为 : com.hht.annotationprocessor.ClassAnnotationProcessor
 * other method：
 * 3.1导入Google的AutoService库
 * 3.2在注解处理器的类添加注解 @AutoService(Processor.class)
 *
 * 4.在项目中应用注解
 * 4.1依赖本项目
 * 4.2Android项目执行gradle build任务，即可生成相应代码
 *
 */
//待处理的注解类路径
@SupportedAnnotationTypes("com.hht.annotationprocessor.ClassAnnotation")
//表示处理的java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ClassAnnotationProcessor extends AbstractProcessor{

    // 类名的前缀后缀
    public static final String SUFFIX = "AutoGenerate";
    public static final String PREFIX = "My_";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    //做了两件事, 1.解析注解并获取需要的值 2.使用JavaFileObject类生成java代码.
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                // 准备在gradle的控制台打印信息
                Messager messager = processingEnv.getMessager();
                // 打印
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());//注: Printing: tv
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getSimpleName());//注: Printing: tv
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getEnclosingElement().toString());//注: Printing: com.realmo.customannoation.MainActivity

                // 获取注解
                ClassAnnotation annotation = e.getAnnotation(ClassAnnotation.class);

                // 获取元素名并将其首字母大写
                String name = e.getSimpleName().toString();
                char c = Character.toUpperCase(name.charAt(0));
                name = String.valueOf(c+name.substring(1));

                // 包裹注解元素的元素, 也就是其父元素, 比如注解了成员变量或者成员函数, 其上层就是该类
                Element enclosingElement = e.getEnclosingElement();
                // 获取父元素的全类名, 用来生成包名
                String enclosingQualifiedName;
                if(enclosingElement instanceof PackageElement){
                    enclosingQualifiedName = ((PackageElement)enclosingElement).getQualifiedName().toString();
                }else {
                    enclosingQualifiedName = ((TypeElement)enclosingElement).getQualifiedName().toString();
                }
                try {
                    // 生成的包名
                    String genaratePackageName = enclosingQualifiedName.substring(0, enclosingQualifiedName.lastIndexOf('.'));
                    // 生成的类名
                    String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;

                    // 创建Java文件
                    JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
                    // 在控制台输出文件路径
                    messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());//注: Printing: file:/C:/Users/54363/Desktop/code/Demo/customannoation/build/generated/source/apt/debug/My_MainActivityAutoGenerate.java
                    Writer w = f.openWriter();
                    try {
                        PrintWriter pw = new PrintWriter(w);
                        pw.println("package " + genaratePackageName + ";");
                        pw.println("\npublic class " + genarateClassName + " { ");
                        pw.println("\n    /** auto class code */");
                        pw.println("    public static void print" + name + "() {");
                        pw.println("        // effect class: " + enclosingElement.toString());
                        pw.println("        System.out.println(\"code path: "+f.toUri()+"\");");
                        pw.println("        System.out.println(\"annotation element: "+e.toString()+"\");");
                        pw.println("        System.out.println(\"annotation value: "+annotation.value()+"\");");
                        pw.println("    }");
                        pw.println("}");
                        pw.flush();
                    } finally {
                        w.close();
                    }
                } catch (IOException x) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            x.toString());
                }
            }
        }

        //该方法返回ture表示该注解已经被处理, 后续不会再有其他处理器处理; 返回false表示仍可被其他处理器处理.
        return true;
    }

//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotations = new LinkedHashSet<String>();
//        annotations.add(ClassAnnotation.class.getCanonicalName());
//        return annotations;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
}
