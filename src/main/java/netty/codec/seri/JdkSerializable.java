package netty.codec.seri;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/8 9:57
 */
public class JdkSerializable {

    public static void main(String[] args) throws IOException {
        Ivring ins = new Ivring();
        ins.setName("欧文");
        ins.setAge(30);
        ins.setHobby("篮球、橄榄球、棒球");
        // jdk序列化大小
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(ins);
        oos.flush();
        oos.close();
        byte[] jdkBytes = bos.toByteArray();
        System.out.println("jdk seri length is:" + jdkBytes.length);
        bos.close();

        byte[] codec = ins.codec();
        System.out.println("byte array length is:" + codec.length);

        System.out.println("==================");

        System.out.println("jdk:  "+ jdkBytes);
        System.out.println("byte :  "+ codec);
    }

    @Data
    static class Ivring implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;
        private String hobby;

        public byte[] codec(){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byte[] nameByte = this.getName().getBytes();
            int length = nameByte.length;
            byteBuffer.putInt(length);
            byteBuffer.put(nameByte);
            byteBuffer.putInt(this.getAge());
            byte[] hobbyByte = this.getHobby().getBytes();
            int hobbyLen = hobbyByte.length;
            byteBuffer.putInt(hobbyLen);
            byteBuffer.put(hobbyByte);
            byteBuffer.flip();
            nameByte = null;hobbyByte = null;
            byte[] res = new byte[byteBuffer.remaining()];
            byteBuffer.get(res);
            return res;
        }
    }
}
