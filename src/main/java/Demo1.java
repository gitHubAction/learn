
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
/**
 * @Author: zhangsh
 * @Date: 2020/2/26 11:02
 * @Version 1.0
 * Description
 */
public class Demo1 {
    /**
     * 获取商品基本信息
     *
     * @param goodsId 商品id
     * @return 商品基本信息
     * @throws InterruptedException
     */
    public String goodsDetailModel(long goodsId){
        //模拟耗时，休眠200ms
        return "商品id:" + goodsId + ",商品基本信息....";
    }

    /**
     * 获取商品图片列表
     *
     * @param goodsId 商品id
     * @return 商品图片列表
     * @throws InterruptedException
     */
    public List<String> goodsImgsModelList(long goodsId) throws InterruptedException {
        //模拟耗时，休眠200ms
        TimeUnit.MILLISECONDS.sleep(200);
        return Arrays.asList("图1", "图2", "图3");
    }

    /**
     * 获取商品描述信息
     *
     * @param goodsId 商品id
     * @return 商品描述信息
     * @throws InterruptedException
     */
    public String goodsExtModel(long goodsId) throws InterruptedException {
        //模拟耗时，休眠200ms
        TimeUnit.MILLISECONDS.sleep(200);
        return "商品id:" + goodsId + ",商品描述信息......";
    }

    //创建个线程池
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 获取商品详情
     *
     * @param goodsId 商品id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<String, Object> goodsDetail(long goodsId) throws ExecutionException, InterruptedException {
        Map<String, Object> result = new HashMap<>();

        //异步获取商品基本信息
        Future<String> gooldsDetailModelFuture = (Future<String>) executorService.submit(() -> {
            return goodsDetailModel(goodsId);
        });
        //异步获取商品图片列表
        Future<List<String>> goodsImgsModelListFuture = executorService.submit(() -> goodsImgsModelList(goodsId));
        //异步获取商品描述信息
        Future<String> goodsExtModelFuture = executorService.submit(() -> goodsExtModel(goodsId));

        result.put("gooldsDetailModel", gooldsDetailModelFuture.get());
        result.put("goodsImgsModelList", goodsImgsModelListFuture.get());
        result.put("goodsExtModel", goodsExtModelFuture.get());
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        long starTime = System.currentTimeMillis();
//        Map<String, Object> map = new Demo1().goodsDetail(1L);
//        System.out.println(map);
//        System.out.println("耗时(ms):" + (System.currentTimeMillis() - starTime));
        System.out.println(1<<1);

        int a = 2;
        param(a);
        System.out.println(a);
    }


    static void param(int a){
        int b = a++;
        System.out.println(b+"-------a="+a);
    }
}
