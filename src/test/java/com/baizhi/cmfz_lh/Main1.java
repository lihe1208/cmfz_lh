import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] a = str.split(" ");
        Integer[] arr = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            arr[i] = Integer.valueOf(a[i]);
        }
        test(arr);
    }
        public static void test(Integer[] a) {
        int count =0;
        for (int i=0;i< a.length-1;i++){
            if(a[i+1] <a[i]){
                count ++;
            }
        }
        if (count>1) {
            System.out.println("No");
        }else {
            System.out.println("YES");
        }

    }
}
