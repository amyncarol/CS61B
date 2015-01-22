public class LeapYear{
  	public static void isleap(int n) {
         	if (n%400==0 || (n%4==0 && n%100!=0)){
                	System.out.println(n + " is a leap year.");
}
                else {
                        System.out.println(n + " is not a leap year.");
}     
}

	public static void main(String[] args) {
		int year=2000;
		isleap(year);
}
}
