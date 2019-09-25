// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

// Extend HttpServlet class
public class review extends HttpServlet {
 
static Cluster cluster;
static Session session;
static ResultSet results;
static Row rows;
static int count;
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
      // Set response content type
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      //fetch data from webpage
      String rname = request.getParameter("rname");
      String review = request.getParameter("review");
      String status = null;
      //connect to cassandra
	try
	{
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect();
		//out.println("connected");
		//session.execute("CREATE KEYSPACE IF NOT EXISTS project WITH replication " + "= 								{'class':'SimpleStrategy','replication_factor':1}; ");
		session.execute("USE project");
		//session.execute("CREATE TABLE IF NOT EXISTS testdata (no int PRIMARY KEY,restaurant text, review text, stars int );");
		results = session.execute("select max(no) from testdata");
		//out.println(results);
		
		for(Row row : results){
			
			count = row.getInt("system.max(no)");
		}
			 //row.getInt("count");
		count = count + 1;
		//out.println(row.getInt("count"));
		session.execute("INSERT INTO testdata (no,restaurant, review, stars) VALUES ("+count+",'"+rname+"','"+review+"',1)");
		//session.execute("INSERT INTO testdata (no,restaurant, review, stars) VALUES ("+count+",'"+rname+"','"+review+"',1)");
		status="success";
	}
       catch(Exception e)
	{
	   out.println("Error: "+e.getMessage());
	   status = "failed";
	}
	out.println(status+"\n");
	cluster.close();
	
      //redirect to another page with results
      String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
         
      out.println(docType +
         "<html>\n" +
        	"<body>"+
			"<a href='http://localhost/frontend.php?status="+status+"'>Go Back</a></p>"+
			
		"</body>\n" +
         "</html>"
      );
	//"<!--<meta http-equiv='refresh' content='2; URL=http://localhost/frontend.php?result="+op+"'>-->"+
   }

  
}

