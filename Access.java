// Michael McKay 16324528

import java.util.Scanner;
import java.io.Writer;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

// eclpise accessing github
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.CommitService;

public class Access
{
  public static void main(String[] args) throws IOException
  {
    Scanner scanner = new Scanner(System.in);

    boolean x = false;
    
    GitHubClient client = new GitHubClient();  
    
    while (x == false)
    {
      x = true;
      client = logIn();
          
      try
      {
        
        System.out.println("Checking Verification For: " + client.getUser());
        
        UserService userService = new UserService(client);               
       
      }
       catch (RequestException exc)                                
      {
        
        if (exc.getMessage().endsWith("Bad credentials (401)"))         
        {
          System.out.println("Bad credentials (401)");                  
          x = false;
        }
      
      } 
    }
    
    JsonObjectBuilder jsobj0 = Json.createObjectBuilder();
    JsonObjectBuilder jsobj1 = Json.createObjectBuilder();
    JsonObjectBuilder jsobj2 = Json.createObjectBuilder();
    JsonObjectBuilder jsobj3 = Json.createObjectBuilder();
    
    RepositoryService service = new RepositoryService();
    
    try
    {
      int x = 25;
      
      for (Repository repo : service.getRepositories(client.getUser()))
      {
        jsobj2 = Json.createObjectBuilder();
        
        CommitService commitService = new CommitService(client);
        
        for (Collection<RepositoryCommit> commits : commitService.pageCommits(repo, x))
        {
          int y = 0;
          
          jsobj3 = Json.createObjectBuilder();
          
          for (RepositoryCommit commit : commits)
          {
            y++;
            
            jsobj2.add("COMMIT "+y, jsobj3.build());

            jsobj3.add("AUTHOR",  commit.getCommit().getAuthor().getName());
            jsobj3.add("DATE", ""+ commit.getCommit().getAuthor().getDate());
            jsobj3.add("Sha-1", commit.getSha().substring(0, 7));
            
             
          }         
          
        }
        
        jsobj1.add("theReponame", repo.getName());
        jsobj1.add("theCommits", jsobj2.build());
        
      }
      jsobj0.add("Repositorys", jsobj1.build());
    } 
    catch (IOException exc)
    {
      System.out.println("Error");
      exc.printStackTrace();
    }
   
    jsobj0.toString();
    
    Writer w = new FileWriter("Output.jsobj0");
    
    w.write(jsobj0.build().toString());
    
    System.out.println("Complete");
  }
  
  public static GitHubClient logIn()
  {
    Scanner scanner = new Scanner(System.in);
    String theUsername = "";
    String thePassword = "";
    GitHubClient client = new GitHubClient();
    
    System.out.print("Username: ");
    theUsername = scan.nextLine();
    
    System.out.print("Password: ");
    thePassword = scan.nextLine();
    
    client.setCredentials(theUsername, thePassword); 

    return client;
  }  
  
}