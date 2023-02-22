package org.example;
import java.util.*;
public class Pokemon {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        a[0] = 1;
        a[1] = 1;
        for (int i = 0; i <= n-3; i++)
            a[i+2] = a[i+1] + a[i];
        System.out.print(Arrays.toString(a));
    }
}