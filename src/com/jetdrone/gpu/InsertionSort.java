package com.jetdrone.gpu;

import java.util.Arrays;
import java.util.Random;

import com.amd.aparapi.Kernel;

public class InsertionSort {

//    static final int size = 512;
//
//    /** Input float array for which square values need to be computed. */
//    static final float[] values = new float[size];
//    
//	private static void insertionsort() {
//		int i, j;
//		float t;
//		for (i = 1; i < size; i++) {
//			j = i;
//			t = values[j];
//			while (j > 0 && values[j - 1] > t) {
//				values[j] = values[j - 1];
//				j--;
//			}
//			values[j] = t;
//		}
//	}
	
	public static void main(String[] args) {

	    final int size = 512;

	    /** Input float array for which square values need to be computed. */
	    final float[] values = new float[size];
	    
	      /** Aparapi Kernel which computes squares of input array elements and populates them in corresponding elements of 
	       * output array. 
	       **/
	      Kernel kernel = new Kernel(){
	         @Override public void run() {
	            int gid = getGlobalId();
	            int j;
	            float t;
	            if(gid > 0) {
	            	j = gid;
	            	t = values[j];
	            	while(j > 0 && values[j - 1] > t) {
	            		values[j] = values[j - 1];
	            		j--;
	            	}
	            	values[j] = t;
	            }
	         }
	      };

	      // Report target execution mode: GPU or JTP (Java Thread Pool).
	      System.out.println("Execution mode=" + kernel.getExecutionMode());
	      
	      // Execute Kernel.
	      Random r = new Random();
	      long t0 = System.nanoTime();
	      for(int i=0; i<10; i++) {
		      /** Initialize input array. */
		      for (int j = 0; j < values.length; j++) {
		         values[j] = r.nextFloat();
		      }
		      kernel.execute(512); // 559231049
//		      insertionsort();     // 24049042
	      }
	      long t1 = System.nanoTime();

//	      // Display computed square values.
//	      for (int i = 0; i < size; i++) {
//	         System.out.printf("%6.0f\n", values[i]);
//	      }
	      
	      System.out.println(t1 - t0); // 559231049

	      // Dispose Kernel resources.
	      kernel.dispose();
	}
}
