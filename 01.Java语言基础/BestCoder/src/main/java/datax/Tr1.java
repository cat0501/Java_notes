package datax;


import java.util.*;
import java.util.* ;

    public class Tr1
    {
        public static void main(String [] args)
        {
            Scanner scan = new Scanner(System.in);

            int T = scan.nextInt();
            while (T -- > 0)
            {
                int n = scan.nextInt();
                int [] nums = new int [n];
                for (int i = 0; i < n; i ++)
                    nums[i] = scan.nextInt();

                Set<Integer> us = new HashSet<>();
                int cnt = 0;
                for (int i = n - 1; i > -1; i --)
                {
                    int x = nums[i];
                    if (us.contains(x) == true)
                    {
                        nums[i] = -1;
                        cnt ++;
                    }
                    else
                    {
                        us.add(x);
                    }
                }

                System.out.println(n - cnt);
                for (int x : nums)
                {
                    if (x != -1)
                        System.out.print(x + " ");
                }
                System.out.println();
            }
        }
    }


