package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/20 13:49
 */
public class LongestFile {

    public static void main(String[] args) {
        String cateCode = "LINK_BTN_1";
        System.out.println(cateCode.substring(0, cateCode.lastIndexOf("_")));
        LongestFile longestFile = new LongestFile();
        System.out.println(longestFile.lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
    }

    int i = 0, res = 0;
    private int lengthLongestPath(String s) {
        dfs(s.split("\n"), 0, 0);
        return res;
    }

    private void dfs(String[] split, int level, int len) {
        String t = "";
        for(int j = 1; j <= level; j++){
            t += "\t";
        }
        while(i < split.length){
            if(
                    (t.length() == 0 && !split[i].startsWith("\t"))
                    ||
                    (t.length() > 0 && !split[i].startsWith(t+"\t") && split[i].startsWith(t))
            ){
                if(split[i].contains(".")){
                    res = Math.max(res, len + (split[i++].length() - level));
                }else{
                    dfs(split, level+1, len + (split[i++].length() - level) + 1);
                }
            }else{
                return;
            }
        }
    }

}
