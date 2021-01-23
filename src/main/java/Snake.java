import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* 场景类，包括🐍，以及画板
* */
public class Snake {
    private enum Goto{
        left,right,top,bottom
    }
    //食物
    private int [] foot = null;
    //y,x，
    private String[][] main = new String[17][17];

    private JFrame f= null;

    private JTextArea jTextArea = null;

    private Goto gotow = Goto.right;

    private List<Body> snakes = new Body(0,0).getSnakes();

    private Snake(){
        for(int i = 0; i < main.length; i++){
            for(int j = 0; j < main[0].length; j++){
                main[i][j] = "   ";
            }
        }
        snakes.add(new Body(0, 0));
        foot = new int[]{0,4};
        f = new JFrame();
        f.setSize(300, 300);
        //设置窗体位置
        f.setLocation(200,300);
        //设置窗体名字
        f.setTitle("初级贪吃蛇");
        jTextArea = new JTextArea();
        f.add(jTextArea);
        jTextArea.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch(e.getKeyCode()){
                    case KeyEvent.VK_DOWN: gotow = Goto.bottom; break;
                    case KeyEvent.VK_UP: gotow = Goto.top; break;
                    case KeyEvent.VK_LEFT: gotow = Goto.left; break;
                    case KeyEvent.VK_RIGHT: gotow = Goto.right; break;
                }
            }
        });
    }
    public static void main(String[] args) throws InterruptedException {
        Snake snake = new Snake();
        String res = null;
        for(;;){
           res = snake.printSnake(snake.main);
           snake.jTextArea.setText(res);
           snake.f.setVisible(true);
           Thread.sleep(500 - snake.snakes.size() * 10);
           snake.remove(snake.gotow);
        }
    }

    //移动
    public void remove(Goto e){
        Random random = new Random();
        Body header = snakes.get(0);
        int [] wz = header.getWz();
        //x
        int tmp = wz[1];
        //y
        int tmp1 = wz[0];
        switch (e){
            case left:
                if (wz[0] == foot[0] && wz[1] - 1 == foot[1]){
                    snakes.add(0, new Body(foot[0],foot[1]));
                    int one = random.nextInt(main.length -1);
                    int two = random.nextInt(main.length -1);
                    foot[0] = one;
                    foot[1] = two;
                    return;
                }
                header.setWz(wz[0],--wz[1]);
                for(int i = 1; i < snakes.size(); i++){
                   Body body = snakes.get(i);
                   int [] bodytm = body.getWz();
                   body.setWz(tmp1, tmp);
                   tmp1 = bodytm[0];
                   tmp = bodytm[1];
                }
                break;
            case right:
                if (wz[0] == foot[0] && wz[1] + 1 == foot[1]){
                    snakes.add(0, new Body(foot[0],foot[1]));
                    int one = random.nextInt(main.length -1);
                    int two = random.nextInt(main.length -1);
                    foot[0] = one;
                    foot[1] = two;
                    return;
                }
                header.setWz(wz[0],++wz[1]);
                for(int i = 1; i < snakes.size(); i++){
                    Body body = snakes.get(i);
                    int [] bodytm = body.getWz();
                    body.setWz(tmp1, tmp);
                    tmp1 = bodytm[0];
                    tmp = bodytm[1];
                }
                break;
            case top:
                if (wz[0] - 1 == foot[0] && wz[1] == foot[1]){
                    snakes.add(0, new Body(foot[0],foot[1]));
                    int one = random.nextInt(main.length -1);
                    int two = random.nextInt(main.length -1);
                    foot[0] = one;
                    foot[1] = two;
                    return;
                }
                header.setWz(--wz[0],wz[1]);
                for(int i = 1; i < snakes.size(); i++){
                    Body body = snakes.get(i);
                    int [] bodytm = body.getWz();
                    body.setWz(tmp1, tmp);
                    tmp1 = bodytm[0];
                    tmp = bodytm[1];
                }
                break;
            case bottom:
                if (wz[0] + 1 == foot[0] && wz[1]  == foot[1]){
                    snakes.add(0, new Body(foot[0],foot[1]));
                    int one = random.nextInt(main.length -1);
                    int two = random.nextInt(main.length -1);
                    foot[0] = one;
                    foot[1] = two;
                    return;
                }
                header.setWz(++wz[0],wz[1]);
                for(int i = 1; i < snakes.size(); i++){
                    Body body = snakes.get(i);
                    int [] bodytm = body.getWz();
                    body.setWz(tmp1, tmp);
                    tmp1 = bodytm[0];
                    tmp = bodytm[1];
                }
                break;
            default:
                break;
        }
    }

    public String printSnake(String [][] arr){
        arr[foot[0]][foot[1]] = "♦";
        for(Body tmp : snakes){
            int [] bodyWz = tmp.getWz();
            try {
                arr[bodyWz[0]][bodyWz[1]] = "♠";
            }catch (Exception e){
                System.out.println("游戏结束你end了");
                return "游戏结束你输了，你的得分是"+ snakes.size();
            }finally {
                System.out.println("ss");
            }

        }
        String res = printArr(arr);
        for(Body tmp : snakes){
            int [] bodyWz = tmp.getWz();
            arr[bodyWz[0]][bodyWz[1]] = "   ";
        }
        return res;
    }

    public String printArr(String [][] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for(int y = 0 ; y < arr.length; y++){
            for(int x = 0; x < arr[y].length; x++){
                if(x == 0){
                    stringBuilder.append("【");
                }
                stringBuilder.append(arr[y][x]);
                if(x == arr[y].length - 1){
                    stringBuilder.append("】");
                    stringBuilder.append("\n");
                }

            }
        }
        return stringBuilder.toString();
    }
}

class Body {

    //🐍的容器
    private ArrayList<Body> snakes = new ArrayList<>();

    private int x = 0;
    private int y = 0;

    public int[] getWz(){
        return new int[]{this.x, this.y};
    }

    public Body(int x , int y){
        this.x = x;
        this.y = y;
    }

    public void setWz(int x, int y){
        this.x = x;
        this.y = y;
    }

    public ArrayList<Body> getSnakes(){
        return this.snakes;
    }

}

