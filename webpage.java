// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class webpage extends HttpServlet {
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
      // Set response content type
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      //fetch data from webpage
      String rname = request.getParameter("rname");
      String loc = request.getParameter("loc");

      //send to python code and get result
  	 String op=null;
      op = runPython(rname,loc,out);
     
      //redirect to another page with results
      String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
         
      out.println(docType +
         "<html>\n" +
        	"<body>"+
			"<a href='http://localhost/frontend.php?result="+op+"'>get result</a></p>"+
			"<p>success</p>"+
		"</body>\n" +
         "</html>"
      );
	//"<!--<meta http-equiv='refresh' content='2; URL=http://localhost/frontend.php?result="+op+"'>-->"+
   }

   //runs python code on terminal and reads op of terminal
   public String runPython(String rname, String loc,PrintWriter out) {
	   String s = null;
	   String result = " ";
           //PrintWriter out = response.getWriter();
	   try {
	    
		    // run the Unix "ps -ef" command
		    // using the Runtime exec method:
		    Process p;
		    //p1 = Runtime.getRuntime().exec("cd home/lenovo");
		    p = Runtime.getRuntime().exec("python naivebayes.py "+rname);    
		    BufferedReader stdInput = new BufferedReader(new 
			 InputStreamReader(p.getInputStream()));

		    BufferedReader stdError = new BufferedReader(new 
			 InputStreamReader(p.getErrorStream()));

		    // read the output from the command
		    
		    if((s = stdInput.readLine()) != null){
			    //out.println("Here is the standard output of the command:\n");
			    while (s != null) {
				//out.println(s);
				result=result.concat(" "+s);
				s = stdInput.readLine();
			    }
			    return result;
		    }
		    // read any errors from the attempted command
		    else{
			    out.println("Here is the standard error of the command (if any):\n");
			    while ((s = stdError.readLine()) != null) {
				out.println(s);
			
			    }
			    return "CodeError";
    		    }
		    //System.exit(0);
	    }
	    catch (IOException e) {
		    System.out.println("exception happened - here's what I know: ");
		    e.printStackTrace();
		    System.exit(-1);
		    return "ExecError";
	}
	  // return null;
  }
}

