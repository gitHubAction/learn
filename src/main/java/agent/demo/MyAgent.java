package agent.demo;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.dynamic.scaffold.inline.MethodNameTransformer;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/6 16:41
 */
public class MyAgent{

    public static void premain(String agentArgs, Instrumentation instrumentation){
        System.out.println("myAgent - premain");
        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(false));
        new AgentBuilder.Default(byteBuddy)
                .type(nameStartsWith("agent.demo"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                        // 进行方法过滤
                        if(typeDescription.getPackage().getActualName().equals("agent.demo")
                            && typeDescription.getName().equals("agent.demo.HelloWordAgent")
                        ){
                            return builder.method(named("hello").and(isPublic()))
                                    .intercept(MethodDelegation.to(MethodCostTime.class));
                        }
                        return builder;
                    }
                })
                .with(new Listener())
                .installOn(instrumentation);
    }

    private static class Listener implements AgentBuilder.Listener {

        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {

        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {

        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }
    }
}
