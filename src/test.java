/**
 * Author: Grsis Fee
 * Date:   2016/1/18.
 */
public class test {

    public static void main(String[] args) {
        System.out.println("System user dir: " + System.getProperty("user.dir") + "该目录在run configuration下更改");
        ChineseSegment chineseSegment = new ChineseSegment(".\\resource\\data\\dict.txt");
        chineseSegment.ReverseMaxMatch("因早盘低开大幅低开，抄底资金纷纷进入，指数亦被拉升，截至中午收盘，上证指数下跌50.53点。");
        chineseSegment.printMatchWords();
    }
}
