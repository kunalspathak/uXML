package parserOfL;
 
import java.io.File;
 
public class LLikeRegression {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[2];
    	// File (or directory) with old name
		File oldFile = new File("src"+File.separator+"Regression.old.txt");
        oldFile.delete();
        // File (or directory) with new name
        File newFile = new File("src"+File.separator+"Regression.new.txt");
        
        if(!newFile.renameTo(oldFile))
        	System.err.println("Coundn't rename the file!!");
        
        args[1] = "src"+File.separator+"Regression.new.txt";
		for(int i = 1;i <= 9;i++) {
			args[0] = String.valueOf(i);
			System.out.println("_________________" + args[0] + ".Llike_________________");
			try {
				L.main(args);
			} catch(RuntimeException e) 
			{
				System.err.println(e.getMessage());
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}
	}
 
}