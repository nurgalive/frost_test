package org.fraunhofer.test;


import java.util.Random;

/** Generate 10 random integers in the range 0..99. */
/*public class RandomGen {
	int res = 0;
	public void generate() {
		log("Generating 10 random integers in range 0..99.");
		//note a single Random object is reused here
		Random randomGenerator = new Random();
		for (int x = 1; x < 11; ++x){
			int randomInt = randomGenerator.nextInt(100) +1;
			log("Generated №" + x + " : " + randomInt);
			this.res = randomInt;
      
		}
  }
  
	private static void log(String a){
    System.out.println(a);
  }
}
*/

public class RandomGen {
   public int n;

public void generate() {
 
      Random rand = new Random();
      
      for (int i=0; i < 10; i++) {
         int n = rand.nextInt(10);
         System.out.println(n);
      }
   }
}