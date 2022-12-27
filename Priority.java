// Java program to implement priority scheduling
// priority (Priority)

import java.util.*;
import java.util.Collections;


class Priority{
    
     public static int[] removeByVal(int[] arr, int val) {
        if (arr == null) {

           return arr;
        }
        int[] copy = new int[arr.length];
        for(int i=0, k=0; i<arr.length; i++)
        {
            if (arr[i] == val) {
                continue;
            }
            copy[k++] = arr[i];       
        } 
    
        return copy;
    }
    public static int[][] removeRow(int[][] mat, int row)
    {
        if (mat == null || row < 0
        || row >= mat.length) {

           return mat;
        }
        int[][] realCopy = new int[mat.length-1][mat[0].length];  
        for(int r=0, k=0; r<mat.length; r++)
        {
            if (r == row) {
                continue;
            }
            realCopy[k++] = mat[r];
            if(r != row){
                realCopy[r] = mat[r];
            }       
        }  
        return realCopy;
    }
     
         // Function to remove the element
    public static int[] removeByIndex(int[] arr, int index)
    {
        if (arr == null || index < 0
            || index >= arr.length) {
 
            return arr;
        }
        int[] anotherArray = new int[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {

            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
 
        return anotherArray;
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        

        System.out.print("Enter # of processes: ");
        int n = sc.nextInt();



        sc.close();

        String[] pname = {"p1", "p2", "p3", "p4", "p5"};  
        int[] pat = {1, 5, 7, 13 ,15};
        int[] pp = {5, 6, 3, 9 ,2};
        int[] pbt = {12,6, 7, 4 ,4};
        Map<Integer, String> p = new HashMap<Integer,String>() {{
            put(pp[0], pname[0]);
            put(pp[1], pname[1]);
            put(pp[2], pname[2]);
            put(pp[3], pname[3]);
            put(pp[4], pname[4]);
        }};

        
        int [][] max_pat_pbt = new int[n+1][n+1]; //arrival time of pp
        int [][] sorted_pat_pbt = new int[n+1][n+1];

        int[] ppDesc = Arrays.stream(pp).boxed()
            .sorted(Collections.reverseOrder())
            .mapToInt(Integer::intValue)
            .toArray();

        for(int i=0; i<n; i++){
          max_pat_pbt[i][0] = pp[i];
          max_pat_pbt[i][1] = pat[i];  
          max_pat_pbt[i][2] = pbt[i];
          
          sorted_pat_pbt[i][0] = pat[i];
          sorted_pat_pbt[i][1] = pbt[i];
          sorted_pat_pbt[i][2] = pp[i];

        }


        for(int i =0 ; i < n; i++){
            System.out.print(sorted_pat_pbt[i][0]);
            System.out.print("               ");
            System.out.print(sorted_pat_pbt[i][1]);
            System.out.print("               ");
            System.out.println(sorted_pat_pbt[i][2]);
        }

        int cnt = 0;
        List<Integer> list=new ArrayList<Integer>();
        int []executed = new int[n+1];
            for(int i=0; i< n-1; i++){
                Integer curr_pp = sorted_pat_pbt[i][2];
                if(curr_pp == ppDesc[0]){
                    sorted_pat_pbt[i][1] = 0;
                    executed[cnt] = curr_pp;
                    ppDesc = removeByIndex(ppDesc, 0);
                    cnt++;
                    continue;
                }

                Integer maxAt = 0, max=0;

                list.add(curr_pp);

                for (Integer j = 0; j < list.size(); j++) {
                    if(max < list.get(j)){
                        maxAt = j;
                        max = list.get(j);
                    }
                }

                Integer bt = sorted_pat_pbt[maxAt][1];
                Integer prior = sorted_pat_pbt[maxAt][2];
                Integer arriv = sorted_pat_pbt[maxAt][0];
                Integer next_arriv = sorted_pat_pbt[maxAt+1][0];

                if(bt ==0 || bt <= (arriv - next_arriv)){
                    list.remove(maxAt);
                    bt = 0;
                    ppDesc = removeByVal(ppDesc, (int)prior);
                    cnt++;
                }else {
                    bt = bt - (next_arriv-arriv);
                    sorted_pat_pbt[maxAt][1] = bt;
                }
                
            }
            System.out.println("Execution Order displayed based on priorities:");
            int num_execeuted = 0;
            for(int i=0; i< executed.length;i++){
                if(executed[i] != 0){
                    System.out.print((i+1)+ ") ");
                    System.out.println(p.get(executed[i]));
                    num_execeuted += i+1;
                }
            }

            for(int i=0; i< ppDesc.length;i++){
                System.out.print((i+num_execeuted+1)+ ") ");
                System.out.println(p.get(ppDesc[i]));
            }

    }
}