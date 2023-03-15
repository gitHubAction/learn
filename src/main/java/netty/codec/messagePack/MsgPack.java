package netty.codec.messagePack;

import com.google.common.collect.Lists;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/8 10:50
 */
public class MsgPack {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = Lists.newArrayList("欧文", "东契奇", "詹姆斯", "David");
        MessagePack pack = new MessagePack();
        byte[] bytes = pack.write(list);
        List<String> deseri = pack.read(bytes, Templates.tList(Templates.TString));
        deseri.forEach(System.out::println);
    }
}
