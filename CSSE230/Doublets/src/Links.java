import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Links {
	private ArrayList<String> list;
	private HashMap<String, HashSet<String>> map;
	
	// init. build list and map. List is the whole words in the dictionary.
	// map contains lists of words have relationship with the key.
	// O(n^2) to build the map
	public Links(String string) {
		list=new ArrayList<String>();
		map=new HashMap<String, HashSet<String>>();
		this.readFileByLines(string);
		for (String i:list){
			HashSet<String> set=new HashSet<String>();
			for(String j:list){
				if (i.length()!=j.length()) continue;
				if (this.judge(i,j)) set.add(j);
			}
			map.put(i,set);
		}
		
	}
	
	// return true if s1 and s2 have and only have one difference
    private boolean judge(String s1, String s2) {
    	int tmp=0;
		for (int i=0;i<s1.length();i++){
			if (s1.charAt(i)!=s2.charAt(i)) tmp++;
			if (tmp>1) return false;
		}
		return tmp==1;
	}
    
    
    // read the dictionary.
	public void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	list.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
	
    public boolean contains(String string){
    	if (!map.containsKey(string)) return false;
    	return true;
    }
    
    // return the list by given word as key.
	public Set<String> getCandidates(String string) {
		if (!map.containsKey(string)) return null;
		Set<String> tmp=this.map.get(string);
		if (tmp.size()==0) return null;
		return tmp;
	}

}
