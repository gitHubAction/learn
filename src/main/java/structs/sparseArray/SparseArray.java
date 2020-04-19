package structs.sparseArray;

/**
 * @Author: zhangsh
 * @Date: 2020/4/17 14:03
 * @Version 1.0
 * Description  稀疏数组
 */
public class SparseArray {


    public static void main(String[] args) {
        int[][] cheesArr = new int[11][11];

        cheesArr[1][2] = 1;
        cheesArr[2][3] = 2;
        cheesArr[3][4] = 1;
        for (int[] row : cheesArr) {
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        //=======================================================
        System.out.println("稀疏数组===============");
        /**
         * 稀疏数组是一个n+1行（n为原始数组的有用数据个数），3列的二位数组，除第一行外，前两列为原始数组坐标值，第三列为数据值
         * 其中第一行数据为原始数组的行数，列数，有数据的个数
         * 其余行为具体数据的坐标和数据值
         */
        //遍历原始数组获取总共有多少个数据
        int sum = 0;
        for (int[] row : cheesArr) {
            for(int data : row){
                if(data != 0)sum++;
            }
        }
        System.out.println("原始数组的一共有data："+sum);

        //构建稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //填充稀疏数组
        int num = 1;//具体元素从第二行开始存储
        for (int i = 0 ; i< cheesArr.length ; i++) {
            for(int j = 0 ; j< cheesArr[i].length ; j++){
                if(cheesArr[i][j] != 0){
                    sparseArr[num][0] = i;
                    sparseArr[num][1] = j;
                    sparseArr[num][2] = cheesArr[i][j];
                    num++;
                }
            }
        }
        System.out.println("稀疏数组为：");
        for (int[] row : sparseArr) {
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        //=======================================================
        //稀疏数组转为原始数组
        //定义原始数组的大小
        int[][] arr = new int[sparseArr[0][0]][sparseArr[0][1]];
        //从第一行开始遍历稀疏数组
        for (int i = 1 ; i< sparseArr.length ; i++) {
            arr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        System.out.println("将稀疏数组转为原始数组为：");
        for (int[] row : arr) {
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }
}
