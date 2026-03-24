public class Menu{
	List<HashMap<String,Integer>> hmenu=new ArrayList<>();
	public Menu(){
		for(int i=0;i<4;i++){
	HashMap<String,Integer> hm=new HashMap<>();
	hm.put("Idle",15);
	hm.put("Dosa",65);
	hm.put("Vada",12);
	hm.put("Poori",45);
	hm.put("Parata",25);
	hmenu.add(hm);
		}
	}
	void viewMenu(int n){
		if(n>0&&n<=hmenu.size()){
		System.out.println(hotels.get(n)+ " Hotel Menu");
		HashMap<String,Integer> hm=hmenu.get(n);
		for(Map.Entry<String,Integer> e:hm.entrySet()){
			System.out.println(e.getKey()+" "+e.getValue());
		}
		}
	}
}