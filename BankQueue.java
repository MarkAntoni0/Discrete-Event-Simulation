//////////////////////////////////////////////////////////////////////////////
// Discrete Event Simulation - Mark Tharwat Antonio - AAST 19200164 - 2022
// Supervision: Prof. Dr. Khaled Mahar / Eng. Sara Mohamed
// 
// This code generates 10 random number and simulate and bank queue
// Inputs can be manipulated at line: 17 and 25
//////////////////////////////////////////////////////////////////////////////
import java.util.Random;
import java.util.stream.IntStream;

public class BankQueue {
	
	//The times between arrivals and the service-time distributions:
	//This is considered to be the input to the program (Same as given)
	static double[][] InterArrivalTable = { 
			{ 0, 0.09, 0 }, 
			{ 1, 0.17, 0 }, 
			{ 2, 0.27, 0 }, 
			{ 3, 0.20, 0 },
			{ 4, 0.15, 0 }, 
			{ 5, 0.12, 0 } };

	static double[][] ServiceTimeTable = { 
			{ 1, 0.20, 0 }, 
			{ 2, 0.40, 0 }, 
			{ 3, 0.28, 0 }, 
			{ 4, 0.12, 0 } };

	
	static int[] interArrivalTimes;          //for the interarrival times calculated
	static int[] serviceTimes;               //for the service times calculated
	static float[] randArray;                //for the random numbers generated

	static int[] itr;
	static int[] arrivalTimes;
	static int[] serviceBeginTimes;
	static int[] waitingTimes;
	static int[] customersInQueue;
	static int[] serviceEndTimes;
	static int[] customerSpentTimes;
	static int[] serverIdleTimes;

	
	//These are the values to be used in the final calculations
	//All initiated to 0 and will be updated once the table is constructed
	static int InterArrivalTimeSum = 0;
	static int ArrivalTimeSum = 0;
	static int ServiceTimeSum = 0;
	static int ServiceBeginTimeSum = 0;
	static int WhoWaitSum = 0;
	static int CustomerInQueueSum = 0;
	static int ServiceEndTimeSUm = 0;
	static int CustomerSpendsInSystemSum = 0;
	static int ServerIdleTime = 0;

	//The following function aims to build the cumulative values
	public static void calculateCumulative() {
		//Initiating the first row cumulative to equal the original probability
		InterArrivalTable[0][2] = InterArrivalTable[0][1]; 
		ServiceTimeTable[0][2] = ServiceTimeTable[0][1]; 
		
		
		for (int i = 1; i < 6; i++) { //This calculates the inter-arrival cumulative values
			InterArrivalTable[i][2]=InterArrivalTable[i-1][2]+InterArrivalTable[i][1];
		}
		for (int i = 1; i < 4; i++) { //This calculates the service time cumulative values
			ServiceTimeTable[i][2]=ServiceTimeTable[i-1][2]+ServiceTimeTable[i][1];
		}
	}
	
	//The following function aims to print the input including the new calculated cumulative column
	public static void printCumulative() {
		//Header (Used printf for formatting instead of importing Mavin library)
		System.out.printf("%-27s %-27s %-27s %n", "InterArrival Time", "Probability", "Cumulative Probability");
		//Nested loop to print all the values in the tables built (Interarrival time)
		for(int i=0;i<6;i++){
			for(int j=0;j<3;j++){
				System.out.printf("%-28f", InterArrivalTable[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("**********************************************************************\n");

		
		
		//Header (Used printf for formatting instead of importing Mavin library)
		System.out.printf("%-27s %-27s %-27s %n", "Service Time", "Probability", "Cumulative Probability");
		//Nested loop to print all the values in the tables built (Service time)
		for(int i=0;i<4;i++){
			for(int j=0;j<3;j++){
				System.out.printf("%-28f", ServiceTimeTable[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("**********************************************************************\n");
	}

	//The following function aims to generate the simulation random numbers
	public static void generateRandom() {
		randArray = new float[10];
		//Creating object from class Random (Java Library)
		Random rd = new Random();
		
		//We are generating only 10 values
		for(int i=0;i<10;i++) {
			//nextFloat aims to generate a random float out of the rd object created previously
			//nextFloat generated numbers from 0 to 1 
			randArray[i]=rd.nextFloat(); 
		}
		
		//Printing the generated random numbers
		System.out.printf("Random numbers generated\n");
		for(int i=0;i<10;i++) {
			System.out.println(randArray[i]);;
		}
		System.out.printf("**********************************************************************\n");

	}
	
	// The following array determines the interArrival time based on the random number in hand
	public static void calcInterArrival() {
		interArrivalTimes = new int[10];
		
		//This function holds the random number [i] and compare it 
		//with the Cumulative column [2] in the service time table
		for(int i=0;i<10;i++) {
			int foundValue=0;
			for(int j=0;j<6;j++) {
				if(randArray[i]>=InterArrivalTable[j][2]) {
					//Keep iterating we didn't find the target yet
				}else{
					//The value in hand (Random number is no longer greater than the cumulative [column 2])
					foundValue=(int)InterArrivalTable[j][0]; //The type casting is necessary as InterArrivalTable was defined as double 
					break;
				}
			}
			//This function also update the interarrival time array with the determined value
			interArrivalTimes[i]=foundValue;
		}
	}
	
	// The following array determines the Service time based on the random number in hand
	public static void calcServiceTime() {
		serviceTimes = new int[10];
		
		//This function holds the random number [i] and compare it 
		//with the Cumulative column [2] in the interarrival time table
		for(int i=0;i<10;i++) {
			int foundValue=0;
			for(int j=0;j<4;j++) {
				if(randArray[i]>=ServiceTimeTable[j][2]) {
					//Keep iterating we didn't find the target yet
				}else{
					//The value in hand (Random number is no longer greater than the cumulative [column 2])
					foundValue=(int)ServiceTimeTable[j][0]; //The type casting is necessary as ServiceTimeTable was defined as double 
					break;
				}
			}
			//This function also update the service time array with the determined value
			serviceTimes[i]=foundValue;
		}
	}
	
	public static void main(String[] args) {
		int rows = 10; //This value is to be used for initiating the calculated arrays
		
		// instantiate arrays with correct size
		itr = new int[10];
		
		calculateCumulative();      //Calculating cumulative values for interarrival and service time
		printCumulative();          //Printing cumulative tables
		generateRandom();           //Generate 10 random numbers
		calcInterArrival();         //Calculate the interarrival time based on the random numbers
		calcServiceTime();			//Calculate the service time based on the random numbers
		
		
		//Initiating all the arrays to be used with the correct count of columns
		arrivalTimes = new int[rows];
		serviceBeginTimes = new int[rows];
		waitingTimes = new int[rows];
		customersInQueue = new int[rows];
		serviceEndTimes = new int[rows];
		customerSpentTimes = new int[rows];
		serverIdleTimes = new int[rows];

		
		//Creating a table to include our rows [single arrays] in
		//Dimensions are Rows(10) X whatever
		int table[][] = new int[rows][];
		
		//Printing the 
		System.out.printf("%-27s %-27s %-27s %-27s %-27s %-27s %-27s %-27s %-27s %-27s %n", "Customer #",
				"Interarrival Time", "Arrival Time", "Service Time", "Time Service Begins", "Waiting Time",
				"Customers in Queue", "Time Service Ends", "Time Customer Spends", "Idle Time");

		for (int i = 0; i < rows; i++) {
			//If the table is empty this simply fill the first row
			if (i == 0) { 
				itr[i] = i + 1;                                             //Increment the iteration counter for the next iteration
				arrivalTimes[i] = interArrivalTimes[i];                     //The first arrival time is the interarrival time
				serviceBeginTimes[i] = arrivalTimes[i];                     //Service begins once there is a task arrived
				waitingTimes[i] = 0;                                        //At no point the first task would wait
				customersInQueue[i] = 0;                                    //The first task would face empty queue
				serviceEndTimes[i] = serviceBeginTimes[i]+serviceTimes[i];  //ServiceEndTime = Service start + service time    
				customerSpentTimes[i] = serviceTimes[i] + waitingTimes[i];  //TimeInSystem = service time + waiting time
				serverIdleTimes[i] = 0 + arrivalTimes[i];                   //Just in case that the first task wasn't there at time 0
			} else {
				boolean calcIdle = false;                                                     //Initiating the value to false - expecting no idle time
				itr[i] = i + 1;		                                                          //Increment the iteration counter for the next iteration
				arrivalTimes[i] = arrivalTimes[i - 1] + interArrivalTimes[i];                 //Calculating the arrival time based on the interarrival time and previous arrival time
				
				if (arrivalTimes[i] > serviceEndTimes[i - 1]) {                               //There is idle time
					serverIdleTimes[i] = arrivalTimes[i] - serviceEndTimes[i - 1];               //Calculating the idle time to be the difference between previous task end time and current task start time
					calcIdle = true;                                                          //Setting the flag that there was an idle time
				}
				
				//Calculating two variables
				//serviceBeginTimes
				//customersInQueue
				if(arrivalTimes[i]>serviceEndTimes[i - 1]) {                                  //Service begin time would be the last end time if they overlap and be the arrival time if there is no overlapping             
					serviceBeginTimes[i]=arrivalTimes[i];                                     //No overlap case 
					customersInQueue[i] = 0;                                                  //Task came to an empty queue
				}else {                                                                      
					serviceBeginTimes[i] = serviceEndTimes[i - 1];                            //Overlap case
					//Task came to a non-empty queue
					//We would calculate how many task in queue based on their end time
					//If end time > arrival time, then it was there when our new task arrived
					//We would repeat comparison the all the previous end times
					int countInQueue=0;
					for(int j=i;j>=0;j--) {
						if(arrivalTimes[i]<serviceEndTimes[j]) {
							countInQueue++;
						}
					}
					customersInQueue[i] = countInQueue;
				}
				
				waitingTimes[i] = serviceBeginTimes[i] - arrivalTimes[i];                     //Calculating the waiting time
				serviceEndTimes[i] = serviceBeginTimes[i] + serviceTimes[i];                  //Calculating end time
				customerSpentTimes[i] = serviceTimes[i] + waitingTimes[i];                    //Calculating time spent in system
			}
			
			
			//Filling Data
			//Filling row by row in the loop based on the generated values above
			table[i] = new int[] { itr[i], interArrivalTimes[i], arrivalTimes[i], serviceTimes[i], serviceBeginTimes[i],
					waitingTimes[i], customersInQueue[i], serviceEndTimes[i], customerSpentTimes[i],
					serverIdleTimes[i] };
		}
		
		
		
		
		//Print table
		for (int[] row : table) {
			System.out.format("%-27d %-27d %-27d %-27d %-27d %-27d %-27d %-27d %-27d %-27d %n", 
					row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]);
		}

		
				
		//ٍSum columns
		InterArrivalTimeSum = IntStream.of(interArrivalTimes).sum();
		ArrivalTimeSum = IntStream.of(arrivalTimes).sum();
		ServiceTimeSum = IntStream.of(serviceTimes).sum();
		ServiceBeginTimeSum = IntStream.of(serviceBeginTimes).sum();
		WhoWaitSum = IntStream.of(waitingTimes).sum();
		CustomerInQueueSum = IntStream.of(customersInQueue).sum();
		ServiceEndTimeSUm = IntStream.of(serviceEndTimes).sum();
		CustomerSpendsInSystemSum = IntStream.of(customerSpentTimes).sum();
		ServerIdleTime = IntStream.of(serverIdleTimes).sum();

		// printing averages
		System.out.format("%-27s %-27.1f %-27.1f %-27.1f %-27.1f %-27.1f %-27.1f %-27.1f %-27.1f %-27.1f %n", "AVERAGE",
				InterArrivalTimeSum        /(float)rows, 
				ArrivalTimeSum             /(float)rows, 
				ServiceTimeSum             /(float)rows, 
				ServiceBeginTimeSum        /(float)rows,
				WhoWaitSum                 /(float)rows, 
				CustomerInQueueSum         /(float)rows, 
				ServiceEndTimeSUm          /(float)rows, 
				CustomerSpendsInSystemSum  /(float)rows,
				ServerIdleTime             /(float)rows);
		
		//Printing sum
		System.out.format("%-27s %-27d %-27d %-27d %-27d %-27d %-27d %-27d %-27d %-27d %n", "SUM", IntStream.of(interArrivalTimes).sum(), ArrivalTimeSum, ServiceTimeSum,
				ServiceBeginTimeSum, WhoWaitSum, CustomerInQueueSum, ServiceEndTimeSUm, CustomerSpendsInSystemSum, ServerIdleTime);

		
		//Calculating the count of customer who wait 
		int customersWhoWait = 0;
		for (int i = 0; i < rows; i++) {
			if (waitingTimes[i] != 0) {
				customersWhoWait++;
			}
		}

		System.out.format("Average Waiting Time in Queue: %.3f%n",               (float) WhoWaitSum / rows);
		System.out.format("Probability of Waiting: %.3f%n",                      (float) customersWhoWait / rows);
		System.out.format("Probability Idle Server: %.3f%n",                     (float) ServerIdleTime / serviceEndTimes[rows - 1]);
		System.out.format("Average Service Time: %.3f%n",                        (float) ServiceTimeSum / rows);
		System.out.format("Average Interrarrival Time: %.3f%n",                  (float) InterArrivalTimeSum / rows - 1);
		System.out.format("Average Waiting of Those Who Wait Time: %.3f%n",      (float) WhoWaitSum / customersWhoWait); 
		System.out.format("Average Time a Customer Spends in System %.3f%n",     (float) CustomerSpendsInSystemSum / rows);
		System.out.format("Service Utilization: %.3f%%%n",                       (1 - ServerIdleTime / (float) serviceEndTimes[rows - 1]) * 100); //1- as it's utilization and not idle percentage

	}
}