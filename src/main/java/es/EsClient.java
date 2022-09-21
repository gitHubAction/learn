package es;

import cn.hutool.core.util.RandomUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/9/5 9:53
 */
public class EsClient {

    public static RestHighLevelClient getClient(){
        BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic","1JGlYwxQiwuQLukz"));
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.31.113.18",9200, "http")
                ).setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider))
        );
    }

        public static void main(String[] args) {
                bulkInsert();
        }

    public static void bulkInsert(){
        try(RestHighLevelClient client = getClient();){
            BulkRequest inserReq = new BulkRequest();
            List<String> list = new ArrayList<>();
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2052");
            for(int i = 0; i < list.size();i++){
                inserReq.add(
                        new IndexRequest("room_test_index", "_doc",(i+1)+"")
                        .source(XContentType.JSON,
                                "roomNo", list.get(i),
                                "phoneList",new StringJoiner(
                                        ","
                                ).add("1"+RandomUtil.randomNumbers(10))
                                .add("1"+RandomUtil.randomNumbers(10))
                                .add("1"+RandomUtil.randomNumbers(10))
                                .toString()
                        )
                );
            }

            BulkResponse bulkResponse = client.bulk(inserReq);
            //4、处理响应
            if(bulkResponse != null) {
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                    if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                            || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                        IndexResponse indexResponse = (IndexResponse) itemResponse;
                        System.out.println(indexResponse);

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                        //TODO 修改成功的处理
                        System.out.println(updateResponse);

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                        //TODO 删除成功的处理
                        System.out.println(deleteResponse);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
