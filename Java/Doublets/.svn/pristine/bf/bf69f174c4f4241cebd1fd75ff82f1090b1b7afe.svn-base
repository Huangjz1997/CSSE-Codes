import java.util.Scanner;
import java.util.Set;

/**
 * Contains the general algorithm to search for doublets.
 *
 * @author Yilun Wu and Knupp Jordan
 *         Created Mar 18, 2011.
 */
public class Doublets {
	private static String begin,end;
	private static String command;
	private static ChainManager manager;
	private static Links links;
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// print some introduction and select the dictionary 
		System.out.println("Welcome to Doublets, a game of 	\"verbal torture.\"");
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter dictionary(10,20,35,95): ");
        command=scanner.nextLine();
        while (!command.equals("10")&& !command.equals("20")&&!command.equals("35") &&!command.equals("95")){
            System.out.print("Wrong input. Enter dictionary(10,20,35,95: ");
            command=scanner.nextLine();
        }
        makelinks("english.cleaned.all."+command+".txt");
		while (true){
			init();
			if (links.contains(begin)&&(links.contains(end))) work();
			else System.out.println("The word is not valid. Please try again.");
		}
	}
	
	// find the solution 
	private static void work() {
		Chain chain;
		String word;
		while (true){
			// get next chain
			chain=manager.next();
			// if it is null so there is no solution 
			if (chain==null){
				System.out.println("No doublet chain exists from owner to bribe.");
				return;
			}
			// get the last word
			word=chain.getLast();
			// if we get the solution, output it
			if (word.equals(end)){
				System.out.println("Chain: "+chain.getList());
				System.out.println("Length: "+chain.length());
				System.out.println("Candidates: "+manager.getNumberOfNexts());
				System.out.println("Max size: "+manager.maxSize());
				return;
			}
			// get the available word of the last word in the chain and make new chain. 
			Set<String> set=links.getCandidates(word);
			if (set!=null){
				for (String k:set){
					Chain tmp=chain.addLast(k);
					if (!tmp.getLast().equals(word))manager.add(tmp);
				}
			}
			
		}
	
	}

	public static String getEnd(){
		return end;
	}
	private static void makelinks(String string){
		links=new Links(string);
	}
	
	
	/* input the begin end word and the command
	 * new class for manager 
	 *  
	 *  */
	
	@SuppressWarnings("resource")
	public static void init(){
        Scanner scanner = new Scanner(System.in);
		System.out.print("Please input the begin word:");
        begin= scanner.nextLine();
        System.out.print("Please input the end word:");
        end=scanner.nextLine();
        System.out.print("Enter chain manager (s: stack, q: queue, p: priority queue, x: exit):");
        command=scanner.nextLine();
        while (!command.equals("s")&& !command.equals("q")&&!command.equals("p") &&!command.equals("x")){
            System.out.print("Wrong input. Plese enter chain manager (s: stack, q: queue, p: priority queue, x: exit):");
            command=scanner.nextLine();
        }
        if (command.equals("x")) {
        	System.out.println("Goodbye");
        	System.exit(0);
        }
        Chain tmp=new Chain();
        tmp=tmp.addLast(begin);
        if (command.equals("s")) manager=new StackChainManager();
        if (command.equals("q")) manager=new QueueChainManager();
        if (command.equals("p")) manager=new PriorityQueueChainManager();
        manager.add(tmp);
	}
}
