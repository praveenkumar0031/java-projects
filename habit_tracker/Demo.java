class P{
    public void display(){
        System.out.println("from parents");
    }
}
class C extends P{
    // public C(){
    //     display();
    // }
    public void display(){
        super.display();
        System.out.println("from childs");
    }
}
public class Demo {
    public static void main(String[] args){
        C co=new C();
        co.display();
    }
}
