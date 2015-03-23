import java.sql.SQLException;
import java.util.Scanner;

public class Controller {


	public static void main(String[] args) throws SQLException {
		Scanner menu = new Scanner(System.in);
		String menuOption;
		Data data = new Data();
		Client client = new Client();
		Database database = new Database();
		database.connectToDatabase();

		do{
			System.out.println("1 = Print all Languages");
			System.out.println("2 = Print all Hashtags ");
			System.out.println("3 = Close application");
			menuOption = menu.nextLine();
			if(menuOption.equals("1"))
			{
				data.extractLanguagesFromDatabase(database);
			}else if(menuOption.equals("2"))
			{
				client.extractHashtagsFromDatabase(database);
			}
			}while(menuOption.equals("3") == false);

	
	

      }
}
