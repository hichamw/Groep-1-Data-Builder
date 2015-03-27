package defaults;
import java.sql.ResultSet;
import java.sql.SQLException;


public class recentTweets {
	
	public ResultSet extractTweetsFromDatabase(Database database) throws SQLException{
		String query = "SELECT Date, Content, TWITTER_USER_Username FROM message";
		ResultSet result = database.retrieveData(query);
		return result;




		}
		
		
	}


