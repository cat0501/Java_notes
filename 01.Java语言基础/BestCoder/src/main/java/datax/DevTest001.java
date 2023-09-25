package datax;




import java.util.*;
public class DevTest001{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while(T-- != 0){
            boolean[] s = new boolean[1001];
            int n = in.nextInt();
            int[] a = new int[n + 1];
            int j = n;
            int[] res = new int[n + 1];
            for(int i = 1;i <= n;i++)a[i] = in.nextInt();
            for(int i = n;i >= 1;i--){
                if(!s[a[i]]){
                    s[a[i]] = true;
                    res[j--] = a[i];
                }
            }
            System.out.println(n - j);
            int len = n - j;
            int t = len;
            for(int i = n;i > j;i--){
                a[t--] = res[i];
            }
            for(int i = 1;i <= len;i++) System.out.print(a[i] + " ");
            System.out.println();
        }
    }
}


