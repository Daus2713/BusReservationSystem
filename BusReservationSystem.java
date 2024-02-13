
import java.time.LocalDate;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
/*
*Source code created by Bro Firdaus(2211901),Bro Hafizudin(2217261), Bro Hafiz(2217477)
 Bro AbdulMajid(2218587) and Sis Rabiatul(2217808), students from EOP Section 4 Sem 1,22/23
*This program, TBS Bus Reservation System, allows users to search, add information,
 and reserve a bus. It also shows reserved buses and allows you to cancel reservations. 
*/
public class BusReservationSystem {
    
    public static void main(String[] args) {
        
        String [] destination = {"Malacca","Johor Bahru","Seremban",            //Southern Malaysia state 0 - 2
                                 "Kangar","Alor Setar", "Penang","Ipoh",        //Northern Malaysia state 3 - 6
                                 "Kota Bahru","Kuala Terengganu","Kuantan"};    //East Coast Malaysia state 7 -9
                                    // #Company Name        # price    #Seat: Morning Afternoon Evening #Schedule:Morning  Afternoon Evening
        String [][] busCompanyInfo = {{"Pidin Express",       "15",             "5",    "7",    "22",           "07:30",  "14:00",  "19:30"         },
                                      {"Abdul Majid Express", "14",             "7",    "11",   "20",           "08:15",  "14:30",  "21:45"         },
                                      {"Hafiz Express",       "10",             "5",    "22",   "21",           "09:00",  "15:00",  "19:45"         },
                                      {"Rabiatul Express",    "15",             "5",    "12",   "24",           "09:30",  "15:45",  "20:15"         },
                                      {"Firdaus Express",     "13",             "7",    "13",   "22",           "10:00",  "16:00",  "20:30"         },
                                      {"Maraliner",           "17",             "4",    "15",   "26",           "11:15",  "16:30",  "20:45"         },
                                      {"Res2 Express",        "22",             "7",    "18",   "27",           "12:00",  "17:30",  "21:30"         },
                                      {"SP Bumi",             "20",             "7",    "12",   "23",           "12:30",  "18:00",  "22:15"         }};
                        
        String[] mainmenu = {"Search Bus","Display reserved bus","Exit"};
        
        String [][] booking = new String [20][6]; 
        int choice ,noBooking = 0;                
        
        Scanner input = new Scanner (System.in);

        System.out.println("\n========================================================================================");
        System.out.println("\t\t  *** Welcome to TBS Bus Reservation System! ***" );
        System.out.println("========================================================================================\n");
        
        do{
            //Call displayOption method to display mainmenu and get input from user;
            choice = displayOption(mainmenu);        
            
            if(choice != 1 && choice != 2 && choice != 3)                //if user input number not in the main menu option
            {   System.out.println("\t\tInvalid input. Please try again\n"); 
                continue; //Skip next statements and loop back to main menu list to ask user input choice again
            }  
           
            if(choice == 1) //The user chose Search Bus option
            {
                    String [][] busInfo = new String[20][6];
                    String [] busReserve = new String[6];
            
                    addBusInfo(destination, busInfo); //Call addBusInfo method that give user input the info of bus they want to reserve
                    searchBus(busInfo,busCompanyInfo,destination, busReserve,booking,noBooking);//Call searchBus to list all bus available
            
                    if(busReserve[0] != null)//The user reserve a bus 
                    {
                        booking[noBooking] = busReserve; // Get reserved bus info into array booking
                        noBooking++; //number of bus in booking list increase
                               
                    }  
            }
            
            else if(choice == 2)//The user chose to display reserved bus information
            {
                int option;
                if(noBooking  == 0) //if there is no booking info reserve by user
                {
                    System.out.println("\t\t\t*** You not reserved any buses ***\n");
                    continue; //skip all statement and loop back to main menu
                }
                
                displayBooking(booking,noBooking); // Call displayBooking method to display all list of reserved bus
                do
                {   
                    String optionList[] = {"Go back to main menu","Cancel booking"};
                     //Display option and get input from user
                    option = displayOption(optionList);
                    int cancelNo = -1;
                    if(option == 2)//The user chose Cancel booking
                    {
                        do{
                            System.out.print("\t\tWhich no. you want to cancel?\n\t\t=> ");//Ask which no. of bus user want to cancel
                            try{
                                cancelNo = input.nextInt();
                            } catch(InputMismatchException ex){
                                input.next();
                            }
                            
                            if(cancelNo < noBooking || cancelNo > noBooking)
                                System.out.println("\n\t\tInvalid input. Please try again");
                            
                        } while (cancelNo < noBooking || cancelNo > noBooking ); 
                        
                        noBooking = deleteRecord(booking,noBooking,cancelNo);//Call deleteRecord method to delete record bus information
                        System.out.println("\t\t*** Successfully cancel booking No. "+ cancelNo);
                        
                    }                                                                               
                     if(option != 1 && option != 2) 
                           System.out.println("\n\t\tInvalid input. Please try again");     
                }while(option != 1 && option != 2); 
            }
           
            } while (choice != 3); //loop if user input value other than 3 (Exit option)
            input.close();
            System.out.println("\t\tThank you for using TBS Bus Reservation System!\n\t\t\t\tHave a nice trip!");
        }
    
    //This method gets the departure date and destination of bus   
    public static void addBusInfo(String[] destination, String[][] busInfo)
    {
        Scanner input = new Scanner(System.in);
        int choice = 2, indexDest;
        String date, dest;
        System.out.println("\n========================================================================================");
        System.out.println("\tThe system will ask to input departure date and destination of the bus you want");
        
        do
        {
            date = getDate(); //Invoke getDate method to get departure date
            
            do{
                //Get bus destination
                System.out.println("\t\tList of destination available:");
                for(int i = 0; i < 5 ; i++)
                {
                    System.out.printf("\t\t%d. %-15s %d. %-20s\n",(i+1) ,destination[i],(i+6),destination[i+5]);
                }
                System.out.print("\t\tWhere do you want to go(Input number):\n\t\t=> ");
                try{
                    indexDest = input.nextInt();
                }catch(InputMismatchException ex){
                    indexDest = -1;
                    input.next();
                }
                if(indexDest < 1 || indexDest > destination.length)
                    System.out.println("Invalid input. Please try again");
                
            }while(indexDest < 1 || indexDest > destination.length);
            
            indexDest -= 1;
            dest = destination[indexDest];//Store bus destination
            
            System.out.println("\t\t** Your Departure date: " + date + " **");
            System.out.println("\t\t** Your destination: " + dest + " **");
             
            //Ask users to confirm the date and destination they have entered
            do{
                String option[] = {"Continue to search bus", "Change Info"};
                choice = displayOption(option);
            
                if(choice != 1 && choice != 2)
                    System.out.println("Invalid input. Please input again");
            
            }while(choice != 1 && choice != 2);
                
        }while(choice != 1);//User not choose to continue to search bus
        
        //Store indexDest in busInfo for further use (GetBusCompanyAvailablemethod)
        busInfo[0][2] = Integer.toString(indexDest);
        
        for(int i = 0; i < busInfo.length; i++)
        {   //Store bus destination and departure date in array busInfo
            busInfo[i][0] = date;
            busInfo[i][1] = dest;
        }
        
    }
    
    //This method gets departure date from user
    public static String getDate(){
        
        Scanner input = new Scanner (System.in);
        boolean validDate = false;
        int day, month, year;
        String date = "";
        LocalDate currentdate = LocalDate.now();//get current date

        System.out.println("========================================================================================");

        do{
            try{
            System.out.println("\t\tPlease enter your departure date(day,month,year)");
            
            System.out.print("\t\tDay: ");
            day = input.nextInt();
            
            System.out.print("\t\tMonth(in number): ");
            month = input.nextInt();

            System.out.print("\t\tYear: ");
            year = input.nextInt();
            
            } catch(InputMismatchException ex){
                day = -1;
                month = -1;
                year = -1;
                input.next();
            }
            //Only current year and next year accepted
            if(year ==  currentdate.getYear() || year == (currentdate.getYear() + 1))
            {
                //validate month and day
                if((month >= 1 && month <= 12)  )
                {   
                    switch (month) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            if (day >= 1 && day <=31)
                                validDate = true;
                            break;
                        case 2:
                            if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)) && (day >= 1 && day <= 29))
                            {
                                validDate = true;
                            } else if (day >= 1 && day <= 28)
                            {
                                validDate = true;
                            }   break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            if (day >= 1 && day <=30)
                                validDate = true;
                            break;        
                    }
                }
            }
 
            if (validDate)
            {   
                //store year
                date = Integer.toString(year);
                if(month < 10) //store month
                   //edit  month number if one digit(important when comparing date)
                    date = date + "-0" + month; //example 2023-1-31 -> 2023-01-31
                
                else
                    date = date + "-" + month;
                
                
                if(day < 10)//store day
                    //edit day number if it one digit(importnt when comparing date)  
                    date = date + "-0" + day;
                else
                    date = date + "-" + day;
                //compare date with current date
                int result = date.compareTo(currentdate.toString());
                
                if(result < 0)//date less than current date
                    validDate = false;
                
            }
            
            if(!validDate)
                System.out.println("The system doesn't have any bus information on that date. Please input the date again\n");
            
        }while(!validDate);
        System.out.println("----------------------------------------------------------------------------------------");
        return date;    
    }
    
    //This method finds and creates a list of available bus using the info that user input (from addBusInfo method)
    public static void searchBus(String[][] busInfo, String[][] busCompanyInfo, String [] destination,String[] busReserve, String[][]booking, int noBooking) 
    {
        int noBus = 0;

        LocalDate currentdate = LocalDate.now();//The LocalDate.now() method returns the instance of the LocalDate class
        Date dt = new Date(); //create Date object from date class
        SimpleDateFormat ft = new SimpleDateFormat ("kk:mm:ss"); //create instance SimpleDateFormat with specific format
        String currentTime = ft.format(dt); //get current time with specific format

        System.out.println("\n========================================================================================");
        System.out.print("Destination: " + busInfo[0][1] +" \tDeparture date: " + busInfo[0][0]);
        System.out.print(" \tCurrent time : " + currentTime);
        System.out.println("\n========================================================================================\n");
        
        int numCompany;
        int [] indexCompany = new int[10];
        //call getBusCompanyAvailable to get indexCompany that provides bus services of the chosen destination and return number of bus company that provide services
        numCompany = getBusCompanyAvailable(destination, indexCompany,busInfo[0][2]);

            for(int i = 0; i < numCompany; i++) // loop to get busCompanyInfo value into busInfo array
            {   
                for(int j = 0; j < 3;j++) //loop three times to get busInfo for Morning, Afternoon, Evening
                {   
                    int noSeatReserved, seatAvailable;
                    
                    int compareTime = (busCompanyInfo[indexCompany[i]][5 + j]).compareTo(currentTime); //compare the time of bus schedule with the current time
                    String seatBus = busCompanyInfo[indexCompany[i]][2 + j];
                                                                                  //This two value being capture first as it use for checking seat available
                    busInfo[noBus][2] = busCompanyInfo[indexCompany[i]][0];       //get bus Company name 
                    busInfo[noBus][5] = busCompanyInfo[indexCompany[i]][5 + j];   //get Departure Time
                    
                    //call method checkSeatAvailable to get number of seat that has occupied by previous reservation
                    noSeatReserved = checkSeatAvailable(booking,noBooking,busInfo[noBus]);

                    //Substract the seat available with number of reserved seat
                    seatAvailable = Integer.parseInt(seatBus) - noSeatReserved ;   
                    
                    if(compareTime < 0 && (busInfo[0][0].compareTo(currentdate.toString()) == 0)) //if compareTime < 0 and the destination date is today 
                        continue; //skip the remaining statements in the loop, exclude this bus info because the bus departure time has already passed today
                    
                    if(seatAvailable < 1)   //if bus doesn't have any seat available anymore
                    {   
                        continue;//skip the remaining statements in the loop , exclude this bus infobus info because all seats are already occupied
                    }
                    
                    busInfo[noBus][3] = busCompanyInfo[indexCompany[i]][1]; //get price of one seat
                    busInfo[noBus][4] = Integer.toString(seatAvailable); //Get total of seat available 

                    noBus++;
                }
            }
                int choice;
            do{

                if(noBus == 0) //there is no bus in array busInfo, meaning no bus available
                {
                    System.out.println("\t\tSorry, there is no bus available... \n");
                    break;
                }
                
                displayBusInfo(busInfo,noBus); //display list of bus information available
                String [] option = {"Reserve bus","Sort the list","Go back to main menu"};
               
                choice =  displayOption(option);//display option after display list of bus info and get input;
                
                if(choice == 1) //The user chose to reserve bus
                {
                    reserveBus(busInfo, busReserve,noBus); // call reserveBus method
                }                
                
                if(choice == 2) //The user chose to sort bus list
                {
                    sortBusList(busInfo,noBus); //call sortBusList to do sorting
                }
                
                if(choice != 1 && choice != 2 && choice != 3)
                    System.out.println("Invalid input! Try again");
  
              }while(choice != 1 && choice != 3); //loop after sorting the list  or invalid input    
        }
    
    //This method finds list of bus company that provides bus services based on the destination the user has already input.
    public static int getBusCompanyAvailable(String[] destination, int [] indexCompany, String busDest  )
    {   
        //bus company divides into 3 type of company 1. Southern states company 2. Northernn states company 3. East Coast states company
        int []indexSouthernCompany = {0,1,2,4}; //Pidin Express, Abdul Majid Express, Hafiz Express, Firdaus Express
        int []indexNorthernCompany = {0,3,5,6,7}; //Pidin Express, Rabiatul Express, Maraliner, Res2 Express, SP Bumi
        int []indexEastCoastCompany = {0,1,4,5,7}; //Pidin Express, Abdul Majid Express, Firdaus Express, maraliner, SP Bumi
        int totalCompany = 0;
        int indexDest = Integer.parseInt(busDest);
            
                switch (indexDest) {
                            //Southern Malaysia Bus Company
                    case 0:
                    case 1:
                    case 2:
                        //copy array in indexSouthernCompany into indexCompany
                        System.arraycopy(indexSouthernCompany, 0, indexCompany, 0, indexSouthernCompany.length); 
                        totalCompany = indexSouthernCompany.length;
                        break;
                            //Northern Malaysia Bus Company
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        //copy array in indexSouthernCompany into indexCompany
                        System.arraycopy(indexNorthernCompany , 0, indexCompany, 0, indexNorthernCompany .length);
                        totalCompany = indexNorthernCompany.length;
                        break;
                            //East Coast Malaysia Bus Company
                    case 7:
                    case 8:
                    case 9:
                        //copy array in indexSouthernCompany into indexCompany
                        System.arraycopy(indexEastCoastCompany, 0, indexCompany, 0, indexEastCoastCompany.length);
                        totalCompany = indexEastCoastCompany.length;
                        break;
                    default:
                        System.out.println("Error! destination not found");
                }
                
        return totalCompany; //total company that provide service based on user's destination
    }
    
    //This method checks the seat that has been reserved by the user and get amount reserved seat
    public static int checkSeatAvailable(String [][] booking,int noBooking,String [] busInfo)
    {   
        int noSeatReserved = 0;
        for(int i = 0; i < noBooking; i++)
        {   //Check if the bus information the user has input has the same information as the reserved bus information
            if(booking[i][0].equals(busInfo[0]) && booking[i][1].equals(busInfo[1]) && booking[i][2].equals(busInfo[2]) && booking[i][5].equals(busInfo[5]))
            {   //get amount seat has been reserved
                noSeatReserved += Integer.parseInt(booking[i][4]);
            }
        }
        return noSeatReserved;
    }
    
    // This method displays the list of buses available in a formatted output.
    public static void displayBusInfo(String[][] busInfo, int noBus)
    {   
        System.out.println("=======================================================================================================");
        System.out.printf("%-4s %-14s %-18s %-20s %-7s %-16s %-7s \n","No.", "Departure Date","Destination","Bus Company","Price", "Seat Available", "Departure time");
        System.out.println("=======================================================================================================");

        for(int i = 0; i < noBus; i++)
        {
            System.out.printf("%-4s %-14s %-18s %-20s RM%-7s %-16s %-7s \n", i+1, busInfo[i][0], busInfo[i][1], busInfo[i][2],busInfo[i][3], busInfo[i][4], busInfo[i][5]);
        }
        System.out.println("=======================================================================================================\n");
    }
    
    //This method asks users to input info of the bus they want to reserve
    public static void reserveBus(String[][]busInfo, String[]busReserve, int noBus)
    {
        Scanner input = new Scanner(System.in);
        int noReserve = 0, amountSeat = 0;
        //Get no. of bus Info 
        do{
            System.out.print("\t\tWhich no. you want to reserve?\n\t\t=> ");
            try{
                noReserve = input.nextInt();
            }catch(InputMismatchException ex){
                input.next();
            }
            if(noReserve < 1 || noReserve > noBus)
                System.out.println("\t\tInvalid input. Try again");
            
        } while(noReserve < 1 || noReserve > noBus);
        //copy all bus info that users have selected into busReserve array
        System.arraycopy(busInfo[noReserve - 1], 0, busReserve, 0, 6);
 
        int seatAvailable = Integer.parseInt(busReserve[4]);
        
        //Get amount of seat want to reserve
        do{
            System.out.print("\t\tHow many seat you want to reserve?\n\t\t=> ");
            
            try{
                amountSeat = input.nextInt();
            }catch(InputMismatchException ex){
                input.next();
            }
            
            if(amountSeat < 1 || amountSeat > seatAvailable)
                System.out.println("\t\tInvalid input. Try again");
            
        } while (amountSeat < 1 || amountSeat > seatAvailable);
        
        busReserve[4] = Integer.toString(amountSeat);//Store the amount seat users reserved
        busReserve[3] = Double.toString(Double.parseDouble(busReserve[3]) * amountSeat);//Store total price of all seats reserved
        System.out.println("\t\t** Successfully reserve bus No." + noReserve +"\n");
    }
    
    //This method sort the elements in array of bus info 
    public static void sortBusList(String[][] busInfo, int noBus)
    {   
        String[] sortOption = {"Sort by Price", "Sort by Departure Time"};
        int choice, indexColumn = -1, result = 0;
        
       do{
           System.out.println("");
           choice = displayOption(sortOption);
           
           if(choice == 1)//The user chose to sort by Price
               indexColumn = 3;
           else if (choice == 2)//The user chose to sort by departure time
               indexColumn = 5;
           
           if(choice < 1 || choice > 3)
               System.out.println("Invalid input!");

       }while(choice < 1 || choice > 3);
            //Sort array using Bubble Sort 
            for (int i = 0; i < noBus-1; i++)   
            {  
                for (int j = 0; j < noBus-i-1; j++)   
                {  
 
                    if(indexColumn == 3) //compare price
                    {
                        result = Integer.compare(Integer.parseInt(busInfo[j][indexColumn]),Integer.parseInt(busInfo[j+1][indexColumn]));
                    }
                    else if(indexColumn == 5) //compare departure time
                        result = busInfo[j][indexColumn].compareTo(busInfo[j+1][indexColumn]);
                
                    if (result > 0)   
                    {
                    String []tmp = new String[6];   
                    tmp = busInfo[j];  
                    busInfo[j] = busInfo[j+1];  
                    busInfo[j+1] = tmp;  
                    }  
                }   
            }      
    }
    
    // This method displays the bus info that user has reserved in a formatted output
    public static void displayBooking(String[][] booking, int noBooking)
    {
        System.out.println("=======================================================================================================");
        System.out.printf("%-4s %-14s %-18s %-20s %-7s %-16s %-7s \n","No.", "Departure Date","Destination","Bus Company","Price", "Amount Seat", "Departure time");
        System.out.println("=======================================================================================================");
        
        for(int i = 0; i < noBooking; i++)
        {
            System.out.printf("%-4s %-14s %-18s %-20s RM%-7s %-16s %-7s \n", i+1, booking[i][0], booking[i][1], booking[i][2],booking[i][3], booking[i][4], booking[i][5]);
        }
        System.out.println("=======================================================================================================\n");
    }
    
    //This method delete the record of bus Info that user want to cancel
    public static int deleteRecord(String[][]listRecord, int length, int indexDelete)
    {
        int index = 0;
        for(int i = 0; i < length; i++)
        {
            if((i+1) == indexDelete)//if bus info user want to delete found
                continue;//ignore that bus info
            //copy busInfo into listRecord
            System.arraycopy(listRecord, i, listRecord, index, i);
            index++;
        }
        return --length;
    }
    
    //This method display option of string array passed into optionList Then get input from user
    public static int displayOption(String[] optionList)
    {       
            Scanner input = new Scanner (System.in);
            int choice = -1;
            System.out.println("X--------------------------------------------------------------------------------------X");
            System.out.println("\t\tPlease select one of the following options :");
            System.out.print("X--------------------------------------------------------------------------------------X\n\t\t");

            for(int i = 0; i < optionList.length; i++)
            {
                System.out.print((i+1) + ". " + optionList[i] + "   ");
            }
            System.out.print("\n\t\t=> ");
            try{
                choice = input.nextInt();
            }catch(InputMismatchException ex){
                input.next();
            }

            System.out.println("X--------------------------------------------------------------------------------------X\n");
            return choice;
    }
}
