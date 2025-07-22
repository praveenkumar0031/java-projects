import java.util.*;
public class Account{
    String name;
    String acno;
    int acbal=0;
    Account(String n,String no){
        this.name=n;
        this.acno=no;
        System.out.println("--------------------\nAccount created !\n-------------------- \nAccount holder: "+n+"\nAccount number: "+no+"\nAccount balance: "+acbal);
    }

}
public class App{
    static boolean isalreadyexists(String an,ArrayList<Account> sb){
        if(sb.isEmpty())
            return false;
        for(Account b:sb){
            if(an.equals(b.acno))
                return true;
        }
        return false;
    }
    static void balance(Account b){
        
        System.out.println("---------------------------------------");   
        System.out.println("Account Holder: "+b.name+"\nAccount number: "+b.acno+"\nAccount balance: "+b.acbal);
        System.out.println("---------------------------------------");
        }
        
    
    
    static void withdraw(Account a,int amt){
    if(amt>0){
            if(a.acbal>=amt){
            a.acbal-=amt;
            System.out.println("Amount withdrawed successfully!");
            System.out.println("---------------------------------------");
            System.out.println("Account Holder:"+a.name+"\nAccount number: "+a.acno+"\nAccount balance: "+a.acbal);
            System.out.println("---------------------------------------");
            }
            else{
                System.out.println("Amount withdrawal failed!(insufficient Balance : "+a.acbal+") ");
                
                }          
    }
    else{
        System.out.println("Amount withdrawal failed! "+amt+" is not a valid amount");
    }
    }
    static void deposit(Account d,int amt){
    if(amt>0){
            d.acbal+=amt;
            System.out.println("Amount Deposited successfully!");
            System.out.println("---------------------------------------");
            System.out.println("Account Holder:"+d.name+"\nAccount number: "+d.acno+"\nAccount balance: "+d.acbal);
            System.out.println("---------------------------------------");
        }
    else{
        //throw new InvalidException("Amount is not a valid amount");
        System.out.println("Amount withdrawal failed! "+amt+" is not a valid amount");
    }
    }
public static void main(String[] args){
Scanner in = new Scanner(System.in);
String an;
int amt;
System.out.println("Bank management system");
System.out.print("___________________________________");
HashMap<String,Account> sb=new HashMap<String,Account>();
while(true){
    System.out.println("\nOpertaions:\n 1.create Account \n 2.view balance \n 3.withdraw amount \n 4.deposit amount \n 5.exit");
    System.out.print("Enter your choise: ");
    int ch=in.nextInt();
    switch(ch){
      case 1:
        System.out.print("Enter account holder name: ");
        String name=in.next();
        System.out.print("Enter account number: ");
        String ac=in.next();
        if(sb.containsKey(ac)){
            System.out.println("Account number not available!");
        }
        else{
        Account a=new Account(name,ac);
        sb.put(ac,a);
        }
        //System.out.print();
        break;
      case 2:
          System.out.print("Enter account number: ");
       an=in.next();
       if(!sb.containsKey(an)){
        System.out.println("Account not available!");
        }
        else{
       Account b=sb.get(an);
       balance(b);
        }
        break;
      case 3:
          System.out.print("Enter account number: ");
         an=in.next();
         System.out.print("Enter withdrawalamount: ");
         amt=in.nextInt();
         
         if(!sb.containsKey(an)){
            System.out.println("Account not available!");
          }
         else{
         Account wa=sb.get(an);
          withdraw(wa,amt);
         }
          break;
      case 4:
          System.out.print("Enter account number: ");
         an=in.next();
         System.out.print("Enter depositamount: ");
         amt=in.nextInt();
         if(!sb.containsKey(an)){
            System.out.println("Account not available!");
          }
         else{
            Account da=sb.get(an);
            deposit(da,amt);
            
         }
          break;
      case 5:
          System.out.println("exiting ....");
          System.out.println("Thanks for using");
          System.exit(0);
          break;
      default:
          System.out.print("Invalid choise");

      }
    }
    
  }
}
