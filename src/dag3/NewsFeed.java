package dag3;

import java.sql.Time;
import java.util.ArrayList;

/**
 * The NewsFeed class stores news posts for the news feed in a
 * social network application.
 * 
 * Display of the posts is currently simulated by printing the
 * details to the terminal. (Later, this should display in a browser.)
 * 
 * This version does not save the data to disk, and it does not
 * provide any search or ordering functions.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 0.2
 */
public class NewsFeed
{
    private ArrayList<Post> posts;

    /**
     * Construct an empty news feed.
     */
    public NewsFeed(){
        posts = new ArrayList<>();
        test();
        printMessagePost();
        System.out.println();
        System.out.println();
        System.out.println();
        showUsingToString();
    }

    /**
     * Add a post to the news feed.
     * 
     * @param post  The post to be added.
     */
    public void addPost(Post post){
        posts.add(post);
    }

    /**
     * Show the news feed. Currently: print the news feed details
     * to the terminal. (To do: replace this later with display
     * in web browser.)
     */
    public void show(){
        // display all posts
        for(Post post : posts) {
            post.display();
            System.out.println();   // empty line between posts
        }
    }
    
    /**
     * Test method to add different posts and call show()
     */
    private void test(){
        
        MessagePost msgPost = new MessagePost("Klæbo", "Jeg vant gull!!!");
        posts.add(msgPost);
        PhotoPost photoPost = new PhotoPost("Northug", "c:/barneskirenn.jpg", "Gull er enkelt:)");
        posts.add(photoPost);
        Post post = new Post("Bjørgen");
        posts.add(post);
        /*
        MessagePost msg2 = new MessagePost("Jens", "Jeg vant 2!!!");
        MessagePost msg3 = new MessagePost("Jens", "Jeg vant 2!!!");

        
        System.out.println(msgPost.equals(msg2));
        System.out.println(msg2.equals(msg3));
        */
    }
    
    
    private void printMessagePost(){
        
        for(int i = 0; i < posts.size(); i++){
            if(posts.get(i) instanceof MessagePost){
                System.out.println(((MessagePost) posts.get(i)).getText());
            }
        }
        
    }
    
    private void showUsingToString(){
        for(int i = 0; i < posts.size(); i++){
            System.out.println(posts.get(i).toString());
            System.out.println();
        }
        show();
        
    }
    
    
}
