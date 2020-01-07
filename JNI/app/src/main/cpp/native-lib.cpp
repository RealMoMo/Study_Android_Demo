#include <jni.h>
#include <string>

#include <android/log.h>

#define TAG "momo"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_realmo_jni_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_realmo_jni_MainActivity_test(JNIEnv *env, jobject thiz, jint number, jstring text,
                                      jintArray int_arrays, jobjectArray str_arrays) {

    // C领域中         JNI领域中          Java领域中
    // int              jint            int
    // const char *    jstring          String

    //操作简单类型
    //================操作int======================
    int my_number = number;
    LOGD("my_number: %d\n ",my_number);

    //====================操作String====================
    // 参数二：第一重意思：是否在内部完成Copy操作，NULL==0 false，  第二重意思：要给他一个值，让内部可以转起来，这个值，随意
    const char* my_text = env->GetStringUTFChars(text,NULL);
    LOGD("my_text: %s\n",my_text);
    // 回收 GetStringUTFChars
    env->ReleaseStringUTFChars(text,my_text);

    //========================int数组===========================
    jint* my_intArray = env->GetIntArrayElements(int_arrays,NULL);
    jsize intSize = env->GetArrayLength(int_arrays);

    for (int i = 0; i < intSize; ++i) {
        //打印数值
        LOGD("int array index[%d]->value:%d",i,*(my_intArray+i));
        //修改数组值 对应java的调用方的数组值也会相应变化(重要：但必须要进行下面回收操作，否则java数组值不会改变的。)
        *(my_intArray+i)+= 100;
    }

    // 回收
    env->ReleaseIntArrayElements(int_arrays, my_intArray, 0); // 0代表要刷新--》java的调用方的数组值刷新

    //===========================string数组===========================
    //打印String数组(同理相当于引用类型的数组)
    jint  strSize = env->GetArrayLength(str_arrays);

    for (int i = 0; i < strSize; ++i) {
        jobject jobject1 = env->GetObjectArrayElement(str_arrays,i);
        jstring  jstring1 = static_cast<jstring >(jobject1);
        const char * my_string = env->GetStringUTFChars(jstring1,NULL);
        //打印数值
        LOGD("str array index[%d]->value:%s",i,my_string);

        env->ReleaseStringUTFChars(jstring1,my_string);
    }

}


extern "C"
JNIEXPORT void JNICALL
Java_com_realmo_jni_MainActivity_putStudent(JNIEnv *env, jobject thiz, jobject student) {
    //=============================操作引用类型=========================

    // C领域中         JNI领域中          Java领域中
    //                jclass            class
    //                jmethodID         Method


    //1.获取字节码
    //student class 路径
    const char  * student_class_path = "com/realmo/jni/Student";
    jclass studentClass = env->FindClass(student_class_path);

    //2.获取方法签名
    //获取方法签名的命令：javap -s +fullclassName(在生成build/intermediates/javac/的class文件目录下 输入此命令)
    const char * sig = "(Ljava/lang/String;)V";
    jmethodID  setName = env->GetMethodID(studentClass,"setName",sig);

    sig = "(I)V";
    jmethodID setAge = env->GetMethodID(studentClass, "setAge", sig);

    sig = "()V";
    //静态方法
    jmethodID myStaticMethod = env->GetStaticMethodID(studentClass, "myStaticMethod", sig);

    //3.调用方法
    jstring my_name = env->NewStringUTF("haha");
    env->CallVoidMethod(student,setName,my_name);

    env->CallVoidMethod(student,setAge,88);

    env->CallStaticVoidMethod(studentClass,myStaticMethod);

    //回收class
    env->DeleteLocalRef(studentClass);

    //回收对象
    env->DeleteLocalRef(student);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_realmo_jni_MainActivity_testObject(JNIEnv *env, jobject thiz) {

    const char * person_class_str = "com/realmo/jni/Person";
    jclass person_class = env->FindClass(person_class_str);

    //AllocObject通过class直接实例化对象,不会调用到构造方法。（还有另一种NewObject通过构造方法进行实例化）
    jobject person = env->AllocObject(person_class);

    // 初始化方法
    const char * sig = "(Lcom/realmo/jni/Student;)V";
    jmethodID setStudent = env->GetMethodID(person_class, "setStudent", sig);

    // 直接创建 Student 对象
    const char * student_class_str = "com/realmo/jni/Student";
    jclass student_class = env->FindClass(student_class_str);
    jobject student = env->AllocObject(student_class);
    // 给我们创建出来的Student对象赋值
    sig = "(Ljava/lang/String;)V";
    jmethodID setName = env->GetMethodID(student_class, "setName", sig);
    jstring value = env->NewStringUTF("xiaomomo");
    env->CallVoidMethod(student, setName, value);
    sig = "(I)V";
    jmethodID setAge = env->GetMethodID(student_class, "setAge", sig);
    env->CallVoidMethod(student, setAge, 1);

    // 调用Person里面的方法
    env->CallVoidMethod(person, setStudent, student);

    // 回收方式
    // jclass  jobject
    env->DeleteLocalRef(student_class);
    env->DeleteLocalRef(student);

}

//============================引用类型 + Java构造方法的实例化====================================

jclass dogClass;

extern "C"
JNIEXPORT void JNICALL
Java_com_realmo_jni_MainActivity_createObject(JNIEnv *env, jobject thiz) {
    // 局部引用：如果在函数里面，是在栈区，不用回收，函数结束，会自动回收 ，为了专业性，最好要写回收

    if (dogClass == NULL) { // 第一次调用该方法满足，  第二次调用该方法不满足了
        // 局部引用的方式
        /*const char * dog_class_str = "com/kevin/ndk09_code/Dog";
        dogClass = env->FindClass(dog_class_str);*/

        // 解决各个局部引用带来的问题，全局引用（自己来提升）
        const char * dog_class_str = "com/realmo/jni/Dog";
        jclass temp = env->FindClass(dog_class_str);
        dogClass = static_cast<jclass>(env->NewGlobalRef(temp));

        LOGD("%s\n","dogClass == NULL");
    }

    // Java构造方法的实例化
    const char * sig = "()V";
    const char * method = "<init>"; // Java构造方法的标识
    jmethodID init = env->GetMethodID(dogClass, method, sig);
    env->NewObject(dogClass, init);

    //TODO: 注意 dogClass不做特殊处理，会隐式释放 dogClass  ， dogClass不为NULL,但成为悬空指针。   同理手动全局变量释放也一致
}


// 此函数就是为了 手动释放全局
extern "C"
JNIEXPORT void JNICALL
Java_com_realmo_jni_MainActivity_releaseObject(JNIEnv *env, jobject thiz) {
    // TODO: implement releaseObject()

    if(dogClass != NULL) {
        LOGD("%s\n","全局应用被释放了");
        env->DeleteGlobalRef(dogClass);
        dogClass = NULL;
    }

}


//======================动态注册==============================
JavaVM * jvm;

//需动态注册的函数register01 register02
void register01(JNIEnv * env, jobject instance, jstring text) {
    const char * textValue = env->GetStringUTFChars(text, NULL);
    LOGD("动态注册的函数执行了%s",textValue);
    env->ReleaseStringUTFChars(text, textValue);
}

void register02(JNIEnv * env, jobject instance, jint number) {
    int my_number = number;
    LOGD("%s  %d\n","动态注册的函数执行了",my_number);


}

//源码JNINativeMethod的结构体
/*
 * typedef struct {
    const char* name;
    const char* signature;
    void*       fnPtr;
    } JNINativeMethod;
 */

static const JNINativeMethod jniNativeMethod[] = {
        {"registerJava01", "(Ljava/lang/String;)V", (void *)(register01)},
        {"registerJava02", "(I)V", (int *)(register02)}
};


//固定函数名JNI_OnLoad
// java lodlibrary时，会触发JNI_OnLoad函数。
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM * javaVm, void * pVoid) {
    jvm = javaVm;

    // 通过虚拟机 创建全新的 evn
    JNIEnv * jniEnv = nullptr;
    jint result = javaVm->GetEnv(reinterpret_cast<void **>(&jniEnv), JNI_VERSION_1_6); // 参数2：是JNI的版本 NDK 1.6   JavaJni 1.8
    if (result != JNI_OK) {
        return -1; // 主动报错
    }

    const char * mainActivityClassStr = "com/realmo/jni/MainActivity";
    jclass mainActivityClass = jniEnv->FindClass(mainActivityClassStr);

    jniEnv->RegisterNatives(mainActivityClass, jniNativeMethod, sizeof(jniNativeMethod) / sizeof(JNINativeMethod)); // 参数三：到底要动态注册几个函数

    return JNI_VERSION_1_6;
}




