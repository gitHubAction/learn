package agent.bytebuddy;

import agent.bytebuddy.intercepter.ConstructorInterceptor;
import agent.bytebuddy.intercepter.IntercepterMorph;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.LoadedTypeInitializer;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Morph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/3 16:26
 */
public class ByteBuddyTest {

    public static void main(String[] args) {
        ByteBuddyTest byteBuddyTest = new ByteBuddyTest();
        byteBuddyTest.interceptConstruct();
    }
    public static class DelegeteFoo {

        public DelegeteFoo(){}
        public DelegeteFoo(String name){
            System.out.println("DelegeteFoo "+ name);
        }
        public String hello(String name) {
            System.out.println("DelegeteFoo:" + name);
            return name;
        }
    }


    public void interceptConstruct(){
        DynamicType.Unloaded<DelegeteFoo> delegeteFooConstruct = new ByteBuddy().subclass(DelegeteFoo.class)
                .name("DelegeteFooConstruct")
                .constructor(any())
                .intercept(
                        SuperMethodCall.INSTANCE.andThen(
                                MethodDelegation.withDefaultConfiguration().to(new ConstructorInterceptor())
                        )
                )
                .make();
        DynamicType.Loaded<DelegeteFoo> load = delegeteFooConstruct.load(getClass().getClassLoader(),INJECTION);
        Class<? extends DelegeteFoo> loaded = load.getLoaded();
        // 反射调用
        try {

            Constructor<?> constructor = loaded.getConstructor(String.class);
            DelegeteFoo foo = (DelegeteFoo) constructor.newInstance("name from 疯狂创客圈");
            System.out.println(foo.hello("hello form 疯狂创客圈"));

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        outputClazz(delegeteFooConstruct.getBytes(),"agent/bytebuddy/DelegeteFooConstruct");
    }


    public void defineClass(){
        DynamicType.Unloaded<Object> make = new ByteBuddy()
                .subclass(Object.class)
                .name("Foo")
                .defineMethod("hello", String.class, Modifier.PUBLIC)
                .withParameter(String.class, "name")
                .intercept(MethodDelegation.to(new DelegeteFoo()))
                .make();
        DynamicType.Loaded<Object> load = make.load(getClass().getClassLoader());
        Class<?> loaded = load.getLoaded();
        try {
            Object hello = loaded.getMethod("hello", String.class).invoke(loaded.newInstance(), "111111");
            System.out.println(hello);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        // 输出类字节码
        outputClazz(make.getBytes(),"agent/bytebuddy/Foo");
    }

    public void morph(){
        DynamicType.Unloaded<DelegeteFoo> morph = new ByteBuddy()
                .subclass(DelegeteFoo.class)
                .name("DelegeteFooChild")
                .method(named("hello"))
                .intercept(MethodDelegation.withDefaultConfiguration().withBinders(
                        Morph.Binder.install(OverwriteCallable.class)
                ).to(new IntercepterMorph()))
                .make();
        DynamicType.Loaded<DelegeteFoo> load = morph.load(getClass().getClassLoader());
        Class<? extends DelegeteFoo> loaded = load.getLoaded();
        try {
            Object hello = loaded.getMethod("hello", String.class).invoke(loaded.newInstance(), "1231231");
            System.out.println(hello);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        // 输出类字节码
        outputClazz(morph.getBytes(),"agent/bytebuddy/DelegeteFooChild");
    }


    public void redefine(){
        DynamicType.Unloaded<DelegeteFoo> make = new ByteBuddy().redefine(DelegeteFoo.class)
                .name("DelegeteFoo")
                .method(named("hello"))
                .intercept(FixedValue.value("hahhahahahah"))
                .defineField("TARGET_INTERCEPTOR",String.class, Modifier.PUBLIC)
                .make();
        DynamicType.Loaded<DelegeteFoo> type = make.load(getClass().getClassLoader());
        // 输出类字节码
        outputClazz(make.getBytes(),"agent/bytebuddy/DelegeteFoo");
    }


    public void subclass() {
        DynamicType.Unloaded<DelegeteFoo> hello = new ByteBuddy().subclass(DelegeteFoo.class)
                .name("agent.bytebuddy.Foo2")
                .method(named("hello"))
                .intercept(MethodDelegation.to(new Interceptor()))
                .make();
        DynamicType.Loaded<DelegeteFoo> type = hello.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER);
        // 输出类字节码
        outputClazz(hello.getBytes(),"agent/bytebuddy/Foo2");
//加载类
        Class<?> clazz = type.getLoaded();
// 反射调用
        try {
            String bar = (String)
                    clazz.getMethod("hello",String.class).invoke(clazz.newInstance(),"bar-from疯狂创客圈");
            System.out.println(bar);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


    private static void outputClazz(byte[] bytes,String clazzName) {
        FileOutputStream out = null;
        try {
            String pathName = ByteBuddyTest.class.getResource("/").getPath() +
                    clazzName+".class";
            File file = new File(pathName);
            if(file.exists()){
                System.out.println(clazzName+"已存在");
                return;
            }
            out = new FileOutputStream(file);
            System.out.println("类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
