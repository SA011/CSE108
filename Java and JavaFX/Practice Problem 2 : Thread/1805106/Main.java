import java.util.Random;

class ParallelMax implements Runnable {
    // your code here
    final private int startIndex,block;
    public Thread t;
    public static int[] numbers;
    public int ans;
    public ParallelMax(int start,int blk){
        t=new Thread(this,"Thread "+(start/blk+1));
        startIndex = start;
        block = blk;
    }

    @Override
    public void run() {
        ans = numbers[startIndex];
        for(int i=0;i<block && i + startIndex < numbers.length;i++){
            ans=Integer.max(ans,numbers[i+startIndex]);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Random random = new Random(100);
        int [] numbers = new int[1000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt();
        }
        ParallelMax [] parallelMax = new ParallelMax[5];
        // your code here
        ParallelMax.numbers = numbers;
        for(int i=0;i<5;i++){
            parallelMax[i] = new ParallelMax(i*200,200);
            parallelMax[i].t.start();
        }
        int ans = numbers[0];
        for(int i=0;i<5;i++){
            try {
                parallelMax[i].t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ans = Integer.max(parallelMax[i].ans,ans);
        }
        System.out.println(ans);
    }
}