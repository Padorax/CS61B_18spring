public class fib{

  public static int  modified_fib(int n, int[] values){
     if(n == 0){
       values[0] = 0;
       return 0;
     }
     if(n == 1){
       values[1] = 1;
       return 1;
     }
     else{
       int val = values[n];
       if(val == 0){
         val = modified_fib(n-1) + modified_fib(n-2);
         values[n] = val;
       }
       return val;
     }
   }  

   public static void main(String[] args) {
    int x = modified_fib(3, new int[3]);
    System.out.println(x);
   }

}